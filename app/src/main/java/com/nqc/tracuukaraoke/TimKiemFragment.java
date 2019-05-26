package com.nqc.tracuukaraoke;

import android.app.Activity;
import android.content.ContentValues;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.googleapis.util.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nqc.adapter.SongAdapter;
import com.nqc.constan.Const;
import com.nqc.email.GMailSender;
import com.nqc.fcm.SendNotification;
import com.nqc.firebase.SongFirebase;
import com.nqc.impl.CharSelectedListener;
import com.nqc.impl.ItemClick;
import com.nqc.impl.LikeClick;
import com.nqc.impl.SaveNewSongOnClickListener;
import com.nqc.model.Song;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.nqc.tracuukaraoke.BarActivity.dsNewSong;
import static com.nqc.tracuukaraoke.TimKiemFragment.DATABASE_NAME;


public class TimKiemFragment extends Fragment {
    View view;
    EditText edtInput;
    ArrayAdapter<String> charAdapter;
    RecyclerView recySong;
    public static String DATABASE_NAME = "Arirang.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    ArrayList<Song> dsSong;
    SongAdapter songAdapter;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    ImageView imgChar;
    ImageView imgAdd;
    DialogAddSong dialogAddSong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_tim_kiem, container, false);
        openDatabase();
        for (SongFirebase sfe : BarActivity.dsSongEdit){
            if (!sfe.getEmail().equals("")){
                sendMessage(sfe);
                mData.child("SongEdit").child(sfe.getMaBH()).child("email").setValue("");
            }
        }
        for (SongFirebase sf : dsNewSong){
            if (sf.getDuyet() == 1) {
                Cursor cursor = database.query("ArirangSongList", null, "MABH=?", new String[]{sf.getMaBH()}, null, null, null);
                if (cursor.moveToNext() == false) {

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("MABH", sf.getMaBH());
                    contentValues.put("TENBH", sf.getTenBH());
                    contentValues.put("LOIBH", sf.getLoiBaiHat());
                    contentValues.put("TACGIA", sf.getTacGia());
                    contentValues.put("THELOAI", "Nhạc trẻ");
                    contentValues.put("YEUTHICH", 0);
                    database.insert("ArirangSongList", null, contentValues);
                }
                if (!sf.getEmail().equals("")) {
                    sendMessage(sf);
                    SendNotification sendNotification = new SendNotification(sf, view.getContext());
                    sendNotification.send();
                    sf.setEmail("");
                    mData.child("NewSong").child(sf.getMaBH()).child("email").setValue("");
                }
            }
        }

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
        database = view.getContext().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
    }

    private void addEvents() {
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
        imgChar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryMenu();
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThemBaiHat();
            }
        });
    }

    private void xuLyThemBaiHat() {
        dialogAddSong.show();
    }

    private void xuLyTimBaiHatTheoKyTu(final CharSequence s) {
        Utils utils = new Utils();
        utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TimBaiHatTheoKyTuTask task = new TimBaiHatTheoKyTuTask();
                task.execute(s.toString().toUpperCase());
            }
        });
    }

    private void addControls() {
        dsSong = new ArrayList<>();
        edtInput = view.findViewById(R.id.edtInput);
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

        imgChar = view.findViewById(R.id.imgChar);
        imgAdd = view.findViewById(R.id.imgAdd);

        SaveNewSongOnClickListener saveNewSongOnClickListener = new SaveNewSongOnClickListener() {
            @Override
            public void onSaveClick(SongFirebase songFirebase) {
                xuLySave(songFirebase);
            }
        };
        dialogAddSong = new DialogAddSong(view.getContext(), saveNewSongOnClickListener);
    }

    private void xuLySave(SongFirebase songFirebase) {
        mData.child("NewSong").child(songFirebase.getMaBH()).setValue(songFirebase, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    FancyToast.makeText(view.getContext(), "Đã gửi yêu cầu thành công " + "\n" + "Đang chờ phê duyệt.", Toast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                } else
                    FancyToast.makeText(view.getContext(), "Yêu cầu thất bại, vui lòng kiểm tra và thử lại.", Toast.LENGTH_LONG, FancyToast.ERROR, true).show();
            }
        });
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
            String s = strings[0];
            ArrayList<Song> songs = new ArrayList<>();
            Cursor cursorTimTen = database.query("ArirangSongList", null,
                    "MABH like ? or TENBH like ? or TACGIA like ?",
                    new String[]{"%" + s + "%", "%" + s + "%", "%" + s + "%"},
                    null, null, null);
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

    private void showCategoryMenu() {
        final CharDropdownMenu menu = new CharDropdownMenu(view.getContext());
        menu.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        menu.setWidth(getPxFromDp(200));
        menu.setOutsideTouchable(true);
        menu.setFocusable(true);
        menu.showAsDropDown(imgChar);
        menu.setCharSelectedListener(new CharSelectedListener() {
            @Override
            public void onCharSelected(String s) {
                TimTheoKyTuDauTienTask task = new TimTheoKyTuDauTienTask();
                task.execute(s);
                menu.dismiss();
            }
        });
    }

    //Convert DP to Pixel
    private int getPxFromDp(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    class TimTheoKyTuDauTienTask extends AsyncTask<String, Void, ArrayList<Song>> {
        @Override
        protected void onPostExecute(ArrayList<Song> songs) {
            super.onPostExecute(songs);
            if (songs.size() != 0) {
                dsSong.clear();
                dsSong.addAll(songs);
                songAdapter.notifyDataSetChanged();
            } else
                xuLyBaiHatGoc();
        }

        @Override
        protected ArrayList<Song> doInBackground(String... strings) {
            String s = strings[0];
            ArrayList<Song> songs = new ArrayList<>();
            String sql = "select * from ArirangSongList where TENBH like '" + s + "%'";
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

    private void sendMessage(final SongFirebase songFirebase) {
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender(Const.user, Const.pass);
                    sender.sendMail("Karaoke App", "Đề xuất chỉnh sửa của bạn đã được phê duyệt. Cảm ơn về đề xuất của bạn" + "\n Tên bài hát: " + songFirebase.getTenBH()
                                    + "\n Mã bài hát: " + songFirebase.getMaBH(),
                            Const.user, songFirebase.getEmail());
                    //Toast.makeText(view.getContext(),"Gửi thành công", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }
}
