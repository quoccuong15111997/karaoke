package com.nqc.tracuukaraoke;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nqc.firebase.SongFirebase;
import com.nqc.impl.EditSongListener;
import com.nqc.model.Song;

import static com.nqc.tracuukaraoke.ChiTietBaiHatActivity.song;


public class BaiHatFragment extends Fragment {
    View view;
    TextView txtMa, txtTen, txtCasi, txtLoi;
    ImageView imgYeuThich;
    Button btnEdit;
    public static SQLiteDatabase database = null;
    public static String DATABASE_NAME = "Arirang.sqlite";
    DialogEditSong dialogEditSong;
    DatabaseReference mData=FirebaseDatabase.getInstance().getReference();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_bai_hat, container, false);
        addControls();
        addEvents();
        openDatabse();
        return view;
    }

    private void addEvents() {
        imgYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(song.getYeuThich()==0){
                    imgYeuThich.setImageResource(R.drawable.ic_heart_clicked1);
                    song.setYeuThich(1);
                    updateDatabase(1);
                }
                else
                {
                    imgYeuThich.setImageResource(R.drawable.ic_heart1);
                    song.setYeuThich(0);
                    updateDatabase(0);
                }
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            dialogEditSong.show();
            }
        });
    }

    private void addControls() {
        txtMa=view.findViewById(R.id.txtMaBH);
        txtCasi=view.findViewById(R.id.txtCaSi);
        txtLoi=view.findViewById(R.id.txtLoiBH);
        txtTen=view.findViewById(R.id.txtTenBH);
        imgYeuThich=view.findViewById(R.id.imgYeuThich);

        txtMa.setText(song.getMaBH());
        txtTen.setText(song.getTenBH());
        txtLoi.setText(song.getLoiBaiHat());
        txtCasi.setText(song.getTacGia());
        if(song.getYeuThich()==0){
            imgYeuThich.setImageResource(R.drawable.ic_heart1);
        } else if (song.getYeuThich() == 1) {
            imgYeuThich.setImageResource(R.drawable.ic_heart_clicked1);
        }
        btnEdit=view.findViewById(R.id.btnEdit);

        EditSongListener editSongListener= new EditSongListener() {
            @Override
            public void saveEditSong(Song song, String email) {
                xuLyEdit(song,email);
            }
        };
        dialogEditSong= new DialogEditSong(view.getContext(),editSongListener,song);
    }

    private void xuLyEdit(Song song, String email) {
        SongFirebase songFirebase= new SongFirebase(song.getMaBH(),
                song.getTenBH(),
                song.getLoiBaiHat(),song.getTacGia(),song.getYeuThich(),0,email);
        mData.child("SongEdit").child(song.getMaBH()).setValue(songFirebase);
        AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
        builder.setTitle("Chỉnh sửa của bạn đã được lưu lại").setMessage("Đang chờ phê duyệt").setIcon(R.drawable.ic_ok);
        builder.show();
        dialogEditSong.dismiss();
    }

    private void openDatabse() {
        database=view.getContext().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
    }
    private void updateDatabase(int i) {
        String sql="UPDATE ArirangSongList SET YEUTHICH = "+i+" WHERE MABH = "+song.getMaBH();
        database.execSQL(sql);
    }
}
