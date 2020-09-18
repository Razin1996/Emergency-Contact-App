package com.example.e_contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Maps_new extends AppCompatActivity {
    private GoogleMap map;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient client;
    double currentLat = 0, currentLong = 0;

    LocationManager lm;
    LocationListener locationListener;
    Location mLoc;
    int code;
    boolean zoomb = true;
    ArrayList<String> ambulances = new ArrayList<String>();
    boolean directioncheck = false;
    // LIST VIEW
    ListView listview;
    ArrayList<String> itemlist = new ArrayList<String>();
    ArrayList<Marker> markerlist;

    Direction direction = new Direction();
    ArrayList<LatLng> pline = new ArrayList<LatLng>();
    LatLng currentmarker;
    Integer cid;

    String provider = "";

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        myMap = googleMap;
//
//        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_new);

        listview = (ListView) findViewById(R.id.listView);
//        listview.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, itemlist));

        // [START maps_current_place_map_fragment]
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //initialize fused location
        client = LocationServices.getFusedLocationProviderClient(this);

        //check permission
        if (ActivityCompat.checkSelfPermission(Maps_new.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            //when permission denied
            ActivityCompat.requestPermissions(Maps_new.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position,
//            long id) {
//            //Toast.makeText(getApplicationContext(), Integer.toString(markerlist.size()), Toast.LENGTH_LONG).show();
//            LatLng point = markerlist.get(position).getPosition();
//            CameraUpdate center = CameraUpdateFactory.newLatLng(point);
//            map.animateCamera(center, 2000, null);
//        }
//        });
    }


    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if (location != null) {
                    //initialize Lat Lng
                    currentLat = location.getLatitude();
                    currentLong = location.getLongitude();
                    //Sync map
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            map = googleMap;
                            //zoom map
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(currentLat,currentLong), 10));
//                            //create marker option
//                            MarkerOptions options = new MarkerOptions().position(currentLat,currentLong)
//                                    .title("I am here");
//
//                            //add marker on map
//                            map.addMarker(options);

                        }
                    });

                        }
                }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 44){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //when permission granted
                //call method
                getCurrentLocation();
            }
        }
    }


}


