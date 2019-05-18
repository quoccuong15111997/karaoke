package com.nqc.tracuukaraoke;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static com.nqc.tracuukaraoke.ChiTietBaiHatActivity.song;


public class BaiHatFragment extends Fragment {
    View view;
    TextView txtMa, txtTen, txtCasi, txtLoi;
    ImageView imgYeuThich;
    public static SQLiteDatabase database = null;
    public static String DATABASE_NAME = "Arirang.sqlite";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_bai_hat, container, false);
        addControls();
        addEvents();
        openDatabse();
        return view;
    }

    private void addEvents() {
        imgYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(song.getYeuThich()==0){
                    imgYeuThich.setImageResource(R.drawable.ic_heart_clicked);
                    song.setYeuThich(1);
                    updateDatabase(1);
                }
                else
                {
                    imgYeuThich.setImageResource(R.drawable.ic_heart);
                    song.setYeuThich(0);
                    updateDatabase(0);
                }
            }
        });
    }

    private void addControls() {
        txtMa=view.findViewById(R.id.txtMaBH);
        txtCasi=view.findViewById(R.id.txtCaSi);
        txtLoi=view.findViewById(R.id.txtLoiBH);
        txtTen=view.findViewById(R.id.txtTenBH);
        imgYeuThich=view.findViewById(R.id.imgYeuThich);

        txtMa.setText(song.getMaBH());
        txtTen.setText(song.getTenBH());
        txtLoi.setText(song.getLoiBaiHat());
        txtCasi.setText(song.getTacGia());
        if(song.getYeuThich()==0){
            imgYeuThich.setImageResource(R.drawable.ic_heart);
        } else if (song.getYeuThich() == 1) {
            imgYeuThich.setImageResource(R.drawable.ic_heart_clicked);
        }

    }

    private void openDatabse() {
        database=view.getContext().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
    }
    private void updateDatabase(int i) {
        String sql="UPDATE ArirangSongList SET YEUTHICH = "+i+" WHERE MABH = "+song.getMaBH();
        database.execSQL(sql);
    }
}
