package com.jspstudio.project11api;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.VH> {


    boolean favorite= true;

    public interface OnItemClickListener{ // OnItemClickListener 인터페이스 선언
        void onItemClicked(int position, String data);
    }

    // OnItemClickListener 참조변수 선언
    private OnItemClickListener itemClickListener;

    // OnItemClickListener 전달 메소드
    public void setOnItemClickListener (OnItemClickListener listener) {
        itemClickListener = listener;
    }

    Context context;
    ArrayList<CafeItem> cafeItems;

    public CafeAdapter(Context context, ArrayList<CafeItem> cafeItems) {
        this.context = context;
        this.cafeItems = cafeItems;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View itemView= layoutInflater.inflate(R.layout.recycler_cafe, parent, false);


        CafeAdapter.VH holder= new CafeAdapter.VH(itemView);

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        holder.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data= "";
                int position= holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    data= holder.getImageView().toString();
                }
                itemClickListener.onItemClicked(position, data);

                // 하트모양 아이콘 클릭시마다 이미지변경
                if ( favorite == true) {
                    holder.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_25);
                    Toast.makeText(context, "해당 문화공간을 즐겨찾기에 추가했습니다.", Toast.LENGTH_SHORT).show();
                    favorite= false;
                }
                else {
                    holder.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
                    Toast.makeText(context, "해당 문화공간을 즐겨찾기목록에서 삭제했습니다.", Toast.LENGTH_SHORT).show();
                    favorite= true;
                }

            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        CafeItem cafeItem= cafeItems.get(position);

        holder.tvCafeName.setText(cafeItem.cafeName); // 카페이름
        holder.tvCafeTel.setText(cafeItem.cafeOpen); // 카페전화번호
        holder.tvCafeAddress.setText(cafeItem.cafeAddress); // 카페주소

        Glide.with(context).load(cafeItem.cafeImage).into(holder.ivCafe); // 카페이미지
        Glide.with(context).load(cafeItem.cafeFavorite).into(holder.ivFavorite); // 카페 찜



    }

    @Override
    public int getItemCount() {
        return cafeItems.size();
    }

    // 검색기능
    public void setCafeItems(ArrayList<CafeItem> list){
        cafeItems= list;
        notifyDataSetChanged();
    }



    class VH extends RecyclerView.ViewHolder{

        ImageView ivCafe, ivFavorite;
        TextView tvCafeName, tvCafeTel, tvCafeAddress;
        LinearLayout root;

        public VH(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            ivFavorite= itemView.findViewById(R.id.iv_favorite);
            ivCafe= itemView.findViewById(R.id.iv_cafe);
            tvCafeName= itemView.findViewById(R.id.tv_cafe_name);
            tvCafeTel= itemView.findViewById(R.id.tv_cafe_open);
            tvCafeAddress= itemView.findViewById(R.id.tv_cafe_address);

        }

        public ImageView getImageView(){
            return ivFavorite;
        }

        public void setImageView (ImageView imageView){
            this.ivFavorite= imageView;
        }
    } // VH class


}// cafeAdapter class
