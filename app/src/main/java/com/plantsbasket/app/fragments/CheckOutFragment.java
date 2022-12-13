package com.plantsbasket.app.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.plantsbasket.app.R;
import com.plantsbasket.app.SignUpModel;
import com.plantsbasket.app.activities.DashboardActivity;
import com.plantsbasket.app.fire_store_data.CartCollectionOperations;
import com.plantsbasket.app.fire_store_data.UserCollectionOperations;

import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;


public class CheckOutFragment extends Fragment {
    AlertDialog.Builder builder;
    private UserCollectionOperations userCollectionOperations;
    EditText firstName, etEmail, etCreditCard, etSecurityCode, etExpDate,ethno,etCity,etCountry,etPincode;
    Button submit;
    private CartCollectionOperations cartCollectionOperations;
    private ProgressDialog progressDialog;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_check_out, container, false);



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("CheckOut");
        progressDialog = new ProgressDialog(getActivity());
        cartCollectionOperations = new CartCollectionOperations();
        userCollectionOperations = new UserCollectionOperations();
        firstName = view.findViewById(R.id.first_name);
        etEmail = view.findViewById(R.id.et_email);
        ethno = view.findViewById(R.id.et_house_no);
        etCity = view.findViewById(R.id.et_city);
        etCountry = view.findViewById(R.id.et_country);
        etPincode = view.findViewById(R.id.et_pincode);
        etCreditCard = view.findViewById(R.id.et_credit_card);
        etSecurityCode = view.findViewById(R.id.et_security_code);
        etExpDate = view.findViewById(R.id.et_exp_date);
        submit = view.findViewById(R.id.bt_submit);
        setDataToViews();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String houseNo = ethno.getText().toString().trim();
                String city = etCity.getText().toString().trim();
                String country = etCountry.getText().toString().trim();
                String pincode = etPincode.getText().toString().trim();
                String creditCard = etCreditCard.getText().toString().trim();
                String securityCode = etSecurityCode.getText().toString().trim();
                String expDate = etExpDate.getText().toString().trim();


                if (TextUtils.isEmpty(houseNo)) {
                    ethno.setError("Please enter house number");

                } else if (TextUtils.isEmpty(city)) {
                    etCity.setError("Please enter city name");

                }else if (TextUtils.isEmpty(country)) {
                    etCountry.setError("Please enter country name");

                }else if (TextUtils.isEmpty(pincode)) {
                    etPincode.setError("Please enter pincode");

                } else if (TextUtils.isEmpty(creditCard)) {
                    etCreditCard.setError("Please enter credit card number");

                } else if (TextUtils.isEmpty(securityCode)) {
                    etSecurityCode.setError("Please enter security code");

                } else if (TextUtils.isEmpty(expDate)) {
                    etExpDate.setError("Please enter expiry date");


                } else {
                    progressDialog.show();
                    String userId = FirebaseAuth.getInstance().getUid();

                    cartCollectionOperations.deleteWholeCartData(userId, new CartCollectionOperations.DeleteCartCollectionCallback() {
                        @Override
                        public void deleteCartDataSuccess() {
                            progressDialog.dismiss();
                            orderPlaceDialog();
                        }

                        @Override
                        public void deleteCartDataFailure(String message) {
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void orderPlaceDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_order_placed);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        KonfettiView konfettiView = dialog.findViewById(R.id.konfettiView);

        EmitterConfig emitterConfig = new Emitter(5L, TimeUnit.SECONDS).perSecond(50);
        Party party = new PartyFactory(emitterConfig)
                .angle(270)
                .spread(200)
                .setSpeedBetween(1f, 5f)
                .timeToLive(5000L)
                .shapes(new Shape.Rectangle(0.2f))
                .sizes(new Size(12, 5f, 0.2f))
                .position(0.0, 0.0, 1.0, 0.0)
                .build();
        konfettiView.start(party);
        MaterialButton btn_okay = dialog.findViewById(R.id.btn_okay);
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finishAffinity();
                Intent move = new Intent(requireContext(), DashboardActivity.class);
                startActivity(move);
            }
        });
        dialog.show();

    }

    private void setDataToViews() {
        progressDialog.show();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userCollectionOperations.getUserData(userId, new UserCollectionOperations.GetUserCollectionCallback() {
            @Override
            public void getUsersDataSuccess(SignUpModel signUpModel) {

                Log.e("Name=====>", "" + signUpModel.getFullName());
                firstName.setText(signUpModel.getFullName());
                etEmail.setText(signUpModel.getEmail());
                progressDialog.dismiss();
            }

            @Override
            public void getUsersDataFailure(String message) {
                Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}


