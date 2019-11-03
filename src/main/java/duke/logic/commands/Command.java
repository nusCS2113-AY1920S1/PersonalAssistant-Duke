package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;
import duke.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static duke.commons.constants.DateConstants.LOCAL_DATE_FORMATTER;

/**
 * Command is the abstract base class for all the command objects.
 * which allow the child class to specify which command (e.g. add, delete, etc) to use.
 */
public abstract class Command {
    protected DateTimeFormatter dateFormat = LOCAL_DATE_FORMATTER;
    protected LocalDate currentDate = LocalDate.now();
    protected Ui ui = new Ui();
    protected String responseStr;
    protected boolean isDone = true;
    protected boolean isFail = false;
    protected String errorStr;
    protected int stage = 0;


    /**
     * This class is an abstract class that will change according to the inheritor.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param user the object that handles all user data
     * @throws DukeException when there is an error
     */
    public abstract void execute(MealList meals, Storage storage, User user, Wallet wallet) throws DukeException;

    public void setResponseStr(String responseStr) {
        this.responseStr = responseStr;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public boolean isExit() {
        return false;
    }

    public void failure() {
        ui.showLine();
        ui.showMessage(this.errorStr);
        ui.showLine();
    }

    public boolean isFail() {
        return this.isFail;
    }
}
