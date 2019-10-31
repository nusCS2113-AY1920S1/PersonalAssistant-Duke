package moomoo.stubs;

import moomoo.task.category.Category;
import moomoo.task.category.CategoryList;

import java.util.ArrayList;

public class CategoryListStub extends CategoryList {
    private ArrayList<Category> newArrayList;

    public CategoryListStub() {
        newArrayList =  new ArrayList<>();
    }

    @Override
    public void add(Category newCategory) {
        newArrayList.add(new CategoryStub("shoes"));
        newArrayList.add(new CategoryStub("food"));
        newArrayList.add(new CategoryStub("window"));
        newArrayList.add(new CategoryStub("places to go"));
        newArrayList.add(new CategoryStub("sweets"));
        newArrayList.add(new CategoryStub("laptop"));
    }

    @Override
    public ArrayList<Category> getCategoryList() {
        return newArrayList;
    }

    @Override
    public Category get(int i) {
        return newArrayList.get(i);
    }

    @Override
    public Category get(String value) {
        for (Category iterCategory : newArrayList) {
            if (iterCategory.toString().equalsIgnoreCase(value)) {
                return iterCategory;
            }
        }
        return null;
    }

    @Override
    public double getTotal(int month, int year) {
        double total = 0;
        for (Category category : newArrayList) {
            total += category.getTotal(month, year);
        }
        return total;
    }
}
