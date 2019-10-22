package logic.commands;

import model.members.Member;
import model.tasks.Deadline;
import model.tasks.Event;
import model.tasks.Last;
import model.tasks.Period;
import model.tasks.ToDo;
import model.tasks.Task;
import logic.CommandResult;
import utils.DukeException;
import utils.Storage;

import java.util.ArrayList;
import java.util.Date;

public class CheckAnomaliesCommand extends Command {

    public CheckAnomaliesCommand() {
    }

    /**
     * Checks for scheduling clashes.
     */
    public CommandResult execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage)
            throws DukeException {

        ArrayList<Task> scheTasks = new ArrayList<Task>();
        String msg = "Here is your time crash\n";
        String output = "";


        ArrayList<Task> temp = (ArrayList<Task>) tasks.clone();
        temp = removeNoTimeTask(temp);

        scheTasks = sortByDate(temp);

        for (int i = 0; i < scheTasks.size() - 1; i++) {
            if (scheTasks.get(i) instanceof Period && scheTasks.get(i + 1) instanceof Period) {
                Period p1 = (Period) scheTasks.get(i);
                Period p2 = (Period) scheTasks.get(i + 1);
                if (p1.getEnd().compareTo(p2.getStart()) > 0) {
                    output += "crash between: " + p1.toString() + " & " + p2.toString() + "\n";
                }
            } else if (scheTasks.get(i) instanceof Period && scheTasks.get(i + 1) instanceof Event) {
                Period p1 = (Period) scheTasks.get(i);
                Event e2 = (Event) scheTasks.get(i + 1);
                if (p1.getStart().compareTo(e2.getTime()) == 0 || p1.getEnd().compareTo(e2.getTime()) > 0) {
                    output += "crash between: " + p1.toString() + " & " + e2.toString() + "\n";
                }
            } else if (scheTasks.get(i) instanceof Event && scheTasks.get(i + 1) instanceof Period) {
                Event e1 = (Event) scheTasks.get(i);
                Period p2 = (Period) scheTasks.get(i + 1);
                if (p2.getStart().compareTo(e1.getTime()) == 0) {
                    output += "crash between: " + e1.toString() + " & " + p2.toString() + "\n";
                }
            } else if (scheTasks.get(i) instanceof Event && scheTasks.get(i + 1) instanceof Event) {
                Event e1 = (Event) scheTasks.get(i);
                Event e2 = (Event) scheTasks.get(i + 1);
                if (e1.getTime().compareTo(e2.getTime()) == 0) {
                    output += "crash between: " + e1.toString() + " & " + e2.toString() + "\n";
                }
            }
        }
        if (!output.equals("")) {
            return new CommandResult(msg + output);
        }
        return new CommandResult("no time crash");
    }

    private ArrayList<Task> removeNoTimeTask(ArrayList<Task> toFilter) {
        ArrayList<Integer> toDelete = new ArrayList<Integer>();

        //remove all without dates (ToDos and Lasts)
        for (int i = 0; i < toFilter.size(); i++) {
            if (toFilter.get(i).getClass().equals(ToDo.class)) {
                toDelete.add(i);
            }
            if (toFilter.get(i).getClass().equals(Last.class)) {
                toDelete.add(i);
            }
            if (toFilter.get(i).getClass().equals(Deadline.class)) {
                toDelete.add(i);
            }
        }

        for (int i = toDelete.size() - 1; i >= 0; ) {
            toFilter.remove((int) toDelete.get(i));
            i--;
        }
        return toFilter;
    }

    private ArrayList<Task> sortByDate(ArrayList<Task> toSort) {
        ArrayList<Task> sorted = new ArrayList<>();

        toSort = removeNoTimeTask(toSort);

        int size = toSort.size();
        for (int i = 0; i < size; i++) {
            Date earliest = new Date(Long.MAX_VALUE);
            int earliestIndex = -1;
            for (int j = 0; j < toSort.size(); j++) {

                if (toSort.get(j).getClass().equals(Event.class)) {
                    Event temp = (Event) toSort.get(j);
                    if (temp.getTime().before(earliest)) {
                        earliest = temp.getTime();
                        earliestIndex = j;
                    }
                } else if (toSort.get(j).getClass().equals(Period.class)) {
                    Period temp = (Period) toSort.get(j);
                    if (temp.getStart().before(earliest)) {
                        earliest = temp.getStart();
                        earliestIndex = j;
                    }
                }
            }

            sorted.add(toSort.get(earliestIndex));
            toSort.remove(earliestIndex);
        }
        return sorted;
    }

}
