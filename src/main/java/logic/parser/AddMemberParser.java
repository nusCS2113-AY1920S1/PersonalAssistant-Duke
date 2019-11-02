package logic.parser;

import logic.command.AddMemberCommand;
import common.DukeException;
import logic.command.Command;

import java.util.HashMap;

public class AddMemberParser {

    public static final String MEMBER_NO_NAME_MESSAGE = "Member name needed. \nShould be: add member [member name]";


    //@@author chenyuheng

    /**
     * parses arguments of addtask into a multimap
     */
    public static AddMemberCommand parseAddMember(String userInput) throws DukeException {
        HashMap<String, String> argumentMultiMap = ArgumentTokenizer.tokenize(userInput);
        String name = argumentMultiMap.get("");
        AddMemberCommand command;
        if (name.length() != 0) {
            command = new AddMemberCommand(name);
        } else {
            throw new DukeException(MEMBER_NO_NAME_MESSAGE);
        }
        String fullSkill = argumentMultiMap.get("/skill");
        if (fullSkill != null) {
            command.setSkill(fullSkill);
        }

        return command;
    }
}
