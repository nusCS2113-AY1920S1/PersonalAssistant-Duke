package moomoo.task;

import java.util.ArrayList;

public class Category {
    private int monthTotal;

    private String name;
    private ArrayList<Expenditure> expenditure;

    /**
     * Initializes a new category with a name, an empty list of expenditures, and a monthly total.
     * @param name category name
     */
    public Category(String name) {
        this.name = name;
        this.expenditure = new ArrayList<>();
        this.monthTotal = 0;
    }

    public int getExpenditureArraySize() {
        return expenditure.size();
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    /**
     * Calculates the total expenditure for every entry in the category.
     * @return total
     */
    public double getTotalExpenditure() {
        double totalCost = 0;
        for (Expenditure entry : expenditure) {
            totalCost += entry.cost;
        }
        return totalCost;
    }
    

    public double getMonthlyTotal(int month) {

        return monthTotal;
    }

    void addExpenditure() {

    }

    void editExpenditure() {

    }

    void deleteExpenditure() {

    }
}
