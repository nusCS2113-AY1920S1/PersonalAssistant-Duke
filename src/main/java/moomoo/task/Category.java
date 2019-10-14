package moomoo.task;

import java.util.ArrayList;

public class Category {

    private String name;
    private ArrayList<String> entries;

    public Category(String name) {
        this.name = name;
        this.entries = new ArrayList<>();
    }

    String getName() {
        return name;
    }
}
