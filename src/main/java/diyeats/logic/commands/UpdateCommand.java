package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.parsers.InputValidator;
import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.time.LocalDate;

//@@author koushireo

/**
 * ListCommand is a public class that inherits from abstract class Command.
 * It displays all the meals in a relevant day in a list to the user
 */
public class UpdateCommand extends Command {
    private String age;
    private String weight;
    private String date;
    private String height;
    private String name;
    private String activity;
    private UpdateWeightCommand c2 = new UpdateWeightCommand();
    private boolean flag = false;
    /**
     * Constructor for UpdateCommand.
     */

    public UpdateCommand(String age, String weight, String date, String height, String name, String activity) {
        this.age = age;
        this.weight = weight;
        this.date = date;
        this.height = height;
        this.name = name;
        this.activity = activity;
    }

    public UpdateCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes the UpdateCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals,  Storage storage, User user, Wallet wallet, Undo undo) {
        switch (stage) {
            case 0:
                if (date == null) {
                    LocalDate now = LocalDate.now();
                    undo.undoUpdate(now.format(dateFormat), user);
                } else {
                    undo.undoUpdate(date, user);
                }
                if (this.age != null) {
                    UpdateAgeCommand c1 = new UpdateAgeCommand(age);
                    c1.execute(meals, storage, user, wallet, undo);
                }
                if (this.height != null) {
                    UpdateHeightCommand c1 = new UpdateHeightCommand(height);
                    c1.execute(meals, storage, user, wallet, undo);
                }
                if (this.name != null) {
                    UpdateNameCommand c1 = new UpdateNameCommand(name);
                    c1.execute(meals, storage, user, wallet, undo);
                }
                if (this.activity != null) {
                    UpdateActivityCommand c1 = new UpdateActivityCommand(activity);
                    c1.execute(meals, storage, user, wallet, undo);
                }
                if (this.weight != null) {
                    if (this.date == null) {
                        c2 = new UpdateWeightCommand(weight);
                    } else {
                        c2 = new UpdateWeightCommand(weight, date);
                    }
                    c2.execute(meals, storage, user, wallet, undo);
                    if (c2.isDone() == false) {
                        this.stage += 1;
                        this.isDone = false;
                    }
                }
                break;
            case 1:
                try {
                    c2.setResponseStr(this.responseStr);
                    c2.execute(meals, storage, user, wallet, undo);
                    this.isDone = c2.isDone();
                    if (c2.isDone()) {
                        this.stage += 1;
                    }
                } catch (ProgramException e) {
                    ui.showMessage(e.getMessage());
                }
                break;
            default:
                isDone = true;
        }
    }

    /**
     * Setter method to obtain user's response.
     * @param response user's response
     */
    public void setResponseStr(String response) {
        this.responseStr = response;
    }

    /**
     * This function facilitates undo for UpdateCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */

    public void undo(MealList meals,  Storage storage, User user, Wallet wallet) {
        UpdateAgeCommand c1 = new UpdateAgeCommand(age);
        c1.undo(meals, storage, user, wallet);
        UpdateWeightCommand c2 = new UpdateWeightCommand(weight, date);
        c2.undo(meals, storage, user, wallet);
        UpdateHeightCommand c3 = new UpdateHeightCommand(height);
        c3.undo(meals, storage, user, wallet);
        UpdateNameCommand c4 = new UpdateNameCommand(name);
        c4.undo(meals, storage, user, wallet);
        UpdateActivityCommand c5 = new UpdateActivityCommand(activity);
        c5.undo(meals, storage, user, wallet);
    }
}
