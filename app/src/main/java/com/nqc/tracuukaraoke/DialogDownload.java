package com.nqc.tracuukaraoke;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nqc.impl.DownloadOnClickListener;
import com.nqc.impl.SaveOnlineOnClickListener;
import com.shashank.sony.fancytoastlib.FancyToast;

public class DialogDownload extends Dialog {
    public DialogDownload(Context context, DownloadOnClickListener downloadOnClickListener) {
        super(context);
      this.downloadOnClickListener=downloadOnClickListener;
      this.context=context;
    }


    DownloadOnClickListener downloadOnClickListener;
    Context context;


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
               if (!edtEmail.getText().toString().equals("") && !edtPhone.getText().toString().equals("")){
                   downloadOnClickListener.onButtonClick(edtEmail.getText().toString(),edtPhone.getText().toString());
               }
               else
                   FancyToast.makeText(context,"Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_LONG,FancyToast.WARNING,true).show();
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
