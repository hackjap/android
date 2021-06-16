package com.example.googlemapexam;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

// README
/*
 *  getMapAsync()
 *  run(map) : 아래 기능 실행
 *      locationServices : 위치에 따른 위도,경도,날씨 값 리턴
 *      geocoderServices : 위치에 따른 주소 값 리턴
 *      markerServices : 마커 생성
 *      makeRequest : Volley 통신 후 url의 string 을 반환
 */
public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {

    SupportMapFragment mapFragment;
    GoogleMap map;
    TextView tvAddress, tvLatlng, tvWeather;
    Button btn;
    LatLng latLng;
    Double latitude; //위도
    Double longitude;  //경도
    Location location;

    String url; // 최종 url
    String defaultUrl; // 기본 url
    String myAPIKey; // api 키
    String result; // parsing 결과
    String address; // 주소

    //volley
    RequestQueue requestQueue;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvLatlng = (TextView) findViewById(R.id.tv_latlng);
        tvWeather = (TextView) findViewById(R.id.tv_weather);
        defaultUrl = "http://api.openweathermap.org/data/2.5/weather?lat=";
        myAPIKey = "a20a8c757607016c465189248c9a61c9";


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        AutoPermissions.Companion.loadAllPermissions(this, 101); // auto permission




        // 구글맵 비동기 로드
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                run(googleMap);

            }
        });

        // 현재 위치 정보 가져오기 버튼 동작
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run(map);   // 프로세스 실행
            }
        });

    } // end of onCreate


    public void run(GoogleMap googlemap) {


        locationServices();
        geocoderServices(latLng);
        makeRequest();
        markerService(googlemap, latLng);


    }


    private void makeRequest() {
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // tvWeather.setText("날씨 : \n"+response);
                        // locationServices();

                        setJason(response);
                        Log.e("makeRequeest() : ", result);
                        tvWeather.setText(result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvWeather.setText(error.getMessage());
                    }
                }
        ); // end of stringRequest
        requestQueue.add(stringRequest);

    }

    public void setJason(String response) {

        try {
            Log.e("JSonData : ", "33333333333333333333333333333333");
            Log.e("getJason : ", response);

            JSONObject weatherObject = new JSONObject(response);
            JSONArray weatherArray = weatherObject.getJSONArray("weather");
            String weatherArrayIndex = weatherArray.getString(0);
            JSONObject wObject = new JSONObject(weatherArrayIndex);
            String weather = wObject.getString("main");

            switch (weather) {
                case "Clear":
                    weather = "맑음";
                    break;
                case "Rain":
                    weather = "비";
                    break;
                case "Clouds":
                    weather = "구름";
                    break;
                case "Mist":
                    weather = "안개";
                    break;
                default :
                    break;
            }
            result = "날씨 : " + weather;


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void markerService(GoogleMap googleMap, LatLng latLng) {
        map = googleMap;
        // Marker
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng,1));
        //googleMap.moveCamera(CameraUpdateFactory.zoomTo(17);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(address);
        googleMap.addMarker(markerOptions);
    }

    public void geocoderServices(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {
            List<Address> list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 8);
            //geocoder.
            if (list != null) {
                if (list.size() == 0) {
                    // tv.setText("주소 정보 없음");
                } else {
                    address = list.get(0).getAddressLine(0).toString();
                    tvAddress.setText("주소 : " + address);
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void locationServices() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                latLng = new LatLng(latitude, longitude);
                tvLatlng.setText("위도 : " + latitude + "\n경도 : " + longitude);
            }
        } catch (SecurityException e) {
            tvLatlng.setText(e.getMessage());
        }

        GPSListener gpsListener = new GPSListener();
        long minTime = 5000;//5초
        float minDistance = 0;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
        url = defaultUrl + latitude + "&lon=" + longitude + "&appid=" + myAPIKey;
        Log.e("Location : ", url);
    }

    class GPSListener implements LocationListener {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            // 실습을 위해 실시간이 아닌 버튼을 눌러야 동작할 수 있게 설정하겠음.
            // latitude = location.getLatitude();
            // longitude = location.getLongitude();
            //tvLatlng.setText("위도:"+latitude+"\n경도:"+longitude);

        }
    }


    @Override
    public void onDenied(int i, String[] strings) {

    }

    @Override
    public void onGranted(int i, String[] strings) {

    }
}
