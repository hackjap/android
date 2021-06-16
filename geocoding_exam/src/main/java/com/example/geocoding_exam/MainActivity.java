package com.example.geocoding_exam;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    GoogleMap map;
    TextView tv;
    MarkerOptions markerOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.textView);
        supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                tv.setText("지도로드");
                map = googleMap;
                LatLng curPoint = new LatLng(37.5841,126.9251);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint,14));

                // 이미지 리사이즈
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),getResources()
                        .getIdentifier("m1","drawable",getPackageName()));
                bitmap = Bitmap.createScaledBitmap(bitmap,70,100,false);
                // 마커 표시
                markerOptions = new MarkerOptions();
                markerOptions.position(curPoint)
                        .title("명지 전문대")
                        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.m1));
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                map.addMarker(markerOptions);

                // geocoding
                Geocoder geocoder = new Geocoder(getApplicationContext());

                try{
                    List <Address> list = geocoder.getFromLocation(curPoint.latitude,curPoint.longitude,10);
                    if(list == null)
                        tv.setText("주소 정보 없음");
                    else{
                        tv.setText(list.get(0).getCountryName());
                        //tv.setText(list.get(0).getAddressLine(0).toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
