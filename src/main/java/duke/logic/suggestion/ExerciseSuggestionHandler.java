package duke.logic.suggestion;

import duke.model.meal.ExerciseList;
import duke.model.user.User;

import java.util.HashMap;

/**
 * Exercise is defined in metabolic unit at rest(MET), eg, 1 hr of running which is 14.5 MET
 * is equal to 14.5 hours of static calorie loss.
 */
public class ExerciseSuggestionHandler {
    private ExerciseList exerciseList;
    private User user;

    public ExerciseSuggestionHandler(ExerciseList exerciseList, User user) {
        this.exerciseList = exerciseList;
        this.user = user;
    }

    public float calculateStaticCalorieExpenditure() {
        float minsInDay = 24*60;
        return user.getDailyCalorie() / minsInDay;
    }

    public HashMap<String, Integer> compute(int calorieToExercise) {
        HashMap<String, Integer> exerciseHashMap = this.exerciseList.getStoredExercises();
        float staticCalorieExpenditure = calculateStaticCalorieExpenditure();
        int minsToBurnCalories = (int) ((float)calorieToExercise / staticCalorieExpenditure);

        for (String itr : exerciseHashMap.keySet()) {
            int met = exerciseHashMap.get(itr);
            met = minsToBurnCalories / met;
            exerciseHashMap.replace(itr, met);
        }

        return exerciseHashMap;
    }
}
