package diyeats.logic.suggestion;

import diyeats.commons.datatypes.Pair;
import diyeats.model.meal.ExerciseList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

//@@author Fractalisk

/**
 * Exercise is defined in metabolic unit at rest (MET), eg, 1 hr of running which is 14 MET
 * is equal to 14 hours of static calorie loss.
 */
public class ExerciseSuggestionHandler {
    private ArrayList<Pair> exerciseArrayList = new ArrayList<>();

    private float calculateStaticCalorieExpenditure(int dailyCalorie) {
        assert dailyCalorie > 0 : "dailyCalorie must be greater than 0";
        float minInDay = 24 * 60;
        return dailyCalorie / minInDay;
    }

    public ExerciseSuggestionHandler() {
    }

    public int getSize() {
        if (exerciseArrayList != null) {
            return exerciseArrayList.size();
        } else {
            return 0;
        }
    }

    public Pair getExercise(int idx) {
        if (idx <= getSize()) {
            String exerciseName = exerciseArrayList.get(idx - 1).getKey();
            int exerciseReps = exerciseArrayList.get(idx - 1).getValue();
            return new Pair(exerciseName, exerciseReps);
        } else {
            return null;
        }
    }

    public ArrayList<Pair> compute(ExerciseList exerciseList, int calorieToExercise, int dailyCalorie, String keyword) {
        exerciseArrayList.clear();  //clear contents of exerciseArrayList in case instance is reused (e.g in testing)
        HashMap<String, Integer> exerciseHashMap = exerciseList.getStoredExercises();
        float staticCalorieExpenditure = calculateStaticCalorieExpenditure(dailyCalorie);
        assert staticCalorieExpenditure > 0 : "staticCalorieExpenditure must be greater than 0";
        int minsToBurnCalories = (int) ((float)calorieToExercise / staticCalorieExpenditure);

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
