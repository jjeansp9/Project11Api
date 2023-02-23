package com.jspstudio.project11api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;


public class CafeActivity extends AppCompatActivity {


    String apiKey= "634657647461623937385051666773";

    ArrayList<CafeItem> cafeItems= new ArrayList<>();
    ArrayList<CafeItem> cafeItemsSearch= new ArrayList<>(); // 검색한 데이터를 얻어오기위한 ArrayList

    RecyclerView recyclerView;
    CafeAdapter adapter;

    EditText etSearch;

    ProgressDialog dialog= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);

        recyclerView= findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter= new CafeAdapter(this, cafeItems);
        recyclerView.setAdapter(adapter);

        etSearch= findViewById(R.id.et_search);

        adapter.setOnItemClickListener(new CafeAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, String data) {

            }
        });



        search(); // 검색기능 불러오기

        CafeThread thread= new CafeThread();
        thread.start();

        Toast.makeText(this, "목록으로 이동", Toast.LENGTH_SHORT).show();

        dialog(); // 프로그래스바 불러오기


    }// onCreate()

    // ProgressDialog
    Handler handler= new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            dialog.dismiss();
            dialog= null;
        }
    };

    // ProgressDialog method
    void dialog(){
        if(dialog != null) return;

        dialog= new ProgressDialog(this);
        dialog.setTitle("잠시만 기다려주세요...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        handler.sendEmptyMessageDelayed(0, 1000);
    }


    // 검색기능 메소드
    void search (){
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText= etSearch.getText().toString();
                cafeItemsSearch.clear();

                if(searchText.equals("")){
                    adapter.setCafeItems(cafeItems);
                }else{
                    for(int i= 0; i< cafeItems.size(); i++){
                        if(cafeItems.get(i).cafeName.toLowerCase().contains(searchText.toLowerCase())||cafeItems.get(i).cafeAddress.toLowerCase().contains(searchText.toLowerCase())||cafeItems.get(i).cafeOpen.toLowerCase().contains(searchText.toLowerCase())){
                            cafeItemsSearch.add(cafeItems.get(i));
                        }
                        adapter.setCafeItems(cafeItemsSearch);
                    }
                }
            }
        });

    }// search()

    // Open API를 얻어와서 파싱작업하는 스레드 클래스
    class CafeThread extends Thread{
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cafeItems.clear();
                    adapter.notifyDataSetChanged();
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

                CafeItem cafeItem= null;

                while (eventType != XmlPullParser.END_DOCUMENT){

                    switch (eventType){
                        case XmlPullParser.START_DOCUMENT:
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                            });
                            break;
                        case XmlPullParser.START_TAG:
                            String tagName= xpp.getName();

                            if(tagName.equals("row")){
                                cafeItem= new CafeItem();

                            }else if(tagName.equals("FAC_NAME")){ // 카페명
                                xpp.next();
                                cafeItem.cafeName= xpp.getText();

                            }else if(tagName.equals("PHNE")){ // 카페 인트로
                                xpp.next();
                                cafeItem.cafeOpen= xpp.getText();

                            }else if(tagName.equals("ADDR")){ // 카페 주소
                                xpp.next();
                                cafeItem.cafeAddress= xpp.getText();
                            }else if(tagName.equals("MAIN_IMG")){ // 카페 이미지
                                xpp.next();
                                cafeItem.cafeImage= xpp.getText();
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;

                        case XmlPullParser.END_TAG:
                            if(xpp.getName().equals("row")){
                                cafeItems.add(cafeItem);
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

        }// run

    };// Thread

}// CafeActivity class
