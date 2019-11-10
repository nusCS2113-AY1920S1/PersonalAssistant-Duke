package logic.parser;

import common.DukeException;
import logic.command.Command;
import logic.command.ShowMemberDetailsCommand;
import logic.command.ShowTaskDetailsCommand;
import model.Model;

//@@author JasonChanWQ

public class ShowCommandParser {

    public static final String SHOW_USAGE = "usage: Show task [Index of task] / show member [name of member]";
    public static final String TASK = "task";
    public static final String MEMBER = "member";
    public static final String INVALID_SHOW_TYPE_MESSAGE = "Please input task/member to show!";
    public static final String EMPTY_TASK_INDEX_MESSAGE = "Index of task cannot be empty!";
    public static final String EMPTY_MEMBER_NAME_MESSAGE = "Name of Member cannot be empty!";
    public static final String INVALID_TASK_INDEX_MESSAGE = "Not a valid task index!";

    //@@author JasonChanWQ
    /**
     * Parses the user input and returns ShowMemberDetailsCommand or ShowTaskDetailsCommand accordingly
     * @param argument [task/member] /of [Index of task / name of member]
     * @return ShowMemberDetailsCommand or ShowTaskDetailsCommand accordingly
     * @throws DukeException exception
     */

    public static Command parseShowCommand(String argument) throws DukeException {
        if (argument.equals("")) {
            throw new DukeException(SHOW_USAGE);
        } else {
            String keyword = argument.trim();
            String[] arrOfStr = keyword.split(" ", 2);
            String editType = arrOfStr[0].trim();

            switch (editType) {
            case TASK:
                try {
                    int taskIndex = Integer.parseInt(arrOfStr[1].trim());
                    return new ShowTaskDetailsCommand(taskIndex);
                } catch (NumberFormatException e) {
                    throw new DukeException(INVALID_TASK_INDEX_MESSAGE);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DukeException(EMPTY_TASK_INDEX_MESSAGE);
                }

            case MEMBER:
                try {
                    String memberName = arrOfStr[1].trim();
                    return new ShowMemberDetailsCommand(memberName);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DukeException(EMPTY_MEMBER_NAME_MESSAGE);
                }

            default:
                throw new DukeException(INVALID_SHOW_TYPE_MESSAGE);
            }

        }
    }
}
