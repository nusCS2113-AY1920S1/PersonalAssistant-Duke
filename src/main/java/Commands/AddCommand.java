package Commands;

import Commons.DukeLogger;
import Commons.Storage;
import Commons.UserInteraction;
import DukeExceptions.DukeInvalidFormatException;
import UserInterface.AlertBox;
import Tasks.Assignment;
import Tasks.TaskList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Represents the command to add a Task object to a TaskList object.
 */
public class AddCommand extends Command {

    private final Assignment task;
    private final Logger LOGGER = DukeLogger.getLogger(AddCommand.class);

    /**
     * Creates an AddCommand object.
     *
     * @param task The Task object to be added
     */
    public AddCommand(Assignment task) {
        this.task = task;
    }

    /**
     * Executes the adding of a Task object to a TaskList object
     * and displaying the add task response.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the add task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display add task message
     */
    @Override
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage) throws ParseException, DukeInvalidFormatException {
        String out = "";

        ArrayList<String> eventConflict;
        ArrayList<String> deadlineConflict;
        if (task.getType().equals("[E]")) {
            try {
                eventConflict = checkEventConflict(events, this.task);
            } catch (ParseException e ) {
                LOGGER.severe("Invalid format for adding event");
                throw new DukeInvalidFormatException("OOPS!!! Please enter event as follows:\n" +
                        "add/e modCode name_of_event /at dd/MM/yyyy /from HHmm /to HHmm\n" +
                        "or add/e modCode name_of_event /at week x day /from HHmm /to HHmm\n " +
                        "For example: add/e CS1231 project meeting /at 1/1/2020 /from 1500 /to 1700");
            }
            int size = events.taskListSize() + 1;

            if (eventConflict.size() == 0) {
                events.addTask(this.task);
                out = ui.showAdd(this.task,size);
                storage.updateEventList(events);
            }else{
                out = "Sorry, you have conflicting events \n";
                for (int i = 0; i< eventConflict.size();i++){
                    out += (i+ 1) + ". " + eventConflict.get(0) +"\n";
                }
            }
        } else if (task.getType().equals("[D]")) {
            deadlineConflict = checkDeadlineConflict(deadlines, this.task);
            int size = deadlines.taskListSize() + 1;
            if (deadlineConflict.size() == 0) {
                deadlines.addTask(this.task);
                out = ui.showAdd(this.task,size);
                storage.updateDeadlineList(deadlines);
            }else{
                out = "Sorry, you have conflicting deadlines \n";
                for (int i = 0; i< deadlineConflict.size();i++){
                    out += (i+ 1) + ". " +deadlineConflict.get(0) +"\n";
                }

            }

        }
        return out;
    }

    public ArrayList<String>  checkEventConflict(TaskList tasklist, Assignment t) throws ParseException {
        ArrayList<String> conflict = new ArrayList<>();
        Date startTime1 =new SimpleDateFormat("hh:mm a").parse(t.getStartTime());
        Date endTime1 =new SimpleDateFormat("hh:mm a").parse(t.getStartTime());
        HashMap<String, HashMap<String, ArrayList<Assignment>>> Map = tasklist.getMap();
        if(Map.containsKey(t.getModCode()) && Map.get(t.getModCode()).containsKey(t.getDate())) {
            ArrayList<Assignment> temp = Map.get(t.getModCode()).get(t.getDate());
            for (Assignment task : temp) {
                {
                    Date startTime = new SimpleDateFormat("hh:mm a").parse(task.getStartTime());
                    Date endTime = new SimpleDateFormat("hh:mm a").parse(task.getEndTime());
                    if (task.getStartTime().equals(t.getStartTime())) {
                        conflict.add(task.displayString());
                    }
                    else if(task.getEndTime().equals(t.getEndTime())){
                        conflict.add(task.displayString());
                    }
                    else if(startTime.after(startTime1) && startTime.before(endTime1)){
                        conflict.add(task.displayString());
                    }
                    else if(startTime1.after(startTime) && startTime1.before(endTime)){
                        conflict.add(task.displayString());
                    }

                }
            }
        }
        return conflict;
    }

    public ArrayList<String>  checkDeadlineConflict(TaskList tasklist, Assignment t){
        ArrayList<String> conflict = new ArrayList<>();
        HashMap<String, HashMap<String, ArrayList<Assignment>>> Map = tasklist.getMap();
        if(Map.containsKey(t.getModCode()) && Map.get(t.getModCode()).containsKey(t.getDate())) {
            ArrayList<Assignment> temp = Map.get(t.getModCode()).get(t.getDate());
            for (Assignment task : temp) {
                {
                    if (task.getTime().equals(t.getTime())) {
                        conflict.add(task.displayString());
                    }
                }
            }
        }
        return conflict;
    }
}