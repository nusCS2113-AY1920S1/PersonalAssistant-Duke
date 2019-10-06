package duke.entities_decrypted;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.image.Image;

public class Ingredient {
    private String name;
    private Image picture;
    private int expiryPeriod;
    private double cost;

    public Ingredient(@JsonProperty("name") String name) {
        this.name = name;
    }

    public Ingredient() {

    }

    public Ingredient(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    /////////////
    //For UI design purpose
    /////////////
    public void init() {
        name = "cheese";
        expiryPeriod = 365;
        cost = 22.02;
        //setPicture("cheese");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public int getExpiryPeriod() {
//        return expiryPeriod;
//    }
//
//    public void setExpiryPeriod(int expiryPeriod) {
//        this.expiryPeriod = expiryPeriod;
//    }
//
//    public double getCost() {
//        return cost;
//    }
//
//    public void setCost(double cost) {
//        this.cost = cost;
//    }

/*
    public void setPicture (String pic_name) {
        String currentDir = System.getProperty("user.dir");
        try {
             this.picture =
                    new Image(new FileInputStream(currentDir + "\\src\\main\\resources\\images\\" + pic_name +
                            ".png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
*/

//    public Image getPicture() {
//        return this.picture;
//    }
}
