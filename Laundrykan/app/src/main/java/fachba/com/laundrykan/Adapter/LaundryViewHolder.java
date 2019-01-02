package fachba.com.laundrykan.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fachba.com.laundrykan.Model.LaundryNew;
import fachba.com.laundrykan.R;

public class LaundryViewHolder extends RecyclerView.ViewHolder {
    public TextView tvnama,tvalamat,tvjam,tvharga;
    public CardView cv;
    public LaundryViewHolder(@NonNull View itemView) {
        super(itemView);
        tvnama=itemView.findViewById(R.id.textView3);
        tvjam=itemView.findViewById(R.id.textView4);
        cv = itemView.findViewById(R.id.idcard);
        tvalamat=itemView.findViewById(R.id.textView5);
        tvharga=itemView.findViewById(R.id.textView6);
    }

    public void bindToLaundry(LaundryNew laundry, View.OnClickListener onClickListener){
        tvnama.setText(laundry.getNama());
        tvjam.setText("Buka : "+laundry.getJamoperasi());

    }
}
