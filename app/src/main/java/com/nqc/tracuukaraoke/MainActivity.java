package com.nqc.tracuukaraoke;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_tim_kiem:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TimKiemFragment()).commit();
                    return true;
                case R.id.nav_yeu_thich:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new YeuThichFragment()).commit();
                    return true;
                case R.id.nav_dia_diem:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MapsFragment()).commit();
                    return true;
                case R.id.nav_tro_giup:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TroGiupFragment()).commit();
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

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TimKiemFragment()).commit();
    }

}
