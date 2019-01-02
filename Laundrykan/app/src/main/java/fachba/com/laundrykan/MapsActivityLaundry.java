package fachba.com.laundrykan;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import fachba.com.laundrykan.Model.LaundryNew;

public class MapsActivityLaundry extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView tvlt,tvln,tvn,tvo,tval;
    private Button btn;
    protected String nm,jo,lt,ln;
    private Geocoder geocoder;
    private List<Address> addresses;
    private static final String TAG = MapsActivityLaundry.class.getSimpleName();
    private SharedPreferences mPrefs;
    private static final String PREF_NAME = TAG+"_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_laundry);
        tvlt=findViewById(R.id.textView13);
        tvln=findViewById(R.id.textView12);
        tvn=findViewById(R.id.textView11);
        tval=findViewById(R.id.textView10);
        tvo=findViewById(R.id.textView14);
        btn=findViewById(R.id.button);

        mPrefs = getSharedPreferences(PREF_NAME,MODE_PRIVATE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();

                String laundrystring = getIntent().getStringExtra("laundry");
                LaundryNew laundryintent = gson.fromJson(laundrystring,LaundryNew.class);

                String json = gson.toJson(laundryintent);
                prefsEditor.putString("MyObject", json);
                prefsEditor.commit();

                Toast.makeText(MapsActivityLaundry.this, "Laundry Difavoritkan akan muncul setelah aplikasi restart", Toast.LENGTH_SHORT).show();

            }
        });



        Gson gson = new Gson();
        String laundrystring = getIntent().getStringExtra("laundry");
        LaundryNew laundryintent = gson.fromJson(laundrystring,LaundryNew.class);
        nm=laundryintent.getNama();
        jo=laundryintent.getJamoperasi();
        lt=laundryintent.getLati();
        ln=laundryintent.getLongi();

        double latitude= Double.valueOf(ln);
        double longitude = Double.valueOf(lt);
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0);
        String postalCode = addresses.get(0).getPostalCode();

        tvlt.setText("Latitude : "+ln);
        tvln.setText("Longitude : "+lt);
        tvn.setText(jo);
        tvo.setText(nm);

        tval.setText(address);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        double nlt = Double.valueOf(lt);
        double nln = Double.valueOf(ln);

        // Add a marker and move the camera
        LatLng laundrymap = new LatLng(nln, nlt);
        mMap.addMarker(new MarkerOptions().position(laundrymap).title(nm));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(laundrymap));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(laundrymap,14.0f));
    }
}
