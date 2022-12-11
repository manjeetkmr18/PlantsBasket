package com.plantsbasket.app.fire_store_data;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.plantsbasket.app.SignUpModel;

import java.util.List;

public class UserCollectionOperations {
    private static String TAG = UserCollectionOperations.class.getSimpleName().toString();

    private FirebaseFirestore firebaseFireStore;
    private CollectionReference dbUsers;

    public UserCollectionOperations() {
        firebaseFireStore = FirebaseFirestore.getInstance();
        dbUsers = firebaseFireStore.collection(Constants.COLLECTION_USERS_DATA);
    }

    public void checkIfUserPresentInUsersCollection(String userId, CheckIfUserPresentCallback checkIfUserPresentCallback){
        dbUsers.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            checkIfUserPresentCallback.checkIfUserPresentResult(false);
                        }else{
                            boolean isUserPresent = false;
                            List<SignUpModel> userDataList = queryDocumentSnapshots.toObjects(SignUpModel.class);
                            for(int i = 0; i < userDataList.size(); i++){
                                if(userDataList.get(i).getUserId().equals(userId)){
                                    isUserPresent = true;
                                    break;
                                }
                            }
                            checkIfUserPresentCallback.checkIfUserPresentResult(isUserPresent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        checkIfUserPresentCallback.checkIfUserPresentResult(false);
                    }
                });
    }



    public interface CheckIfUserPresentCallback{
        public void checkIfUserPresentResult(boolean isPresent);
    }

    public  void getUserData(String userId,
                             GetUserCollectionCallback getUserCollectionCallback){
        dbUsers.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            getUserCollectionCallback.getUsersDataFailure("User not found");
                        }else{
                            List<SignUpModel> userDataList = queryDocumentSnapshots.toObjects(SignUpModel.class);
                            for(int i = 0; i < userDataList.size(); i++){
                                SignUpModel signUpModel = userDataList.get(i);
                                Log.e(TAG,"signUpModel---==="+new Gson().toJson(signUpModel));
                                if(signUpModel.getUserId().equals(userId)){
                                    getUserCollectionCallback.getUsersDataSuccess(userDataList.get(i));
                                    break;
                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        getUserCollectionCallback.getUsersDataFailure(e.getMessage());
                    }
                });
    }

    public interface GetUserCollectionCallback{
        public void getUsersDataSuccess(SignUpModel signUpModel);
        public void getUsersDataFailure(String message);
    }

    public void saveOrEditUserData(SignUpModel signUpModel, SaveUserCollectionCallback saveUserCollectionCallback){
        dbUsers.document(signUpModel.getUserId()).set(signUpModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        saveUserCollectionCallback.saveUsersDataSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        saveUserCollectionCallback.saveUsersDataFailure(e.getMessage());
                    }
                });
    }


    public interface SaveUserCollectionCallback{
        public void saveUsersDataSuccess();
        public void saveUsersDataFailure(String message);
    }

}
