package moomoo.task;

import java.time.LocalDate;
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
        category.add(newExpenditure);
    }

    /**
     * Calculates the total expenditure for every entry in the category.
     * @return totalCost
     */
    double getCategoryMonthTotal() {
        double totalCost = 0.00;
        for (Expenditure expenditure : category) {
            LocalDate date = expenditure.date;
            LocalDate now = LocalDate.now(); // Now see if the month and year match.
            if (date.getMonth() == now.getMonth() && date.getYear() == now.getYear()) {
                // You have a hit.
                totalCost += expenditure.amount();
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
