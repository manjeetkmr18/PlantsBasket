package com.plantsbasket.app.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.plantsbasket.app.PlantsModel;
import com.plantsbasket.app.R;
import com.plantsbasket.app.activities.interfaces.ItemClickListener;
import com.plantsbasket.app.adapter.CategoryRecyclerAdapter;
import com.plantsbasket.app.adapter.PlantsRecyclerAdapter;

import java.util.ArrayList;

public class ShopFragment extends Fragment {
    private RecyclerView rvMainCategory, rvMainPlants;
    ItemClickListener itemClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Shop");
        rvMainCategory = view.findViewById(R.id.rvMainCategory);
        rvMainPlants =view. findViewById(R.id.rvMainPlants);

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
        rvMainCategory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        CategoryRecyclerAdapter categoryRecyclerAdapter = new CategoryRecyclerAdapter(requireActivity(), categoryList);
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
// Initialize listener
        itemClickListener=new ItemClickListener() {
            @Override
            public void onClick(int position, String value) {

                Bundle bundle = new Bundle();
                FragmentManager fm =  getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment =new Plant_Detail_Fragment();
                fragment.setArguments(bundle);

                ft.addToBackStack(null);
                ft.replace(R.id.content_frame, fragment).commit();


                // Display toast
              /*  Toast.makeText(requireContext(),"Position : "
                        +position +" || Value : "+value,Toast.LENGTH_SHORT).show();*/
            }
        };
        rvMainPlants.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        PlantsRecyclerAdapter plantsRecyclerAdapter = new PlantsRecyclerAdapter(requireActivity(), plantsModelArrayList,itemClickListener);
        rvMainPlants.setAdapter(plantsRecyclerAdapter);
    }
}