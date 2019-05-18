package com.nqc.tracuukaraoke;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nqc.adapter.SongAdapter;
import com.nqc.impl.ItemClick;
import com.nqc.impl.LikeClick;
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
    DatabaseReference mData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_tim_kiem, container, false);
        openDatabase();
        addControls();
        addEvents();
        Utils utils = new Utils();
        utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                xuLyBaiHatGoc();
            }
        });
        return view;
    }

    private void openDatabase() {
        database = view.getContext().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    private void addEvents() {
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtInput.getText().toString().equals("")) {
                    TimBaiHatTheoTenTask task = new TimBaiHatTheoTenTask();
                    task.execute(edtInput.getText().toString());
                } else
                    xuLyBaiHatGoc();
            }
        });
        edtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Toast.makeText(view.getContext(),s,Toast.LENGTH_SHORT).show();
                xuLyTimBaiHatTheoKyTu(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void xuLyTimBaiHatTheoKyTu(final CharSequence s) {
        Utils utils = new Utils();
        utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TimBaiHatTheoKyTuTask task = new TimBaiHatTheoKyTuTask();
                task.execute(s.toString());
            }
        });
    }

    private void addControls() {
        mData = FirebaseDatabase.getInstance().getReference();
        dsSong = new ArrayList<>();
        edtInput = view.findViewById(R.id.edtInput);
        imgSearch = view.findViewById(R.id.imgSeach);
        recySong = view.findViewById(R.id.recySong);
        recySong.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recySong.addItemDecoration(new DividerItemDecoration(view.getContext(), new LinearLayoutManager(view.getContext()).getOrientation()));
        ItemClick itemClick = new ItemClick() {
            @Override
            public void isItemClick(int position) {
                Song song = dsSong.get(position);
                Intent intent = new Intent(view.getContext(), ChiTietBaiHatActivity.class);
                intent.putExtra("SONG", song);
                startActivityForResult(intent, 200);
            }
        };
        LikeClick likeClick = new LikeClick() {
            @Override
            public void likeIsClicked(int position) {
                Song songs = dsSong.get(position);
                updateDatabase(songs.getYeuThich(), songs);
                dsSong.clear();
                xuLyBaiHatGoc();
            }
        };
        songAdapter = new SongAdapter(view.getContext(), dsSong, itemClick, likeClick);
        recySong.setAdapter(songAdapter);

    }

    public void xuLyBaiHatGoc() {
        Cursor cursor = database.query("ArirangSongList", null, null, null, null, null, null);
        dsSong.clear();
        while (cursor.moveToNext()) {
            String mabh = cursor.getString(0);
            String tenbh = cursor.getString(1);
            String loibh = cursor.getString(2);
            String casi = cursor.getString(3);
            int yeuthich = cursor.getInt(5);
            Song song = new Song(mabh, tenbh, loibh, casi, yeuthich);
            dsSong.add(song);
        }
        songAdapter.notifyDataSetChanged();
    }

    private void updateDatabase(int i, Song song) {
        String sql = "UPDATE ArirangSongList SET YEUTHICH = " + i + " WHERE MABH = " + song.getMaBH();
        database.execSQL(sql);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == Activity.RESULT_OK) {
                xuLyBaiHatGoc();
            }
        }
    }

    class TimBaiHatTheoTenTask extends AsyncTask<String, Void, ArrayList<Song>> {
        @Override
        protected void onPostExecute(ArrayList<Song> songs) {
            super.onPostExecute(songs);
            if (songs.size() != 0) {
                dsSong.clear();
                dsSong.addAll(songs);
                songAdapter.notifyDataSetChanged();
            } else
                timBaiHatTheoTacGia();
        }

        @Override
        protected ArrayList<Song> doInBackground(String... strings) {
            ArrayList<Song> songs = new ArrayList<>();
            String sql = "select * from ArirangSongList where TENBH like '%" + strings[0] + "%'";
            Cursor cursorTimTen = database.rawQuery(sql, null);
            while (cursorTimTen.moveToNext()) {
                String mabh = cursorTimTen.getString(0);
                String tenbh = cursorTimTen.getString(1);
                String loibh = cursorTimTen.getString(2);
                String casi = cursorTimTen.getString(3);
                int yeuthich = cursorTimTen.getInt(5);
                Song song = new Song(mabh, tenbh, loibh, casi, yeuthich);
                songs.add(song);
            }
            cursorTimTen.close();
            return songs;
        }
    }

    class TimBaiHatTheoMaTask extends AsyncTask<String, Void, ArrayList<Song>> {
        @Override
        protected void onPostExecute(ArrayList<Song> songs) {
            super.onPostExecute(songs);
            if (songs.size() != 0) {
                dsSong.clear();
                dsSong.addAll(songs);
                songAdapter.notifyDataSetChanged();
            } else
                Toast.makeText(view.getContext(), "Không tìm thấy bài hát", Toast.LENGTH_LONG).show();
        }

        @Override
        protected ArrayList<Song> doInBackground(String... strings) {
            ArrayList<Song> songs = new ArrayList<>();
            String sql = "select * from ArirangSongList where MABH like '%" + strings[0] + "%'";
            Cursor cursorTimTen = database.rawQuery(sql, null);
            while (cursorTimTen.moveToNext()) {
                String mabh = cursorTimTen.getString(0);
                String tenbh = cursorTimTen.getString(1);
                String loibh = cursorTimTen.getString(2);
                String casi = cursorTimTen.getString(3);
                int yeuthich = cursorTimTen.getInt(5);
                Song song = new Song(mabh, tenbh, loibh, casi, yeuthich);
                songs.add(song);
            }
            cursorTimTen.close();
            return songs;
        }
    }

    private void timBaiHatTheoMa() {
        TimBaiHatTheoMaTask task = new TimBaiHatTheoMaTask();
        task.execute(edtInput.getText().toString());
    }

    class TimBaiHatTheoTacGiaTask extends AsyncTask<String, Void, ArrayList<Song>> {
        @Override
        protected void onPostExecute(ArrayList<Song> songs) {
            super.onPostExecute(songs);
            if (songs.size() != 0) {
                dsSong.clear();
                dsSong.addAll(songs);
                songAdapter.notifyDataSetChanged();
            } else
                timBaiHatTheoMa();
        }

        @Override
        protected ArrayList<Song> doInBackground(String... strings) {
            ArrayList<Song> songs = new ArrayList<>();
            String sql = "select * from ArirangSongList where TACGIA like '%" + strings[0] + "%'";
            Cursor cursorTimTen = database.rawQuery(sql, null);
            while (cursorTimTen.moveToNext()) {
                String mabh = cursorTimTen.getString(0);
                String tenbh = cursorTimTen.getString(1);
                String loibh = cursorTimTen.getString(2);
                String casi = cursorTimTen.getString(3);
                int yeuthich = cursorTimTen.getInt(5);
                Song song = new Song(mabh, tenbh, loibh, casi, yeuthich);
                songs.add(song);
            }
            cursorTimTen.close();
            return songs;
        }
    }

    private void timBaiHatTheoTacGia() {
        TimBaiHatTheoTacGiaTask task = new TimBaiHatTheoTacGiaTask();
        task.execute(edtInput.getText().toString());
    }

    class TimBaiHatTheoKyTuTask extends AsyncTask<String, Void, ArrayList<Song>> {
        @Override
        protected void onPostExecute(ArrayList<Song> songs) {
            super.onPostExecute(songs);
            if (songs.size() != 0) {
                dsSong.clear();
                dsSong.addAll(songs);
                songAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected ArrayList<Song> doInBackground(String... strings) {
            ArrayList<Song> songs = new ArrayList<>();
            String sql = "select * from ArirangSongList where TENBH like '%" + strings[0] + "'";
            Cursor cursorTimTen = database.rawQuery(sql, null);
            while (cursorTimTen.moveToNext()) {
                String mabh = cursorTimTen.getString(0);
                String tenbh = cursorTimTen.getString(1);
                String loibh = cursorTimTen.getString(2);
                String casi = cursorTimTen.getString(3);
                int yeuthich = cursorTimTen.getInt(5);
                Song song = new Song(mabh, tenbh, loibh, casi, yeuthich);
                songs.add(song);
            }
            cursorTimTen.close();
            return songs;
        }
    }

    public class Utils {
        public void runOnUiThread(Runnable runnable) {
            final Handler UIHandler = new Handler(Looper.getMainLooper());
            UIHandler.post(runnable);
        }
    }
}
