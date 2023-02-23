package com.jspstudio.project11api;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    String apiKey= "634657647461623937385051666773";

    ArrayList<MainActivityPageItem> mainItems= new ArrayList<>();
    MainPagerAdapter mainPagerAdapter;
    ViewPager2 pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.img_home_cafe).setOnClickListener(view -> cafeList());
        findViewById(R.id.btn_left).setOnClickListener(view -> clickLeft());
        findViewById(R.id.btn_right).setOnClickListener(view -> clickRight());

        pager= findViewById(R.id.main_pager);
        mainPagerAdapter= new MainPagerAdapter(this, mainItems);
        pager.setAdapter(mainPagerAdapter);

        MainThread thread= new MainThread();
        thread.start();


    } // onCreate

    // 클릭하면 화면을 전환해주는 메소드
    void cafeList(){

        Intent intent= new Intent(this, CafeActivity.class);
        startActivity(intent);

    } // cafeList()

    void clickLeft(){

    }

    void clickRight(){


    }

    class MainThread extends Thread{
        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mainItems.clear();
                    mainPagerAdapter.notifyDataSetChanged();
                }
            });


            String address= "http://openapi.seoul.go.kr:8088/"
                    + apiKey
                    + "/xml/culturalSpaceInfo/1/50/";

            try {
                URL url= new URL(address);

                InputStream inputStream= url.openStream();
                InputStreamReader inputStreamReader= new InputStreamReader(inputStream);

                XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
                XmlPullParser xpp= factory.newPullParser();

                xpp.setInput(inputStreamReader);

                int eventType= xpp.getEventType();

                MainActivityPageItem mainItem= null;

                while (eventType != XmlPullParser.END_DOCUMENT){

                    switch (eventType){
                        case  XmlPullParser.START_DOCUMENT:
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mainPagerAdapter.notifyDataSetChanged();
                                }
                            });
                            break;
                        case XmlPullParser.START_TAG:
                            String tagName= xpp.getName();

                            if(tagName.equals("row")){
                                mainItem= new MainActivityPageItem();

                            }else if(tagName.equals("FAC_NAME")){ // 이름
                                xpp.next();
                                mainItem.mainCafeName= xpp.getText();

                            }else if(tagName.equals("FAC_DESC")){ // 내용
                                xpp.next();
                                mainItem.mainCafeContents= xpp.getText();

                            }else if(tagName.equals("MAIN_IMG")){ // 이미지
                                xpp.next();
                                mainItem.mainCafeImg= xpp.getText();
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;

                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("row")){
                                mainItems.add(mainItem);
                            }
                            break;


                    } // switch
                    eventType= xpp.next();
                } // while




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }


        }
    }




}



























