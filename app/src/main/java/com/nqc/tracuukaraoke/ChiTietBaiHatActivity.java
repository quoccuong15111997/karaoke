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
    public static Song song;
    Intent intent;
    ImageView imgBack;
    TextView txtBaiHat, txtOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_bai_hat);
        addControls();
        addEvents();
        txtBaiHat.setBackgroundResource(R.drawable.bober_txt_select);
        txtOnline.setBackgroundResource(R.drawable.bober_abc);
        moManHinhChiTiet();
    }



    private void addEvents() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
               setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        txtBaiHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    txtBaiHat.setBackgroundResource(R.drawable.bober_txt_select);
                    txtOnline.setBackgroundResource(R.drawable.bober_abc);
                    moManHinhChiTiet();
                }
        });
        txtOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBaiHat.setBackgroundResource(R.drawable.bober_abc);
                txtOnline.setBackgroundResource(R.drawable.bober_txt_select);
                moManHinhHatOnline();
            }
        });
    }

    private void moManHinhHatOnline() {
        getSupportFragmentManager().beginTransaction().replace(R.id.containerBaiHat,new HatOnlineFragment()).commit();
    }

    private void moManHinhChiTiet() {
        getSupportFragmentManager().beginTransaction().replace(R.id.containerBaiHat,new BaiHatFragment()).commit();
    }

    private void addControls() {
        intent= getIntent();
        song= (Song) intent.getSerializableExtra("SONG");

        txtBaiHat= findViewById(R.id.txtBaiHat);
        txtOnline=findViewById(R.id.txtOnline);
        imgBack=findViewById(R.id.imgBack);
    }
}
