package com.plantsbasket.app.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.plantsbasket.app.PlantsModel;
import com.plantsbasket.app.ProgressDialog;
import com.plantsbasket.app.fire_store_data.Constants;
import com.plantsbasket.app.fire_store_data.PlantCollectionOperations;
import com.plantsbasket.app.R;
import com.plantsbasket.app.activities.interfaces.ItemClickListener;
import com.plantsbasket.app.adapter.CategoryRecyclerAdapter;
import com.plantsbasket.app.adapter.PlantsRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {
    private static String TAG = ShopFragment.class.getSimpleName();
    private RecyclerView rvMainCategory, rvMainPlants;
    ItemClickListener itemClickListener;
    private PlantCollectionOperations plantCollectionOperations;
    ArrayList<PlantsModel> plantsModelFullArrayList = new ArrayList<>();
    ArrayList<PlantsModel> plantsModelArrayList = new ArrayList<>();
    private PlantsRecyclerAdapter plantsRecyclerAdapter;
    private ProgressDialog progressDialog;

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
        progressDialog = new ProgressDialog(getActivity());
        plantCollectionOperations = new PlantCollectionOperations();
        getActivity().setTitle("Shop");
        rvMainCategory = view.findViewById(R.id.rvMainCategory);
        rvMainPlants =view. findViewById(R.id.rvMainPlants);

        getPlantData();

    }

    private void getPlantData() {
        plantsModelFullArrayList.clear();
        plantsModelArrayList.clear();
        progressDialog.show();
        plantCollectionOperations.getPlantsData(new PlantCollectionOperations.GetPlantCollectionCallback() {
            @Override
            public void getPlantDataSuccess(List<PlantsModel> plantList) {
                plantsModelFullArrayList.addAll(plantList);
                plantsModelArrayList.addAll(plantList);
                setCategoryAdapter();
                setPlantsAdapter();
                progressDialog.dissmiss();
            }

            @Override
            public void getPlantDataFailure(String message) {
                Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                progressDialog.dissmiss();
            }
        });
    }

    private void setCategoryAdapter() {
        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("Popular");
        categoryList.add("Organic");
        categoryList.add("Synthetic");
        categoryList.add("Indoors");
        categoryList.add("Small Plants");
        rvMainCategory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        CategoryRecyclerAdapter categoryRecyclerAdapter = new CategoryRecyclerAdapter(requireActivity(), categoryList, new ItemClickListener() {
            @Override
            public void onClick(int position, String value) {
                if(position == 0){
                    plantsModelArrayList.clear();
                    plantsModelArrayList.addAll(plantsModelFullArrayList);
                    plantsRecyclerAdapter.notifyDataSetChanged();
                }else{
                    String categoryName = categoryList.get(position).toLowerCase();
                    Log.e(TAG,"categoryName---="+categoryName);
                    Log.e(TAG,"categoryName---innerCategoryName--="+new Gson().toJson(plantsModelFullArrayList));
                    plantsModelArrayList.clear();
                    for(int i = 0; i < plantsModelFullArrayList.size(); i++) {
                        String innerCategoryName = plantsModelFullArrayList.get(i).getCategory().toLowerCase();
                        if(categoryName.equalsIgnoreCase(innerCategoryName)) {
                            plantsModelArrayList.add(plantsModelFullArrayList.get(position));
                        }
                    }
                    plantsRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });
        rvMainCategory.setAdapter(categoryRecyclerAdapter);
    }

    private void setPlantsAdapter() {

        itemClickListener=new ItemClickListener() {
            @Override
            public void onClick(int position, String value) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.INTENT_PLANT_INFO, new Gson().toJson(plantsModelArrayList.get(position)));
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
        plantsRecyclerAdapter = new PlantsRecyclerAdapter(requireActivity(), plantsModelArrayList,itemClickListener);
        rvMainPlants.setAdapter(plantsRecyclerAdapter);
    }
}