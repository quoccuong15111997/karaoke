package com.nqc.tracuukaraoke;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nqc.impl.SaveOnlineOnClickListener;
import com.shashank.sony.fancytoastlib.FancyToast;

public class DialogSaveOnline extends Dialog {
    public DialogSaveOnline(Context context, SaveOnlineOnClickListener saveOnlineOnClickListener) {
        super(context);
      this.saveOnlineOnClickListener=saveOnlineOnClickListener;
      this.context=context;
    }


    SaveOnlineOnClickListener saveOnlineOnClickListener;
    Context context;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_save_online);

        final EditText edttName=findViewById(R.id.edtName);
        final EditText edtEmail=findViewById(R.id.edtEmail);
        final EditText edtPhone=findViewById(R.id.edtPhone);

        Button btnLuu = (Button) findViewById(R.id.btnLuuMonHoc);
        btnLuu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!edtEmail.getText().equals("") && !edtPhone.getText().equals("") && !edttName.getText().equals("")){
                    saveOnlineOnClickListener.onButtonClick(edttName.getText().toString(),
                            edtEmail.getText().toString(),
                            edtPhone.getText().toString());
                }
                else
                    FancyToast.makeText(context,"Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_LONG,FancyToast.WARNING,true).show();
            }
        });
        Button btnHuy=findViewById(R.id.btnHuyMonHoc);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
