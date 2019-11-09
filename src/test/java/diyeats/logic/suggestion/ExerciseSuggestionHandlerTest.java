package diyeats.logic.suggestion;

import diyeats.commons.datatypes.Pair;
import diyeats.model.meal.ExerciseList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class ExerciseSuggestionHandlerTest {
    private ExerciseList exerciseList = new ExerciseList();
    private ExerciseSuggestionHandler exerciseSuggestionHandler = new ExerciseSuggestionHandler();

    public void setupExerciseList() {
        exerciseList.addStoredExercises("Running, 8 mph (7.5 min/mile)", 8);
        exerciseList.addStoredExercises("Running, 10 mph (6 min/mile)", 10);
        exerciseList.addStoredExercises("Cycling, 10 - 12mph (light effort)", 7);
        exerciseList.addStoredExercises("Cycling, 12 - 14mph (moderate effort)", 8);
        exerciseList.addStoredExercises("Cycling, 14 - 16mph (heavy effort)", 10);
        exerciseList.addStoredExercises("Soccer, casual, general", 7);
        exerciseList.addStoredExercises("Tennis, casual, general", 7);
        exerciseList.addStoredExercises("Martial arts, different types, moderate pace "
                + "(taichi, taekwondo, etc)", 10);
    }

    @Test
    public void getSizeTest() {
        assertEquals(0, exerciseSuggestionHandler.getSize());
    }

    @Test
    public void getExerciseTest() {
        assertNull(exerciseSuggestionHandler.getExercise(20));
    }

    @Test
    public void computeTest() {
        setupExerciseList();
        ArrayList<Pair> pairList = exerciseSuggestionHandler.compute(exerciseList, 1000,
                2000, null);
        assertEquals(8, pairList.size());

        pairList = exerciseSuggestionHandler.compute(exerciseList, 1000,
                2000, "Cycling");
        assertEquals(3, pairList.size());

        pairList = exerciseSuggestionHandler.compute(exerciseList, 1000,
                2000, "cycling");
        assertEquals(3, pairList.size());

        pairList = exerciseSuggestionHandler.compute(exerciseList, 1000,
                2000, "biCycling");
        assertEquals(0, pairList.size());

        pairList = exerciseSuggestionHandler.compute(exerciseList, 1000,
                2000, "");
        assertEquals(8, pairList.size());

        try {
            exerciseSuggestionHandler.compute(exerciseList, 0,
                    2000, "null");
            exerciseSuggestionHandler.compute(exerciseList, -100,
                    2000, "null");
        } catch (Exception e) {
            fail();
        }
    }
}
