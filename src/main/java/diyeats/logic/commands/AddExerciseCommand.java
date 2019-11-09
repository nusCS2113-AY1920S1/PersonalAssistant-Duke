package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.ExerciseList;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

public class AddExerciseCommand extends Command {

    private String exerciseNameStr;
    private int exerciseValueInt;

    /**
     * Constructor for AddExerciseCommand.
     *
     */
    public AddExerciseCommand(String exerciseNameStr, int exerciseValueInt) {
        this.exerciseNameStr = exerciseNameStr;
        this.exerciseValueInt = exerciseValueInt;
    }

    // This constructor is called if there are issues parsing user input.
    public AddExerciseCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes AddExerciseCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) throws ProgramException {
        ExerciseList exerciseList = meals.getExerciseList();
        exerciseList.addStoredExercises(this.exerciseNameStr, this.exerciseValueInt);
        meals.setExerciseList(exerciseList);
        try {
            storage.updateExercises(meals);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showMessage("The addexercise command is successful!");
    }
}
