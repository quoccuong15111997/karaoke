package com.nqc.tracuukaraoke;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nqc.model.Song;

import java.util.ArrayList;

public class ChiTietBaiHatActivity extends AppCompatActivity {
    TextView txtMa, txtTen, txtCasi, txtLoi;
    ImageView imgYeuThich;
    ImageView imgBack;
    Song song;
    Intent intent;
    public static SQLiteDatabase database = null;
    public static String DATABASE_NAME = "Arirang.sqlite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_bai_hat);
        addControls();
        addEvents();
        openDatabse();
    }

    private void openDatabse() {
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
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
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    private void updateDatabase(int i) {
        String sql="UPDATE ArirangSongList SET YEUTHICH = "+i+" WHERE MABH = "+song.getMaBH();
        database.execSQL(sql);
    }

    private void addControls() {
        intent= getIntent();
        song= (Song) intent.getSerializableExtra("SONG");
        txtMa=findViewById(R.id.txtMaBH);
        txtCasi=findViewById(R.id.txtCaSi);
        txtLoi=findViewById(R.id.txtLoiBH);
        txtTen=findViewById(R.id.txtTenBH);
        imgYeuThich=findViewById(R.id.imgYeuThich);

        txtMa.setText(song.getMaBH());
        txtTen.setText(song.getTenBH());
        txtLoi.setText(song.getLoiBaiHat());
        txtCasi.setText(song.getTacGia());
        if(song.getYeuThich()==0){
            imgYeuThich.setImageResource(R.drawable.ic_heart);
        } else if (song.getYeuThich() == 1) {
            imgYeuThich.setImageResource(R.drawable.ic_heart_clicked);
        }
        imgBack=findViewById(R.id.imgBack);
    }
}
