package com.nqc.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nqc.model.QuanKaraoke;
import com.nqc.tracuukaraoke.R;


public class QuanKaraokeAdapter extends ArrayAdapter<QuanKaraoke> {
    Activity context;
    int resource;
    public QuanKaraokeAdapter(@NonNull Activity context, @LayoutRes int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=context.getLayoutInflater().inflate(resource,null);
        ImageView imgHinh= (ImageView) view.findViewById(R.id.imgHinh);
        TextView txtTen= (TextView) view.findViewById(R.id.txtTen);
        QuanKaraoke karaoke=getItem(position);
        imgHinh.setImageResource(karaoke.getHinh());
        txtTen.setText(karaoke.getTen());
        return view;
    }
}
