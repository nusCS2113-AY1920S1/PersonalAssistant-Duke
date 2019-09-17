package Tasks;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.*;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

/**
 * To keep track of the list of task input by user.
 */
public class TaskList {
    protected ArrayList<Task> list;

    /**
     * Creates a TaskList object.
     */
    public TaskList(){
        this.list = new ArrayList<>();
    }

    /**
     * Retrieve the ArrayList from the TaskList object.
     * @return This returns the ArrayList from the TaskList object
     */
    public ArrayList<Task> getList() {
        return list;
    }

    /**
     * This method adds a Task object into the ArrayList.
     * @param task Task object to be added
     */
    public void addTask(Task task){
        this.list.add(task);
    }

    /**
     * This method removes the task from the ArrayList.
     * @param index Index in the ArrayList to remove the Task object from
     */
    public void removeTask(int index){
        this.list.remove(index);
    }

    /**
     * This method retrieves the current index in the ArrayList.
     * @param index Index in the ArrayList to retrieve the Task object from
     * @return This returns the Task object that was retrieved
     */
    public Task getTask(int index){
        return this.list.get(index);
    }

    /**
     * This method converts a specific Task object from the ArrayList to a string.
     * @param index Index in the ArrayList to retrieve the Task object from
     * @return This returns the string of the Task object that was retrieved
     */
    public String taskToString(int index){
        return list.get(index).toString();
    }

    /**
     * This method marks a specific Task object in the ArrayList as done.
     * @param index Index in the ArrayList to retrieve the Task object from
     */
    public void markAsDone(int index){
        this.list.get(index).setDone(true);
    }

    /**
     * This method retrieves the size of the ArrayList.
     * @return This returns the size of the ArrayList
     */
    public int taskListSize(){
        return list.size();
    }

    /**
     * This method finds the tasks in the ArrayList that contains the keyword.
     * @param key The keyword input by the user
     * @return This returns a TaskList object containing all the tasks with the keyword
     */
    public TaskList findTask(String key) {
        TaskList temp = new TaskList();
        for (Task task : list) {
            if (task.getDescription().contains(key)) {
                temp.addTask(task);
            }
        }
        return temp;
    }

    /**
     * This method retrieves the earliest possible block period
     * @param duration The period indicated by the user
     * @return This returns a string in the format "datetime until datetime"
     * @throws ParseException
     */
    public String findFreeTimes(String duration) throws ParseException {
        int intDuration = Integer.parseInt(duration);

        //Date pattern formats
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm aa");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");

        Date date = new Date();
        DateTime current= new DateTime(date);
        DateTime currentPlusDuration = current.plusHours(intDuration);
        String strCurrent = dateFormat.format(date);
        String strCurrentPlusDuration = dateFormat.format(currentPlusDuration.toDate());

        if(list.size() == 0) return (strCurrent + " until " + strCurrentPlusDuration);
        //removes duplicated values
        Set<Date> dateTime = new HashSet<Date>();
        dateTime.add(date);

        for (Task task: list){
            Date dateFromList = dateFormat.parse(task.getDateTime()); // string -> date

            if(!task.getDateTime().equals("void") && dateFromList.compareTo(date) > 0) //check if date from list after current(getDateTime) date time
            dateTime.add(dateFromList);
        }
        //sorts set
        Set<Date> sortedDateTime = new TreeSet<Date>(dateTime);

        Iterator i = sortedDateTime.iterator();
        i.next();
        for(Date set: sortedDateTime){
            if(i.hasNext()) {
                DateTime dt1 = new DateTime((Date) i.next());
                DateTime dt2 = new DateTime(set);
                long diffHours = Hours.hoursBetween(dt2, dt1).getHours();

                if (diffHours >= (long)intDuration){
                    DateTime dt3 = dt2.plusHours(intDuration);
                    return dateFormat.format(set) + " until " + dateFormat.format(dt3);
                }
            }
        }
        DateTime dt2 = new DateTime(((TreeSet<Date>) sortedDateTime).last());
        DateTime dt3 = dt2.plusHours(intDuration);

        return dateFormat.format(dt2.toDate()) + " until " + timeFormat.format(dt3.toDate());
    }
}
