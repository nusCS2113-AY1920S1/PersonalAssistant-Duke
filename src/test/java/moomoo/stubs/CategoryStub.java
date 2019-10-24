package moomoo.stubs;

import moomoo.task.Category;

public class CategoryStub extends Category {
    private String name;

    public CategoryStub() {

    }

    public CategoryStub(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
