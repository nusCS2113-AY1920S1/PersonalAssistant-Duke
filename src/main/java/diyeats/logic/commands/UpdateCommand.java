package diyeats.logic.commands;

import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

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
        this.isFail = flag;
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
    public void execute(MealList meals,  Storage storage, User user, Wallet wallet) {
        switch (stage) {
            case 0:
                if (this.age != null) {
                    UpdateAgeCommand c1 = new UpdateAgeCommand(age);
                    c1.execute(meals, storage, user, wallet);
                }
                if (this.weight != null) {
                    if (this.date == null) {
                        c2 = new UpdateWeightCommand(weight);
                    } else {
                        c2 = new UpdateWeightCommand(weight, date);
                    }
                    c2.execute(meals, storage, user, wallet);
                    if (c2.isDone() == false) {
                        this.stage += 1;
                        this.isDone = false;
                    }
                }
                if (this.height != null) {
                    UpdateHeightCommand c1 = new UpdateHeightCommand(height);
                    c1.execute(meals, storage, user, wallet);
                }
                if (this.name != null) {
                    UpdateNameCommand c1 = new UpdateNameCommand(name);
                    c1.execute(meals, storage, user, wallet);
                }
                if (this.activity != null) {
                    UpdateActivityCommand c1 = new UpdateActivityCommand(activity);
                    c1.execute(meals, storage, user, wallet);
                }
                break;
            case 1:
                c2.setResponseStr(this.responseStr);
                c2.execute(meals, storage, user, wallet);
                this.isDone = c2.isDone();
                if (c2.isDone()) {
                    this.stage += 1;
                }
                break;
            default:
                isDone = true;
        }
    }

    public void setResponseStr(String response) {
        this.responseStr = response;
    }
}
