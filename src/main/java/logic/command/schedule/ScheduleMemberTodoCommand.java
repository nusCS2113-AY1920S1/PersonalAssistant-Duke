package logic.command.schedule;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class ScheduleMemberTodoCommand extends Command {

    private static final String SUCCESS_MSSAGE = "Schedule todo tasks of member: ";
    private static final String FAIL_MSSAGE = "fail to schedule todo tasks of member: ";
    private static final String EMPTY_MSSAGE = "no todo task for member: ";

    private String memberName;

    public ScheduleMemberTodoCommand(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        try {
            String tasks = model.scheduleMemberTodo(memberName);
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
