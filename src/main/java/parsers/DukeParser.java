package parsers;

import commands.Command;
import utils.DukeException;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.lang.reflect.Method;

/**
 * deals with making sense of the user command
 */
public class DukeParser {

    /**
     * <p>Parse a command line String to a Commands.Command object.</p>
     *
     * @param line the input command line String
     * @return the new Commands.Command object
     * @throws DukeException if the format of command cannot be parsed
     */
    public static Command commandLine(String line) throws DukeException {
        String[] splites = line.trim().split("\\s+", 2);
        if (splites.length == 1) {
            String[] newSplites = new String[2];
            newSplites[0] = splites[0];
            newSplites[1] = "";
            splites = newSplites;
        }
        splites[0] = splites[0].trim().toUpperCase();
        String[] dict = {
            "ADD", "LIST", "DONE", "BYE", "DELETE", "FIND", "RECURRING", "SNOOZE",
            "SCHEDULE", "CHECK", "LINK", "UNLINK", "REMOVE", "HELP"
        };
        splites[0] = SpellingErrorCorrector.commandCorrector(dict, splites[0]);
        Command temp = null;
        try {
            Method[] parse = new Method[dict.length];
            // the parse method array should be corresponding to the dict String array.
            parse[0] = AddCommandParser.class.getDeclaredMethod("parse", String.class);
            parse[1] = ListCommandParser.class.getDeclaredMethod("parse", String.class);
            parse[2] = DoneCommandParser.class.getDeclaredMethod("parse", String.class);
            parse[3] = SimpleCommandParser.class.getDeclaredMethod("bye", String.class);
            parse[4] = DeleteCommandParser.class.getDeclaredMethod("parse", String.class);
            parse[5] = FindCommandParser.class.getDeclaredMethod("parse", String.class);
            parse[6] = RecurringCommandParser.class.getDeclaredMethod("parse", String.class);
            parse[7] = SnoozeCommandParser.class.getDeclaredMethod("parse", String.class);
            parse[8] = ScheduleCommandParser.class.getDeclaredMethod("parse", String.class);
            parse[9] = CheckCommandParser.class.getDeclaredMethod("parse", String.class);
            parse[10] = LinkCommandParser.class.getDeclaredMethod("parse", String.class);
            parse[11] = UnlinkCommandParser.class.getDeclaredMethod("parse", String.class);
            parse[12] = DeleteCommandParser.class.getDeclaredMethod("parse", String.class);
            parse[13] = SimpleCommandParser.class.getDeclaredMethod("help", String.class);
            for (int i = 0; i < dict.length; i++) {
                if (splites[0].equals(dict[i])) {
                    temp = (Command) parse[i].invoke(null, splites[1]);
                }
            }
            if (temp == null) {
                throw new DukeException("command not found");
            }
        } catch (NoSuchMethodException e) {
            throw new DukeException("no such method");
        } catch (InvocationTargetException e) {
            throw new DukeException("invocation target exception");
        } catch (IllegalAccessException e) {
            throw new DukeException("illegal acccess Exception");
        }
        return temp;
    }

    /**
     * <p>Parse an add command to get the corresponding Task.Task object.</p>
     *
     * @param line the add command line with "add" removed
     * @return the corresponding Task.Task object
     * @throws DukeException if the format of command cannot be parsed
     */

    public static Date parseDate(String line) throws DukeException {
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date temp = ft.parse(line);
            return temp;
        } catch (ParseException e) {
            throw new DukeException("Invalid date format, the correct format is: dd/MM/yyyy");
        }
    }



}
