package com.nqc.tracuukaraoke;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nqc.adapter.MyCustomInfoAdapter;
import com.nqc.adapter.MyInforAdapter;
import com.nqc.firebase.QuanKaraFirebase;
import com.nqc.impl.CharSelectedListener;
import com.nqc.impl.QuanKaraSelectedListener;
import com.nqc.model.QuanKaraoke;
import com.nqc.model.Song;

import java.util.ArrayList;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    View view;
    public static LatLng latLng;
    private GoogleMap mMap;
    TextView txtSeach;
    ArrayList<QuanKaraFirebase> karaokes;
    QuanKaraDropdownMenu menu;
    SQLiteDatabase database;
    public static String DATABASE_NAME = "Arirang.sqlite";
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://tracuukaraoke-4a625.appspot.com/");
    DatabaseReference mData= FirebaseDatabase.getInstance().getReference();
    GoogleMap.OnMyLocationChangeListener onMyLocationChangeListener= new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            latLng= new LatLng(location.getLatitude(),location.getLongitude());
            //Marker marker=mMap.addMarker(new MarkerOptions().position(latLng));
            Marker marker= mMap.addMarker(
                    new MarkerOptions()
                            .position(latLng)
                            .title("")
                            .snippet(""));
            MyInforAdapter adapter=new MyInforAdapter(getActivity());
            mMap.setInfoWindowAdapter(adapter);
            marker.showInfoWindow();
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        }
    };

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.maps_fragment, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.main_branch_map);
        mapFragment.getMapAsync(this);
        addControls();
        addEvens();
        return view;
    }

    private void addEvens() {
        txtSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListQuanKara();
            }
        });
    }

    private void addControls() {
        txtSeach=view.findViewById(R.id.txtSeach);
        karaokes= new ArrayList<>();
        fakeData();
    }

    private void showListQuanKara() {
        //final CharDropdownMenu menu = new CharDropdownMenu(view.getContext());
        menu= new QuanKaraDropdownMenu(view.getContext(),karaokes);
        menu.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        menu.setOutsideTouchable(true);
        menu.setFocusable(true);
        menu.showAsDropDown(txtSeach);
        menu.setQuanKaraListener(new QuanKaraSelectedListener() {
            @Override
            public void onCharSelected(QuanKaraFirebase quanKaraoke) {
                LatLng latLngQuan= new LatLng(quanKaraoke.getKinhdo(),quanKaraoke.getVido());
                Marker marker= mMap.addMarker(
                        new MarkerOptions()
                                .position(latLngQuan)
                                .title(quanKaraoke.getTen())
                                .snippet(quanKaraoke.getDiaChi()));
                MyCustomInfoAdapter myCustomInfoAdapter= new MyCustomInfoAdapter(getActivity(),quanKaraoke);
                mMap.setInfoWindowAdapter(myCustomInfoAdapter);
                marker.showInfoWindow();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngQuan,15));
                mMap.setOnMyLocationChangeListener(null);
                menu.dismiss();
            }
        });
    }
    private int getPxFromDp(int dp){
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(onMyLocationChangeListener);
    }
    private void fakeData() {
       LayDanhSachQuanKaraokeTask layDanhSachQuanKaraokeTask= new LayDanhSachQuanKaraokeTask();
       layDanhSachQuanKaraokeTask.execute();
    }
    class LayDanhSachQuanKaraokeTask extends AsyncTask<Void,Void,ArrayList<QuanKaraFirebase>>{
        @Override
        protected void onPostExecute(ArrayList<QuanKaraFirebase> quanKaraFirebases) {
            super.onPostExecute(quanKaraFirebases);
            karaokes.addAll(quanKaraFirebases);
        }

        @Override
        protected ArrayList<QuanKaraFirebase> doInBackground(Void... voids) {
            ArrayList<QuanKaraFirebase> ds= new ArrayList<>();
            database=view.getContext().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
            Cursor cursor = database.query("QuanKaraoke", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String ten=cursor.getString(0);
                String url=cursor.getString(1);
                String gio=cursor.getString(2);
                String diaChi=cursor.getString(3);
                double kinhdo=cursor.getDouble(4);
                double vido=cursor.getDouble(5);
                QuanKaraFirebase quanKaraFirebase= new QuanKaraFirebase(ten,url,gio,diaChi,kinhdo,vido,1);
                ds.add(quanKaraFirebase);
               //mData.child("QuanKaraoke").push().setValue(quanKaraFirebase);
            }
            return ds;
        }
    }
}
