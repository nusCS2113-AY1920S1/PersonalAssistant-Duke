package logic.command.schedule;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class ScheduleMemberTodoCommand extends Command {

    private static final String SUCCESS_MSSAGE = "Schedule todo tasks of member: ";
    private static final String EMPTY_MSSAGE = "no todo task for member: ";

    private int memberIndexInList;

    public ScheduleMemberTodoCommand(int memberIndexInList) {
        this.memberIndexInList = memberIndexInList;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        if (!checkMemberIndex(memberIndexInList, model)) {
            throw new DukeException(Command.INDEX_NOT_IN_MEMlIST_MESSAGE);
        } else {
            String tasks = model.scheduleMemberTodo(memberIndexInList - 1);
            String memberName = model.getMemberNameById(memberIndexInList - 1);
            if (tasks.equals("")) {
                return new CommandOutput(EMPTY_MSSAGE + memberName);
            } else {
                return new CommandOutput(SUCCESS_MSSAGE + memberName + tasks);
            }
        }
    }
}
