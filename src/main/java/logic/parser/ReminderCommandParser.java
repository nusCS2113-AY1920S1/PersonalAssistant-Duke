package logic.parser;

import common.DukeException;
import logic.command.AddMemberCommand;
import logic.command.ReminderCommand;

//@@author AugGust
public class ReminderCommandParser {
    /**
     * parses arguments of addtask into a multimap
     * */
    public static ReminderCommand parseReminder(String userInput) throws DukeException {
        String[] splites = userInput.split(" ");
        if (splites.length != 2) {
            throwSyntaxError();
        }

        int taskIndex = 0;
        int beforeInt = 0;

        try {
            taskIndex = Integer.parseInt(splites[0]);
            beforeInt = Integer.parseInt(splites[1].substring(0, splites[1].length() - 1));
        } catch (NumberFormatException e) {
            throwSyntaxError();
        }

        char beforeMult = splites[1].charAt(splites[1].length() - 1);
        if (beforeMult != 'm' && beforeMult != 'h' && beforeMult != 'd')    {
            throw new DukeException("Specify m (minutes), h (hours) or d (days)");
        }
        return new ReminderCommand(taskIndex, beforeInt, beforeMult);
    }

    /**
     * Shows tooltip for correct syntax
     *
     * @throws DukeException Hint for proper syntax
     */
    public static void throwSyntaxError() throws DukeException {
        throw new DukeException("usage: reminder [task index] [time before]\n"
                + "*Only for tasks with dates\n"
                + "Time before: e.g. 5m, 10h, 3d");
    }
}
