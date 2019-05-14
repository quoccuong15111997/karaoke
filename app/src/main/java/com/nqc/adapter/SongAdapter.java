package com.nqc.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nqc.impl.ItemClick;
import com.nqc.impl.LikeClick;
import com.nqc.model.Song;
import com.nqc.tracuukaraoke.R;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Song> data;
    ItemClick itemClick;
    LikeClick likeClick;

    public SongAdapter(Context context, ArrayList<Song> data, ItemClick itemClick, LikeClick likeClick){
        this.context=context;
        this.data=data;
        this.itemClick=itemClick;
        this.likeClick=likeClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view=null;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_song,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Song song=data.get(i);
        viewHolder.txtMaBH.setText(song.getMaBH()+"");
        viewHolder.txtTenBH.setText(song.getTenBH());
        viewHolder.txtCaSi.setText(song.getTacGia());
        viewHolder.txtLoiBH.setText(song.getLoiBaiHat());
        if(song.getYeuThich()==1){
            viewHolder.imgLike.setImageResource(R.drawable.ic_heart_clicked);
        }
        else if(song.getYeuThich()==0){
            viewHolder.imgLike.setImageResource(R.drawable.ic_heart);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaBH;
        TextView txtTenBH;
        TextView txtCaSi;
        TextView txtLoiBH;
        ImageView imgLike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           txtMaBH=itemView.findViewById(R.id.txtMaBH);
           txtTenBH=itemView.findViewById(R.id.txtTenBH);
           txtCaSi=itemView.findViewById(R.id.txtCaSi);
           txtLoiBH=itemView.findViewById(R.id.txtLoiBH);
           imgLike=itemView.findViewById(R.id.imgLike);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   itemClick.isItemClick(getAdapterPosition());
               }
           });
           imgLike.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Song song=data.get(getAdapterPosition());
                   if(song.getYeuThich()==0){
                       imgLike.setImageResource(R.drawable.ic_heart_clicked);
                       song.setYeuThich(1);
                   }
                   else if(song.getYeuThich()==1){
                       imgLike.setImageResource(R.drawable.ic_heart);
                       song.setYeuThich(0);
                   }
                   likeClick.likeIsClicked(getAdapterPosition());
               }
           });
        }
    }
}
