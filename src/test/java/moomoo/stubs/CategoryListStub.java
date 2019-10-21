package moomoo.stubs;

import moomoo.task.Category;
import moomoo.task.CategoryList;

import java.lang.reflect.Array;
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

}
