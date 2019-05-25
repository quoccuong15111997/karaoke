package com.nqc.tracuukaraoke;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nqc.constan.Const;
import com.nqc.email.GMailSender;
import com.nqc.firebase.SongFirebase;

import java.util.ArrayList;

import static com.nqc.tracuukaraoke.TimKiemFragment.DATABASE_NAME;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_tim_kiem:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimKiemFragment()).commit();
                    return true;
                case R.id.nav_yeu_thich:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new YeuThichFragment()).commit();
                    return true;
                case R.id.nav_dia_diem:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapsFragment()).commit();
                    return true;
                case R.id.nav_tro_giup:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TroGiupFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimKiemFragment()).commit();

    }
}
