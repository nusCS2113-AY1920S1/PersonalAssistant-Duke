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
    
    //Methods
    public int size() {
        return categoryList.size();
    }
    
    public Category get(int i) {
        return categoryList.get(i);
    }
    
    public double getGrandMonthTotal() {
        double total = 0;
        for (Category category : categoryList) {
            total += category.getCategoryMonthTotal();
        }
        return total;
    }
    
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
            categoryList = categoryList.concat("\n" + i + ". " + this.categoryList.get(i).getName());
        }
        ui.showCategoryList(categoryList);
    }
    
    public void add(Category newCategory) {
        categoryList.add(newCategory);
    }
}
