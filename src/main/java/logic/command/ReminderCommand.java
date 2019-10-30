package logic.command;

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
        if (taskIndex > model.getTaskListSize()) {
            return new CommandOutput("Please provide a valid task index");
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
            return new CommandOutput(feedback);
        }

    }
}
