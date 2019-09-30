package Tasks;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import Interface.DukeException;
import javafx.util.Pair;

/**
 * To keep track of the list of task input by user.
 */
public class TaskList {
    private static final String NO_FIELD = new String ("void");

    private ArrayList<Task> list;
    private ArrayList<String> todoArrList = new ArrayList<>();
    private ArrayList<String> deadlineArrList = new ArrayList<>();
    private ArrayList<String> eventArrList = new ArrayList<>();

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
        //System.out.println("HERE IS THE LIST PRINTED" + this.list);
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
     * This method generates a increased a dateTime given and a given duration and returns the new dateTime
     * @param inDate The dateTime given
     * @param duration The duration given
     * @return The new dateTime after increasing the inDate
     */
    public Date increaseDateTime(Date inDate, int duration){
        Calendar c = Calendar.getInstance();
        c.setTime(inDate);
        c.add(Calendar.HOUR, duration);
        Date outDate = c.getTime();

        return outDate;
    }

    /**
     * This method sort and removes duplicated Dates of the list
     * @param date The current datetime instance which locks the time
     * @return A list of dates combining data from taskList
     * @throws ParseException
     */
    public Set<Date> sortAndRemoveDuplicatedDates(Date date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm aa");

        Set<Date> dateTime = new HashSet<>();
        dateTime.add(date);

        for (Task task : list) {
            if(!task.getDateTime().equals(NO_FIELD)) {
                Date dateFromList = dateFormat.parse(task.getDateTime()); // string -> date

                if (!task.getDateTime().equals(NO_FIELD) && (dateFromList.compareTo(date) > 0)) //check if date from list after current(getDateTime) date time
                    dateTime.add(dateFromList);
            }
        }
        Set<Date> sortedDateTime = new TreeSet<>(dateTime);
        return sortedDateTime;
    }


    /**
     * This method retrieves the earliest possible block period with the duration given.
     * @param duration The period indicated by the user
     * @return This returns a pair in the format datetime then datetime plus duration
     * @throws ParseException
     */
    public Pair<Date, Date> findEarliestFreeTime (String duration) throws ParseException {
        int intDuration = Integer.parseInt(duration);

        //Date pattern formats
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm aa");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        //SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");

        Date date = new Date();
        if (list.size() == 0) return new Pair <Date, Date> (date, increaseDateTime(date, intDuration));
        ArrayList< Pair<Date, Date>> availableTimeSlot = new ArrayList< Pair<Date, Date>>();

        Set<Date> sortedDateTime = sortAndRemoveDuplicatedDates(date);

        Iterator i = sortedDateTime.iterator();
        i.next();
        for (Date Date1 : sortedDateTime) {
            if (i.hasNext()) {
                Date Date2 = (Date) (i.next());
                long diff = Date2.getTime() - Date1.getTime();

                //long diffSeconds = diff / 1000 % 60;
                //long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);

                if ((diffDays > 0 || diffHours >= (long) intDuration)) {
                    Date2 = increaseDateTime(Date1, intDuration);
                    return new Pair <Date,Date>(Date1, Date2);
                }
            }
        }
        if(availableTimeSlot.size() == 0) {
            Date date1 = ((TreeSet<Date>) sortedDateTime).last();
            Date date2 = increaseDateTime(date1, intDuration);
            return new Pair<Date, Date> (date1, date2);
        }
        return new Pair<Date, Date> (date, increaseDateTime(date, intDuration));
    }

    /**
     * This method retrieves the 5 earliest possible block period with the duration given
     * @param duration The period indicated by the user
     * @return This returns a ArrayList of pair in the format datetime then datetime plus duration
     * @throws ParseException
     */
    public ArrayList <Pair<Date, Date>> findFreeTimes(String duration, int numOfTimeSlot) throws ParseException {
        int intDuration = Integer.parseInt(duration);

        //Date pattern formats
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm aa");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        //SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");

        Date date = new Date();
        ArrayList< Pair<Date, Date>> availableTimeSlot = new ArrayList< Pair<Date, Date>>();
        availableTimeSlot.add(new Pair <Date, Date> (date, increaseDateTime(date, intDuration)));
        if (list.size() == 0) return availableTimeSlot;
        availableTimeSlot = new ArrayList< Pair<Date, Date>>();

        Set<Date> sortedDateTime = sortAndRemoveDuplicatedDates(date);

        Iterator i = sortedDateTime.iterator();
        i.next();
        for (Date Date1 : sortedDateTime) {
            if (i.hasNext()) {
                Date Date2 = (Date) (i.next());
                long diff = Date2.getTime() - Date1.getTime();

                //long diffSeconds = diff / 1000 % 60;
                //long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);

                if ((diffDays > 0 || diffHours >= (long) intDuration) && numOfTimeSlot>0) {
                    numOfTimeSlot--;
                    Date2 = increaseDateTime(Date1, intDuration);

                    //return (dateFormat.format(Date1) + " for " + duration + "hours.");
                     availableTimeSlot.add(new Pair<Date, Date> (Date1, Date2));
                }

            }
        }
        if(availableTimeSlot.size() == 0) {
            Date date1 = ((TreeSet<Date>) sortedDateTime).last();
            Date date2 = increaseDateTime(date1, intDuration);
            for(int k = 0; k < numOfTimeSlot; k++) {
                availableTimeSlot.add(new Pair<Date, Date>(increaseDateTime(date1, k),increaseDateTime(date2, k)));
            }
        }

        return availableTimeSlot;
    }

        
    /**
     * This method sort the tasks according to their categories.
     */
    private void sortList() {
        for (int i = 0; i < list.size(); i++) {
            String description = list.get(i).toString();
            if (list.get(i).getType().equals("[T]")) {
                this.todoArrList.add(description);
            } else if (list.get(i).getType().equals("[D]")) {
                this.deadlineArrList.add(description);
            } else if (list.get(i).getType().equals("[E]")){
                this.eventArrList.add(description);
            }
        }
    }

    /**
     * This method gets the schedule requested by user.
     * @return This returns the String containing the schedule requested by user
     */
    public String schedule() {
        sortList();
        int sizeOfDeadlineArr = getDeadlineArrList().size();
        int sizeOfEventArr = getEventArrList().size();
        int sizeOfTodoArr = getTodoArrList().size();
        String finalSchedule = "Here is your schedule!\n";
        if (sizeOfDeadlineArr != 0) {
            finalSchedule += "DEADLINE Task\n";
            int num = 1;

            for (int i = 0; i < sizeOfDeadlineArr; i++) {
                finalSchedule = finalSchedule + num + "." + getDeadlineArrList().get(i) + "\n";
                num++;
            }
        }
        if (sizeOfEventArr != 0) {
            finalSchedule += "EVENT Task\n";
            int num = 1;

            for (int i = 0; i < sizeOfEventArr; i++) {
                finalSchedule = finalSchedule + num + "." + getEventArrList().get(i) + "\n";
                num++;
            }
        }
        if (sizeOfTodoArr != 0) {
            finalSchedule += "TODO Task\n";
            int num = 1;

            for (int i = 0; i < sizeOfTodoArr; i++) {
                finalSchedule = finalSchedule + num + "." + getTodoArrList().get(i) + "\n";
                num++;
            }
        }
        todoArrList.clear();
        deadlineArrList.clear();
        eventArrList.clear();
        return finalSchedule;
    }

    /**
     * @return the TodoArrayList
     */
    private ArrayList<String> getTodoArrList() {
        return this.todoArrList;
    }

    /**
     * @return the DeadlineArrayList
     */
    private ArrayList<String> getDeadlineArrList() {
        return this.deadlineArrList;
    }

    /**
     * @return the EventArrayList
     */
    private ArrayList<String> getEventArrList() {
        return this.eventArrList;
    }

   /** This method snoozes the task in the ArrayList.
     * @param index Index in the ArrayList of the Task Object to snooze
     * @param dateString New date for the Task Object
     * @return This returns the ArrayList
     * @throws DukeException On invalid input or when wrong input format is entered
     */
    public ArrayList<Task> snoozeTask(int index, String dateString) throws DukeException {
        try {
            TaskList temp1 = new TaskList();
            for (Task task : list) {
                temp1.addTask(task);
            }
            Task temp = temp1.getTask(index);
            if (temp.toString().startsWith("[D]")) {
                this.list.add(new Deadline(temp.getDescription(), dateString));
                this.list.remove(index);
            } else {
                this.list.add(new Event(temp.getDescription(), dateString));
                this.list.remove(index);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(" OOPS!!! Please check that you only snoozed deadlines and events");
        }
        return this.list;
    }
}
