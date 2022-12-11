package   com.plantsbasket.app;

import android.graphics.drawable.Drawable;

public class PlantsModel {
    private String plantId, location, name, category, price, description, plantImageSrc, documentId;
    private int count = 1;
    private Drawable plantImage;

    public PlantsModel() {
    }

    public PlantsModel(Drawable plantImage, String location, String name, String category, String price,String count) {
        this.plantImage = plantImage;
        this.location = location;
        this.name = name;
        this.category = category;
        this.price = price;
    }
    public PlantsModel(String plantId, String plantImageSrc, String location, String name, String category, String price, String description) {
        this.plantId = plantId;
        this.plantImageSrc = plantImageSrc;
        this.location = location;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public Drawable getPlantImage() {
        return plantImage;
    }

    public void setPlantImage(Drawable plantImage) {
        this.plantImage = plantImage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlantImageSrc() {
        return plantImageSrc;
    }

    public void setPlantImageSrc(String plantImageSrc) {
        this.plantImageSrc = plantImageSrc;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }


}
