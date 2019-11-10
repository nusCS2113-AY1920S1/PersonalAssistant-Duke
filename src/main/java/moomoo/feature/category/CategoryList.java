package moomoo.feature.category;

import moomoo.feature.MooMooException;

import java.time.LocalDate;
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

    public void add(Category newCategory) {
        categoryList.add(newCategory);
    }

    public void delete(int categoryIndex) {
        categoryList.remove(categoryIndex);
    }

    /**
     * Returns the category if it currently exists.
     * @param value Name of category to return.
     * @return The category with name equal to value, or null if it is not found.
     */
    public Category get(String value) throws MooMooException {
        for (Category category : categoryList) {
            String name = category.name();
            if (name.contentEquals(value)) {
                return category;
            }
        }
        return null;

    }

    /**
     * Returns the category at the specified index of a category list, throws exception if not found.
     * @param i index of the category list
     * @return category at the specified index
     * @throws MooMooException if the index does not exist in the list
     */
    public Category get(int i) throws MooMooException {
        try {
            return categoryList.get(i);
        } catch (IndexOutOfBoundsException e) {
            throw new MooMooException("Sorry I couldn't find a category with that index.");
        }
    }

    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    /**
     * Finds a category within the category list.
     * @param categoryName category to find
     * @return index of the category to be found
     * @throws MooMooException thrown if category is not in the category list
     */
    public int find(String categoryName) throws MooMooException {
        for (int i = 0; i < size(); i++) {
            if (get(i).name().contentEquals(categoryName)) {
                return i;
            }
        }
        throw new MooMooException("Sorry I could not find a category named " + categoryName);
    }

    /**
     * Return the total sum of all expenditure across all categories for the current month.
     * @return total The total sum
     */
    private double getTotal(int month, int year) {
        double total = 0;
        for (Category category : categoryList) {
            total += category.getTotal(month, year);
        }
        return total;
    }

    public double getTotal() {
        return getTotal(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
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
     * @return Length of the name of the Category with the longest name, if it is longer than 14 characters, return 14
     */
    public int getLongestCategory() {
        int longestName = 0;
        for (Category category : categoryList) {
            if (category.name().length() > longestName) {
                longestName = category.name().length();
            }
            if (longestName >= 14) {
                longestName = 14;
                break;
            }
        }

        return longestName;
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
