package com.example.jsonparsing;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> data;
    ArrayList<String> str;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView)findViewById(R.id.tv1);
        String jsonData = "{\"culturalSpaceInfo\":{\"list_total_count\":930,\"RESULT\":{\"CODE\":\"INFO-000\",\"MESSAGE\":\"정상 처리되었습니다\"},\"row\":[{\"NUM\":\"1\",\"SUBJCODE\":\"미술관\",\"FAC_NAME\":\"김영옥 미술관\",\"ADDR\":\"서울특별시 종로구 창의문로10길 11\",\"X_COORD\":\"37.5938781052674\",\"Y_COORD\":\"126.964253937947\",\"PHNE\":\"02-391-1218\",\"FAX\":\"\",\"HOMEPAGE\":\"www.magmasup.com\",\"OPENHOUR\":\"\",\"ENTR_FEE\":\"\",\"CLOSEDAY\":\"없음\",\"OPEN_DAY\":\"\",\"SEAT_CNT\":\"\",\"MAIN_IMG\":\"HTTP://CULTURE.SEOUL.GO.KR/data/cf/20210416152219.jpg\",\"ETC_DESC\":\"\",\"FAC_DESC\":\"\",\"ENTRFREE\":\"무료\",\"SUBWAY\":\"\",\"BUSSTOP\":\"자하문터널입구 석파정\",\"YELLOW\":\"\",\"GREEN\":\"1711, 7016, 7018, 1020, 7211\",\"BLUE\":\"\",\"RED\":\"\",\"AIRPORT\":\"\"},{\"NUM\":\"2\",\"SUBJCODE\":\"공연장\",\"FAC_NAME\":\"노원어린이극장\",\"ADDR\":\"서울특별시 노원구 노해로 502 지하1층\",\"X_COORD\":\"37.6544500497108\",\"Y_COORD\":\"127.063692307584\",\"PHNE\":\"02-2289-343\",\"FAX\":\"\",\"HOMEPAGE\":\"https://www.nowonarts.kr/html/\",\"OPENHOUR\":\"\",\"ENTR_FEE\":\"\",\"CLOSEDAY\":\"공연에 따라 상이\",\"OPEN_DAY\":\"\",\"SEAT_CNT\":\"\",\"MAIN_IMG\":\"HTTP://CULTURE.SEOUL.GO.KR/data/cf/20210412160831.jpg\",\"ETC_DESC\":\"\",\"FAC_DESC\":\"\",\"ENTRFREE\":\"무료\",\"SUBWAY\":\"4,7호선 노원역\",\"BUSSTOP\":\"\",\"YELLOW\":\"\",\"GREEN\":\"\",\"BLUE\":\"\",\"RED\":\"\",\"AIRPORT\":\"\"}]}}";



        data = new ArrayList<>();
        str = new ArrayList<>();

        String result="";

       // String name = culture.getString("FAC_NAME");
        //String addr = culture.getString("ADDR");

        try{

            JSONObject jsonObject = new JSONObject(jsonData);
            String culturalSpaceInfo = jsonObject.getString("culturalSpaceInfo");
            JSONObject subObject = new JSONObject(culturalSpaceInfo);

            String row = subObject.getString("row");
            JSONArray jsonArray = new JSONArray(row);

            for(int i=0;i<jsonArray.length();i++){
                String index = jsonArray.getString(i);
                JSONObject culture = new JSONObject(index);
                String name = culture.getString("FAC_NAME");
                String addr = culture.getString("ADDR");
                data.add(new Item(name,addr));
                str.add(name);



            }
            result += "name : "+ data.get(1).getName() + "ADDR : "+data.get(1).getAddr();
            for(int i=0;i<data.size();i++){
                Log.e("data : ",data.get(i).getAddr());
            }

            tv1.setText(result);


        }catch(JSONException e){
            e.printStackTrace();
        }

    }
}
