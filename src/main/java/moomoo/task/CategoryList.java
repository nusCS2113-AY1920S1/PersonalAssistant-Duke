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

    /**
     * Prints the current list of categories.
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

    public ArrayList<Category> getCategoryList() {
        return this.categoryList;
    }
}
