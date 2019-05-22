package com.nqc.tracuukaraoke;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.nqc.adapter.DropdownAdapter;
import com.nqc.adapter.DropdownQuanKaraokeAdapter;
import com.nqc.firebase.QuanKaraFirebase;
import com.nqc.impl.CharSelectedListener;
import com.nqc.impl.QuanKaraSelectedListener;
import com.nqc.impl.SeachTextWatch;
import com.nqc.model.QuanKaraoke;

import java.util.ArrayList;


public class QuanKaraDropdownMenu extends PopupWindow {
    View view;
    private Context context;
    private ArrayList<QuanKaraFirebase> karaokes;
    private RecyclerView rvCategory;
    private DropdownQuanKaraokeAdapter dropdownAdapter;
    FloatingActionButton fabAdd;


    public QuanKaraDropdownMenu(Context context, ArrayList<QuanKaraFirebase> karaokes) {
        super(context);
        this.context = context;
        this.karaokes = karaokes;
        setupView();
    }

    public void setQuanKaraListener(QuanKaraSelectedListener quanKaraListener) {
        dropdownAdapter.selected(quanKaraListener);
    }

    private void setupView() {
        view = LayoutInflater.from(context).inflate(R.layout.popup_quan_kara, null);
        addControls();
        addEvents();
        setContentView(view);
    }

    private void addEvents() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(view.getContext(),ThemQuanKaraokeActivity.class);
                context.startActivity(intent);
            }
        });
    }

    private void addControls() {
        rvCategory = view.findViewById(R.id.rvCategory);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvCategory.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        dropdownAdapter = new DropdownQuanKaraokeAdapter(view.getContext(), karaokes);
        rvCategory.setAdapter(dropdownAdapter);
        fabAdd=view.findViewById(R.id.fabAdd);
    }
}
