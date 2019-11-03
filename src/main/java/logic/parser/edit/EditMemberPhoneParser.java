package logic.parser.edit;

import common.DukeException;
import logic.command.Command;
import logic.command.edit.EditMemberBioCommand;
import logic.command.edit.EditMemberPhoneCommand;
import logic.parser.ArgumentTokenizer;

import java.text.ParseException;
import java.util.HashMap;

public class EditMemberPhoneParser {

    private static final String EDIT_USAGE = "Usage: edit [member] [bio/email/phone] [index] /to ...";
    private static final String NAME_NO_EMPTY = "the name of member shouldn't be empty.";
    private static final String CHANGE_NO_EMPTY = "put phone number after /to";
    private static final String GET_INDEX_FAIL = "after edit type, put a valid index.";


    //@@author yuyanglin28
    /**
     * parse delete command, divide to task or member
     * @param partialCommand argument part of the command
     * @return a delete command
     * @throws DukeException exception
     */
    public static Command parseEditMemberPhone(String partialCommand) throws DukeException {

        HashMap<String, String> argumentMultimap = ArgumentTokenizer.tokenize(partialCommand);
        String name = argumentMultimap.get("");
        String changeContent = argumentMultimap.get("/to");

        if (name.length() == 0) {
            throw new DukeException(NAME_NO_EMPTY + "\n" + EDIT_USAGE);
        } else if (changeContent.length() == 0) {
            throw new DukeException(CHANGE_NO_EMPTY + "\n" + EDIT_USAGE);
        } else {
            name = name.trim();
            try {
                int indexInList = Integer.parseInt(name);
                changeContent = changeContent.trim();
                return new EditMemberPhoneCommand(indexInList, changeContent);
            } catch (Exception e) {
                throw new DukeException(GET_INDEX_FAIL + "\n" + EDIT_USAGE);
            }
        }
    }
}
