package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;
import duke.ui.Ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Command is the abstract base class for all the command objects.
 * which allow the child class to specify which command (e.g. add, delete, etc) to use.
 */
public abstract class Command {
    protected DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    protected Calendar calendarDate = Calendar.getInstance();
    protected String currentDate = dateFormat.format(calendarDate.getTime());
    protected Ui ui = new Ui();
    protected String response;
    protected boolean isDone = true;
    protected boolean isFail = false;
    protected String error;
    protected int stage = 0;


    /**
     * This class is an abstract class that will change according to the inheritor.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param user the object that handles all user data
     * @throws DukeException when there is an error
     */
    public abstract void execute(MealList meals, Storage storage, User user, Wallet wallet);

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public boolean isExit() {
        return false;
    }

    public void failure() {
        ui.showMessage(this.error);
    }

    public boolean isFail() {
        return this.isFail;
    }
}
