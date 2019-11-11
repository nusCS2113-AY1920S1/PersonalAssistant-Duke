package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.ExerciseList;
import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

//@@author Fractalisk

/**
 * AddExerciseCommand is a public class that inherits from abstract class Command.
 * An AddExerciseCommand object encapsulates exerciseNameStr and exerciseValueInt, which is added to ExerciseList
 * This class adds a suggestable exercise, not an exercise to a date.
 */
public class AddExerciseCommand extends Command {

    private String exerciseNameStr;
    private int exerciseValueInt;

    /**
     * Constructor for addExerciseCommand.
     * @param exerciseNameStr name of the exercise to be added
     * @param exerciseValueInt MET value of exercise to be added
     */
    public AddExerciseCommand(String exerciseNameStr, int exerciseValueInt) {
        this.exerciseNameStr = exerciseNameStr;
        this.exerciseValueInt = exerciseValueInt;
    }

    // This constructor is called if there are issues parsing user input.
    public AddExerciseCommand(boolean isFail, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes AddExerciseCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param undo the object that facilitates the removal of effect of previous command
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet, Undo undo) throws ProgramException {
        ExerciseList exerciseList = meals.getExerciseList();
        if (exerciseList.getStoredExercises().containsKey(this.exerciseNameStr)) {
            undo.undoAddExercise(this.exerciseNameStr, exerciseList.getStoredExercises().get(this.exerciseNameStr));
        }  else {
            undo.undoAddExercise(this.exerciseNameStr, -1);
        }
        exerciseList.addStoredExercises(this.exerciseNameStr, this.exerciseValueInt);
        meals.setExerciseList(exerciseList);
        try {
            storage.writeExercises(meals);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showMessage("The add exercise command is successful!");
    }

    public void undo(MealList meals, Storage storage, User user, Wallet wallet) {
        if (this.exerciseValueInt == -1) {
            ExerciseList exerciseList = meals.getExerciseList();
            exerciseList.removeStoredExercises(this.exerciseNameStr);
        } else {
            ExerciseList exerciseList = meals.getExerciseList();
            exerciseList.addStoredExercises(this.exerciseNameStr, this.exerciseValueInt);
        }
        try {
            storage.writeExercises(meals);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
    }

    public void undoForDelete(MealList meals, Storage storage, User user, Wallet wallet) {
        ExerciseList exerciseList = meals.getExerciseList();
        exerciseList.addStoredExercises(this.exerciseNameStr, this.exerciseValueInt);
        meals.setExerciseList(exerciseList);
        try {
            storage.writeExercises(meals);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
    }
}
