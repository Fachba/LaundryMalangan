package fachba.com.laundrykan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import fachba.com.laundrykan.Model.LaundryNew;

public class DetailLaundry extends AppCompatActivity {
    TextView tv;
    String nm,jo,lt,ln;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laundry);
        tv = findViewById(R.id.textView9);

        Gson gson = new Gson();
        String laundrystring = getIntent().getStringExtra("laundry");
        LaundryNew laundryintent = gson.fromJson(laundrystring,LaundryNew.class);
        nm=laundryintent.getNama();
        jo=laundryintent.getJamoperasi();
        lt=laundryintent.getLati();
        ln=laundryintent.getLongi();
        tv.setText(nm+jo+lt+ln);

    }
}
