package com.nqc.tracuukaraoke;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import static android.support.v4.content.res.ResourcesCompat.getDrawable;


public class TroGiupFragment extends Fragment {
    TabHost tabHost;
    TextView txt_call, txt_send;
    EditText edt_send;
    Button btn_send;
    LinearLayout lnl_send;

    public static String so = "0356484803";

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_tro_giup, container, false);

        addControls();
        addEvents();
        return view;
    }

    private void addEvents() {
        txt_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(so)) {
                    String dial = "tel:" + so;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                } else {
                    Toast.makeText(view.getContext(), "Nhap vao so dien thoai", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_send.setOnClickListener(new View.OnClickListener() {
            boolean visible;

            @Override
            public void onClick(View v) {
                visible = !visible;
                lnl_send.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tin = edt_send.getText().toString();
                String send = "smsto:" + so;
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(send));
                intent.putExtra("sms_body", tin);
                startActivity(intent);
            }
        });

    }

    private void addControls() {
        tabHost = view.findViewById(R.id.tabHost);
        tabHost.setup();

        txt_call = view.findViewById(R.id.txt_call);
        txt_send = view.findViewById(R.id.txt_send);
        edt_send = view.findViewById(R.id.edt_send);
        btn_send = view.findViewById(R.id.btn_send);
        lnl_send = view.findViewById(R.id.lnl_send);

        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setIndicator("Trợ Giúp");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setIndicator("Liên Hệ");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);
    }

}
