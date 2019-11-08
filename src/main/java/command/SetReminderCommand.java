package command;

import dictionary.Bank;
import exception.ReminderSetupException;
import parser.Parser;
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
    private static final int ASK_FOR_NEW_WORD = 2;
    private static final int ASK_FOR_REMINDER_DATE = 3;

    protected static ArrayList<String> reminderWordList;
    protected String userResponse;

    /**
     * State at which the reminder setup is executing at.
     */
    protected static int reminderSetupState;

    /**
     * Assigns the relevant values for a reminder setup for a single line schedule command.
     * @param state the state at which to execute the reminder setup (in this case always 4)
     * @param wordArrayList the list of words to remind the users of
     * @param dateDetail the date and time of the reminder
     */
    public SetReminderCommand(int state, ArrayList<String> wordArrayList, String dateDetail) {
        reminderWordList = wordArrayList;
        reminderSetupState = state;
        userResponse = dateDetail;
    }

    /**
     * Updates the state count of the setup stage.
     * @param state the number representing the stage the setup is at
     */
    public SetReminderCommand(int state) {
        reminderSetupState = state;
        if (reminderSetupState == 1) {
            reminderWordList = new ArrayList<>();
        }
    }

    public SetReminderCommand(String userInput) {
        userResponse = userInput;
    }

    public SetReminderCommand(int state, String userInput) {
        reminderSetupState = state;
        userResponse = userInput;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            switch (reminderSetupState) {
            case 1: //ask for a list of words
                return ui.showReminderSetup(ASK_FOR_WORDS);
            case 2: //take in the words
                reminderWordList.add(userResponse.trim());
                return ui.showReminderSetup(ASK_FOR_NEW_WORD);
            case 3: //ask for reminder date
                return ui.showReminderSetup(ASK_FOR_REMINDER_DATE);
            case 4:
                Date date = Parser.parseDate(userResponse.trim());
                new Reminder(date, reminderWordList);
                //write words into reminder file
                storage.writeFile(reminderWordList.toString(), true, "reminder");
                return ui.showReminderSummary(reminderWordList, date);
            default:
                throw new ReminderSetupException();
            }
        } catch (ReminderSetupException e) {
            return e.showError();
        }
    }
}
