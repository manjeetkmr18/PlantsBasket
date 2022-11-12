package   com.plantsbasket.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created on 27/09/22
 */
public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<String> categoryList;
    private static int SELECTED_POSITION = 0;

    public CategoryRecyclerAdapter(Activity context, ArrayList<String> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvItemCategoryName.setText(categoryList.get(position));
        if(SELECTED_POSITION == position){
            holder.tvItemCategoryName.setAlpha(1f);
            holder.tvItemCategoryName.setTextSize(14f);
        }else{
            holder.tvItemCategoryName.setAlpha(0.6f);
            holder.tvItemCategoryName.setTextSize(12f);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECTED_POSITION = position;
                notifyDataSetChanged();
                context.startActivity(new Intent(context, Activity_Checkout.class));
            }
        });
    }



    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemCategoryName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemCategoryName = itemView.findViewById(R.id.tvItemCategoryName);
        }
    }
}
