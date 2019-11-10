package logic.command;

import java.util.Calendar;
import java.util.Date;

import logic.ReminderController;
import model.Model;
import model.Task;

//@@author AugGust
public class ReminderCommand extends Command {
    private int taskIndex;
    private int beforeInt;
    private char beforeMult;

    /**
     * Creates new ReminderCommand
     *
     * @param taskIndex  index of task to be reminded about
     * @param beforeInt  numeric value of time before
     * @param beforeMult multiplier of time before, minutes, hours or days
     */
    public ReminderCommand(int taskIndex, int beforeInt, char beforeMult) {
        this.taskIndex = taskIndex;
        this.beforeInt = beforeInt;
        this.beforeMult = beforeMult;
    }

    /**
     * Generates new reminder
     */
    @Override
    public CommandOutput execute(Model model) {
        if (taskIndex > model.getTaskListSize() || taskIndex <= 0) {
            return new CommandOutput("Please provide a valid task number");
        } else if (model.getTasksManager().getTaskById(taskIndex - 1).getTime() == null) {
            return new CommandOutput("Please set a time for the task first");
        } else {
            String feedback = "Alright! I'll remind you about:\n"
                    + model.getTasksManager().getTaskById(taskIndex - 1) + "\n" + beforeInt;
            if (beforeMult == 'm') {
                feedback += " minutes ";
            } else if (beforeMult == 'h') {
                feedback += " hours ";
            } else {
                feedback += " days ";
            }
            feedback += "before it's due";

            //Check for valid date
            Calendar cal = Calendar.getInstance();
            cal.setTime(model.getTasksManager().getTaskById(taskIndex - 1).getTime());

            if (beforeMult == 'm') {
                cal.add(Calendar.MINUTE, -1 * beforeInt);
            } else if (beforeMult == 'h') {
                cal.add(Calendar.HOUR, -1 * beforeInt);
            } else {
                cal.add(Calendar.HOUR, -24 * beforeInt);
            }

            Date reminderTime = cal.getTime();

            Date now = new Date();
            if (reminderTime.compareTo(now) <= 0) {
                return new CommandOutput("You can't set a reminder earlier than the current time!");
            }

            model.getTasksManager().addReminder(taskIndex - 1, reminderTime);
            model.save();

            ReminderController.refreshAllReminders();

            return new CommandOutput(feedback);
        }

    }
}
