package   com.plantsbasket.app;

import android.graphics.drawable.Drawable;

public class PlantsModel {
    private String location, name, category, price;
    private Drawable plantImage;
    public PlantsModel(Drawable plantImage, String location, String name, String category, String price) {
        this.plantImage = plantImage;
        this.location = location;
        this.name = name;
        this.category = category;
        this.price = price;
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

    public void setPrice(String price) {
        this.price = price;
    }
}
