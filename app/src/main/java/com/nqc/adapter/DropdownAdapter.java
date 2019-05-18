package com.nqc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nqc.impl.CharSelectedListener;
import com.nqc.impl.ItemClick;
import com.nqc.impl.LikeClick;
import com.nqc.model.Song;
import com.nqc.tracuukaraoke.R;

import java.util.ArrayList;

public class DropdownAdapter extends RecyclerView.Adapter<DropdownAdapter.ViewHolder>{
    private Context context;
    private ArrayList<String> data;
    private CharSelectedListener charSelectedListener;
    public  void selected (CharSelectedListener charSelectedListener){
        this.charSelectedListener=charSelectedListener;
    }
    public DropdownAdapter(Context context, ArrayList<String> data){
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view=null;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_char,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final String s=data.get(i);
        viewHolder.txtChar.setText(s);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtChar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           txtChar=itemView.findViewById(R.id.txtChar);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   charSelectedListener.onCharSelected(data.get(getAdapterPosition()));
               }
           });
        }
    }
}
