package com.nqc.tracuukaraoke;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.nqc.adapter.SongAdapter;
import com.nqc.impl.ItemClick;
import com.nqc.model.Song;

import java.util.ArrayList;


public class TimKiemFragment extends Fragment {
    View view;
    EditText edtInput;
    ImageView imgSearch;
    RecyclerView recySong;
    public static String DATABASE_NAME = "Arirang.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    ArrayList<Song> dsSong;
    SongAdapter songAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_tim_kiem, container, false);
        addControls();
        addEvents();
        xuLyBaiHatGoc();
        return view;
    }

    private void addEvents() {
    }

    private void addControls() {
        dsSong= new ArrayList<>();
        edtInput=view.findViewById(R.id.edtInput);
        imgSearch=view.findViewById(R.id.imgSeach);
        recySong=view.findViewById(R.id.recySong);
        recySong.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recySong.addItemDecoration(new DividerItemDecoration(view.getContext(),new LinearLayoutManager(view.getContext()).getOrientation()));
        ItemClick itemClick= new ItemClick() {
            @Override
            public void isItemClick(int position) {
                Song song=dsSong.get(position);
                Intent intent= new Intent(view.getContext(),ChiTietBaiHatActivity.class);
                intent.putExtra("SONG",song);
                startActivity(intent);
            }
        };
        songAdapter= new SongAdapter(view.getContext(),dsSong,itemClick);
        recySong.setAdapter(songAdapter);

    }
    private void xuLyBaiHatGoc() {
        database = view.getContext().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
        Cursor cursor = database.query("ArirangSongList", null, null, null, null, null, null);
        dsSong.clear();
        while (cursor.moveToNext()) {
            String mabh = cursor.getString(0);
            String tenbh = cursor.getString(1);
            String loibh=cursor.getString(2);
            String casi = cursor.getString(3);
            int yeuthich = cursor.getInt(5);
            Song song= new Song(mabh,tenbh,loibh,casi,yeuthich);
            dsSong.add(song);
        }
        cursor.close();
        database.close();
        songAdapter.notifyDataSetChanged();
    }
}
