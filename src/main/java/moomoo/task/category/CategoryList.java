package moomoo.task.category;

import moomoo.task.MooMooException;
import moomoo.task.Ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

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

    public void add(Category newCategory) {
        categoryList.add(newCategory);
    }

    public void delete(String categoryName) throws MooMooException {
        int categoryNumber = find(categoryName);
        categoryList.remove(categoryNumber);
    }

    /**
     * Returns the category if it currently exists.
     * @param value Name of category to return.
     * @return The category with name equal to value, or null if it is not found.
     */
    public Category get(String value) throws MooMooException {
        for (Category iterCategory : categoryList) {
            if (iterCategory.toString().equalsIgnoreCase(value)) {
                return iterCategory;
            }
        }
        throw new MooMooException("Sorry I could not find a category named " + value);
    }

    public Category get(int i) throws IndexOutOfBoundsException {
        return categoryList.get(i);
    }

    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    private int find(String categoryName) throws MooMooException {
        for (int i = 0; i < size(); i++) {
            if (get(i).toString().contentEquals(categoryName)) {
                return i;
            }
        }
        throw new MooMooException("Sorry I could not find a category named " + categoryName);
    }

    /**
     * Return the total sum of all expenditure across all categories for the current month.
     * @return total The total sum
     */
    public double getTotal(int month, int year) {
        double total = 0;
        for (Category category : categoryList) {
            total += category.getTotal(month, year);
        }
        return total;
    }

    public double getTotal(int month) {
        return getTotal(month, LocalDate.now().getYear());
    }

    public double getTotal() {
        return getTotal(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
    }

    /**
     * Gets the total expenditure of a single category.
     * @param month specified month
     * @param year specified year
     * @return total expenditure of the category from specified month and year
     */
    private double getCategoryTotal(String categoryName, int month, int year) throws MooMooException {
        return get(categoryName).getTotal(month, year);
    }

    public double getCategoryTotal(String categoryName, int month) throws MooMooException {
        return getCategoryTotal(categoryName, month, LocalDate.now().getYear());
    }

    public double getCategoryTotal(String categoryName) throws MooMooException {
        return getCategoryTotal(categoryName, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
    }
    
    /**
     * Find the category with the largest total expenditure and return the value of that expenditure.
     *
     * @return expenditure The value of the largest expenditure
     */
    public double getLargestExpenditure(int month) {
        double expenditure = 0;
        for (Category category : categoryList) {
            if (category.getTotal(month) > expenditure) {
                expenditure = category.getTotal(month);
            }
        }
        return expenditure;
    }
    
    /**
     * Get the length of the name of the Category with the longest name.
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

    /**
     * Prints the current list of categories.
     * @param ui MooMoo's ui
     */
    public void list(Ui ui) {
        String categoryList = "";
        for (int i = 0; i < this.categoryList.size(); i++) {
            categoryList = categoryList.concat("\n" + i + ". "
                    + this.categoryList.get(i).toString()
                    + " [ $" + this.categoryList.get(i).getTotal() + " ]");
        }
        ui.showCategoryList(categoryList);
    }

    /**
     * Determines if the category list contains a category with its name matching the query.
     * @param categoryName Name of the category to be found
     * @return True if a category is found, false if otherwise
     */
    public boolean hasCategory(String categoryName) {
        try {
            find(categoryName);
            return true;
        } catch (MooMooException e) {
            return false;
        }
    }

    /**
     * Sorts the category list in alphabetical order.
     */
    public void sortByName() {
        for (Category category : categoryList) {
            category.sort("name");
        }
    }

    /**
     * Sorts the category list from largest to smallest total expenditure for the month.
     */
    public void sortByValue() {
        for (Category category : categoryList) {
            category.sort("value");
        }
    }

    /**
     * Sorts the category list in chronological order.
     */
    public void sortByTime() {
        for (Category category : categoryList) {
            category.sort("date");
        }
    }

    /**
     * Populate the categoryList array with dummy variables. FOR TESTING PURPOSES.
     */
    public void testPopulate() {
        ArrayList<String> population = new ArrayList<>();
        population.add("Drugs");
        population.add("Food");
        population.add("Transportation");
        population.add("Individualistically");
        population.add("Compartmentalisation");
        for (int i = 0; i < 5; i += 1) {
            Category newCategory = new Category(population.get(i));
            categoryList.add(newCategory);
        }
    }
}
