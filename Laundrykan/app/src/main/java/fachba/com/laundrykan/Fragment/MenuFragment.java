package fachba.com.laundrykan.Fragment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.DeflaterOutputStream;

import fachba.com.laundrykan.Adapter.LaundryAdapter;
import fachba.com.laundrykan.Adapter.LaundryViewHolder;
import fachba.com.laundrykan.DetailLaundry;
import fachba.com.laundrykan.MapsActivityLaundry;
import fachba.com.laundrykan.Model.LaundryNew;
import fachba.com.laundrykan.R;

public class MenuFragment extends Fragment {

    private View view;
    private RecyclerView mrecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    //private RecyclerView.Adapter mAdapter;
    private List<LaundryNew> myLaundry = new ArrayList<>();
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<LaundryNew, LaundryViewHolder> mAdapterNew;
    private Geocoder geocoder;
    private List<Address> addresses;
    private FusedLocationProviderClient client;


    public MenuFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu,container,false);
        mrecyclerView=view.findViewById(R.id.list);
        mLayoutManager=new LinearLayoutManager(this.getActivity());
        mrecyclerView.setLayoutManager(mLayoutManager);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final Query query = getQuery(mDatabase);

        FirebaseRecyclerOptions<LaundryNew> options = new FirebaseRecyclerOptions.Builder<LaundryNew>()
                .setQuery(query, LaundryNew.class)
                .build();

        mAdapterNew = new FirebaseRecyclerAdapter<LaundryNew, LaundryViewHolder>(options) {

            @NonNull
            @Override
            public LaundryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new LaundryViewHolder(inflater.inflate(R.layout.listcard_laundry, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull LaundryViewHolder holder, final int position, @NonNull final LaundryNew model) {

                holder.bindToLaundry(model, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

                double latitude= Double.valueOf(model.getLongi());
                double longitude = Double.valueOf(model.getLati());
                geocoder = new Geocoder(getActivity(), Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String address = addresses.get(0).getAddressLine(0);
                String postalCode = addresses.get(0).getPostalCode();
                //holder.tvalamat.setText(address);
                //holder.tvharga.setText("Kode Pos : "+postalCode);

                holder.cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getActivity(), "Tunggu ....", Toast.LENGTH_SHORT).show();

                        LaundryNew ln= new LaundryNew();
                        ln.setNama(model.getNama());
                        ln.setJamoperasi(model.getJamoperasi());
                        ln.setLati(model.getLati());
                        ln.setLongi(model.getLongi());

                        Gson gson = new Gson();
                        String laundrystring = gson.toJson(ln);

                        Intent i = new Intent(getActivity(),MapsActivityLaundry.class);
                        i.putExtra("laundry",laundrystring);
                        startActivity(i);
                    }
                });
            }
        };

        mAdapterNew.notifyDataSetChanged();
        mrecyclerView.setAdapter(mAdapterNew);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapterNew != null) {
            mAdapterNew.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapterNew != null) {
            mAdapterNew.stopListening();
        }
    }

    private Query getQuery(DatabaseReference mDatabase) {
        Query query = mDatabase.child("laundry");
        return query;
    }
}
