package diyeats.storage;

import com.google.gson.GsonBuilder;
import diyeats.commons.datatypes.Pair;
import diyeats.commons.file.LocalDateAdapter;
import diyeats.logic.autocorrect.Autocorrect;
import diyeats.model.meal.ExerciseList;
import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//@@author Fractalisk

public class LoadTest {
    private Load loader = new Load(new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class,
            new LocalDateAdapter()).create());
    private LocalDate date = LocalDate.parse("2019-11-08");

    @Test
    public void loadMealListDataTest() {
        final String expectedEntry1 = "[B][NO] burger | sodium:20 fats:20 calorie:1000 cost: 0";
        final String expectedEntry2 = "[L][YES] mac & cheese | sodium:20 fats:30 calorie:500 cost: 0";
        final String expectedEntry3 = "[D][NO] pizza | calorie:800 cost: 0";
        MealList meals = new MealList();

        loader.test();
        try {
            loader.loadMealListData(meals);
            ArrayList<Meal> mealTracker = meals.getMealTracker().get(date);
            assertEquals(expectedEntry1, mealTracker.get(0).toString());
            assertEquals(expectedEntry2, mealTracker.get(1).toString());
            assertEquals(expectedEntry3, mealTracker.get(2).toString());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void loadDefaultMealDataTest() {
        MealList meals = new MealList();

        loader.test();
        try {
            loader.loadDefaultMealData(meals);
            HashMap<String, HashMap<String, Integer>> mealTracker = meals.getDefaultValues();
            HashMap<String, Integer> nutritionalValue;
            nutritionalValue = mealTracker.get("burger");
            assertEquals(nutritionalValue.get("calorie"), 500);
            assertEquals(nutritionalValue.get("fats"), 20);
            assertEquals(nutritionalValue.get("sodium"), 20);
            nutritionalValue = mealTracker.get("steak");
            assertEquals(nutritionalValue.get("calorie"), 600);
            assertEquals(nutritionalValue.get("fats"), 20);
            assertEquals(nutritionalValue.get("sodium"), 10);
            nutritionalValue = mealTracker.get("Tomato Pasta");
            assertEquals(nutritionalValue.get("calorie"), 450);
            assertEquals(nutritionalValue.get("fats"), 10);
            assertEquals(nutritionalValue.get("sodium"), 10);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void loadExercisesTest() {
        MealList meals = new MealList();

        loader.test();
        try {
            loader.loadExercises(meals);
            ExerciseList exerciseList = meals.getExerciseList();
            Pair pair = exerciseList.getExerciseAtDate(date);
            assertEquals(pair.getKey(), "Running");
            assertEquals(pair.getValue(), 35);
            HashMap<String, Integer> storedExercises = exerciseList.getStoredExercises();
            assertEquals(storedExercises.get("Running"), 14);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void loadGoalTest() {
        final String expectedEntry = "2019-11-20|2020-11-20|60.0|1404116|4";
        User user = new User();

        loader.test();
        try {
            loader.loadGoals(user);
            assertEquals(user.getGoal().toString(), expectedEntry);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void loadTransactionsTest() {
        Wallet wallet = new Wallet();

        loader.test();
        try {
            loader.loadTransactions(wallet);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void loadUserTest() {
        final String expectedString = "Hashir|175|22|MALE|true|2|70.0|2019-11-08";
        User user = new User();

        loader.test();
        try {
            user = loader.loadUser();
            assertEquals(user.toString(), expectedString);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void loadAutocorrectTest() {
        Autocorrect autocorrect = new Autocorrect();

        loader.test();
        try {
            loader.loadAutoCorrect(autocorrect);
            autocorrect.setWord("calorei");
            autocorrect.execute();
            assertEquals(autocorrect.getWord(), "calorie");
            autocorrect.setWord("lunxh");
            autocorrect.execute();
            assertEquals(autocorrect.getWord(), "lunch");
            autocorrect.setWord("cacium");
            autocorrect.execute();
            assertEquals(autocorrect.getWord(), "calcium");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void loadHelpTest() {
        ArrayList<String> lines = new ArrayList<>();

        loader.test();
        try {
            loader.loadHelp(lines, "add");
        } catch (Exception e) {
            fail();
        }
    }
}
