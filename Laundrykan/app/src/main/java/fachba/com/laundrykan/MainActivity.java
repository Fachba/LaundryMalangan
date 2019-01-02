package fachba.com.laundrykan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;

import fachba.com.laundrykan.Fragment.AboutFragment;
import fachba.com.laundrykan.Fragment.MapsFragment;
import fachba.com.laundrykan.Fragment.MenuFragment;
import fachba.com.laundrykan.Model.LaundryNew;

public class MainActivity extends AppCompatActivity {

    private TabLayout tablayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    private static final String TAG = MapsActivityLaundry.class.getSimpleName();
    private SharedPreferences mPrefs;
    private static final String PREF_NAME = TAG+"_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPrefs = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        Gson gson = new Gson();
        String json = mPrefs.getString("MyObject", "");
        LaundryNew obj = gson.fromJson(json, LaundryNew.class);

        if(obj==null)
        {
            LaundryNew objn = new LaundryNew("Favorite Laundry Kosong ","0","0","0");

            String jsonn = gson.toJson(objn);
            prefsEditor.putString("MyObject", jsonn);
            prefsEditor.commit();
        }

        tablayout=findViewById(R.id.tablayout_id);
        appBarLayout=findViewById(R.id.appbarid);
        viewPager=findViewById(R.id.viewpager_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new MenuFragment(),"Laundry");
        adapter.AddFragment(new MapsFragment(),"Favorite");
        adapter.AddFragment(new AboutFragment(),"Tentang");

        if (cekinternet())
        {
            Toast.makeText(this, "Mohon Tunggu Sebentar", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Tidak Ada Koneksi Intenet", Toast.LENGTH_SHORT).show();
            showDialog();
        }

        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }

    private void showDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Peringatan");
        alertDialogBuilder
                .setMessage("Mohon koneksikan internet untuk menggunakan aplikasi")
                .setCancelable(false)
                .setIcon(R.drawable.laundry)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private boolean cekinternet()
    {
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo()!=null;
    }
}
