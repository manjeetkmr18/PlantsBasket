package com.plantsbasket.app.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.plantsbasket.app.ProgressDialog;
import com.plantsbasket.app.R;
import com.plantsbasket.app.activities.DashboardActivity;
import com.plantsbasket.app.fire_store_data.CartCollectionOperations;

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

    EditText firstName, etEmail, etCreditCard, etSecurityCode, etExpDate;
    Button submit;
    private CartCollectionOperations cartCollectionOperations;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_out, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartCollectionOperations = new CartCollectionOperations();
        progressDialog = new ProgressDialog(getActivity());
        firstName = view.findViewById(R.id.first_name);
        etEmail = view.findViewById(R.id.et_email);
        etCreditCard = view.findViewById(R.id.et_credit_card);
        etSecurityCode = view.findViewById(R.id.et_security_code);
        etExpDate = view.findViewById(R.id.et_exp_date);
        submit = view.findViewById(R.id.bt_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = firstName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String creditCard = etCreditCard.getText().toString().trim();
                String securityCode = etSecurityCode.getText().toString().trim();
                String expDate = etExpDate.getText().toString().trim();


                if (TextUtils.isEmpty(name)) {
                    firstName.setError("Please enter name");

                } else if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Please enter email");

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
                            progressDialog.dissmiss();
                            orderPlaceDialog();
                        }

                        @Override
                        public void deleteCartDataFailure(String message) {
                            progressDialog.dissmiss();
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
        btn_okay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getActivity().finishAffinity();
                Intent move = new Intent(requireContext(), DashboardActivity.class);
                startActivity(move);
            }
        });
        dialog.show();

    }
}


