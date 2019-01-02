package fachba.com.laundrykan;

import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, RoutingListener {

    private GoogleMap mMap;

    double latStart,latEnd,lngStart,lngEnd;

    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvInfo=findViewById(R.id.textView8);

        latStart =-7.939811;
        lngStart =112.624592;

        latEnd =-7.9797;
        lngEnd =112.6304;

        Location locationA = new Location("point A");

        locationA.setLatitude(latStart);
        locationA.setLongitude(lngStart);

        Location locationB = new Location("point B");

        locationB.setLatitude(latEnd);
        locationB.setLongitude(lngEnd);

        float distance = locationA.distanceTo(locationB);

        tvInfo.setText(String.valueOf(distance));

        LatLng start = new LatLng(latStart, lngStart);
        LatLng end = new LatLng(latEnd, lngEnd);

        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .key("key-direction-api")
                .waypoints(start, end)
                .withListener(this)
                .build();
        routing.execute();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        latEnd =-7.9797;
        lngEnd =112.6304;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latEnd,  lngEnd);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Malang"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,14.0f));


    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> routes, int i) {
        Log.d("tag", "routing success " + routes.size());

        mMap.addMarker(new MarkerOptions().position(new LatLng(latStart, lngStart)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(latEnd, lngEnd)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latStart, lngStart), 14));

        String txtInfo = "";

        for (Route data : routes) {
            // draw polyline
            Log.d("tag", "write polyline " + data.getDistanceText());
            txtInfo += data.getDistanceText() + " " + data.getDurationText();

            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.width(10);
            polylineOptions.color(Color.RED);
            polylineOptions.addAll(data.getPoints());
            mMap.addPolyline(polylineOptions);
        }

        tvInfo.setText(txtInfo);
    }

    @Override
    public void onRoutingCancelled() {

    }
}
