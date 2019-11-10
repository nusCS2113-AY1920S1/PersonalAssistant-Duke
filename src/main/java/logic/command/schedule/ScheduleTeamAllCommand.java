package logic.command.schedule;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class ScheduleTeamAllCommand extends Command {

    private static final String SUCCESS_MSSAGE = "Schedule all tasks of the whole team: ";
    private static final String EMPTY_MSSAGE = "No task for the whole team.";

    //@@author yuyanglin28

    /**
     * This method is to schedule all tasks
     * @param model Model interface
     * @return sorted tasks
     */
    @Override
    public CommandOutput execute(Model model) {
        String tasks = model.tasksAllInorderTime();
        if (tasks.equals("")) {
            return new CommandOutput(EMPTY_MSSAGE);
        } else {
            return new CommandOutput(SUCCESS_MSSAGE + tasks);
        }

    }
}
