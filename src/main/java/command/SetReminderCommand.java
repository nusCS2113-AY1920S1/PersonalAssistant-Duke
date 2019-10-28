package command;

import dictionary.Bank;
import exception.ReminderSetupException;
import reminder.Reminder;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a command from user to add a task.
 * Inherits from Command class.
 */
public class SetReminderCommand extends Command {

    private static final int ASK_FOR_WORDS = 1;
    private static final int ASK_FOR_REMINDERDATE = 2;

    protected ArrayList<String> reminderWordList;
    protected String userResponse;

    /**
     * State at which the reminder setup is executing at.
     */
    protected static int reminderSetupState;

    public SetReminderCommand(int state) {
        reminderSetupState = state;
    }

    public SetReminderCommand(String userInput) {
        userResponse = userInput;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            switch (reminderSetupState) {
                case 1: //ask for a list of words
                    return ui.showReminderSetup(ASK_FOR_WORDS);
                case 2: //take in the words
                    reminderWordList.add(userResponse);
                case 3: //ask for reminder date
                    return ui.showReminderSetup(ASK_FOR_REMINDERDATE);
                case 4:
                    Date date = Reminder.parseDate(userResponse);
                    new Reminder(date);
                    return ui.showReminderSummary(reminderWordList, date);
                default:
                    throw new ReminderSetupException();
            }
        } catch (ReminderSetupException e) {
            return e.showError();
        }
    }
}
