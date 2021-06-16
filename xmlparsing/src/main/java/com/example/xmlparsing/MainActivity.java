package com.example.xmlparsing;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv1 = (TextView)findViewById(R.id.text_xml1);
        TextView tv2 = (TextView)findViewById(R.id.text_xml2);
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = factory.newPullParser();


            String xmlString ="<member><name>kim</name><name>jang</name><pwd>1234</pwd></member>";
            xmlPullParser.setInput(new StringReader(xmlString));

            tv1.append("\n" + xmlString);
            int eventType = xmlPullParser.getEventType();
            boolean nameFlag = false, pwdFlag = false;
            while(eventType != xmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_TAG){
                    if(xmlPullParser.getName().equals("name"))nameFlag=true;
                    if(xmlPullParser.getName().equals("pwd"))pwdFlag=true;
                }else if(eventType == XmlPullParser.TEXT){
                    if(nameFlag){
                        tv2.append("\nname:"+ xmlPullParser.getText());
                        nameFlag = false;
                    }
                    if(pwdFlag){
                        tv2.append(("\npwd : " + xmlPullParser.getText()));
                        pwdFlag = false;
                    }
                }
                eventType = xmlPullParser.next();
            }

        }catch (Exception e){

        }
    }
}
