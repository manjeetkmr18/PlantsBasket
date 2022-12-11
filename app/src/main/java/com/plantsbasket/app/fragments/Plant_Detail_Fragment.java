package com.plantsbasket.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.plantsbasket.app.activities.DashboardActivity;
import com.plantsbasket.app.fire_store_data.CartCollectionOperations;
import com.plantsbasket.app.fire_store_data.Constants;
import com.plantsbasket.app.PlantsModel;
import com.plantsbasket.app.R;


public class Plant_Detail_Fragment extends Fragment {
    private Button bt_add_cart,bt_cancel;
    private TextView product_name, product_price, product_desc;
    private ImageView product_image;
    private PlantsModel plantsModel = null;
    private CartCollectionOperations cartCollectionOperations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plant_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartCollectionOperations = new CartCollectionOperations();
        Bundle bundle = getArguments();
        if(bundle != null){
            if(bundle.containsKey(Constants.INTENT_PLANT_INFO)){
                String data = bundle.getString(Constants.INTENT_PLANT_INFO);
                plantsModel = new Gson().fromJson(data, PlantsModel.class);
            }
        }

        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Plant Detail");
        product_image = view.findViewById(R.id.product_image);
        product_name = view.findViewById(R.id.product_name);
        product_price = view.findViewById(R.id.product_price);
        product_desc = view.findViewById(R.id.product_desc);

        setProductsDetails();
        bt_add_cart = view.findViewById(R.id.bt_add_cart);
        bt_cancel = view.findViewById(R.id.bt_cancel);

        bt_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = FirebaseAuth.getInstance().getUid();

                cartCollectionOperations.saveOrEditCartData(userId, plantsModel.getPlantId(), new CartCollectionOperations.SaveCartCollectionCallback() {
                    @Override
                    public void saveCartDataSuccess() {
                        Bundle bundle = new Bundle();
                        FragmentManager fm =  getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment fragment =new CartFragment();
                        fragment.setArguments(bundle);

                        ft.addToBackStack(null);
                        ft.replace(R.id.content_frame, fragment).commit();
                    }

                    @Override
                    public void saveCartDataFailure(String message) {
                        Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                FragmentManager fm =  getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment =new ShopFragment();
                fragment.setArguments(bundle);
                ft.addToBackStack(null);
                ft.replace(R.id.content_frame, fragment).commit();
            }
        });

    }

    private void setProductsDetails() {
        String imgSrc = plantsModel.getPlantImageSrc();
        Glide.with(getActivity())
                .load(imgSrc)
                .centerCrop()
                .placeholder(R.drawable.image_place_holder)
                .into(product_image);

        String name = plantsModel.getName();
        product_name.setText(name);

        String price = plantsModel.getPrice();
        product_price.setText(price);

        String description = plantsModel.getDescription();
        product_desc.setText(description);
    }
}