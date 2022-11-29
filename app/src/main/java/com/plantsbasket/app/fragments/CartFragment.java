package com.plantsbasket.app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.plantsbasket.app.R;
import com.plantsbasket.app.activities.Activity_Checkout;
import com.plantsbasket.app.activities.Dashboard_Activity;


public class CartFragment extends Fragment {
    Button bt_checkOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Cart");
        bt_checkOut = view.findViewById(R.id.bt_checkOut);
        bt_checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                FragmentManager fm =  getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment =new CheckOutFragment();
                fragment.setArguments(bundle);

                ft.addToBackStack(null);
                ft.replace(R.id.content_frame, fragment).commit();

            }
        });
    }
}