package logic.command.schedule;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class ScheduleTeamAllCommand extends Command {

    private static final String SUCCESS_MSSAGE = "Schedule all tasks of the whole team: ";
    private static final String FAIL_MSSAGE = "fail to schedule all tasks of the whole team.";
    private static final String EMPTY_MSSAGE = "no task for the whole team.";

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        try {
            String tasks = model.scheduleTeamAll();
            if (tasks.equals("")) {
                return new CommandOutput(EMPTY_MSSAGE);
            } else {
                return new CommandOutput(SUCCESS_MSSAGE + tasks);
            }
        } catch (Exception e) {
            throw new DukeException(FAIL_MSSAGE);
        }

    }
}
