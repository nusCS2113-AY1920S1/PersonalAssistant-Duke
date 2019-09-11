import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Parser {
    /**
     * <p>parse a line in the data text to an object.</p>
     * @param line a line of String to be parsed, without \n last
     * @return a Task object produced by the input line
     * @throws ParseException if the line cannot be parsed properly
     */
    public static Task dataLine(String line) throws ParseException {
        String[] splites = line.split(" \\| ");
        if (splites.length < 3 || (splites.length < 2 && (splites[0].equals("E") || splites[0].equals("D")))) {
            throw new ParseException("Invalid Duke data line, the information is incomplete.", -1);
        }
        Task temp;
        if (splites[0].equals("T")) {
            temp = new ToDo("");
        } else if (splites[0].equals("E")) {
            temp = new Event("");
        } else if (splites[0].equals("D")) {
            temp = new Deadline("");
        } else {
            throw new ParseException(
                    "Invalid data line input: the first character is not T, E or D,"
                            + " which cannot represent any task type Duke support.",-1);
        }
        try {
            if (Integer.parseInt(splites[1]) != 0) {
                temp.markAsDone();
            }
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid number format for the second column of Duke data line.", -1);
        }
        temp.setDescription(splites[2]);
        if (splites[0].equals("E") || splites[0].equals("D")) {
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
            try {
                temp.setTime(ft.parse(splites[3]));
            } catch (ParseException e) {
                throw e;
            }
        }
        return temp;
    }

    /**
     * <p>Parse a command line String to a Command object.</p>
     * @param line the input command line String
     * @return the new Command object
     * @throws DukeException if the format of command cannot be parsed
     */
    public static Command commandLine(String line) throws DukeException {
        String[] splites = line.replaceAll("\\s{2,}", " ").split(" ",2);
        splites[0] = splites[0].trim().toUpperCase();
        Command temp = null;
        if (splites[0].equals("ADD")) {
            temp = new AddCommand(splites[1]);
        } else if (splites[0].equals("LIST")) {
            temp = new ListCommand();
        } else if (splites[0].equals("DONE")) {
            temp = new DoneCommand(splites[1]);
        } else if (splites[0].equals("BYE")) {
            temp = new ByeCommand();
        } else if (splites[0].equals("DELETE")) {
            temp = new DeleteCommand(splites[1]);
        } else if (splites[0].equals("FIND")) {
            temp = new FindCommand(splites[1]);
        } else {
            throw new DukeException("command not found");
        }
        return temp;
    }

    /**
     * <p>Parse an add command to get the corresponding Task object.</p>
     * @param line the add command line with "add" removed
     * @return the corresponding Task object
     * @throws DukeException if the format of command cannot be parsed
     */
    public static Task addCommand(String line) throws DukeException {
        String[] splites = line.split(" ", 2);
        splites[0] = splites[0].toUpperCase();
        Task temp = null;
        if (splites.length < 2) {
            throw new DukeException("No description");
        }
        if (splites[0].equals("TODO")) {
            temp = new ToDo(splites[1]);
            return temp;
        } else if (splites[0].equals("DEADLINE")) {
            splites = splites[1].split("/by");
            if (splites.length < 2) {
                throw new DukeException("No time keyword /by");
            }
            splites[0] = splites[0].trim();
            splites[1] = splites[1].trim();
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
            try {
                temp = new Deadline(splites[0], ft.parse(splites[1]));
                return temp;
            } catch (ParseException e) {
                throw new DukeException("Invalid date format, the correct format is: dd/MM/yyyy hhmm");
            }
        } else if (splites[0].equals("EVENT")) {
            splites = splites[1].split("/at");
            if (splites.length < 2) {
                throw new DukeException("No time keyword /at");
            }
            splites[0] = splites[0].trim();
            splites[1] = splites[1].trim();
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
            try {
                temp = new Event(splites[0], ft.parse(splites[1]));
                return temp;
            } catch (ParseException e) {
                throw new DukeException("Invalid date format, the correct format is: dd/MM/yyyy hhmm");
            }
        } else {
            throw new DukeException("Task type " + splites[0] + " not recognized");
        }
    }
}
