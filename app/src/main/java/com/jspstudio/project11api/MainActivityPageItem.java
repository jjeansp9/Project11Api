package com.jspstudio.project11api;

import android.widget.ImageView;
import android.widget.TextView;

public class MainActivityPageItem {


    String mainCafeImg // 카페이미지
            , mainCafeName // 카페이름
            , mainCafeContents; // 카페 소개

    public MainActivityPageItem(String mainCafeImg, String mainCafeName, String mainCafeContents) {
        this.mainCafeImg = mainCafeImg;
        this.mainCafeName = mainCafeName;
        this.mainCafeContents = mainCafeContents;
    }

    public MainActivityPageItem() {
    }
}
