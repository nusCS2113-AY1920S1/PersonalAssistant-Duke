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
     * @return total The total sum
     */
    public double getGrandMonthTotal() {
        double total = 0;
        for (Category category : categoryList) {
            total += category.getCategoryMonthTotal();
        }
        return total;
    }
    
    /**
     * Find the category with the largest total expenditure and return the value of that expenditure.
     * @return expenditure The value of the largest expenditure
     */
    public double getLargestExpenditure() {
        double expenditure = 0;
        for (Category category : categoryList) {
            if (category.getCategoryMonthTotal() > expenditure) {
                expenditure = category.getCategoryMonthTotal();
            }
        }
        return expenditure;
    }
    
    /**
     * Prints the current list of categories.
     *
     * @param ui MooMoo's ui
     */
    public void list(Ui ui) {
        String categoryList = "These are your current categories:";
        for (int i = 0; i < this.categoryList.size(); i++) {
            categoryList = categoryList.concat("\n" + (i+1) + ". " + this.categoryList.get(i).getName());
        }
        ui.showCategoryList(categoryList);
    }
  
    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    public void add(Category newCategory) {
        categoryList.add(newCategory);
    }

    public void deleteCategory(int categoryNumber) {
        categoryList.remove(categoryNumber);
    }

    public double getMonthlyGrandTotal(int month) {
        return 0;
    }
}
