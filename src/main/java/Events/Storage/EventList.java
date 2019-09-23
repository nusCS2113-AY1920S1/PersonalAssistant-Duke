package Events.Storage;

import Events.EventTypes.Event;
import Events.EventTypes.EventSubClasses.AssessmentSubClasses.Exam;
import Events.EventTypes.EventSubClasses.AssessmentSubClasses.Recital;
import Events.EventTypes.EventSubClasses.Concert;
import Events.EventTypes.EventSubClasses.RecurringEventSubclasses.Lesson;
import Events.EventTypes.EventSubClasses.RecurringEventSubclasses.Practice;
import Events.EventTypes.EventSubClasses.ToDo;
import Events.EventTypes.Task;
import Events.Formatting.DateObj;
import Events.Formatting.Predicate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Allows for access to the list of tasks currently stored, and editing that list of tasks.
 * Does NOT contain any methods for reading/writing to savefile.
 */
public class EventList {

    /**
     * list of Model_Class.Task objects currently stored.
     */
    private ArrayList<Event> taskArrayList;
    
    /**
	 * Filter type codes
	 */
    static final int DATE = 0;
    static final int TYPE = 1;

    protected int ONE_SEMESTER_DAYS = 16*7;

    /**
     * Creates new Model_Class.EventList object.
     *
     * @param inputList list of strings containing all information extracted from save file
     */
    public EventList(ArrayList<String> inputList) {
        //magic characters for type of event
        final char TODO = 'T';
        final char CONCERT = 'C';
        final char LESSON = 'L';
        final char PRACTICE = 'P';
        final char EXAM = 'E';
        final char RECITAL = 'R';

        taskArrayList = new ArrayList<Event>();
        for (String currLine : inputList) {
            boolean isDone = (currLine.substring(4,7).equals("✓"));
            if (currLine.charAt(1) == TODO) { //todo type task
                taskArrayList.add(new ToDo());
            } else if (currLine.charAt(1) == CONCERT) { //event type task
                taskArrayList.add(new Concert());
            } else if (currLine.charAt(1) == LESSON) { //deadline type task
                taskArrayList.add(new Lesson());
            } else if (currLine.charAt(1) == PRACTICE) {
                taskArrayList.add(new Practice());
            } else if (currLine.charAt(1) == EXAM) {
                taskArrayList.add(new Exam());
            } else if (currLine.charAt(1) == RECITAL) {
                taskArrayList.add(new Recital());
            }
        }
    }

    /**
     * Checks for a clash, then adds a new task if possible.
     *
     * @param task Model_Class.Task object to be added
     * @return boolean signifying whether or not the task was added successfully. True if succeeded
     * and false if not
     */
    public boolean addTask(Event event) {
        if (event instanceof ToDo) {
            this.taskArrayList.add(event);
            return true;
        }
        else {
            Event clashTask = clashTask(event); //check the list for a schedule clash
            if (clashTask == null) { //null means no clash was found
                this.taskArrayList.add(event);
                return true;
            } else return false;
        }
    }

    /**
     * Adds recurring events to the list.
     *
     * @param event Event to be added as recursion.
     * @param period Period of the recursion.
     */
    public boolean addRecurringEvent(Event event, int period) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HHmm");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        DateObj taskDate = new DateObj(event.getStartDateObj().toOutputString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(taskDate.getJavaDate());
        for (int addTaskCount = 0; addTaskCount*period <= ONE_SEMESTER_DAYS; addTaskCount++) {
            String timeString = null;
            if (taskDate.getFormat() == 1) {
                timeString = format1.format(calendar.getTime());
            }
            else if (taskDate.getFormat() == 2) {
                timeString = format2.format(calendar.getTime());
            }
            this.taskArrayList.add(new Event(event.getDescription(), timeString));
            calendar.add(Calendar.DATE, period);
        }
        return true;
    }

    /**
     * Checks the list of tasks for any clashes with the newly added task. If
     * there is a clash, return a reference to the task, if not, return null.
     * @param task newly added task
     * @return task that causes a clash
     */
    private Event clashTask(Event task) {
        for (Event currTask : taskArrayList) {
            try {
                if (currTask.startDateToString().equals(task.startDateToString())) {
                    return currTask;
                }
            } catch (Exception e){
            }
        }
        return null;
    }

    /**
     * Deletes a task from the list.
     *
     * @param taskNo Index of task to be deleted
     */
    public void deleteTask(int taskNo) {
        this.taskArrayList.remove(taskNo);
    }

    /**
     * Gets list of Model_Class.Task objects stored
     *
     * @return Array of TaskLists containing all tasks.
     */
    public ArrayList<Event> getTaskArrayList() {
        return this.taskArrayList;
    }

    /**
     * Gets number of tasks stored.
     *
     * @return number of tasks stored
     */
    public int getNumTasks() {
        return taskArrayList.size();
    }

    /**
     * Gets a specific task using indexing.
     *
     * @param index Index of task to be extracted
     * @return Model_Class.Task object of specified task
     */
    public Event getEvent(int index) {
        return taskArrayList.get(index);
    }

    /**
     * Gets the entire list of tasks stored in String format
     *
     * @return String containing all tasks, separated by a newline.
     */
    public String listOfTasks_String() {
        String allTasks = "";
        for (int i = 0; i < taskArrayList.size(); ++i) {
            if (taskArrayList.get(i) == null) continue;
            int j = i + 1;
            allTasks += j + ". " + this.getTask(i).toString() + "\n";
        }
        return allTasks;
    }
    
    /**
     * Gets a filtered list of tasks based on a predicate.
     * @return String containing the filtered list of tasks, separated by a newline.
     */
    public String filteredList(Predicate<Object> predicate, int filterCode) {
        String filteredTasks = "";
        int j = 1;
        for (int i = 0; i < taskArrayList.size(); ++i) {
            if (taskArrayList.get(i) == null) {
            	continue;
            } else if (filterCode == DATE) {
                if (taskArrayList.get(i) instanceof Event || taskArrayList.get(i) instanceof Deadline) {
                	if (!predicate.check(taskArrayList.get(i).getStartDateObj())) {
                		continue;
                	} 
                } else {
                	continue;
                }
            } else if (filterCode == TYPE) {
                if (!predicate.check(taskArrayList.get(i).getType())) {
                	continue;
                }
            } 
            filteredTasks += j + ". " + this.getEvent(i).toString() + "\n";
            j++;
        }
        return filteredTasks;
    }
}
