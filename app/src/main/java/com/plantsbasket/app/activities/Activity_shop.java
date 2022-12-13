package com.plantsbasket.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.plantsbasket.app.activities.interfaces.ItemClickListener;
import com.plantsbasket.app.adapter.CategoryRecyclerAdapter;
import com.plantsbasket.app.R;

import java.util.ArrayList;

public class Activity_shop extends AppCompatActivity {
    private RecyclerView rvMainCategory, rvMainPlants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_shop);

         rvMainCategory = findViewById(R.id.rvMainCategory);
         rvMainPlants = findViewById(R.id.rvMainPlants);

        setCategoryAdapter();
        setPlantsAdapter();
    }
    private void setCategoryAdapter() {
        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("Popular");
        categoryList.add("Organic");
        categoryList.add("Synthetic");
        categoryList.add("Indoors");
        categoryList.add("Small Plants");
        rvMainCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        CategoryRecyclerAdapter categoryRecyclerAdapter = new CategoryRecyclerAdapter(this, categoryList, new ItemClickListener() {
            @Override
            public void onClick(int position, String value) {

            }
        });
        rvMainCategory.setAdapter(categoryRecyclerAdapter);
    }

    private void setPlantsAdapter() {

    }
}