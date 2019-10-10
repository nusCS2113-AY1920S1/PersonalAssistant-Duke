package moomoo.task;

import java.util.ArrayList;

public class CategoryList {
    private ArrayList<Category> catList;

    public CategoryList() {
        catList = new ArrayList<Category>();
    }

    public CategoryList(ArrayList<Category> inList) {
        this.catList = inList;
    }

}

