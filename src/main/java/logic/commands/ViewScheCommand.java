package logic.commands;

import logic.CommandResult;
import logic.commands.Command;
import core.Ui;
import model.members.Member;
import model.tasks.Deadline;
import model.tasks.Event;
import model.tasks.Last;
import model.tasks.Period;
import model.tasks.ToDo;
import model.tasks.Task;
import utils.DukeException;
import logic.parsers.DukeParser;
import utils.Storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewScheCommand extends Command {
    private String option;

    private String memberName;

    private Date date;

    private boolean sortByDate = false;

    /**
     * This is a class for command SCHEDULE, which displays tasks that has DATETIME in chronological order.
     * Optionally provided DATE allows filtering of tasks from that day only
     *
     * @param line is /all or /member [memberName]
     */
    public ViewScheCommand(String line) throws DukeException {
        String[] arrOfStr = line.split("\\s+");

        this.option = arrOfStr[0].trim();
        if (option.equals("/all")) {
            if (arrOfStr.length > 1) {
                this.date = DukeParser.parseDate(arrOfStr[1].trim());
                sortByDate = true;
            }
        } else if (option.equals("/member")) {
            this.memberName = arrOfStr[1].trim();
            if (arrOfStr.length > 2) {
                this.date = DukeParser.parseDate(arrOfStr[2].trim());
                sortByDate = true;
            }
        }
    }

    @Override
    //@@author auggust yuyanglin28
    public CommandResult execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage)
            throws DukeException {
        ArrayList<Task> scheTasks = new ArrayList<Task>();
        ArrayList<Task> temp = new ArrayList<>();
        String output = "";
        if (option.equals("/all")) {
            output = "Here is the whole team schedule in order";
            temp = (ArrayList<Task>) tasks.clone();
        } else if (option.equals("/member")) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getName().equals(memberName)) {
                    output = "Here is " + memberName + "'s schedule in order\n";
                    temp = (ArrayList<Task>) members.get(i).getTasksInCharge().clone();
                }
            }
        }

        if (sortByDate) {
            temp = removeNoTimeTask(temp);
            temp = filterByDate(temp, date);
            scheTasks = sortByDate(temp);
            if (scheTasks.size() > 0) {
                output += " on " + date;
            } else {
                output += "There is no task on " + date;
            }
        } else {
            scheTasks = sortByDate(temp);
        }

        for (int i = 0; i < scheTasks.size(); i++) {
            output += "\n" + scheTasks.get(i);
        }
        return new CommandResult(output);
    }


    /**
     * Removes and return list of tasks by provided date
     * @param toFilter original list of tasks
     * @param date date of tasks to be filtered by
     * @return List of tasks filtered by date
     */
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


    /**
     * Returns list of tasks provided without those that has no Date
     * @param toFilter original list of tasks
     * @return filtered task list
     */
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

    /**
     * Sorts list of tasks into chronological order
     * @param toSort list of tasks to by sorted
     * @return list of tasks sorted by date
     */
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
}
