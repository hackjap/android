package com.example.xmlparsing;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class XMLParsingRecyclerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FineDustAdapter fineDustAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xmlparsing_recycler);




//        list.add(new FineDust("서울","100","2021-05-20"));
//        list.add(new FineDust("부산","200","2021-05-14"));



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/g" +
                "etUlfptcaAlarmInfo?serviceKey=%2F6YC05LX402Mq1%2Beg8O2m74njrcI2N0h7Km4BNbneOT0MOJlEhWA7Zj7y5c0NrBO2PYPPHWjGBf" +
                "MFEk6731aug%3D%3D&returnType=xml&numOfRows=10&pageNo=1&year=2021";

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parsingData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(stringRequest);

    }


    public void parsingData(String data){
        recyclerView = (RecyclerView)findViewById(R.id.rc_xml);
        ArrayList<FineDust> list= new ArrayList<>();
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(data));

            int eventType = xpp.getEventType();
            boolean dataFalg=false,cityFalg=false,issueValFalg=false;
            String tmpDistrict = "",tmpData="",tmpValue = "";
            int count =0;
            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_TAG){
                    if(xpp.getName().equals("districtName")) cityFalg = true;
                    if(xpp.getName().equals("issueDate")) dataFalg = true;
                    if(xpp.getName().equals("issueVal")) issueValFalg = true;
                }else if(eventType == XmlPullParser.TEXT){
                    if(cityFalg){
//                      tvTotalCnt.append("\n지역 : " + xpp.getText());
                        tmpDistrict = xpp.getText();
                        Log.e("XPP",""+1);
                        cityFalg = false;
                        count ++;
                    }
                    else if(dataFalg){
                        tmpData = xpp.getText();
                        Log.e("XPP",""+2);
                        dataFalg = false;
                        count ++;
                    }
                    else if(issueValFalg){
                        tmpValue = xpp.getText();
                        Log.e("XPP",""+3);
                        issueValFalg = false;
                        count ++;
                    }
                }


                if(count == 3){
                    list.add(new FineDust(tmpDistrict,tmpValue,tmpData));
                    count =0;
                }


                /*if( cityFalg  && dataFalg && issueValFalg){
                    list.add(new FineDust(tmpDistrict,tmpValue,tmpData));
                    cityFalg =false;
                    dataFalg =false;
                    issueValFalg =false;
                }

                 */
                eventType = xpp.next();
            } // end of while
        }catch (Exception e){

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FineDustAdapter(list));
    } // end of parsingData()
}
