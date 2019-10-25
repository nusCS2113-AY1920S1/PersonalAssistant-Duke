package commands;

import java.util.ArrayList;

import core.Ui;
import members.Member;
import tasks.Deadline;
import tasks.Event;
import tasks.Period;
import tasks.Task;
import utils.DukeException;
import utils.Storage;

public class RemindCommand extends Command {
    public String line;

    public RemindCommand(String line) {
        this.line = line;
    }

    @Override
    public void execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage) throws DukeException {
        String[] splites = line.split(" ");
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

        String response = "Alright! I'll remind you about: \n";
        try {
            response += tasks.get(taskIndex - 1).toString() + '\n';
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Task does not exist!");
        }
        response += beforeInt;
        
        char beforeMult = splites[1].charAt(splites[1].length() - 1);
        if (beforeMult == 'm') {
            response += " mintutes";
        } else if (beforeMult == 'h') {
            response += " hours";
        } else if (beforeMult == 'd') {
            response += " days";
        } else {
            throw new DukeException("Specify m (minutes), h (hours) or d (days)");
        }

        Class classOfTask = tasks.get(taskIndex - 1).getClass();
        if (classOfTask != Deadline.class && classOfTask != Event.class && classOfTask != Period.class) {
            throw new DukeException("Only for Deadline, Event or Period tasks");
        }

        response += " before its time";
        Ui.print(response);
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Shows tooltip for correct syntax
     *
     * @throws DukeException Hint for proper syntax
     */
    public void throwSyntaxError() throws DukeException {
        throw new DukeException("usage: reminder [task index] [time before]\n"
                + "*Only for tasks with dates\n"
                + "Time before: e.g. 5m, 10h, 3d");
    }
}
