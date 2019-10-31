package moomoo.task.category;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

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

    public int size() {
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

    public void delete(int expenditureNumber) {
        category.remove(expenditureNumber);
    }

    /**
     * Calculates the total expenditure for every entry in the category.
     * @return totalCost
     */
    public double getTotal() {
        double totalCost = 0.00;
        for (Expenditure expenditure : category) {
            LocalDate date = expenditure.getDate();
            LocalDate now = LocalDate.now(); // Now see if the month and year match.
            if (date.getMonth() == now.getMonth() && date.getYear() == now.getYear()) {
                // You have a hit.
                totalCost += expenditure.getCost();
            }
        }
        return totalCost;
    }

    public double getTotal(int month) {
        return monthTotal;
    }

    /**
     * Returns the total expenditure for the given month and year.
     * @param month integer value representing the month
     * @param year integer value representing the value.
     * @return total expenditure spent for corresponding month and year
     */
    public double getTotal(int month, int year) {
        double totalCost = 0.00;
        for (Expenditure currExpenditure : category) {
            if (currExpenditure.getDate().getMonthValue() == month
                    && currExpenditure.getDate().getYear() == year) {
                totalCost += currExpenditure.getCost();
            }
        }
        return totalCost;
    }
    
    /**
     * Return the expenditure with the largest value.
     * @return expenditure The value of the largest expenditure
     */
    public double getLargestExpenditure() {
        double expenditure = 0;
        for (Expenditure exp : category) {
            if (exp.getCost() > expenditure) {
                expenditure = exp.getCost();
            }
        }
        return expenditure;
    }
    
    /**
     * The expenditure with the longest name.
     * @return The name of the longest expenditure
     */
    public int getLongestExpenditure() {
        int longestName = 0;
        for (Expenditure exp : category) {
            if (exp.toString().length() > longestName) {
                longestName = exp.toString().length();
            }
            if (longestName >= 14) {
                longestName = 14;
                break;
            }
        }
        return longestName;
    }

    void sort(String type) {
        switch (type) {
        case "name": {
            category.sort(Comparator.comparing(Expenditure::toString));
            return;
        }
        case "cost": {
            category.sort(Comparator.comparing(Expenditure::costToString));
            return;
        }
        case "date": {
            category.sort(Comparator.comparing(Expenditure::dateToString));
        }
        default:
        }
    }

    /**
     * Populate the categoryList array with dummy variables. FOR TESTING PURPOSES.
     */
    void testPopulate() {
        ArrayList<String> population = new ArrayList<>();
        population.add("SanicTheHodgepodge");
        population.add("MetalGearLiquid");
        population.add("GTB");
        population.add("Far:Automata");
        population.add("League of Mobile Legends");
        for (int i = 0; i < 5; i += 1) {
            Expenditure newExp = new Expenditure(population.get(i), i * 100 / (i + 3), null);
            category.add(newExp);
        }
        monthTotal = 75;
    }
}
