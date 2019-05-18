package com.nqc.tracuukaraoke;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.nqc.adapter.DropdownAdapter;
import com.nqc.impl.CharSelectedListener;

import java.util.ArrayList;


public class CharDropdownMenu extends PopupWindow {
    private Context context;
    private RecyclerView rvCategory;
    private DropdownAdapter dropdownAdapter;

    public CharDropdownMenu(Context context){
        super(context);
        this.context = context;
        setupView();
    }

    public void setCharSelectedListener(CharSelectedListener charSelectedListener) {
        dropdownAdapter.selected(charSelectedListener);
    }

    private void setupView() {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_char, null);

        rvCategory = view.findViewById(R.id.rvCategory);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvCategory.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        ArrayList<String>arrChar= new ArrayList<>();
        arrChar.add("A");
        arrChar.add("B");
        arrChar.add("C");
        arrChar.add("D");
        arrChar.add("E");
        arrChar.add("F");
        arrChar.add("G");
        arrChar.add("H");
        arrChar.add("I");
        arrChar.add("J");
        arrChar.add("K");
        arrChar.add("L");
        arrChar.add("M");
        arrChar.add("N");
        arrChar.add("O");
        arrChar.add("P");
        arrChar.add("Q");
        arrChar.add("R");
        arrChar.add("S");
        arrChar.add("T");
        arrChar.add("U");
        arrChar.add("V");
        arrChar.add("W");
        arrChar.add("X");
        arrChar.add("Y");
        arrChar.add("Z");
        dropdownAdapter= new DropdownAdapter(view.getContext(),arrChar);
        rvCategory.setAdapter(dropdownAdapter);
        setContentView(view);
    }
}
