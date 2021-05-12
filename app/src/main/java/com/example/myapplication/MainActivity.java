package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;

public class MainActivity extends AppCompatActivity {

    String originLatitude = "30.191819789648623";
    String originLongitude = "71.4434906262873";
    String destinationLatitude = "30.17590443756386";
    String destinationLongitude = "71.46563628556687";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDistanceAndTime(originLatitude, originLongitude, destinationLatitude, destinationLongitude);
    }

    public void getDistanceAndTime(String originLatitude, String originLongitude, String destinationLatitude, String destinationLongitude){

        HttpRequest request = new HttpRequest();
        request.setOnResponseListener(response -> {
            if (response.code == HttpResponse.HTTP_OK) {

                try {
                    String distance = response.toJSONObject().getJSONArray("routes").
                            getJSONObject(0).getJSONArray("legs").
                            getJSONObject(0).getJSONObject("distance").
                            get("text").toString().trim();
                    String distanceString = distance.substring(0,distance.length()-2);
                    double distanceDouble = Double.parseDouble(distanceString);
                    System.out.println("This is "+distance+"   "+ distanceString+"   "+ distanceDouble);

                    String duration = response.toJSONObject().getJSONArray("routes").
                            getJSONObject(0).getJSONArray("legs").
                            getJSONObject(0).getJSONObject("duration").
                            get("text").toString().trim();
                    String durationString = duration.substring(0,duration.length()-4);
                    double durationDouble = Double.parseDouble(durationString);
                    System.out.println("This  "+duration+"   "+durationString +"  "+durationDouble);
                } catch (JSONException e) {
                    System.out.println("This is catch"+ e);
                    e.printStackTrace();
                }
            }
        });
        request.setOnErrorListener(error -> {
            System.out.println("This is error "+ error);
            // There was an error, deal with it
        });
        request.get("https://maps.googleapis.com/maps/api/directions/json?origin="+originLatitude+", "+originLongitude+"&destination="+destinationLatitude+", "+destinationLongitude+"&key=AIzaSyBnZOsXK4C0GDjsYSxG3cMc73A7_sfGM_8&mode=driving&sensor=false&mode=driving");

    }
}