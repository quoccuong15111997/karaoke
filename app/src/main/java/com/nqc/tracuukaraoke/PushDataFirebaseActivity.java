package com.nqc.tracuukaraoke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nqc.firebase.QuanKaraFirebase;
import com.nqc.model.QuanKaraoke;

import java.util.ArrayList;

public class PushDataFirebaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_data_firebase);
        DatabaseReference mData= FirebaseDatabase.getInstance().getReference();
        String ten="Karaoke Viva";
        String url="https://firebasestorage.googleapis.com/v0/b/tracuukaraoke-4a625.appspot.com/o/2017-11-08.jpg?alt=media&token=bc81e873-3de3-4cf6-87b8-07112b7975a5";
        String gio="Cả ngày";
        String diaChi="364 Ung Văn Khiêm, Phường 25, Bình Thạnh, Hồ Chí Minh, Việt Nam";
        double kinhdo=10.801140;
        double vido=106.723403;
        QuanKaraFirebase quanKaraFirebase = new QuanKaraFirebase(ten,url,gio,diaChi,kinhdo,vido,0);
        mData.child("QuanKaraoke").push().setValue(quanKaraFirebase);
    }
}
