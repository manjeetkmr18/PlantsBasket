package com.plantsbasket.app.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plantsbasket.app.PlantsModel;
import com.plantsbasket.app.R;
import com.plantsbasket.app.activities.Activity_shop;
import com.plantsbasket.app.activities.interfaces.ItemClickListener;


import java.util.ArrayList;

public class PlantsRecyclerAdapter extends RecyclerView.Adapter<PlantsRecyclerAdapter.ViewHolder>{
    ItemClickListener itemClickListener;
    private Activity context;
    private ArrayList<PlantsModel> plantsList;
    private static int SELECTED_POSITION = 0;
    public PlantsRecyclerAdapter(Activity context, ArrayList<PlantsModel> plantsList,ItemClickListener itemClickListener) {
        this.context = context;
        this.plantsList = plantsList;
        this.itemClickListener = itemClickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plants, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvItemPlantCategory.setText(plantsList.get(position).getCategory());
        holder.tvItemPlantName.setText(plantsList.get(position).getName());
        holder.tvItemPlantLocation.setText(plantsList.get(position).getLocation());
        holder.tvItemPlantPrice.setText(plantsList.get(position).getPrice());
        holder.ivPlantsImg.setImageDrawable(plantsList.get(position).getPlantImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get adapter position
                int position=holder.getAdapterPosition();
                // call listener
                itemClickListener.onClick(position, String.valueOf(plantsList.get(position)));
                // update position
                SELECTED_POSITION=position;
                // notify
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return plantsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemPlantCategory, tvItemPlantName, tvItemPlantLocation, tvItemPlantPrice;
        ImageView ivPlantsImg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemPlantCategory = itemView.findViewById(R.id.tvItemPlantCategory);
            tvItemPlantName = itemView.findViewById(R.id.tvItemPlantName);
            tvItemPlantLocation = itemView.findViewById(R.id.tvItemPlantLocation);
            tvItemPlantPrice = itemView.findViewById(R.id.tvItemPlantPrice);
            ivPlantsImg = itemView.findViewById(R.id.ivPlantsImg);
        }


    }
}
