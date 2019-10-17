package moomoo.task;

import java.util.ArrayList;

public class Category {
    //Dummy variable
    private int monthTotal;
    private String name;
    private ArrayList<Expenditure> expenditure;

    public Category(String name) {
        this.name = name;
        this.expenditure = new ArrayList<>();
    }

    public int getExpenditureArraySize() {
        return expenditure.size();
    }

    /**
     * Calculates the total expenditure for every entry in the category.
     * @return totalCost
     */
    public double getTotalExpenditure() {
        double totalCost = 0;
        for (Expenditure entry : expenditure) {
            totalCost += entry.cost;
        }
        return totalCost;
    }
    
    //Method
    public String toString() {
        return name;
    }
    
    public double getCategoryMonthTotal() {
        return monthTotal;
    }

    void addExpenditure() {

    }

    void editExpenditure() {

    }

    void deleteExpenditure() {

    }
    
    /**
     * Set the month total (FOR TESTING PURPOSES).
     * @param value The value to be set
     */
    public void setMonthTotal(int value) {
        monthTotal = value;
    }
    
}
