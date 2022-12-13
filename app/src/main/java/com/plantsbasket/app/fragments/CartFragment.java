package com.plantsbasket.app.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.plantsbasket.app.CartModel;
import com.plantsbasket.app.PlantsModel;
import com.plantsbasket.app.R;
import com.plantsbasket.app.activities.DashboardActivity;
import com.plantsbasket.app.adapter.CartRecyclerAdapter;
import com.plantsbasket.app.fire_store_data.CartCollectionOperations;
import com.plantsbasket.app.fire_store_data.PlantCollectionOperations;

import java.util.ArrayList;


public class CartFragment extends Fragment {
    private static String TAG = CartModel.class.getSimpleName();
    Button bt_checkOut,bt_continue;
    private PlantCollectionOperations plantCollectionOperations;
    private CartCollectionOperations cartCollectionOperations;
    private ArrayList<PlantsModel> plantsModelArrayList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private RecyclerView view_cart_recycle;
    private TextView tv_price, tv_total;
    private CartRecyclerAdapter cartRecyclerAdapter;
    private float totalPrice = 0.0f;
    private float subtotalPrice = 0.0f;
    String theRemovedItemPrice;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view_cart_recycle = view.findViewById(R.id.view_cart_recycle);
        tv_price = view.findViewById(R.id.tv_price);
        tv_total = view.findViewById(R.id.tv_total);
        progressDialog = new ProgressDialog(getActivity());
        plantCollectionOperations = new PlantCollectionOperations();
        cartCollectionOperations = new CartCollectionOperations();

        setCartData();

        String userId = FirebaseAuth.getInstance().getUid();
        plantsModelArrayList.clear();
        progressDialog.show();
        cartCollectionOperations.getCartDataData(userId, new CartCollectionOperations.GetCartDataCollectionCallback() {
            @Override
            public void getCartDataSuccess(ArrayList<CartModel> cartList) {
                progressDialog.dismiss();
                totalPrice = 0;
                Log.e(TAG, "plantData---cartList---=" + new Gson().toJson(cartList));
                for (int i = 0; i < cartList.size(); i++) {
                    int finalI = i;
                    Log.e(TAG, "plantData---cartList.get(i).getPlantId()---=" + new Gson().toJson(cartList.get(i).getPlantId()));
                    plantCollectionOperations.getSpecificPlantsData(cartList.get(i).getPlantId(), new PlantCollectionOperations.GetSpecificPlantCallback() {
                        @Override
                        public void getSpecificPlantDataSuccess(PlantsModel plantModel) {
                            Log.e(TAG,"plantsModel----"+new Gson().toJson(plantModel));
                            progressDialog.dismiss();;
                            plantModel.setDocumentId(cartList.get(finalI).getDocumentId());
                            plantsModelArrayList.add(plantModel);
                            Log.e(TAG,"plantsModel----plantsModelArrayList--"+new Gson().toJson(plantsModelArrayList));
                            setTotalPrice(plantModel.getPrice());
                            cartRecyclerAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void getSpecificPlantDataFailure(String message) {
                            progressDialog.dismiss();
                        }
                    });
                }

            }

            @Override
            public void getCartDataFailure(String message) {
                progressDialog.dismiss();
            }
        });
        getActivity().setTitle("Cart");
        bt_checkOut = view.findViewById(R.id.bt_checkOut);
        bt_continue = view.findViewById(R.id.bt_continue);
        bt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(requireContext(), DashboardActivity.class);
                startActivity(move);
            }
        });
        bt_checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = new CheckOutFragment();
                fragment.setArguments(bundle);

                ft.addToBackStack(null);
                ft.replace(R.id.content_frame, fragment).commit();

            }
        });
    }

    private void setTotalPrice(String price) {
        if(price.contains("$")){
            price = price.replace("$","");
        }
        //  totalPrice = Float.parseFloat(price)* Integer.parseInt(quantity)  + totalPrice;
        totalPrice = Float.parseFloat(price)  + totalPrice;

        tv_price.setText("$"+totalPrice);
        tv_total.setText("$"+totalPrice);
    }

    private void setCartData() {
        view_cart_recycle.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        cartRecyclerAdapter = new CartRecyclerAdapter(getActivity(), plantsModelArrayList, new CartRecyclerAdapter.ItemClickListener() {
            @Override
            public void onClick(int position, String value) {
                deleteCartItem(position);
                plantsModelArrayList.remove(position);
                cartRecyclerAdapter. notifyItemRemoved(position);
                totalPrice = 0;
                for(int i  = 0; i < plantsModelArrayList.size(); i++){
                    String price = plantsModelArrayList.get(i).getPrice();
                    if(price.contains("$")){
                        price = price.replace("$", "");
                    }
                    float priceFlt = Float.parseFloat(price);
                    int countInt = plantsModelArrayList.get(i).getCount();
                    float fullPrice = priceFlt * countInt;
                    setTotalPrice(""+fullPrice);
                }

            }

            @Override
            public void cartPriceChange(int position, int count) {
                PlantsModel plantsModel = plantsModelArrayList.get(position);
                plantsModel.setCount(count);
                plantsModelArrayList.set(position, plantsModel);
                cartRecyclerAdapter.notifyDataSetChanged();
                totalPrice = 0;
                for(int i  = 0; i < plantsModelArrayList.size(); i++){
                    String price = plantsModelArrayList.get(i).getPrice();
                    if(price.contains("$")){
                        price = price.replace("$", "");
                    }
                    float priceFlt = Float.parseFloat(price);
                    int countInt = plantsModelArrayList.get(i).getCount();
                    float fullPrice = priceFlt * countInt;
                    setTotalPrice(""+fullPrice);
                }
            }
        });
        view_cart_recycle.setAdapter(cartRecyclerAdapter);

    }

    private void deleteCartItem(int position){
        PlantsModel plantsModel = plantsModelArrayList.get(position);
        String documentId = plantsModel.getDocumentId();
        theRemovedItemPrice = plantsModel.getPrice();
        if(theRemovedItemPrice.contains("$")){
            theRemovedItemPrice = theRemovedItemPrice.replace("$","");
        }


        cartCollectionOperations.deleteCartData(documentId, new CartCollectionOperations.DeleteCartCollectionCallback() {
            @Override
            public void deleteCartDataSuccess() {
                Log.e(TAG,"success");
                totalPrice =   totalPrice-Float.parseFloat(theRemovedItemPrice);
                tv_price.setText("$"+totalPrice);
                tv_total.setText("$"+totalPrice);
                // setTotalPrice(String.valueOf(subtotalPrice));

            }

            @Override
            public void deleteCartDataFailure(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}