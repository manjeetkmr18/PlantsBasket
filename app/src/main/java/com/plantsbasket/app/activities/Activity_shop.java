package com.plantsbasket.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.plantsbasket.app.adapter.CategoryRecyclerAdapter;
import com.plantsbasket.app.PlantsModel;
import com.plantsbasket.app.adapter.PlantsRecyclerAdapter;
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
        CategoryRecyclerAdapter categoryRecyclerAdapter = new CategoryRecyclerAdapter(this, categoryList);
        rvMainCategory.setAdapter(categoryRecyclerAdapter);
    }

    private void setPlantsAdapter() {

        ArrayList<PlantsModel> plantsModelArrayList = new ArrayList<>();
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_1), "Brooklyn", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_2), "London", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_3), "Japan", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_4), "Leighton", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_5), "Brooklyn", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_1), "Brooklyn", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_2), "London", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_3), "Japan", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_4), "Leighton", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_5), "Brooklyn", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_1), "Brooklyn", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_2), "London", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_3), "Japan", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_4), "Leighton", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_5), "Brooklyn", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_1), "Brooklyn", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_2), "London", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_3), "Japan", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_4), "Leighton", "Succulent", "Organic", "$35.99"));
        plantsModelArrayList.add(new PlantsModel(getResources().getDrawable(R.drawable.plant_image_5), "Brooklyn", "Succulent", "Organic", "$35.99"));

     /*   rvMainPlants.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        PlantsRecyclerAdapter plantsRecyclerAdapter = new PlantsRecyclerAdapter(this, plantsModelArrayList);

        rvMainPlants.setAdapter(plantsRecyclerAdapter);*/
    }
}