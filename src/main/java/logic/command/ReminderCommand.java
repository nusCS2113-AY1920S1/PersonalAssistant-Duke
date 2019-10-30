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
     * @param taskIndex index of task to be reminded about
     * @param beforeInt numeric value of time before
     * @param beforeMult multiplier of time before, minutes, hours or days
     */
    public ReminderCommand(int taskIndex, int beforeInt, char beforeMult)   {
        this.taskIndex = taskIndex;
        this.beforeInt = beforeInt;
        this.beforeMult = beforeMult;
    }

    /**
     * Generates new reminder
     */
    @Override
    public CommandOutput execute(Model model) {
        return new CommandOutput("Reminder todo");
    }
}
