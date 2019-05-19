package com.nqc.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.nqc.model.QuanKaraoke;
import com.nqc.tracuukaraoke.R;


public class MyCustomInfoAdapter implements GoogleMap.InfoWindowAdapter {
    Activity context;
    QuanKaraoke quanKaraoke;
    public MyCustomInfoAdapter(Activity context, QuanKaraoke quanKaraoke)
    {
        this.context=context;
        this.quanKaraoke=quanKaraoke;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view=context.getLayoutInflater().inflate(R.layout.itemgoogle,null);
        ImageView imgHinh= (ImageView) view.findViewById(R.id.imgHinh);
        TextView txtTen= (TextView) view.findViewById(R.id.txtTen);
        imgHinh.setImageResource(quanKaraoke.getHinh());
        txtTen.setText(quanKaraoke.getTen());
        return view;
    }
}
