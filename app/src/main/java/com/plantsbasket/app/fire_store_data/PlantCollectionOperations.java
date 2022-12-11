package com.plantsbasket.app.fire_store_data;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.plantsbasket.app.PlantsModel;

import java.util.List;

public class PlantCollectionOperations {
    private static String TAG = PlantCollectionOperations.class.getSimpleName().toString();

    private FirebaseFirestore firebaseFireStore;
    private CollectionReference dbPlants;

    public PlantCollectionOperations() {
        firebaseFireStore = FirebaseFirestore.getInstance();
        dbPlants = firebaseFireStore.collection(Constants.COLLECTION_PLANT_DATA);
    }

    public void checkIfUserPresentInUsersCollection(String plantId, CheckIfUserPresentCallback checkIfUserPresentCallback){
        dbPlants.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            checkIfUserPresentCallback.checkIfUserPresentResult(false);
                        }else{
                            boolean isUserPresent = false;
                            List<PlantsModel> userDataList = queryDocumentSnapshots.toObjects(PlantsModel.class);
                            for(int i = 0; i < userDataList.size(); i++){
                                if(userDataList.get(i).getPlantId().equals(plantId)){
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

    public  void getPlantsData(GetPlantCollectionCallback getUserCollectionCallback){
        dbPlants.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            getUserCollectionCallback.getPlantDataFailure("Plants not found");
                        }else{
                            List<PlantsModel> plantsModelArrayList = queryDocumentSnapshots.toObjects(PlantsModel.class);
                            getUserCollectionCallback.getPlantDataSuccess(plantsModelArrayList);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        getUserCollectionCallback.getPlantDataFailure(e.getMessage());
                    }
                });
    }

    public interface GetPlantCollectionCallback{
        public void getPlantDataSuccess(List<PlantsModel> plantsModelArrayList);
        public void getPlantDataFailure(String message);
    }

    public  void getSpecificPlantsData(String plantId, GetSpecificPlantCallback getUserCollectionCallback){
        dbPlants.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            getUserCollectionCallback.getSpecificPlantDataFailure("Plants not found");
                        }else{
                            List<PlantsModel> plantsModelArrayList = queryDocumentSnapshots.toObjects(PlantsModel.class);
                            for(int i = 0; i < plantsModelArrayList.size(); i++){
                                if(plantId.equals(plantsModelArrayList.get(i).getPlantId())) {
                                    PlantsModel plantsModel = plantsModelArrayList.get(i);
                                    getUserCollectionCallback.getSpecificPlantDataSuccess(plantsModel);
                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        getUserCollectionCallback.getSpecificPlantDataFailure(e.getMessage());
                    }
                });
    }

    public interface GetSpecificPlantCallback{
        public void getSpecificPlantDataSuccess(PlantsModel plantModel);
        public void getSpecificPlantDataFailure(String message);
    }

    public void saveOrEditPlants(PlantsModel plantsModel, SaveUserCollectionCallback saveUserCollectionCallback){
        checkIfUserPresentInUsersCollection(plantsModel.getPlantId(), new CheckIfUserPresentCallback() {
            @Override
            public void checkIfUserPresentResult(boolean isPresent) {
                if(!isPresent){
                    dbPlants.document(plantsModel.getPlantId()).set(plantsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
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
            }
        });
       
    }

    public interface SaveUserCollectionCallback{
        public void saveUsersDataSuccess();
        public void saveUsersDataFailure(String message);
    }

}
