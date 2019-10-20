package moomoo.stubs;

import moomoo.task.Category;
import moomoo.task.CategoryList;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CategoryListStub extends CategoryList {
    @Override
    public ArrayList<Category> getCategoryList() {
        ArrayList<Category> newArrayList = new ArrayList<>();
        newArrayList.add(new CategoryStub("shoes"));
        newArrayList.add(new CategoryStub("food"));
        newArrayList.add(new CategoryStub("window"));
        newArrayList.add(new CategoryStub("places to go"));
        newArrayList.add(new CategoryStub("sweets"));
        newArrayList.add(new CategoryStub("laptop"));
        return newArrayList;
    }

}
