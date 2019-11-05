package moomoo.stubs;

import moomoo.feature.Budget;
import moomoo.feature.category.Category;
import moomoo.feature.storage.Storage;
import moomoo.feature.Ui;

import java.util.ArrayList;
import java.util.HashMap;

public class StorageStub extends Storage {
    /**
     * Initializes storage and the filepath for each file.
     *
     * @param budgetFilePath   File path to store the budget into.
     * @param scheduleFilePath File path to store all categories
     */
    public StorageStub(String budgetFilePath, String scheduleFilePath) {
        super(budgetFilePath, scheduleFilePath);
    }

    public StorageStub() {
        super();
    }

    @Override
    public void saveBudgetToFile(Budget budget) {
    }

    @Override
    public HashMap<String, Double> loadBudget(ArrayList<Category> catList, Ui ui) {
        HashMap<String, Double> newHashMap = new HashMap<>();
        return newHashMap;
    }
}
