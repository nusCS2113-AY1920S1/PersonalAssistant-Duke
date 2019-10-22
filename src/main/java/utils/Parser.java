package utils;

import commands.AddCommand;
import commands.ByeCommand;
import commands.CheckAnomaliesCommand;
import commands.Command;
import commands.DeleteCommand;
import commands.DoneCommand;
import commands.FindCommand;
import commands.HelpCommand;
import commands.LinkCommand;
import commands.ListCommand;
import commands.MemberAddCommand;
import commands.MemberDeleteCommand;
import commands.RecurringCommand;
import commands.SnoozeCommand;
import commands.UnlinkCommand;
import commands.ViewScheCommand;
import members.Member;
import core.Ui;
import tasks.Deadline;
import tasks.Event;
import tasks.Last;
import tasks.Period;
import tasks.ToDo;
import tasks.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * deals with making sense of the user command
 */
public class Parser {
    /**
     * <p>parse a line in the data text to an object.</p>
     *
     * @param line a line of String to be parsed, without \n last
     * @throws ParseException if the line cannot be parsed properly
     */
    public static Task taskDataLine(String line) throws ParseException {
        String[] splites = line.split(" \\| ");
        if (splites.length < 3 || (splites.length < 2 && (splites[0].equals("E") || splites[0].equals("D")))) {
            throw new ParseException("Invalid Duke data line, the information is incomplete.", -1);
        }
        Task temp;
        if (splites[0].equals("T")) {
            temp = new ToDo();
        } else if (splites[0].equals("E")) {
            temp = new Event();
        } else if (splites[0].equals("D")) {
            temp = new Deadline();
        } else if (splites[0].equals("L")) {
            temp = new Last();
        } else if (splites[0].equals("P")) {
            temp = new Period();
        } else {
            throw new ParseException(
                    "Invalid data line input: the first character is not T, E, D, L or P,"
                            + " which cannot represent any task type Duke support.", -1);
        }
        try {
            if (Integer.parseInt(splites[1]) != 0) {
                temp.markAsDone();
            }
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid number format for the second column of Duke data line.", -1);
        } catch (DukeException e) {
            Ui.print(e.getMessage());
        }
        temp.setDescription(splites[2]);
        try {
            temp.addPrecondition(splites[3]);
        } catch (DukeException e) {
            throw new ParseException(e.getMessage(), -1);
        }
        if (splites[0].equals("E") || splites[0].equals("D")) {
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
            try {
                temp.setTime(ft.parse(splites[4]));
            } catch (ParseException e) {
                throw e;
            }
        } else if (splites[0].equals("L")) {
            temp.setDuration(splites[4]);
        } else if (splites[0].equals("P")) {
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
            try {
                temp.setStart(ft.parse(splites[4]));
                temp.setEnd(ft.parse(splites[5]));
            } catch (ParseException e) {
                throw e;
            }
        }
        return temp;
    }

    /**
     * <p>parse a line in the data text to an object.</p>
     *
     * @param line a line of String to be parsed, without \n last
     * @return a Member. Member object produced by the input line
     */
    public static Member memberDataLine(String line) {
        String[] splites = line.split(" \\| ");
        String name = splites[0].trim();
        int numOfTaskInCharge = splites.length - 1;
        ArrayList<Integer> taskInCharge = new ArrayList<>();
        for (int i = 0; i < numOfTaskInCharge; i++) {
            taskInCharge.add(Integer.parseInt(splites[1 + i].trim()));
        }
        return new Member(name, taskInCharge);
    }

    /**
     * <p>Parse a command line String to a Commands.Command object.</p>
     *
     * @param line the input command line String
     * @return the new Commands.Command object
     * @throws DukeException if the format of command cannot be parsed
     */
    public static Command commandLine(String line) throws DukeException {
        String[] splites = line.trim().split("\\s+", 2);
        splites[0] = splites[0].trim().toUpperCase();
        splites[0] = commandCorrector(splites[0]);
        Command temp = null;
        int length = splites.length;
        if (splites[0].equals("HELP")) {
            temp = new HelpCommand();
        } else if (splites[0].equals("ADD")) {
            if (length < 2) {
                throw new DukeException("usage: add [type of tasks] ...");
            }
            temp = new AddCommand(splites[1]);
        } else if (splites[0].equals("LIST")) {
            if (length < 2) {
                throw new DukeException("usage: list [tasks/members]");
            }
            temp = new ListCommand(splites[1].trim());
        } else if (splites[0].equals("DONE")) {
            if (length < 2) {
                throw new DukeException("usage: done [index]");
            }
            temp = new DoneCommand(splites[1]);
        } else if (splites[0].equals("BYE")) {
            temp = new ByeCommand();
        } else if (splites[0].equals("DELETE")) {
            if (length < 2) {
                throw new DukeException("usage: delete [index]");
            }
            temp = new DeleteCommand(splites[1]);
        } else if (splites[0].equals("FIND")) {
            if (length < 2) {
                throw new DukeException("usage: find [keyword]");
            }
            temp = new FindCommand(splites[1]);
        } else if (splites[0].equals("RECURRING")) {
            temp = new RecurringCommand(splites[1]);
        } else if (splites[0].equals("SNOOZE")) {
            temp = new SnoozeCommand(splites[1]);
        } else if (splites[0].equals("LINK")) {
            temp = new LinkCommand(splites[1]);
        } else if (splites[0].equals("UNLINK")) {
            temp = new UnlinkCommand(splites[1]);
        } else if (splites[0].equals("REMOVE")) {
            temp = new MemberDeleteCommand(splites[1]);
        } else if (splites[0].equals(("SCHEDULE"))) {
            temp = new ViewScheCommand(splites.length > 1 ? splites[1] : "");
        } else if (splites[0].equals("CHECK")) {
            temp = new CheckAnomaliesCommand();
        } else if (splites[0].equals("MEMBER")) {
            if (length < 2) {
                throw new DukeException("usage: member [name]");
            }
            temp = new MemberAddCommand(splites[1]);
        } else {
            throw new DukeException("command not found");
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

    /**
     * Adds the task to the database.
     *
     * @param line is the full command
     * @return the corresponding Task.Task object
     * @throws DukeException if the format of command cannot be parsed
     */
    public static Task addCommand(String line) throws DukeException {
        String[] splites = line.trim().split("\\s+", 2);
        splites[0] = splites[0].toUpperCase();
        Task temp = null;
        if (splites.length < 2) {
            throw new DukeException("No descriptions");
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
        } else if (splites[0].equals("LAST")) {
            splites = splites[1].split("/last");
            if (splites.length < 2) {
                throw new DukeException("No time keyword /last");
            }
            splites[0] = splites[0].trim();
            splites[1] = splites[1].trim();
            temp = new Last(splites[0], splites[1]);
            return temp;
        } else if (splites[0].equals("PERIOD")) {
            splites = splites[1].split("/from");
            if (splites.length < 2) {
                throw new DukeException("No time keyword /from");
            }
            String description = splites[0].trim();
            splites[1] = splites[1].trim();
            splites = splites[1].split("/to");
            if (splites.length < 2) {
                throw new DukeException("No time keyword /to");
            }
            splites[0] = splites[0].trim();
            splites[1] = splites[1].trim();
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
            try {
                Date start = ft.parse(splites[0]);
                Date end = ft.parse(splites[1]);
                if (start.compareTo(end) > 0) {
                    throw new DukeException("Invalid time period, start time should before end time");
                }
                temp = new Period(description, start, end);
                return temp;
            } catch (ParseException e) {
                throw new DukeException("Invalid date format, the correct format is: dd/MM/yyyy hhmm");
            }
        } else {
            throw new DukeException("Task.Task type " + splites[0] + " not recognized");
        }
    }

    /**
     * This method automatically correct typos of command, only support the command words
     * in the dict String Array: <br />
     * <code>dict = {"ADD", "LIST", "DONE", "BYE", "DELETE", "FIND", "RECURRING", "SNOOZE", "SCHEDULE", "CHECK",
     * "LINK", "UNLINK", "REMOVE"};
     * </code>
     *
     * @param command the original command word
     * @return If the method can recognize the word, return the correct(ed) command word;
     * if the method cannot recognize the word, return the original word.
     */
    public static String commandCorrector(String command) {
        String[] dict = {"HELP", "ADD", "LIST", "DONE", "BYE", "DELETE",
                "FIND", "RECURRING", "SNOOZE", "SCHEDULE", "CHECK", "LINK", "UNLINK", "REMOVE"};
        double[] similarity = new double[dict.length];
        double maxSimilarity = 0;
        int maxSimilarityCommandIndex = -1;
        for (int i = 0; i < dict.length; i++) {
            similarity[i] = getSimilarity(dict[i], command);
            maxSimilarity = maxSimilarity > similarity[i] ? maxSimilarity : similarity[i];
            maxSimilarityCommandIndex = maxSimilarity > similarity[i] ? maxSimilarityCommandIndex : i;
        }
        if (maxSimilarity > 0.4) {
            return dict[maxSimilarityCommandIndex];
        }
        return command;
    }

    /**
     * Get the similarity between two Strings.<br />
     * The similarity has some properties below:
     * <ul>
     * <li>If similarity == 1, the two Strings are equal.</li>
     * <li>If similarity < 1, the two Strings have certain difference.</li>
     * <li>If similarity < 0, the two Strings have different lengths.</li>
     * </ul>
     *
     * @param string1 the first String
     * @param string2 the second String
     * @return the defined similarity
     */
    public static double getSimilarity(String string1, String string2) {
        double delta = 1; // lost penalty
        double alpha = 1; // mismatch penalty
        double[][] opt = new double[string1.length() + 1][string2.length() + 1];
        opt[0][0] = 0;
        for (int i = 0; i < string1.length() + 1; i++) {
            opt[i][0] = i * delta;
        }
        for (int i = 0; i < string2.length() + 1; i++) {
            opt[0][i] = i * delta;
        }
        for (int j = 1; j < string2.length() + 1; j++) {
            for (int i = 1; i < string1.length() + 1; i++) {
                double s1 = opt[i - 1][j - 1];
                if (string1.charAt(i - 1) != string2.charAt(j - 1)) {
                    s1 = alpha + opt[i - 1][j - 1];
                }
                double s2 = delta + opt[i - 1][j];
                double s3 = delta + opt[i][j - 1];
                opt[i][j] = Math.min(Math.min(s1, s2), s3);
            }
        }
        return 2 * (0.5 - opt[string1.length()][string2.length()] / (string1.length() + string2.length()));
    }
}
