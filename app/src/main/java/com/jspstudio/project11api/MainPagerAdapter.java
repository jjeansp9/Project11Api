package com.jspstudio.project11api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainPagerAdapter extends RecyclerView.Adapter<MainPagerAdapter.VH> {

    Context context;
    ArrayList<MainActivityPageItem> mainItems;

    public MainPagerAdapter(Context context, ArrayList<MainActivityPageItem> mainItems) {
        this.context = context;
        this.mainItems = mainItems;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View itemView= layoutInflater.inflate(R.layout.activity_main_page_item, parent, false);

        VH holder= new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        MainActivityPageItem mainActivityPageItem= mainItems.get(position);

        holder.tvMainCafeName.setText(mainActivityPageItem.mainCafeName);
        holder.tvMainCafeContents.setText(mainActivityPageItem.mainCafeContents);

        Glide.with(context).load(mainActivityPageItem.mainCafeImg).into(holder.tvMainCafeImg);
    }

    @Override
    public int getItemCount() {
        return mainItems.size();
    }

    class VH extends RecyclerView.ViewHolder{

        ImageView tvMainCafeImg;
        TextView tvMainCafeName, tvMainCafeContents;


        public VH(@NonNull View itemView) {
            super(itemView);

            tvMainCafeImg= itemView.findViewById(R.id.main_cafe_img);
            tvMainCafeName= itemView.findViewById(R.id.main_cafe_name);
            tvMainCafeContents= itemView.findViewById(R.id.main_cafe_contents);

        }
    }
}
