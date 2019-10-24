package moomoo.task;

import java.util.ArrayList;

public class Category {
    private double monthTotal;
    private String categoryName;
    private ArrayList<Expenditure> category;

    public Category() {

    }

    /**
     * Initializes a new category with a name, an empty list of expenditures, and a monthly total.
     * @param name category name
     */
    public Category(String name) {
        this.categoryName = name;
        this.category = new ArrayList<>();
        this.monthTotal = 0.00;
    }

    public int getCategoryArraySize() {
        return category.size();
    }

    public Expenditure get(int i) {
        return category.get(i);
    }

    public String toString() {
        return categoryName;
    }

    public void add(Expenditure newExpenditure) {
        monthTotal += newExpenditure.getCost();
        category.add(newExpenditure);
    }

    /**
     * Calculates the total expenditure for every entry in the category.
     * @return totalCost
     */
    public double getCategoryMonthTotal() {
        double totalCost = 0.00;
        for (int i = 0; i < category.size(); i++) {
            totalCost += category.get(i).getCost();
        }
        return totalCost;
    }

    /**
     * Returns the total expenditure for the given month and year.
     * @param month integer value representing the month
     * @param year integer value representing the value.
     * @return total expenditure spent for corresponding month and year
     */
    public double getCategoryTotalPerMonthYear(int month, int year) {
        double totalCost = 0.00;
        for (int i = 0; i < category.size(); i++) {
            Expenditure currExpenditure = category.get(i);
            if (currExpenditure.getDateTime().getMonthValue() == month
                    && currExpenditure.getDateTime().getYear() == year) {
                totalCost += currExpenditure.getCost();
            }
        }
        return totalCost;
    }

    public double getMonthlyTotal(int month) {
        return monthTotal;
    }

    public void addExpenditure() {

    }

    public void editExpenditure() {

    }

    public void deleteExpenditure() {

    }
    
    /**
     * Set the month total (FOR TESTING PURPOSES).
     * @param value The value to be set
     */
    public void setMonthTotal(int value) {
        monthTotal = value;
    }
    
}
