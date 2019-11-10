package diyeats.model.meal;

import diyeats.commons.datatypes.Pair;

import java.time.LocalDate;
import java.util.HashMap;

//@@author Fractalisk

/**
 * ExerciseList is a structure to store exercises in the model.
 */
public class ExerciseList {
    private HashMap<String, Integer> storedExercises = new HashMap<>();
    private HashMap<LocalDate, Pair> exercisePlan = new HashMap<>();

    /**
     * Constructor for exerciseList.
     */
    public ExerciseList() {
        //These instances of stored exercises are hardcoded for practical exam purposes
        addStoredExercises("Running, 8 mph (7.5 min/mile)", 8);
        addStoredExercises("Running, 10 mph (6 min/mile)", 10);
        addStoredExercises("Cycling, 10 - 12mph (light effort)", 7);
        addStoredExercises("Cycling, 12 - 14mph (moderate effort)", 8);
        addStoredExercises("Cycling, 14 - 16mph (heavy effort)", 10);
        addStoredExercises("Soccer, casual, general", 7);
        addStoredExercises("Tennis, casual, general", 7);
        addStoredExercises("Martial arts, different types, moderate pace (taichi, taekwondo, etc)", 10);
    }

    /**
     * Getter for storedExercises.
     * @return HashMap containing all exercise names and their associated MET.
     */
    public HashMap<String, Integer> getStoredExercises() {
        return this.storedExercises;
    }

    /**
     * Adds an exercise along with its MET into the storedExercises HashMap.
     * @param description name of the exercise
     * @param value MET value of the exercise
     */
    public void addStoredExercises(String description, int value) {
        if (!storedExercises.containsKey(description)) {
            storedExercises.put(description, value);
        } else {
            storedExercises.replace(description, value);
        }
    }

    /**
     * Getter for an exercise on a date.
     * @param date date exercise was scheduled for
     * @return a pair object encapsulating name and duration of exercise
     */
    public Pair getExerciseAtDate(LocalDate date) {
        return exercisePlan.get(date);
    }

    /**
     * Adds an exercise to a date.
     * @param date date exercise is scheduled for
     * @param selectedExercise a pair object encapsulating name and duration of exercise
     */
    public void addExerciseAtDate(LocalDate date, Pair selectedExercise) {
        exercisePlan.put(date, selectedExercise);
    }

}
