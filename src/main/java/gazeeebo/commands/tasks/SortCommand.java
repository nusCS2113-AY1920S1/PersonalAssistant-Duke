//@@author mononokehime14

package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import java.io.IOException;
import gazeeebo.exception.DukeException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;
import java.util.stream.Collectors;

public class SortCommand extends Command {
    /**
     * This method sort the task list in given order
     * @param list          Task list
     * @param ui            The object that deals with
     *                      printing things to the user.
     * @param storage       The object that deals with
     *                      storing data to the Save.txt file.
     * @param commandStack the stack of previous commands.
     * @param deletedTask the list of deleted task.
     * @param triviaManager the object for triviaManager
     * @throws DukeException  Throws custom exception when
     *                        format of fixed duration command is wrong
     * @throws ParseException Catch error if parsing of command fails
     * @throws IOException    Catch error if the read file fails
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack,
                        ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException,
            ParseException, IOException {
        try {
            if (ui.fullCommand.length() == 4) {
                throw new DukeException("OOPS!!! Please enter method of sorting.");
            } else {
                ArrayList<Task> sortedList = new ArrayList<Task>(list);
                if (ui.fullCommand.split(" ")[1].equals("name")) {
                    Collections.sort(sortedList, new CustomNameComparator());
                    for (int i = 0; i < sortedList.size(); i++) {
                        System.out.println(sortedList.get(i).listFormat());
                    }
                } else if (ui.fullCommand.split(" ")[1].equals("date")) {
                    System.out.println("Following is the sorted list of deadlines:");
                    sortedList = sortedList.stream().filter(data -> data.toString()
                            .split("\\|")[0].equals("D")
                            || data.toString().split("\\|")[0].equals("E"))
                            .collect(Collectors.toCollection(ArrayList<Task>::new));
                    Collections.sort(sortedList, new CustomeDateComparator());
                    for (int i = 0; i < sortedList.size(); i++) {
                        System.out.println(sortedList.get(i).listFormat());
                    }
                } else if (ui.fullCommand.split(" ")[1].equals("priority")) {
                    Collections.sort(sortedList, new CustomPriorityComparator());
                    for (int i = 0; i < sortedList.size(); i++) {
                        System.out.println(sortedList.get(i).listFormat()
                                + " priority: " + sortedList.get(i).priority);
                    }
                } else {
                    throw new DukeException("OOPS!!! "
                            + "Gazeeebo currently does not support this sorting method.");
                }
            }
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Program does not exit and continues running
     * since command "bye" is not called.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}

class CustomPriorityComparator implements Comparator<Task> {
    public int compare(Task a, Task b) {
        return b.priority - a.priority;
    }
}

class CustomNameComparator implements Comparator<Task> {
    public int compare(Task a, Task b) {
        return a.description.compareTo(b.description);
    }
}

class CustomeDateComparator implements Comparator<Task> {
    public int compare(Task a,Task b) {
        if (a.toString().split("\\|")[0].equals("E")
                && b.toString().split("\\|")[0].equals("E")) {
            Event ea = (Event)a;
            Event eb = (Event)b;
            return LocalDateTime.of(ea.date,ea.start)
                    .compareTo(LocalDateTime.of(eb.date,eb.start));
        } else if (a.toString().split("\\|")[0].equals("D")
                && b.toString().split("\\|")[0].equals("D")) {
            Deadline da = (Deadline)a;
            Deadline db = (Deadline)b;
            return da.by.compareTo(db.by);
        } else if (a.toString().split("\\|")[0].equals("E")
                && b.toString().split("\\|")[0].equals("D")) {
            Event ea = (Event)a;
            Deadline db = (Deadline)b;
            LocalDateTime temp = LocalDateTime.of(ea.date,ea.start);
            int result = temp.compareTo(db.by);
            return LocalDateTime.of(ea.date,ea.start).compareTo(db.by);
        } else if (a.toString().split("\\|")[0].equals("D")
                && b.toString().split("\\|")[0].equals("E")) {
            Event eb = (Event)b;
            Deadline da = (Deadline)a;
            LocalDateTime temp = LocalDateTime.of(eb.date,eb.start);
            int result = temp.compareTo(da.by);
            return LocalDateTime.of(eb.date,eb.start).compareTo(da.by);
        } else if ((a.toString().split("\\|")[0].equals("E")
                || a.toString().split("\\|")[0].equals("D"))
                && !(b.toString().split("\\|")[0].equals("E")
                || b.toString().split("\\|")[0].equals("D"))) {
            return -1;
        } else if ((b.toString().split("\\|")[0].equals("E")
                || b.toString().split("\\|")[0].equals("D"))
                && !(a.toString().split("\\|")[0].equals("E")
                || a.toString().split("\\|")[0].equals("D"))) {
            return 1;
        } else {
            return 0;
        }
    }
}
