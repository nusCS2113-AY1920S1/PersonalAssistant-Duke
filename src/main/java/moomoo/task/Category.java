package moomoo.task;

import java.util.ArrayList;

public class Category {
    String categoryName;
    //Dummy variable
    int monthTotal;
    
    public Category() {


    private String name;
    private ArrayList<String> entries;

    public Category(String name) {
        this.name = name;
        this.entries = new ArrayList<>();
    }

    String getName() {
        return name;
    }
    
    //Method
    public String toString() {
        return categoryName;
    }
    
    public double getCategoryMonthTotal() {
        return monthTotal;
    }
}
