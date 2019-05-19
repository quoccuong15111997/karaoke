package com.nqc.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.nqc.tracuukaraoke.R;

public class MyInforAdapter implements GoogleMap.InfoWindowAdapter {
    Activity context;
    public MyInforAdapter(Activity context)
    {
        this.context=context;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LayoutInflater inflater=context.getLayoutInflater();
        View view=inflater.inflate(R.layout.itemgoogle,null);
        ImageView imgHinh= (ImageView) view.findViewById(R.id.imgHinh);
        TextView txtTen= (TextView) view.findViewById(R.id.txtTen);
        imgHinh.setImageResource(R.drawable.ic_person);
        txtTen.setText(R.string.your_current_location);

        return view;
    }
}
