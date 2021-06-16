package com.example.location_exam1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {

    TextView tv; Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.textView);
        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationService();

            }
        });
        AutoPermissions.Companion.loadAllPermissions(this,101); // 자동으로 위치 기록함


    }

    private void locationService() {
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try{
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location!=null){
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                tv.setText("위도 : "+String.format("%2f",latitude)+ " 경도 : "+String.format("%2f",longitude)+"\n");
                Log.e("onclick","onclick");
            }
        }catch (SecurityException e){
            tv.setText(e.getMessage());

        }
        GPSListener gpsListener = new GPSListener();
        long minTime = 20000;
        float minDistance=0;
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED){
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,minTime,minDistance,gpsListener);

    }



    class GPSListener implements LocationListener{
        @Override
        public void onLocationChanged(@NonNull Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            tv.append("위도 : "+String.format("%2f",latitude)+ " 경도 : "+String.format("%2f",longitude)+"\n");
        }
    }
    @Override
    public void onDenied(int i, String[] strings) {

    }
    @Override
    public void onGranted(int i, String[] strings) {

    }



}
