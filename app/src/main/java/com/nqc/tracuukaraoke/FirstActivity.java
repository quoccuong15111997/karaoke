package com.nqc.tracuukaraoke;

import android.content.Intent;
import android.os.Bundle;

import com.nqc.sharedpreferences.SharedPreferencesManager;
import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughActivity;
import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughCard;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends FancyWalkthroughActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FancyWalkthroughCard fancywalkthroughCard1 = new FancyWalkthroughCard("Tìm Kiếm Dễ Dàng", "Tìm kiếm mã bài hát, tên bài hát, ca sĩ... với bản cập nhật Karaoke mới nhất.",R.drawable.ic_seach_song);
        FancyWalkthroughCard fancywalkthroughCard2 = new FancyWalkthroughCard("Bài Hát yêu Thích", "Có thể lưu lại danh sách các bài hát yêu thích, đồng bộ với các thiết bị khác một cách dễ dàng",R.drawable.ic_favorite_song);
        FancyWalkthroughCard fancywalkthroughCard3 = new FancyWalkthroughCard("Hát Online Ngay Trên Ứng Dụng", "Có thể hát Online cực chuẩn từ kho Video khổng lồ Youtube.",R.drawable.ic_youtube_sing);
        FancyWalkthroughCard fancywalkthroughCard4 = new FancyWalkthroughCard("Danh Sách Quán Karaoke", "Có thể xem danh sách các quán Karaoke, vị trí các quán Karaoke trên bản đồ ngay trên ứng dụng.",R.drawable.ic_maps);

        fancywalkthroughCard1.setBackgroundColor(R.color.white);
        fancywalkthroughCard1.setIconLayoutParams(300,300,0,0,0,0);
        fancywalkthroughCard2.setBackgroundColor(R.color.white);
        fancywalkthroughCard2.setIconLayoutParams(300,300,0,0,0,0);
        fancywalkthroughCard3.setBackgroundColor(R.color.white);
        fancywalkthroughCard3.setIconLayoutParams(300,300,0,0,0,0);
        fancywalkthroughCard4.setBackgroundColor(R.color.white);
        fancywalkthroughCard4.setIconLayoutParams(300,300,0,0,0,0);
        List<FancyWalkthroughCard> pages = new ArrayList<>();

        pages.add(fancywalkthroughCard1);
        pages.add(fancywalkthroughCard2);
        pages.add(fancywalkthroughCard3);
        pages.add(fancywalkthroughCard4);

        for (FancyWalkthroughCard page : pages) {
            page.setTitleColor(R.color.black);
            page.setDescriptionColor(R.color.black);
        }
        setFinishButtonTitle("Bắt đầu nào");
        showNavigationControls(true);
        setColorBackground(R.color.colorGreen);
        //setImageBackground(R.drawable.restaurant);
        setInactiveIndicatorColor(R.color.grey_600);
        setActiveIndicatorColor(R.color.colorGreen);
        setOnboardPages(pages);

    }

    @Override
    public void onFinishButtonPressed() {
        SharedPreferencesManager.setFirstTimeSetup(false);
        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}
