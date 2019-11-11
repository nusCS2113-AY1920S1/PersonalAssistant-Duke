package moomoo.stubs;

import moomoo.feature.Budget;
import moomoo.feature.ScheduleList;
import moomoo.feature.category.Category;
import moomoo.feature.storage.Storage;

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
    public HashMap<String, Double> loadBudget(ArrayList<Category> catList) {
        HashMap<String, Double> newHashMap = new HashMap<>();
        return newHashMap;
    }

    @Override
    public void saveScheduleToFile(ScheduleList calendar) {
    }

    @Override
    public HashMap<String, ArrayList<String>> loadCalendar() {
        HashMap<String, ArrayList<String>> newCalendar = new HashMap<>();
        return newCalendar;
    }
}
