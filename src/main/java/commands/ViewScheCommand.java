package commands;

import core.Ui;
import tasks.Deadline;
import tasks.Event;
import tasks.Last;
import tasks.Period;
import tasks.ToDo;
import tasks.Task;
import utils.DukeException;
import utils.Parser;
import utils.Storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewScheCommand extends Command {
    private String line;

    /**
     * This is a class for command FIND, which search a keyword from the task list and print all results.
     *
     * @param line the keyword to be searched
     */
    public ViewScheCommand(String line) {
        this.line = line.trim();
    }

    @Override
    public void execute(ArrayList<Task> tasks, Storage storage) throws DukeException {
        ArrayList<Task> scheTasks = new ArrayList<Task>();
        String output = "Here is your schedule in order";
        if (line.length() > 0) {
            Date date = Parser.parseDate(line);
            output += " on " + line;
            ArrayList<Task> temp = (ArrayList<Task>) tasks.clone();
            temp = removeNoTimeTask(temp);
            temp = filterByDate(temp, date);
            scheTasks = sortByDate(temp);
        } else {
            ArrayList<Task> temp = (ArrayList<Task>) tasks.clone();
            scheTasks = sortByDate(temp);

        }
        for (int i = 0; i < scheTasks.size(); i++) {
            output += "\n" + scheTasks.get(i);
        }
        Ui.print(output);
    }

    private ArrayList<Task> filterByDate(ArrayList<Task> toFilter, Date date) {
        ArrayList<Integer> toDelete = new ArrayList<Integer>();
        Calendar calAim = Calendar.getInstance();
        Calendar calTest = Calendar.getInstance();
        calAim.setTime(date);

        //remove all on different dates
        for (int i = 0; i < toFilter.size(); i++) {
            if (toFilter.get(i).getClass().equals(Deadline.class)) {
                Deadline temp = (Deadline) toFilter.get(i);
                calTest.setTime(temp.getTime());
            } else if (toFilter.get(i).getClass().equals(Event.class)) {
                Event temp = (Event) toFilter.get(i);
                calTest.setTime(temp.getTime());
            } else if (toFilter.get(i).getClass().equals(Period.class)) {
                Period temp = (Period) toFilter.get(i);
                calTest.setTime(temp.getStart());
            }
            boolean sameDay = calAim.get(Calendar.DAY_OF_YEAR) == calTest.get(Calendar.DAY_OF_YEAR)
                    && calAim.get(Calendar.YEAR) == calTest.get(Calendar.YEAR);
            if (!sameDay) {
                toDelete.add(i);
            }
        }

        for (int i = toDelete.size() - 1; i >= 0; ) {
            toFilter.remove((int) toDelete.get(i));
            i--;
        }
        return toFilter;
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
                if (toSort.get(j).getClass().equals(Deadline.class)) {
                    Deadline temp = (Deadline) toSort.get(j);
                    if (temp.getTime().before(earliest)) {
                        earliest = temp.getTime();
                        earliestIndex = j;
                    }
                } else if (toSort.get(j).getClass().equals(Event.class)) {
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

    @Override
    public boolean isExit() {
        return false;
    }
}
