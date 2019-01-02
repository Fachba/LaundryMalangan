package fachba.com.laundrykan.Fragment;

import android.arch.lifecycle.ViewModelStore;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fachba.com.laundrykan.MapsActivityLaundry;
import fachba.com.laundrykan.Model.LaundryNew;
import fachba.com.laundrykan.R;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private View view;
    MapView mMapView;
    private GoogleMap mMap;

    private static final String TAG = MapsActivityLaundry.class.getSimpleName();
    private SharedPreferences mPrefs;
    private static final String PREF_NAME = TAG+"_prefs";
    double lt,ln;
    String nm;

    public MapsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mPrefs = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("MyObject", "");
        LaundryNew obj = gson.fromJson(json, LaundryNew.class);
        lt=Double.valueOf(obj.getLati());
        ln=Double.valueOf(obj.getLongi());
        nm=obj.getNama();

        view = inflater.inflate(R.layout.fragment_maps,container,false);

        mMapView = (MapView) view.findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        return view;
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return super.getViewModelStore();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double longi=ln;
        double lati=lt;

        // Add a marker and move the camera
        LatLng laundrymaps = new LatLng(longi,  lati);
        mMap.addMarker(new MarkerOptions().position(laundrymaps).title(nm));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(laundrymaps));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(laundrymaps,14.0f));

    }
}
