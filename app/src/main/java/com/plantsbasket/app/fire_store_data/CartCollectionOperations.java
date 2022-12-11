package com.plantsbasket.app.fire_store_data;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.plantsbasket.app.CartModel;

import java.util.ArrayList;
import java.util.List;

public class CartCollectionOperations {
    private static String TAG = CartCollectionOperations.class.getSimpleName().toString();

    private FirebaseFirestore firebaseFireStore;
    private CollectionReference dbCart;

    public CartCollectionOperations() {
        firebaseFireStore = FirebaseFirestore.getInstance();
        dbCart = firebaseFireStore.collection(Constants.COLLECTION_CART_DATA);
    }


    public  void getCartDataData(String userId, GetCartDataCollectionCallback getCartDataCollectionCallback){
        dbCart.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty()){
                    getCartDataCollectionCallback.getCartDataFailure("Cart is Empty!!!");
                }else {
                    ArrayList<CartModel> cartListData = new ArrayList<>();
                    List<CartModel> cartList = queryDocumentSnapshots.toObjects(CartModel.class);
                    for (int i = 0; i < cartList.size(); i++) {
                        String cartUserId = cartList.get(i).getUserId();
                        if (cartUserId.equals(userId)) {
                            String documentId = queryDocumentSnapshots.getDocuments().get(i).getId();
                            CartModel cartModel = cartList.get(i);
                            cartModel.setDocumentId(documentId);
                            cartListData.add(cartModel);
                        }
                    }

                    getCartDataCollectionCallback.getCartDataSuccess(cartListData);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getCartDataCollectionCallback.getCartDataFailure(e.getMessage());
            }
        });
    }

    public interface GetCartDataCollectionCallback{
        public void getCartDataSuccess(ArrayList<CartModel> cartList);
        public void getCartDataFailure(String message);
    }

    public void saveOrEditCartData(String userId, String plantId, SaveCartCollectionCallback saveCartCollectionCallback){
        String cartId = dbCart.document().getId();
        CartModel cartModel = new CartModel(cartId, plantId, userId);
        dbCart.add(cartModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        saveCartCollectionCallback.saveCartDataSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        saveCartCollectionCallback.saveCartDataFailure(e.getMessage());
                    }
                });
    }


    public interface SaveCartCollectionCallback{
        public void saveCartDataSuccess();
        public void saveCartDataFailure(String message);
    }

    public void deleteCartData(String documentId, DeleteCartCollectionCallback deleteCartCollectionCallback){
        dbCart.document(documentId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                deleteCartCollectionCallback.deleteCartDataSuccess();
            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                deleteCartCollectionCallback.deleteCartDataFailure(e.getMessage());
            }
        });
    }


    public void deleteWholeCartData(String userId, DeleteCartCollectionCallback deleteCartCollectionCallback){
        dbCart.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty()){
                    deleteCartCollectionCallback.deleteCartDataFailure("Cart is Empty!!!");
                }else {
                    ArrayList<CartModel> cartListData = new ArrayList<>();
                    List<CartModel> cartList = queryDocumentSnapshots.toObjects(CartModel.class);
                    for (int i = 0; i < cartList.size(); i++) {
                        String cartUserId = cartList.get(i).getUserId();
                        if (cartUserId.equals(userId)) {
                            String documentId = queryDocumentSnapshots.getDocuments().get(i).getId();
                            CartModel cartModel = cartList.get(i);
                            cartModel.setDocumentId(documentId);
                            cartListData.add(cartModel);
                        }
                    }

                    for (int i = 0; i < cartListData.size(); i++) {
                        String cartUserId = cartListData.get(i).getUserId();
                        if (cartUserId.equals(userId)) {
                            String documentId = cartListData.get(i).getDocumentId();
                            int finalI = i;
                            dbCart.document(documentId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (finalI == cartListData.size() - 1) {
                                        deleteCartCollectionCallback.deleteCartDataSuccess();
                                    }

                                }
                            }) .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    deleteCartCollectionCallback.deleteCartDataFailure(e.getMessage());
                                }
                            });
                        }
                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                deleteCartCollectionCallback.deleteCartDataFailure(e.getMessage());
            }
        });
    }


    public interface DeleteCartCollectionCallback{
        public void deleteCartDataSuccess();
        public void deleteCartDataFailure(String message);
    }


}
