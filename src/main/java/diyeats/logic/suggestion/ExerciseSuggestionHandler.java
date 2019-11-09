package diyeats.logic.suggestion;

import diyeats.commons.datatypes.Pair;
import diyeats.model.meal.ExerciseList;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

//@@author Fractalisk

/**
 * Exercise is defined in metabolic unit at rest (MET), eg, 1 hr of running which is 14.5 MET
 * is equal to 14.5 hours of static calorie loss.
 */
public class ExerciseSuggestionHandler {
    private ArrayList<Pair> exerciseArrayList = new ArrayList<>();
    private User user;

    public ExerciseSuggestionHandler(User user) {
        this.user = user;
    }

    public float calculateStaticCalorieExpenditure() {
        float minsInDay = 24 * 60;
        return user.getDailyCalorie() / minsInDay;
    }

    public int getSize() {
        if (exerciseArrayList != null) {
            return exerciseArrayList.size();
        } else {
            return 0;
        }
    }

    public void addChosenExercise(int idx, MealList meals, LocalDate date) {
        String exerciseName = exerciseArrayList.get(idx).getKey();
        int exerciseReps = exerciseArrayList.get(idx).getValue();
        meals.getExerciseList().addExerciseAtDate(date, exerciseName, exerciseReps);
    }

    public ArrayList<Pair> compute(MealList meals, int calorieToExercise, String keyword) {
        ExerciseList exerciseList = meals.getExerciseList();
        HashMap<String, Integer> exerciseHashMap = exerciseList.getStoredExercises();
        float staticCalorieExpenditure = calculateStaticCalorieExpenditure();
        int minsToBurnCalories = (int) ((float)calorieToExercise / staticCalorieExpenditure);
        this.exerciseArrayList.clear();

        for (String itr : exerciseHashMap.keySet()) {
            int met = exerciseHashMap.get(itr);
            met = minsToBurnCalories / met;
            Pair exerciseDurationPair = new Pair(itr, met);
            if (keyword == null || itr.toLowerCase().contains(keyword.toLowerCase())) {
                this.exerciseArrayList.add(exerciseDurationPair);
            }
        }
        Collections.sort(this.exerciseArrayList, (lhs, rhs) -> lhs.getKey().compareToIgnoreCase(rhs.getKey()));
        return this.exerciseArrayList;
    }
}
