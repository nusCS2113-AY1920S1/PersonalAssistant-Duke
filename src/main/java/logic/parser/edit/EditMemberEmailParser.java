package logic.parser.edit;

import common.DukeException;
import logic.command.Command;
import logic.command.edit.EditMemberBioCommand;
import logic.command.edit.EditMemberEmailCommand;
import logic.parser.ArgumentTokenizer;

import java.text.ParseException;
import java.util.HashMap;

public class EditMemberEmailParser {

    private static final String CHANGE_NO_EMPTY = "Put email after /to";


    //@@author yuyanglin28
    /**
     * parse edit member email logic.command
     * @param partialCommand logic.command after email, contains memberName and change content
     * @return edit member email logic.command
     * @throws DukeException throw exception when member name is empty or change content is empty
     */
    public static Command parseEditMemberEmail(String partialCommand) throws DukeException {

        HashMap<String, String> argumentMultimap = ArgumentTokenizer.tokenize(partialCommand);
        String name = argumentMultimap.get("");
        String changeContent = argumentMultimap.get("/to");

        if (name.length() == 0) {
            throw new DukeException(EditMemberParser.NAME_NO_EMPTY + "\n" + EditMemberParser.EDIT_USAGE);
        } else if (changeContent.length() == 0) {
            throw new DukeException(CHANGE_NO_EMPTY + "\n" + EditMemberParser.EDIT_USAGE);
        } else {
            name = name.trim();
            changeContent = changeContent.trim();
            return new EditMemberEmailCommand(name, changeContent);
        }
    }
}
