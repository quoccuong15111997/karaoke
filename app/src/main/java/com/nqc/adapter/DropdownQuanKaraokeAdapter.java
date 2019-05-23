package com.nqc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nqc.firebase.QuanKaraFirebase;
import com.nqc.impl.CharSelectedListener;
import com.nqc.impl.QuanKaraSelectedListener;
import com.nqc.model.QuanKaraoke;
import com.nqc.model.Sing;
import com.nqc.tracuukaraoke.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DropdownQuanKaraokeAdapter extends RecyclerView.Adapter<DropdownQuanKaraokeAdapter.ViewHolder>{
    private Context context;
    private ArrayList<QuanKaraFirebase> data;
    private QuanKaraSelectedListener quanKaraSelectedListener;
    public void selected (QuanKaraSelectedListener quanKaraSelectedListener){
        this.quanKaraSelectedListener=quanKaraSelectedListener;
    }
    public DropdownQuanKaraokeAdapter(Context context, ArrayList<QuanKaraFirebase> data){
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view=null;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_quan_kara,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final QuanKaraFirebase s=data.get(i);
        viewHolder.txtTen.setText(s.getTen());
        viewHolder.txtGioLamViec.setText(s.getGioMoCua());
        viewHolder.txtDiaChi.setText(s.getDiaChi());
        Picasso.with(context).load(s.getUrlHinh()).into(viewHolder.imgHinh);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTen;
        TextView txtDiaChi;
        TextView txtGioLamViec;
        ImageView imgHinh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           txtTen=itemView.findViewById(R.id.txtTenQuan);
           txtDiaChi=itemView.findViewById(R.id.txtDiaChi);
           txtGioLamViec=itemView.findViewById(R.id.txtGioMoCua);
           imgHinh=itemView.findViewById(R.id.imgHinh);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   quanKaraSelectedListener.onCharSelected(data.get(getAdapterPosition()));
               }
           });
        }
    }
}
