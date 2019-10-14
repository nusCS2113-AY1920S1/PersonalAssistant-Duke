package compal.commons;

import compal.model.tasks.Task;
import compal.storage.StorageManager;
import compal.ui.UiPart;
import compal.logic.parser.ParserManager;
import compal.storage.Storage;
import compal.model.tasks.TaskList;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;


/**
 * Main class.
 */
public class Compal {

    //***Class Properties/Variables***--------------------------------------------------------------------------------->
    //objects supporting COMPal.Compal
    public UiPart ui;
    public Storage storage;
    public TaskList tasklist;
    public ParserManager parser;
    //----------------------->

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    /**
     * Constructs Compal object.
     * Initializes supporting objects.
     * Starts off the parser CLI parsing loop.
     */
    public Compal() {
        System.out.println("Compal:LOG: In Compal Constructor");
        //Instantiate objects
        tasklist = new TaskList(this);

        storage = new StorageManager();

        tasklist.arrlist = new ArrayList<>();
        tasklist.arrlist = storage.loadCompal();

        ui = new UiPart(this, tasklist.arrlist);

        //start parsing commands
        parser = new ParserManager(this, tasklist);
    }
    //----------------------->


    //***MISC FUNCTIONS***----------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    //----------------------->

    /**
     * This static inner class is the custom exception class extending Exception
     * that overwrites toString() for returning custom exception messages.
     * It is thrown when command is unknown or when there are invalid arguments.
     */
    public static class DukeException extends Exception {

        String description;

        public DukeException(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    /**
     * Lists all tasks that are incomplete and due in number of days input by user,
     * or by default 7 days, as well as tasks with reminders set as true.
     * Will print colour-coded and sorted by importance/priority.
     */
    public void viewReminder() {
        int numberOfDays = 7;

        ArrayList<Task> reminder = new ArrayList<>();
        Date currentDate = Calendar.getInstance().getTime();

        Calendar c = Calendar.getInstance();

        c.setTime(currentDate);
        c.add(Calendar.DATE, numberOfDays);
        Date dateAfter = c.getTime();

        c.setTime(currentDate);
        Date dateToday = c.getTime();

        for (Task t : tasklist.arrlist) {
            Calendar  tempTaskDateAndTime = Calendar.getInstance();
            tempTaskDateAndTime.setTime(t.getDate());
            int endingHour = Integer.parseInt(t.getStringEndTime().substring(0,2));
            tempTaskDateAndTime.set(Calendar.HOUR_OF_DAY, endingHour);
            int endingMinute = Integer.parseInt(t.getStringEndTime().substring(2,4));
            tempTaskDateAndTime.set(Calendar.MINUTE, endingMinute);

            Date deadline = tempTaskDateAndTime.getTime();


            if (deadline != null && !t.isDone && deadline.after(dateToday)
                    && (deadline.before(dateAfter) || t.hasReminder())) {
                t.calculateAndSetPriorityScore();
                reminder.add(t);
            }
        }

        //sort/compare by task priority score
        Comparator<Task> compareByDateTime = (Task t1, Task t2) ->
                Long.compare(t2.getPriorityScore(), t1.getPriorityScore());
        reminder.sort(compareByDateTime);

        //clear secondary window
        ui.clearSecondary();

        //display the results
        if (reminder.isEmpty()) {
            ui.printg("You currently have no tasks that have reminders set or are due within "
                            + numberOfDays + " days!",
                    "verdana", 15, Color.DARKGREEN);
        } else {
            int counter = 1;
            for (Task t : reminder) {
                if (t.getPriority().equals(Task.Priority.high)) {
                    //compal.ui.printg(counter + ". " + t.toString(),"verdana",15, Color.RED);
                    ui.printSecondaryg(counter + ". " + t.toString(), "verdana", 15, Color.RED);
                } else if (t.getPriority().equals(Task.Priority.medium)) {
                    //compal.ui.printg(counter + ". " + t.toString(),"verdana",15, Color.ORANGE);
                    ui.printSecondaryg(counter + ". " + t.toString(), "verdana", 15, Color.ORANGE);
                } else {
                    //compal.ui.printg(counter + ". " + t.toString(),"verdana",15, Color.GREEN);
                    ui.printSecondaryg(counter + ". " + t.toString(), "verdana", 15, Color.GREEN);
                }
                counter++;
            }
        }

    }
}






