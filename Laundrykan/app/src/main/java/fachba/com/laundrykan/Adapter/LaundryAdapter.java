package fachba.com.laundrykan.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


import fachba.com.laundrykan.Model.LaundryNew;
import fachba.com.laundrykan.R;

public class LaundryAdapter extends RecyclerView.Adapter<LaundryAdapter.ViewHolder> {
    private Context context;
    public List<LaundryNew> myLaundry;

    public LaundryAdapter(Context context, List<LaundryNew> myLaundry) {
        this.context = context;
        this.myLaundry = myLaundry;
    }

    public LaundryAdapter(List<LaundryNew> myLaundry) {
        this.myLaundry=myLaundry;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listcard_laundry,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final LaundryNew laundry = myLaundry.get(position);
        holder.tvnama.setText(laundry.getNama());
        //holder.tvalamat.setText(laundry.getAlamat());
        holder.tvjam.setText("Buka"+laundry.getJamoperasi());
        //holder.tvharga.setText("Rp. "+String.valueOf(laundry.getHarga())+" Per KG");
        holder.tvnama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return myLaundry.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvnama,tvalamat,tvjam,tvharga;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvnama=itemView.findViewById(R.id.textView3);
            tvalamat=itemView.findViewById(R.id.textView4);
            tvjam=itemView.findViewById(R.id.textView5);
            tvharga=itemView.findViewById(R.id.textView6);
        }
    }
}
