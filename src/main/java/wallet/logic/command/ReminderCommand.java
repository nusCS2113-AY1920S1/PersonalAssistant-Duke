//@@author A0171206R

package wallet.logic.command;

import wallet.logic.LogicManager;
import wallet.model.Wallet;
import wallet.reminder.Reminder;
import wallet.ui.Ui;

public class ReminderCommand extends Command {
    public static final String COMMAND_WORD = "reminder";
    public static final String MESSAGE_USAGE = "Usage for reminder command."
            + "\n" + COMMAND_WORD + "on"
            + "\n" + COMMAND_WORD + "off"
            + "\n" + COMMAND_WORD + "set" + "<TIME IN SECONDS>";
    public static final String MESSAGE_SUCCESS_REMINDER_ON = "Got it. I've turned on reminders!";
    public static final String MESSAGE_FAILURE_REMINDER_ON = "It looks like you already have reminders turned on!";
    public static final String MESSAGE_FAILURE_REMINDER_OFF = "It looks like you already have reminders turned off!";
    public static final String MESSAGE_SUCCESS_REMINDER_OFF = "Got it. I've turned off reminders!";
    public static final String MESSAGE_SUCCESS_REMINDER_SET = "Got it. I've set reminders to notify every ";
    public static final String MESSAGE_FAILURE_REMINDER_SET = "Sorry! It looks like setting reminder time failed!";

    private Reminder reminder = null;
    private String input;
    private Ui ui;
    private int timeInSeconds;

    public ReminderCommand(String input) {
        this.input = input;
    }

    @Override
    public boolean execute(Wallet wallet) {
        String[] info = input.split(" ");
        if (info.length == 1) {
            if (info[0].equals("on")) {
                if (LogicManager.getReminder().getAutoRemind()) {
                    System.out.println(MESSAGE_FAILURE_REMINDER_ON);
                } else {
                    LogicManager.getReminder().setAutoRemind(true);
                    LogicManager.getReminder().autoRemindStart();
                    System.out.println(MESSAGE_SUCCESS_REMINDER_ON);
                }
            } else if (info[0].equals("off")) {
                if (!LogicManager.getReminder().getAutoRemind()) {
                    System.out.println(MESSAGE_FAILURE_REMINDER_OFF);
                } else {
                    LogicManager.getReminder().autoRemindStop();
                    System.out.println(ReminderCommand.MESSAGE_SUCCESS_REMINDER_OFF);
                    LogicManager.getReminder().setAutoRemind(false);
                }
            }
        } else if (info.length == 2) {
            if (info[0].equals("set")) {
                timeInSeconds = Integer.parseInt(info[1]);
                LogicManager.getReminder().autoRemindStop();
                LogicManager.getReminder().setTimeInSeconds(timeInSeconds);
                System.out.println(MESSAGE_SUCCESS_REMINDER_SET + timeInSeconds + " seconds");
                if (LogicManager.getReminder().getAutoRemind()) {
                    LogicManager.getReminder().autoRemindStart();
                }
            }
        } else {
            System.out.println(MESSAGE_USAGE);
        }
        return false;
    }
}
