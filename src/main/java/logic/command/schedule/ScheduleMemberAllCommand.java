package logic.command.schedule;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

public class ScheduleMemberAllCommand extends Command {

    private static final String SUCCESS_MSSAGE = "Schedule all tasks of member: ";
    private static final String EMPTY_MSSAGE = "No task for member: ";

    private String memberName;

    public ScheduleMemberAllCommand(String memberName) {
        this.memberName = memberName;
    }

    //@@author yuyanglin28

    /**
     * This method is to schedule all tasks for a member
     *
     * @param model Model interface
     * @return sorted tasks
     * @throws DukeException throw exception when member name is not in the member list
     */
    @Override
    public CommandOutput execute(Model model) throws DukeException {
        if (!checkMemberName(memberName, model)) {
            throw new DukeException(Command.NAME_NOT_IN_MEMlIST_MESSAGE);
        } else {
            String tasks = model.scheduleMemberAll(memberName);
            if (tasks.equals("")) {
                return new CommandOutput(EMPTY_MSSAGE + memberName);
            } else {
                return new CommandOutput(SUCCESS_MSSAGE + memberName + tasks);
            }
        }
    }
}
