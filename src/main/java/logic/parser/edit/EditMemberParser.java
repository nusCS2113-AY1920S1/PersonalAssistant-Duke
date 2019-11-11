package logic.parser.edit;

import common.DukeException;
import logic.command.Command;
import logic.parser.NewParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditMemberParser {


    public static final String EDIT_USAGE = "Usage: edit member {name/bio/email/phone} [MEMBER_NAME] /to [NEW_CONTENT]";
    private static final String NAME = "name";
    private static final String BIO = "bio";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";

    public static final String NAME_NO_EMPTY = "The name of member shouldn't be empty.";


    //@@author yuyanglin28
    /**
     * parse edit member logic.command, pass to name, bio, email or phone
     * @param partialCommand logic.command after member
     * @return a edit member logic.command
     * @throws DukeException throw exception when edit member type is not correct.
     */
    public static Command parseEditMember(String partialCommand) throws DukeException {
        final Matcher matcher = NewParser.BASIC_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException(EDIT_USAGE);
        }

        String editType = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        editType = editType.trim();

        switch (editType) {
        case NAME:
            return EditMemberNameParser.parseEditMemberName(arguments);
        case BIO:
            return EditMemberBioParser.parseEditMemberBio(arguments);
        case EMAIL:
            return EditMemberEmailParser.parseEditMemberEmail(arguments);
        case PHONE:
            return EditMemberPhoneParser.parseEditMemberPhone(arguments);
        default:
            throw new DukeException(EDIT_USAGE);
        }

    }
}
