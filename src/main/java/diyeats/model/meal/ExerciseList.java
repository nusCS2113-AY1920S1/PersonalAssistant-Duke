package diyeats.model.meal;

import diyeats.commons.datatypes.Pair;

import java.time.LocalDate;
import java.util.HashMap;

//@@author Fractalisk

public class ExerciseList {
    private HashMap<String, Integer> storedExercises = new HashMap<>();
    private HashMap<LocalDate, Pair> exercisePlan = new HashMap<>();

    public ExerciseList() {
        addStoredExercises("Running, 8 mph (7.5 min/mile)", 8);
        addStoredExercises("Running, 10 mph (6 min/mile)", 10);
        addStoredExercises("Cycling, 10 - 12mph (light effort)", 7);
        addStoredExercises("Cycling, 12 - 14mph (moderate effort)", 8);
        addStoredExercises("Cycling, 14 - 16mph (heavy effort)", 10);
        addStoredExercises("Soccer, casual, general", 7);
        addStoredExercises("Tennis, casual, general", 7);
        addStoredExercises("Martial arts, different types, moderate pace (taichi, taekwondo, etc)", 10);
    }

    public HashMap<String, Integer> getStoredExercises() {
        return this.storedExercises;
    }

    public void addStoredExercises(String description, int value) {
        if (!storedExercises.containsKey(description)) {
            storedExercises.put(description, value);
        } else {
            storedExercises.replace(description, value);
        }
    }

    public Pair getExerciseAtDate(LocalDate date) {
        return exercisePlan.get(date);
    }

    public void addExerciseAtDate(LocalDate date, String exercise, int factor) {
        exercisePlan.put(date, new Pair(exercise, factor));
    }

    public boolean checkExerciseAtDate(LocalDate date) {
        return exercisePlan.containsKey(date);
    }

}
