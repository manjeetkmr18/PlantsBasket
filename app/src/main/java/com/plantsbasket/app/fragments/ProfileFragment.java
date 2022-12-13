package com.plantsbasket.app.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.plantsbasket.app.R;
import com.plantsbasket.app.SignUpModel;
import com.plantsbasket.app.fire_store_data.UserCollectionOperations;

public class ProfileFragment extends Fragment {
    private View view;
    private EditText et_name, et_email, et_phone_number;
    private UserCollectionOperations userCollectionOperations;
    private ProgressDialog progressDialog;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";

    SharedPreferences sharedpreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        progressDialog = new ProgressDialog(getActivity());
        userCollectionOperations = new UserCollectionOperations();
        et_name = view.findViewById(R.id.et_name);
        et_email = view.findViewById(R.id.et_email);
        et_phone_number = view.findViewById(R.id.et_phone_number);
        setDataToViews();
        return view;
    }

    private void setDataToViews() {
        progressDialog.show();
        String userId =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        userCollectionOperations.getUserData(userId, new UserCollectionOperations.GetUserCollectionCallback() {
            @Override
            public void getUsersDataSuccess(SignUpModel signUpModel) {
                et_name.setText(signUpModel.getFullName());
                et_email.setText(signUpModel.getEmail());


                progressDialog.dismiss();
            }

            @Override
            public void getUsersDataFailure(String message) {
                Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profile");
    }
}