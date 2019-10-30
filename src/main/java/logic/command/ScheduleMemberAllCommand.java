package logic.command;

import common.DukeException;
import model.Model;

public class ScheduleMemberAllCommand extends Command {

    private static final String SUCCESS_MSSAGE = "Schedule all tasks of member: ";
    private static final String FAIL_MSSAGE = "fail to schedule all tasks of member: ";
    private static final String EMPTY_MSSAGE = "no task for member: ";

    private String memberName;

    public ScheduleMemberAllCommand(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        try {
            String tasks = model.scheduleMemberAll(memberName);

            if (tasks.equals("")) {
                return new CommandOutput(EMPTY_MSSAGE + memberName);
            } else {
                return new CommandOutput(SUCCESS_MSSAGE + memberName + tasks);
            }

        } catch (Exception e) {
            throw new DukeException(FAIL_MSSAGE + memberName);
        }
    }
}
