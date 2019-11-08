package Commands;

import Commons.Storage;
import Commons.UserInteraction;
import Tasks.Assignment;
import Tasks.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Abstract class Command with methods representing all the Command subclasses to be
 * carried out when an input is entered by the user.
 */
public abstract class Command {
    public abstract String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage) throws Exception;

    public  ArrayList<String> checkEventConflict(TaskList taskList, Assignment t) throws ParseException {
        ArrayList<String> conflict = new ArrayList<>();
        Date startTime1 = new SimpleDateFormat("hh:mm a").parse(t.getStartTime());
        Date endTime1 = new SimpleDateFormat("hh:mm a").parse(t.getStartTime());
        HashMap<String, HashMap<String, ArrayList<Assignment>>> mapObtained = taskList.getMap();
        if (mapObtained.containsKey(t.getModCode()) && mapObtained.get(t.getModCode()).containsKey(t.getDate())) {
            ArrayList<Assignment> temp = mapObtained.get(t.getModCode()).get(t.getDate());
            for (Assignment task : temp) {
                Date taskStartTime = new SimpleDateFormat("hh:mm a").parse(task.getStartTime());
                Date taskEndTime = new SimpleDateFormat("hh:mm a").parse(task.getEndTime());
                if (task.getTime().equals(t.getTime())) {
                    conflict.add(task.displayString());
                }else if(!(taskStartTime.after(endTime1)) || !(taskEndTime).before(startTime1) ){
                    conflict.add(task.displayString());
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