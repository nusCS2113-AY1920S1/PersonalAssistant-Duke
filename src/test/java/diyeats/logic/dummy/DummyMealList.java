package diyeats.logic.dummy;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.ExerciseList;
import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is a stub mealList class for testing purposes.
 */
public class DummyMealList extends MealList {
    private HashMap<LocalDate, ArrayList<Meal>> mealTracker = new HashMap<>();
    private HashMap<String, HashMap<String, Integer>> defaultValues = new HashMap<>();
    private ExerciseList exerciseList = new ExerciseList();

    public DummyMealList(ExerciseList exerciseList) {
        exerciseList.addStoredExercises("Running, 8 mph (7.5 min/mile)", 8);
        exerciseList.addStoredExercises("Running, 10 mph (6 min/mile)", 10);
        exerciseList.addStoredExercises("Cycling, 10 - 12mph (light effort)", 7);
        exerciseList.addStoredExercises("Cycling, 12 - 14mph (moderate effort)", 8);
        exerciseList.addStoredExercises("Cycling, 14 - 16mph (heavy effort)", 10);
        exerciseList.addStoredExercises("Soccer, casual, general", 7);
        exerciseList.addStoredExercises("Tennis, casual, general", 7);
        exerciseList.addStoredExercises("Martial arts, different types, moderate pace (taichi, taekwondo, etc)", 10);
        this.exerciseList = exerciseList;
    }

    @Override
    public void addMeals(Meal data) throws ProgramException {
    }

    @Override
    public void addDefaultValues(Meal item) {
    }

    @Override
    public Meal getMeal(LocalDate date, int index) {
        return null;
    }

    @Override
    public Meal delete(LocalDate date, int index) {
        return null;
    }

    @Override
    public void deleteAllMealsOnDate(LocalDate dateStr) {
    }

    @Override
    public Meal markDone(LocalDate date, int index) {
        return null;
    }

    @Override
    public boolean checkDate(LocalDate date) {
        return mealTracker.containsKey(date);
    }

    @Override
    public void setMealTracker(HashMap<LocalDate, ArrayList<Meal>> mealTracker) {
        this.mealTracker = mealTracker;
    }

    @Override
    public void setDefaultValues(HashMap<String, HashMap<String, Integer>> defaultValues) {
        this.defaultValues = defaultValues;
    }

    @Override
    public void setExerciseList(ExerciseList exerciseList) {
        this.exerciseList = exerciseList;
    }

    @Override
    public ArrayList<Meal> getMealsList(LocalDate inputDate) {
        if (mealTracker.containsKey(inputDate)) {
            return mealTracker.get(inputDate);
        } else {
            mealTracker.put(inputDate, new ArrayList<>());
            return mealTracker.get(inputDate);
        }
    }

    @Override
    public HashMap<LocalDate, ArrayList<Meal>> getMealTracker() {
        return mealTracker;
    }

    @Override
    public HashMap<String, HashMap<String, Integer>> getDefaultValues() {
        return defaultValues;
    }

    @Override
    public int getCalorieBalanceOnDate(LocalDate date) {
        return 1600;
    }

    @Override
    public ExerciseList getExerciseList() {
        return this.exerciseList;
    }
}
