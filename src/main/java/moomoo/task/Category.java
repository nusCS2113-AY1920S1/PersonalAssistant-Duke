package moomoo.task;

public class Category {
    String categoryName;
    //Dummy variable
    int monthTotal;
    
    public Category() {

    }
    
    //Method
    public String toString() {
        return categoryName;
    }
    
    public double getCategoryMonthTotal() {
        return monthTotal;
    }
}
