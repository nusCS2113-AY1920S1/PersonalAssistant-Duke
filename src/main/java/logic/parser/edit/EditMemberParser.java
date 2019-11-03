package logic.parser.edit;

import common.DukeException;
import logic.command.Command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditMemberParser {

    private static final Pattern BASIC_ADD_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public static final String EDIT_USAGE = "Usage: edit [member] [des/bio/email/phone] [index] /to ...";
    public static final String DES = "des";
    public static final String BIO = "bio";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";


    //@@author yuyanglin28
    /**
     * parse delete command, divide to task or member
     * @param partialCommand argument part of the command
     * @return a delete command
     * @throws DukeException ex ception
     */
    public static Command parseEditMember(String partialCommand) throws DukeException {
        final Matcher matcher = BASIC_ADD_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException("Message is invalid" + "\n" + EDIT_USAGE + "\n");
        }

        String editType = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        editType = editType.trim();

        switch (editType) {
        case DES:
            return EditMemberDescriptionParser.parseEditMemberDescription(arguments);
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
