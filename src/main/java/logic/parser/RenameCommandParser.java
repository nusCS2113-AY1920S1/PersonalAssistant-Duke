package logic.parser;

import common.DukeException;
import logic.command.Command;
import logic.command.RenameMemberCommand;
import logic.command.RenameTaskCommand;
import model.Model;

//@@ JasonChanWQ

public class RenameCommandParser {

    public static final String RENAME_USAGE = "usage: rename [task/member] [Index of task/member] /to [New Name]";
    public static final String INVALID_RENAME_TYPE_MESSAGE = "Please input task/member to rename!";
    public static final String INVALID_TASK_INDEX_MESSAGE = "Not a valid task index!";
    public static final String INVALID_MEMBER_INDEX_MESSAGE = "Not a valid member index!";
    public static final String EMPTY_INDEX_MESSAGE = "task/member index cannot be empty!";
    public static final String TO_NOT_FOUND_MESSAGE = "Please input a /to";

    /**
     * parse the rename command, check if rename task or member,
     * pass the index (in task/member list) and the new name to RenameTaskCommand or Rename MemberCommand accordingly
     * @param argument [task/member] [Index of task/member] /to [New Name]
     * @return RenameTaskCommand or Rename MemberCommand accordingly
     * @throws DukeException exception
     */
    //@@ author JasonChanWQ
    public static Command parseRenameCommand(String argument) throws DukeException {
        if (argument.equals("")) {
            throw new DukeException(RENAME_USAGE);
        } else {
            String keyword = argument.trim();
            String[]arrOfStr = keyword.split(" ",2);
            boolean isFound = arrOfStr[1].indexOf(" /to ") != -1 ? true : false;

            if (isFound == true) {
                String[]arrOfStr2 = arrOfStr[1].split(" /to ",2);

                if (arrOfStr[0].equals("task")) {
                    try {
                        int taskIndex = Integer.parseInt(arrOfStr2[0]);
                        String newName = arrOfStr2[1];
                        return new RenameTaskCommand(taskIndex, newName);

                    } catch (NumberFormatException e) {
                        throw new DukeException(INVALID_TASK_INDEX_MESSAGE);
                    } catch (NullPointerException e) {
                        throw new DukeException(EMPTY_INDEX_MESSAGE);
                    }

                } else if (arrOfStr[0].equals("member")) {
                    try {
                        int memberIndex = Integer.parseInt(arrOfStr2[0]);
                        String newName = arrOfStr2[1];
                        return new RenameMemberCommand(memberIndex, newName);
                    } catch (NumberFormatException e) {
                        throw new DukeException(INVALID_MEMBER_INDEX_MESSAGE);
                    } catch (NullPointerException e) {
                        throw new DukeException(EMPTY_INDEX_MESSAGE);
                    }
                } else {
                    throw new DukeException(INVALID_RENAME_TYPE_MESSAGE);
                }
            } else {
                throw new DukeException(TO_NOT_FOUND_MESSAGE);
            }


        }
    }
}