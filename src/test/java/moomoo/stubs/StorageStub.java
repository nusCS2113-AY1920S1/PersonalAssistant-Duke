package moomoo.stubs;

import moomoo.task.Budget;
import moomoo.task.Category;
import moomoo.task.Storage;
import moomoo.task.Ui;

import java.util.ArrayList;
import java.util.HashMap;

public class StorageStub extends Storage {
    @Override
    public void saveBudgetToFile(Budget budget) {
    }

    @Override
    public HashMap<String, Double> loadBudget(ArrayList<Category> catList, Ui ui) {
        HashMap<String, Double> newHashMap = new HashMap<>();
        return newHashMap;
    }
}
