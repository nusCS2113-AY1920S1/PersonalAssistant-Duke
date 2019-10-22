package moomoo.task;

import java.util.ArrayList;

public class CategoryList {
    
    private ArrayList<Category> categoryList;
    
    public CategoryList() {
        categoryList = new ArrayList<>();
    }
    
    public CategoryList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }
    
    public int size() {
        return categoryList.size();
    }
    
    public Category get(int i) {
        return categoryList.get(i);
    }
    
    /**
     * Return the total sum of all expenditure across all categories for the current month.
     *
     * @return total The total sum
     */
    public double getGrandMonthTotal(int month) {
        double total = 0;
        for (Category category : categoryList) {
            total += category.getMonthlyTotal(month);
        }
        return total;
    }
    
    /**
     * Find the category with the largest total expenditure and return the value of that expenditure.
     *
     * @return expenditure The value of the largest expenditure
     */
    public double getLargestExpenditure(int month) {
        double expenditure = 0;
        for (Category category : categoryList) {
            if (category.getMonthlyTotal(month) > expenditure) {
                expenditure = category.getMonthlyTotal(month);
            }
        }
        return expenditure;
    }
    
    /**
     * Prints the current list of categories.
     * @param ui MooMoo's ui
     */
    public void list(Ui ui) {
        String categoryList = "";
        for (int i = 0; i < this.categoryList.size(); i++) {
            categoryList = categoryList.concat("\n" + i + ". "
                    + this.categoryList.get(i).toString()
                    + " [ $" + this.categoryList.get(i).getCategoryMonthTotal() + " ]");
        }
        ui.showCategoryList(categoryList);
    }
    
    /**
     * Get the length of the name of the Category with the longest name.
     *
     * @return Length of the name of the Category with the longest name
     */
    public int getLongestCategory() {
        int longestName = 0;
        for (Category category : categoryList) {
            if (category.toString().length() > longestName) {
                longestName = category.toString().length();
            }
            if (longestName >= 14) {
                longestName = 14;
                break;
            }
        }
        
        return longestName;
    }
    
    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    /**
     * Calculates the total of all expenditures from all categories for selected month and year.
     * @param month month to check
     * @return total expenditure for the month
     */
    public double getMonthlyGrandTotal(int month, int year) {
        double total = 0;
        for (Category category : categoryList) {
            total += category.getCategoryTotalPerMonthYear(month, year);
        }
        return total;
    }

    public void add(Category newCategory) {
        categoryList.add(newCategory);
    }

    public void deleteCategory(int categoryNumber) {
        categoryList.remove(categoryNumber);
    }
    
    /**
     * Populate the categoryList array with dummy variables. FOT TESTING PURPOSES
     */
    public void testPopulate() {
        ArrayList<String> population = new ArrayList<String>();
        population.add("Games");
        population.add("Food");
        population.add("Transportation");
        population.add("Individualistically");
        population.add("Compartmentalisation");
        for (int i = 0; i < 5; i += 1) {
            Category newCategory = new Category(population.get(i));
            newCategory.setMonthTotal(i * 100 / (i + 3));
            categoryList.add(newCategory);
        }
    }
}
