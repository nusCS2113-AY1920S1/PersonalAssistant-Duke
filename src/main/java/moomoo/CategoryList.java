package moomoo;

import java.util.ArrayList;

public class CategoryList {
    private ArrayList<Category> categories;

    public CategoryList() {
        categories = new ArrayList<>();
    }

    void list(Ui ui) {
        String categoryList = "These are your current categories:";
        for (int i = 0; i < categories.size(); i++) {
            categoryList = categoryList.concat("\n" + i + ". " + categories.get(i).getName());
        }
        ui.showCategoryList(categoryList);
    }

    void add(Category newCategory) {
        categories.add(newCategory);
    }
}
