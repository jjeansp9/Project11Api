package com.jspstudio.project11api;

public class CafeItem {

    String cafeImage; // 카페 이미지
    String cafeName; // 카페사업장명
    String cafeOpen; // 카페 전화번호
    String cafeAddress; // 카페 주소
    int cafeFavorite; // 카페 찜

    public CafeItem(String cafeImage, String cafeName, String cafeOpen, String cafeAddress, int cafeFavorite) {
        this.cafeImage = cafeImage;
        this.cafeName = cafeName;
        this.cafeOpen = cafeOpen;
        this.cafeAddress = cafeAddress;
        this.cafeFavorite = cafeFavorite;
    }

    public CafeItem() {
    }




}
