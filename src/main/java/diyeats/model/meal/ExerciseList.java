package diyeats.model.meal;

import diyeats.commons.datatypes.Pair;

import java.time.LocalDate;
import java.util.HashMap;

//@@author Fractalisk
public class ExerciseList {
    private HashMap<String, Integer> storedExercises = new HashMap<>();
    private HashMap<LocalDate, Pair> exercisePlan = new HashMap<>();

    public ExerciseList() {
        addStoredExercises("Running", 14);
    }

    public HashMap<String, Integer> getStoredExercises() {
        return this.storedExercises;
    }

    public void addStoredExercises(String description, int value) {
        storedExercises.put(description, value);
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
