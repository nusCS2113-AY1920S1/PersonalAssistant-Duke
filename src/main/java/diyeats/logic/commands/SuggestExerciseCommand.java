package diyeats.logic.commands;

import diyeats.commons.datatypes.Pair;
import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.suggestion.ExerciseSuggestionHandler;
import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

import static diyeats.commons.constants.DateConstants.LOCAL_DATE_FORMATTER;


//@@author Fractalisk

/**
 * Analyze the list of SuggestExercise objects as well as the current calorie goal of the
 * user, the date provided and the user meal parameters provided to give appropriate exercise suggestion.
 * This class adds an exercise to a date, not a suggestable exercise.
 */
public class SuggestExerciseCommand extends Command {
    private LocalDate date;
    private String keyword = null;
    private ExerciseSuggestionHandler exerciseSuggestionHandler;
    private Pair revertExercise = null;

    /**This constructor is to construct an empty SuggestExerciseCommand.
     */
    public SuggestExerciseCommand() {
    }

    /**This constructor is to construct SuggestExerciseCommand to facilitate undo.
     * @param date the date of the exercise to be removed
     */

    public SuggestExerciseCommand(LocalDate date) {
        this.date = date;
    }

    /**This constructor is to construct SuggestExerciseCommand to facilitate undo.
     * @param date the date of the exercise to be reverted
     * @param revertExercise the reverted exercise
     */

    public SuggestExerciseCommand(LocalDate date, Pair revertExercise, boolean flag) {
        this.date = date;
        this.revertExercise = revertExercise;
    }

    /**
     * Constructor for suggestExerciseCommand.
     * @param date the date for which an exercise suggestion is to be made
     * @param keyword any keywords the user has entered
     */
    public SuggestExerciseCommand(LocalDate date, String keyword) {
        this.date = date;
        this.keyword = keyword;
    }

    // This constructor is called if there are issues parsing user input.
    public SuggestExerciseCommand(boolean isFail, String message) {
        this.isFail = true;
        this.errorStr = message;
    }

    /**
     * Executes the suggestExercise command. Has 2 stages.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param undo the object that facilitates the removal of effect of previous command
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet, Undo undo) {
        isDone = false;
        switch (stage) {
            case 0:
                //Shows the user the list of relevant exercises as well as their duration
                exerciseSuggestionHandler = new ExerciseSuggestionHandler();
                execute_stage_0(meals, user);
                stage++;
                break;
            case 1:
                //Adds the selected exercise routine to exerciseList
                execute_stage_1(meals, storage, undo);
                break;
            default:
                //Exits execute loop if command enters invalid state
                isDone = true;
        }
    }

    /**
     * First stage of execute.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param user the object that handles all user data
     */
    private void execute_stage_0(MealList meals, User user) {
        if (user.getGoal() == null) {
            ui.goalNotFound();
            isDone = true;
        } else if (user.getTargetActivityLevel() == 5) {
            ui.goalNotFound();
            isDone = true;
        } else {
            int calorieToElevateActivityLevel = (int) (user.getDailyCalorie() * user.getActivityLevelDifference());
            int excessCalorie = user.getCalorieBalance() - meals.getCalorieBalanceOnDate(date);
            if (excessCalorie < 0) {
                //subtract current day's deficit of calories to the amount required for exercise
                calorieToElevateActivityLevel -= excessCalorie;
            }
            ui.showExerciseRequired(calorieToElevateActivityLevel, date);
            ArrayList<Pair> exerciseArrayList = this.exerciseSuggestionHandler.compute(meals.getExerciseList(),
                    calorieToElevateActivityLevel, user.getDailyCalorie(), keyword);

            ui.showExerciseOptions(exerciseArrayList);
            ui.showMessage("Input 0 to cancel selection");
        }
    }

    /**
     * Second stage of execute.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     */
    private void execute_stage_1(MealList meals, Storage storage, Undo undo) {
        int exerciseIdx;
        try {
            exerciseIdx = Integer.parseInt(this.responseStr);
        } catch (NumberFormatException e) {
            ui.showMessage("Could not parse " + responseStr + " as a number. Please input an integer.");
            return;
        }

        if (exerciseIdx == 0) {
            ui.showMessage("The suggest exercise command has been canceled");
            isDone = true;
            return;
        }

        if (exerciseIdx < 0 || exerciseIdx > exerciseSuggestionHandler.getSize()) {
            ui.showMessage(responseStr + " is out of bounds. Please input a valid index.");
            return;
        }

        Pair selectedExercise = exerciseSuggestionHandler.getExercise(exerciseIdx);
        undo.undoSuggestExercise(date, meals.getExerciseList().getExerciseAtDate(date));
        meals.getExerciseList().addExerciseAtDate(date, selectedExercise);
        ui.showMessage("Got it!, I have set the chosen exercise for the date "
                + date.format(LOCAL_DATE_FORMATTER) + ".");

        try {
            storage.writeExercises(meals);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
        isDone = true;
    }

    public void undo(MealList meals, Storage storage, User user, Wallet wallet) {
        if (revertExercise == null) {
            meals.getExerciseList().revertExerciseAtDate(date);
        } else {
            meals.getExerciseList().revertExerciseAtDate(date, revertExercise);
        }
    }
}
