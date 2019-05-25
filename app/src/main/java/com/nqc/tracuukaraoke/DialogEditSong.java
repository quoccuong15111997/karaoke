package com.nqc.tracuukaraoke;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nqc.impl.EditSongListener;
import com.nqc.impl.SaveOnlineOnClickListener;
import com.nqc.model.Song;
import com.shashank.sony.fancytoastlib.FancyToast;

public class DialogEditSong extends Dialog {
    public DialogEditSong(Context context, EditSongListener editSongListener, Song song) {
        super(context);
        this.editSongListener = editSongListener;
        this.song = song;
        this.context=context;
    }


    EditSongListener editSongListener;
    Song song;
    Context context;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_edit_song);

        final EditText edtTenBH = findViewById(R.id.edtTenBH);
        final EditText edtMaBH = findViewById(R.id.edtMaBH);
        final EditText edtCaSi = findViewById(R.id.edtCaSi);
        final EditText edtLoi = findViewById(R.id.edtLoibaiHat);
        final EditText edtEmail = findViewById(R.id.edtEmail);

        edtMaBH.setText(song.getMaBH());
        edtTenBH.setText(song.getTenBH());
        edtCaSi.setText(song.getTacGia());
        edtLoi.setText(song.getLoiBaiHat());

        Button btnLuu = (Button) findViewById(R.id.btnLuuEdit);
        btnLuu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!edtEmail.getText().toString().equals("")) {
                    editSongListener.saveEditSong(new Song(edtMaBH.getText().toString(),
                            edtTenBH.getText().toString(),
                            edtLoi.getText().toString(),
                            edtCaSi.getText().toString(), 0), edtEmail.getText().toString());
                }
                else
                    FancyToast.makeText(context,"Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_LONG,FancyToast.WARNING,true).show();
            }
        });
        Button btnHuy = findViewById(R.id.btnHuyEdit);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
