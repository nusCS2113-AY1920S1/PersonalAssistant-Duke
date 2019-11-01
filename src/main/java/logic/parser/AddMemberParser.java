package logic.parser;

import logic.command.AddMemberCommand;
import common.DukeException;

public class AddMemberParser {

    public static final String MEMBER_NO_NAME_MESSAGE = "Member name needed. \nShould be: add member [member name]";


    //@@author chenyuheng
    /**
     * parses arguments of addtask into a multimap
     * */
    public static AddMemberCommand parseAddMember(String userInput) throws DukeException {
        if (userInput != null) {
            return new AddMemberCommand(userInput);
        } else {
            throw new DukeException(MEMBER_NO_NAME_MESSAGE);
        }
    }
}
