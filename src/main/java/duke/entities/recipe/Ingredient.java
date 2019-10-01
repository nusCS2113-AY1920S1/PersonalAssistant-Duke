package duke.entities.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Ingredient {
    private String name;
    private Image picture;
    private int expiryPeriod;
    private double cost;

    public Ingredient (@JsonProperty String name) {
        this.name = name;
    }

    /////////////
    //For UI design purpose
    /////////////
    public void init() {
        name = "cheese";
        expiryPeriod = 365;
        cost = 22.02;
        setPicture("cheese");
    }

    public String getName() {
        return name;
    }

    public int getExpiryPeriod() {
        return expiryPeriod;
    }

    public void setExpiryPeriod(int expiryPeriod) {
        this.expiryPeriod = expiryPeriod;
    }

    public double getCost() {
        return cost;
    }

    public void setCost (double cost) {
        this.cost = cost;
    }

    public void setPicture (String name) {
        String currentDir = System.getProperty("user.dir");
        try {
             this.picture =
                    new Image(new FileInputStream(currentDir + "\\src\\main\\resources\\images\\" + name +
                            ".png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Image getPicture() {
        return this.picture;
    }
}
