package com.plantsbasket.app.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.plantsbasket.app.PlantsModel;
import com.plantsbasket.app.R;
import com.plantsbasket.app.activities.interfaces.ItemClickListener;
import com.plantsbasket.app.fire_store_data.Constants;

import java.util.ArrayList;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder>{
    ItemClickListener itemClickListener;
    private Activity context;
    private ArrayList<PlantsModel> plantsList;
    private static int SELECTED_POSITION = 0;
    public CartRecyclerAdapter(Activity context, ArrayList<PlantsModel> plantsList,ItemClickListener itemClickListener) {
        this.context = context;
        this.plantsList = plantsList;
        this.itemClickListener = itemClickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvItemPlantCategory.setText(plantsList.get(position).getCategory());
        holder.tvItemPlantName.setText(plantsList.get(position).getName());
        holder.tvItemPlantLocation.setText(plantsList.get(position).getLocation());
        holder.tvItemPlantPrice.setText(plantsList.get(position).getPrice());
        String imgSrc = plantsList.get(position).getPlantImageSrc();
        Glide.with(context)
                .load(imgSrc)
                .centerCrop()
                .placeholder(R.drawable.image_place_holder)
                .into(holder.ivPlantsImg);
        String price = plantsList.get(position).getPrice();
        if(price.contains("$")){
            price = price.replace("$", "");
        }
        float priceFlt = Float.parseFloat(price);
        int countStr = plantsList.get(position).getCount();
        holder.tv_count.setText(""+countStr);
        float fullPrice = priceFlt * countStr;
        holder.tvItemPlantPrice.setText("$"+fullPrice);
        //holder.ivPlantsImg.setImageDrawable(plantsList.get(position).getPlantImage());
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get adapter position
                int position=holder.getAdapterPosition();
                // call listener
                itemClickListener.onClick(position, String.valueOf(plantsList.get(position)));
                // update position
                SELECTED_POSITION=position;
                // notify
                // notifyDataSetChanged();
            }
        });*/
        holder.b_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count= Integer.parseInt(String.valueOf(holder.tv_count.getText()));
                count++;
                holder.tv_count.setText("" + count);
                itemClickListener.cartPriceChange(position, count);
                String price = plantsList.get(position).getPrice();
                if(price.contains("$")){
                    price = price.replace("$", "");
                }
                float priceFlt = Float.parseFloat(price);
                String countStr = holder.tv_count.getText().toString().trim();
                float fullPrice = priceFlt * Integer.parseInt(countStr);
                holder.tvItemPlantPrice.setText("$"+fullPrice);
                // notifyDataSetChanged();
            }
        });
        holder.b_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count= Integer.parseInt(String.valueOf(holder.tv_count.getText()));
                if (count == 1) {
                    holder.tv_count.setText("1");
                } else {
                    count -= 1;
                    holder.tv_count.setText("" + count);
                }
                itemClickListener.cartPriceChange(position, count);
                String price = plantsList.get(position).getPrice();
                if(price.contains("$")){
                    price = price.replace("$", "");
                }
                float priceFlt = Float.parseFloat(price);
                String countStr = holder.tv_count.getText().toString().trim();
                float fullPrice = priceFlt * Integer.parseInt(countStr);
                holder.tvItemPlantPrice.setText("$"+fullPrice);

                // notifyDataSetChanged();
            }
        });

        holder.iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get adapter position
                int position=holder.getAdapterPosition();
                // call listener
                itemClickListener.onClick(position, String.valueOf(plantsList.get(position)));
                // update position
                SELECTED_POSITION=position;

            }
        });
    }

    @Override
    public int getItemCount() {
        return plantsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemPlantCategory, tvItemPlantName, tvItemPlantLocation, tvItemPlantPrice,tv_count;
        ImageView ivPlantsImg,b_positive,b_negative,iv_remove;
        int cartCount =1;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemPlantCategory = itemView.findViewById(R.id.tvItemPlantCategory);
            tvItemPlantName = itemView.findViewById(R.id.tv_name);
            tvItemPlantLocation = itemView.findViewById(R.id.tvItemPlantLocation);
            tvItemPlantPrice = itemView.findViewById(R.id.tv_price);
            ivPlantsImg = itemView.findViewById(R.id.iv_image);
            iv_remove = itemView.findViewById(R.id.iv_remove);
            b_positive = itemView.findViewById(R.id.b_positive);
            b_negative = itemView.findViewById(R.id.b_negative);
            tv_count = itemView.findViewById(R.id.tv_count);
        }


    }


    public interface ItemClickListener {
        void onClick(int position,String value);
        void cartPriceChange(int position, int count);
    }
}
