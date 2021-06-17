package com.example.xmlparsing;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class ExerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tvTotalCnt;
    ArrayList<Covid19> list;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_data);

        // recyclerview setting
        recyclerView = (RecyclerView)findViewById(R.id.rc_public);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        recyclerView.setAdapter(new Covid19Adapter(list,this));
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, @NonNull MotionEvent e) {
                View v = rv.findChildViewUnder(e.getX(),e.getY());
                int position = rv.getChildAdapterPosition(v);
                Toast.makeText(getApplicationContext(),list.get(position).getDecideCNT(),Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        // public url
        String serviceUrl = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson";
        String serviceKey = "?serviceKey=%2F6YC05LX402Mq1%2Beg8O2m74njrcI2N0h7Km4BNbneOT0MOJlEhWA7Zj7y5c0NrBO2PYPPHWjGBfMFEk6731aug%3D%3D";
        String numOfRows = "&numOfRows=30";
        String pageNo = "&pageNo=1";
        String startCreate ="&startCreateDt=20210421";
        String endCreate = "&endCreateDt=20210521";
        String urlBuilder = serviceUrl +  serviceKey + numOfRows + pageNo+startCreate+endCreate;

        // Volley ( requestque, stringrequest, requestque(stringrequest) )
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // StringRequest(GET,url,response,error)
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
                }
        );

        requestQueue.add(stringRequest);

    }

    // xml parsing
    public void parsingData(String data){

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(data));

            int eventType = xpp.getEventType();
            boolean seqFalg = false, decideFalg = false, dataFalg = false;
            String tmpSeq = "",  tmpDecide="", tmpDate ="";
            int count = 0;

            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_TAG){
                    if(xpp.getName().equals("seq")) seqFalg = true;
                    if(xpp.getName().equals("decideCnt")) decideFalg = true;
                    if(xpp.getName().equals("createDt")) dataFalg = true;
                }else if(eventType == XmlPullParser.TEXT){
                    if(seqFalg){
                        tmpSeq = xpp.getText();
                        seqFalg = false;
                        count ++;
                    }
                    else if(decideFalg){
                        tmpDecide = xpp.getText();
                        decideFalg = false;
                        count ++;
                    }else if(dataFalg){
                        tmpDate = xpp.getText();
                        dataFalg = false;
                        count ++;
                    }else{
                        //
                    }
                }
                if( count == 3){
                    list.add(new Covid19(tmpDecide,tmpSeq,tmpDate));
                    count = 0;
                }
                eventType = xpp.next();
            }

        }catch (Exception e){

        }

    } // end of parsingData()
}
