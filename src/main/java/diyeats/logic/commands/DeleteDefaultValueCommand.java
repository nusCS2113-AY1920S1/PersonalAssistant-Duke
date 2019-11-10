package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;

//@@author Fractalisk

/**
 * DeleteDefaultValueCommand is a public class that inherits from abstract class Command.
 * An DeleteDefaultValueCommand object encapsulates the keyword to be deleted, as well as a list of items with similar
 * name to the provided keyword.
 */
public class DeleteDefaultValueCommand extends Command {
    private String keywordStr;
    /* Marker for instant delete without proceeding to second stage.
       Occurs when exact match exists. Or there is only 1 similar entry*/
    private boolean isInstantDelete = false;
    private ArrayList<String> deleteCandidateKeys = new ArrayList<>();

    /**
     * Constructor for DeleteDefaultValueCommand.
     * @param keywordStr the keyword of meal to be deleted.
     */
    public DeleteDefaultValueCommand(String keywordStr) {
        this.keywordStr = keywordStr;
    }

    // This constructor is called if there are issues parsing user input.
    public DeleteDefaultValueCommand(boolean isFail, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes the DeleteDefaultValueCommand. Has 2 stages.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        isDone = false;

        switch (stage) {
            case 0:
                //Checks for exact matches and deletes. Otherwise shows a list of similar items.
                execute_stage_0(meals, storage);
                stage++;
                break;
            case 1:
                //Checks user input for index. Deletes item indicated by index on previously shown list.
                execute_stage_1(meals, storage);
                break;
            default:
                //Exits execute loop if command enters invalid state
                isDone = true;
        }
    }

    /**
     * First stage of execute.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     */
    private void execute_stage_0(MealList meals, Storage storage) {
        HashMap<String, HashMap<String, Integer>> defaultValues = meals.getDefaultValues();

        if (defaultValues.get(keywordStr) != null) {
            isInstantDelete = true;
            deleteCandidateKeys.add(keywordStr);
        } else {
            for (String itr : defaultValues.keySet()) {
                if (itr.toLowerCase().contains(keywordStr.toLowerCase())) {
                    deleteCandidateKeys.add(itr);
                }
            }
        }

        if (isInstantDelete || deleteCandidateKeys.size() == 1) {
            int lastIdx = deleteCandidateKeys.size() - 1;
            ui.showMessage("Success! " + deleteCandidateKeys.get(lastIdx)
                    + " has been deleted from the list of default values.");
            meals.getDefaultValues().remove(deleteCandidateKeys.get(lastIdx));

            try {
                storage.writeFile(meals);
            } catch (ProgramException e) {
                ui.showMessage(e.getMessage());
            }
            isDone = true;
        } else if (deleteCandidateKeys.size() == 0) {
            ui.showMessage("It appears there are no meals associated with the keyword provided.");
            isDone = true;
        } else {
            ui.showDeleteCandidateKeys(deleteCandidateKeys);
            ui.showMessage("Input 0 to cancel selection.");
        }
    }

    /**
     * Second stage of execute.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     */
    private void execute_stage_1(MealList meals, Storage storage) {
        int deleteIdx;

        try {
            deleteIdx = Integer.parseInt(this.responseStr);
        } catch (NumberFormatException e) {
            ui.showMessage("Could not parse " + responseStr + " as a number. Please input an integer.");
            return;
        }

        if (deleteIdx == 0) {
            ui.showMessage("The delete default command has been canceled.");
            isDone = true;
            return;
        }

        if (deleteIdx < 0 || deleteIdx > deleteCandidateKeys.size()) {
            ui.showMessage(responseStr + " is out of bounds. Please input a valid index.");
            return;
        }

        ui.showMessage("Success! " + deleteCandidateKeys.get(deleteIdx - 1)
                + " has been deleted from the list of default values.");
        meals.getDefaultValues().remove(deleteCandidateKeys.get(deleteIdx - 1));

        try {
            storage.writeFile(meals);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }

        isDone = true;
    }
}
