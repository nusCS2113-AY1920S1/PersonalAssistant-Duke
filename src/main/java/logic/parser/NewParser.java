package logic.parser;

import logic.command.Command;
import utils.DukeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final String ADD_COMMAND_WORD = "ADD";
    private static final String LIST_COMMAND_WORD = "LIST";
    private static final String DELETE_COMMAND_WORD = "DELETE";

    //@@author JustinChia1997

    /**
     * <p>Parse a command line String to a Commands.Command object.</p>
     *
     * @param fullCommand the input command line String
     * @return the new Commands.Command object
     * @throws DukeException if the format of command cannot be parsed
     */
    public static Command parseCommand(String fullCommand) throws DukeException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(fullCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException("Message is invalid");
        }
        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        commandWord = commandWord.trim().toUpperCase();

        String[] dict = {
                "ADD", "LIST", "DONE", "BYE", "DELETE", "FIND", "SNOOZE",
                "SCHEDULE", "CHECK", "LINK", "UNLINK", "REMOVE", "HELP"
        };
        commandWord = SpellingErrorCorrector.commandCorrector(dict, commandWord);

        switch (commandWord) {
            case ADD_COMMAND_WORD:
                return AddCommandParser.parseAddCommand(arguments);
            case LIST_COMMAND_WORD:
                return ListCommandParser.parseListCommand(arguments);
            default:
                throw new DukeException("Command not found");

        }

    }
}


//       try {
//            Method[] parse = new Method[dict.length];
//            // the parse method array should be corresponding to the dict String array.
//            parse[0] = AddCommandParser.class.getDeclaredMethod("parseAdd", String.class);
//            parse[1] = ListCommandParser.class.getDeclaredMethod("parseList", String.class);
//            for (int i = 0; i < dict.length; i++) {
//                if (splites[0].equals(dict[i])) {
//                    temp = (Command) parse[i].invoke(null, splites[1]);
//                }
//            }
//            if (temp == null) {
//                throw new DukeException("command not found");
//            }
//        } catch (DukeException e) {
//            throw e;
//        } catch (NoSuchMethodException e) {
//            throw new DukeException("no such method");
//        } catch (InvocationTargetException e) {
//            if (e.getCause().getClass().equals(DukeException.class)) {
//                throw (DukeException) e.getCause();
//            } else {
//                e.getCause().printStackTrace();
//            }
//        } catch (IllegalAccessException e) {
//            throw new DukeException("illegal access Exception");
//        }
//        if (temp == null) {
//            throw new DukeException("cannot resolve this command");
//        }
//        return temp;
