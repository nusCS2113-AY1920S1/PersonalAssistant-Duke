package Commands;

import Commons.Storage;
import Commons.UserInteraction;
import DukeExceptions.DukeException;
import Tasks.Assignment;
import Tasks.TaskList;

import java.util.ArrayList;
import java.util.HashMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Abstract class Command with methods representing all the Command subclasses to be
 * carried out when an input is entered by the user.
 */
public abstract class Command {
    public abstract String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage) throws Exception;

    /**
     * This method checks if task already exist.
     * @param map Map of events or deadlines
     * @param task task to be taken action
     * @throws DukeException on wrong input
     */
    public void insideMapChecker(HashMap<String, HashMap<String, ArrayList<Assignment>>> map, Assignment task) throws DukeException {
        String modCode = task.getModCode();
        String dateOfTask = task.getDate();
        if (!map.containsKey(modCode)) {
            throw new DukeException("Sorry, you have no such mod in the system");
        } else if (!map.get(modCode).containsKey(dateOfTask)) {
            throw new DukeException("Sorry, you have no such date of the mod in the system");
        } else {
            for (Assignment taskInList : map.get(modCode).get(dateOfTask)) {
                if (taskInList.getDateTime().equals(task.getDateTime())) {
                    if (!taskInList.getDescription().equals(task.getDescription())) {
                        throw new DukeException("Sorry, the description of your task mismatches");
                    } else {
                        return;
                    }
                }
            }
            throw new DukeException("Sorry, you have no timing of the task in the system");
        }
    }

   /* public void addInsideMapChecker(HashMap<String, HashMap<String, ArrayList<Assignment>>> eventMap, Assignment task) throws DukeException {
        String modCode = task.getModCode();
        String dateOfTask = task.getDate();
        if (eventMap.containsKey(modCode) && eventMap.get(modCode).containsKey(dateOfTask)) {
            throw new DukeException("Sorry, you have similar event at the same time on the same day");
        } else {
            return;
        }
    } */

    /**
     * This method checks whether there is a conflict in the adding of events based on module code and timing.
     * @param taskList The TaskList object for events
     * @param t The event task that is added
     * @return ArrayList containing the assignment that has conflict
     */
    public  ArrayList<String> checkEventConflict(TaskList taskList, Assignment t) throws ParseException {
        ArrayList<String> conflict = new ArrayList<>();
        Date startTime1 = new SimpleDateFormat("hh:mm a").parse(t.getStartTime());
        Date endTime1 = new SimpleDateFormat("hh:mm a").parse(t.getEndTime());
        HashMap<String, HashMap<String, ArrayList<Assignment>>> mapObtained = taskList.getMap();

        for (String modCode : mapObtained.keySet()) {
            for (String date : mapObtained.get(modCode).keySet()) {
                for (Assignment task : mapObtained.get(modCode).get(date)) {

                    if (date.equals(t.getDate())) {
                        Date taskStartTime = new SimpleDateFormat("hh:mm a").parse(task.getStartTime());
                        Date taskEndTime = new SimpleDateFormat("hh:mm a").parse(task.getEndTime());
                        //start time is the same
                        if (taskStartTime.equals(startTime1) || taskStartTime.equals(endTime1)) {
                            conflict.add(task.displayString());
                            //end time is the same
                        } else if (taskEndTime.equals(endTime1) || taskEndTime.equals(startTime1)) {
                            conflict.add(task.displayString());
                            //new task start time overlaps with existing task
                        } else if (startTime1.after(taskStartTime) && startTime1.before(taskEndTime)) {
                            conflict.add(task.displayString());
                            //new task end time overlaps with existing task
                        } else if (endTime1.after(taskEndTime) && endTime1.before(taskEndTime)) {
                            conflict.add(task.displayString());
                            //existing task in new task period
                        } else if (taskStartTime.after(startTime1) && taskStartTime.before(endTime1)) {
                            conflict.add(task.displayString());
                        } else if (taskEndTime.after(startTime1) && taskEndTime.before(endTime1)) {
                            conflict.add(task.displayString());
                        }
                    }
                }
            }
        }
        return conflict;
    }

    /**
     * This method checks whether there is a conflict in the adding of deadlines based on module code and timing.
     * @param taskList The TaskList object for deadlines
     * @param t The deadline task that is added
     * @return ArrayList containing the assignment that has conflict
     */
    public ArrayList<String> checkDeadlineConflict(TaskList taskList, Assignment t) {
        ArrayList<String> conflict = new ArrayList<>();
        HashMap<String, HashMap<String, ArrayList<Assignment>>> mapObtained = taskList.getMap();
        if (mapObtained.containsKey(t.getModCode()) && mapObtained.get(t.getModCode()).containsKey(t.getDate())) {
            ArrayList<Assignment> temp = mapObtained.get(t.getModCode()).get(t.getDate());
            for (Assignment task : temp) {
                if (task.getTime().equals(t.getTime())) {
                    conflict.add(task.displayString());
                }
            }
        }
        return conflict;
    }
}