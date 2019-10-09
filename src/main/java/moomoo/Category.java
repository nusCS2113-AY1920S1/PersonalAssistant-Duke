package moomoo;

import java.util.ArrayList;

class Category {

    private String name;
    private ArrayList<String> entries;

    Category(String name) {
        this.name = name;
        this.entries = new ArrayList<>();
    }

    String getName() {
        return name;
    }
}
