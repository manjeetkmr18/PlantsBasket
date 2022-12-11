package com.plantsbasket.app;

import android.graphics.drawable.Drawable;

public class CartModel {
    private String cartId, plantId, userId, documentId;

    public CartModel() {
    }

    public CartModel(String cartId, String plantId, String userId) {
        this.cartId = cartId;
        this.plantId = plantId;
        this.userId = userId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}