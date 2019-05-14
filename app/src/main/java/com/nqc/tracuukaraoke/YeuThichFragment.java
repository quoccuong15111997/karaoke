package com.nqc.tracuukaraoke;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.TextView;

import com.nqc.adapter.SongAdapter;
import com.nqc.impl.ItemClick;
import com.nqc.impl.LikeClick;
import com.nqc.model.Song;

import java.util.ArrayList;


public class YeuThichFragment extends Fragment {
    View view;
    RecyclerView recySongLike;
    SongAdapter songAdapter;
    public static String DATABASE_NAME = "Arirang.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    ArrayList<Song> dsSongYeuThich;
    Button btnDown, btnUp;
    TextView txtSoBaiHatThich;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_yeu_thich, container, false);
        openDatabase();
        addControls();
        addEvents();
        xuLyBaiHatYeuThich();
        return view;
    }

    private void openDatabase() {
        database = view.getContext().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
    }

    private void addEvents() {

    }

    private void addControls() {
        dsSongYeuThich= new ArrayList<>();
        ItemClick itemClick= new ItemClick() {
            @Override
            public void isItemClick(int position) {
                Song song=dsSongYeuThich.get(position);
                Intent intent= new Intent(view.getContext(),ChiTietBaiHatActivity.class);
                intent.putExtra("SONG",song);
                startActivityForResult(intent,200);
            }
        };
        LikeClick likeClick= new LikeClick() {
            @Override
            public void likeIsClicked(int position) {
                Song songs=dsSongYeuThich.get(position);
                updateDatabase(songs.getYeuThich(),songs);
                dsSongYeuThich.clear();
                xuLyBaiHatYeuThich();
            }
        };
        recySongLike=view.findViewById(R.id.recySongLike);
        recySongLike.setLayoutManager(new LinearLayoutManager(view.getContext()));
        songAdapter= new SongAdapter(view.getContext(),dsSongYeuThich,itemClick,likeClick);
        recySongLike.addItemDecoration(new DividerItemDecoration(view.getContext(),new LinearLayoutManager(view.getContext()).getOrientation()));
        recySongLike.setAdapter(songAdapter);
        btnDown=view.findViewById(R.id.btnDownload);
        btnUp=view.findViewById(R.id.btnSaveOnline);
        txtSoBaiHatThich=view.findViewById(R.id.txtSoBaiHat);
    }

    private void xuLyBaiHatYeuThich() {
        LayBaiHatYeuThichTask task= new LayBaiHatYeuThichTask();
        task.execute();
    }

    private void updateDatabase(int i, Song song) {
        String sql="UPDATE ArirangSongList SET YEUTHICH = "+i+" WHERE MABH = "+song.getMaBH();
        database.execSQL(sql);
    }
    class LayBaiHatYeuThichTask extends AsyncTask<Void,Void,ArrayList<Song>>{
        @Override
        protected void onPostExecute(ArrayList<Song> songs) {
            super.onPostExecute(songs);
            if (songs.size() != 0) {
                dsSongYeuThich.clear();
                dsSongYeuThich.addAll(songs);
                songAdapter.notifyDataSetChanged();
                txtSoBaiHatThich.setText(songs.size()+" bài");
            }
        }

        @Override
        protected ArrayList<Song> doInBackground(Void... voids) {
            ArrayList<Song> dsSong= new ArrayList<>();
            String sql="select * from ArirangSongList where YEUTHICH=1";
            Cursor cursor=database.rawQuery(sql,null);
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
            return dsSong;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200){
            if(resultCode == Activity.RESULT_OK){
                xuLyBaiHatYeuThich();
            }
        }
    }
}
