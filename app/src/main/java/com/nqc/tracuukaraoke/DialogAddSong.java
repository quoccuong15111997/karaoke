package com.nqc.tracuukaraoke;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nqc.firebase.SongFirebase;
import com.nqc.impl.EditSongListener;
import com.nqc.impl.SaveNewSongOnClickListener;
import com.nqc.model.Song;
import com.shashank.sony.fancytoastlib.FancyToast;

public class DialogAddSong extends Dialog {
    public DialogAddSong(Context context, SaveNewSongOnClickListener saveNewSongOnClickListener) {
        super(context);
      this.saveNewSongOnClickListener=saveNewSongOnClickListener;
      this.context=context;
    }

    SaveNewSongOnClickListener saveNewSongOnClickListener;
    Context context;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_song);

        final EditText edtTenBH=findViewById(R.id.edtNameSong);
        final EditText edtMaBH=findViewById(R.id.edtMaSong);
        final EditText edtCaSi=findViewById(R.id.edtCaSi);
        final EditText edtLoi=findViewById(R.id.edtLoibaiHat);
        final EditText edtEmail=findViewById(R.id.edtEmail);

        ImageView imgSave=findViewById(R.id.imgSaveSong);
        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!edtCaSi.getText().toString().equals("") && !edtEmail.getText().toString().equals("") && !edtLoi.getText().toString().equals("") && !edtMaBH.getText().toString().equals("") && !edtTenBH.getText().toString().equals("")){
                    saveNewSongOnClickListener.onSaveClick(new SongFirebase(edtMaBH.getText().toString(),
                            edtTenBH.getText().toString(),
                            edtLoi.getText().toString(),
                            edtCaSi.getText().toString(),
                            0,0,edtEmail.getText().toString()));
                    dismiss();
                }
                else
                    FancyToast.makeText(context,"Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_LONG,FancyToast.WARNING,true).show();
            }
        });
       ImageView imgCancel=findViewById(R.id.imgCancelSong);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
