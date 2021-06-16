package com.example.xmlparsing;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

public class PublicDataActivity extends AppCompatActivity {

    // 미세먼지 경보 공공데이터 xml 파싱 실습
    RecyclerView recyclerView;
    TextView tvTotalCnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_data);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String serviceUrl = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson";
        String serviceKey = "?serviceKey=%2F6YC05LX402Mq1%2Beg8O2m74njrcI2N0h7Km4BNbneOT0MOJlEhWA7Zj7y5c0NrBO2PYPPHWjGBfMFEk6731aug%3D%3D";
        String numOfRows = "&numOfRows=30";
        String pageNo = "&pageNo=1";
        String startCreate ="&startCreateDt=20210421";
        String endCreate = "&endCreateDt=20210521";
        String urlBuilder = serviceUrl +  serviceKey + numOfRows + pageNo+startCreate+endCreate;


        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                urlBuilder,
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
        recyclerView = (RecyclerView)findViewById(R.id.rc_public);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Covid19> list= new ArrayList<>();

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(data));

            int eventType = xpp.getEventType();
            boolean seqFalg=false,decideFalg=false,dateFalg=false;
            String tmpSeq = "",tmpDecide="",tmpDate = "";
            int count =0;
            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_TAG){
                    if(xpp.getName().equals("seq")) seqFalg = true;
                    if(xpp.getName().equals("decideCnt")) decideFalg = true;
                    if(xpp.getName().equals("createDt")) dateFalg = true;
                }else if(eventType == XmlPullParser.TEXT){
                    if(seqFalg){
//                      tvTotalCnt.append("\n지역 : " + xpp.getText());
                        tmpSeq = xpp.getText();
                        Log.e("XPP",""+1);
                        seqFalg = false;
                        count ++;
                    }
                    else if(decideFalg){
                        tmpDecide = xpp.getText();
                        Log.e("XPP",""+2);
                        decideFalg = false;
                        count ++;
                    }
                    else if(dateFalg){
                        tmpDate = xpp.getText();
                        Log.e("XPP",""+3);
                        dateFalg = false;
                        count ++;
                    }
                }


                if(count == 3){
                    list.add(new Covid19(tmpDecide,tmpSeq,tmpDate));
                    count = 0;
                }

                eventType = xpp.next();
            } // end of while
        }catch (Exception e){

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Covid19Adapter(list));
    } // end of parsingData()

}
