package com.nqc.tracuukaraoke;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.nqc.impl.DownloadOnClickListener;
import com.nqc.impl.SaveOnlineOnClickListener;

public class DialogDownload extends Dialog {
    public DialogDownload(Context context, DownloadOnClickListener downloadOnClickListener) {
        super(context);
      this.downloadOnClickListener=downloadOnClickListener;
    }


    DownloadOnClickListener downloadOnClickListener;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_download);

        final EditText edtEmail=findViewById(R.id.edtEmail);
        final EditText edtPhone=findViewById(R.id.edtPhone);

        Button btnLuu = (Button) findViewById(R.id.btnLuuMonHoc);
        btnLuu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
               downloadOnClickListener.onButtonClick(edtEmail.getText().toString(),edtPhone.getText().toString());
            }
        });
        Button btnHuy=findViewById(R.id.btnHuyDown);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
