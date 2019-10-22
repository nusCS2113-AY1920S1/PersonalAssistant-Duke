package Tasks;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import Interface.DukeException;
import javafx.util.Pair;

/**
 * To keep track of the list of task input by user.
 */
public class TaskList {
    private static final String NO_FIELD = "void";

    private ArrayList<Task> list;
    private HashMap<String, HashMap<String, ArrayList<Task>>> map;
    private HashMap<String, HashMap<String, ArrayList<String>>> deadlineList = new HashMap<>();
    private HashMap<String, HashMap<String, ArrayList<String>>> eventList = new HashMap<>();
    private ArrayList<String> deadlineArrList = new ArrayList<>();
    private ArrayList<String> eventArrList = new ArrayList<>();

    /**
     * Creates a TaskList object.
     */
    public TaskList(){
        this.list = new ArrayList<>();
        this.map = new HashMap<>();
    }

    public ArrayList<Task> getList() {
        return list;
    }

    public HashMap<String, HashMap<String, ArrayList<Task>>> getMap(){
        return this.map;
    }

    public ArrayList<Task> getListFromDate(Task task) {
        return this.map.get(task.getModCode()).get(task.getDate());
    }

    public HashMap<String, ArrayList<Task>> getMapFromModCode(Task task) {
        return this.map.get(task.getModCode());
    }

    public void addTask(Task task){
        this.list.add(task);
        if (this.map.containsKey(task.getModCode())) {
            if (!this.map.get(task.getModCode()).containsKey(task.getDate())) {
                map.get(task.getModCode()).put(task.getDate(), new ArrayList<>());
            }
        } else {
            this.map.put(task.getModCode(), new HashMap<>());
            this.map.get(task.getModCode()).put(task.getDate(), new ArrayList<>());
        }
        this.map.get(task.getModCode()).get(task.getDate()).add(task);
    }

    public void removeTask(Task task) {
        for(Task taskInList : this.map.get(task.getModCode()).get(task.getDate())) {
            if(taskInList.getDescription().equals(task.getDescription())) {
                this.map.get(task.getModCode()).get(task.getDate()).remove(taskInList);
                break;
            }
        }
    }
    //Do not use this: User will input the task in the CLI
    public Task getTask(int index){
        return this.list.get(index);
    }
    //Do not use this: Use toString method in Task
    public String taskToString(int index){
        return list.get(index).toString();
    }

    public int taskListSize(){
        return list.size();
    }

    public void setReminder(Task task, String time, boolean reminder){
        for (Task taskInList : this.map.get(task.getModCode()).get(task.getDate())) {
            if (taskInList.getDescription().equals(task.getDescription())) {
                if (reminder) {
                    taskInList.setRemindTime(time);
                    taskInList.setReminder(true);
                    break;
                } else {
                    taskInList.setRemindTime("");
                    taskInList.setReminder(false);
                    break;
                }
            }
        }
    }

    /**
     * This method generates a increased a dateTime given by days or hours based on given duration and returns the new dateTime.
     * @param inDate The dateTime given
     * @param duration The duration given
     * @param type type task based on user's input
     * @return The new dateTime after increasing the inDate
     */
    private Date increaseDateTime(Date inDate, int duration, String type){
        Calendar c = Calendar.getInstance();
        c.setTime(inDate);
        if(type.equals("event")) c.add(Calendar.HOUR, duration);
        else if (type.equals("todo")) c.add(Calendar.DATE, duration);
        return c.getTime();
    }

    /**
     * This method sort and removes duplicated Dates of the list.
     * @param date The current datetime instance which locks the time
     * @return A list of dates combining data from taskList
     * @throws ParseException Throws a error when date being passed contains an error
     */
    private TreeSet<Date> sortAndRemoveDuplicatedDates(Date date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm aa");
        Set<Date> dateTime = new HashSet<>();
        dateTime.add(date);

        for (Task task : list) {
            if(!task.getDateTime().equals(NO_FIELD)) {
                Date dateFromList = dateFormat.parse(task.getDateTime());
                if (dateFromList.compareTo(date) > 0) dateTime.add(dateFromList);
            }
        }
        return new TreeSet<>(dateTime);
    }

    /**
     * This method retrieves the earliest possible block period with the duration given.
     * @param duration The period indicated by the user
     * @param type The type task based on user's input
     * @return This returns a pair in the format datetime then datetime plus duration
     * @throws ParseException Throws an error when method availableTimeSlot contains an error
     */
    public Pair<Date, Date> findEarliestFreeTime (String duration, String type) throws ParseException {
        int intDuration = Integer.parseInt(duration);
        Date date = new Date();
        if (list.size() == 0) return new Pair <> (date, increaseDateTime(date, intDuration, type));

        TreeSet<Date> sortedDateTime = sortAndRemoveDuplicatedDates(date);

        Iterator i = sortedDateTime.iterator();
        i.next();
        for (Date Date1 : sortedDateTime) {
            if (i.hasNext()) {
                Date Date2 = (Date) (i.next());
                long diff = Date2.getTime() - Date1.getTime();
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);

                if ((diffDays > 0 || diffHours >= (long) intDuration)) {
                    Date2 = increaseDateTime(Date1, intDuration, type);
                    return new Pair <>(Date1, Date2);
                }
            }
        }
        Date date1 = sortedDateTime.last();
        Date date2 = increaseDateTime(date1, intDuration, type);
        return new Pair<> (date1, date2);
    }

    /**
     * This method retrieves the 5 earliest possible block period with the duration given
     * @param duration The period indicated by the user
     * @param numOfTimeSlot The number of free time slots to generated
     * @param type The type task based on user's input
     * @return This returns a ArrayList of pair in the format datetime then datetime plus duration
     * @throws ParseException e
     */
    public ArrayList <Pair<Date, Date>> findFreeTimes(String duration, int numOfTimeSlot, String type) throws ParseException {
        int intDuration = Integer.parseInt(duration);
        Date date = new Date();
        ArrayList< Pair<Date, Date>> availableTimeSlot = new ArrayList<>();
        TreeSet<Date> sortedDateTime = sortAndRemoveDuplicatedDates(date);

        Iterator i = sortedDateTime.iterator();
        i.next();
        for (Date Date1 : sortedDateTime) {
            if (i.hasNext()) {
                Date Date2 = (Date) (i.next());
                long diff = Date2.getTime() - Date1.getTime();
                if(type.equals("event")) {
                    long diffHours = diff / (60 * 60 * 1000) % 24;
                    long diffDays = diff / (24 * 60 * 60 * 1000);

                    if ((diffDays > 0 || diffHours >= (long) intDuration) && numOfTimeSlot > 0) {
                        numOfTimeSlot--;
                        Date2 = increaseDateTime(Date1, intDuration, type);
                        availableTimeSlot.add(new Pair<>(Date1, Date2));
                    }
                } else if (type.equals("todo")){
                    long diffDays = diff / (24 * 60 * 60 * 1000);

                    if ((diffDays >= (long) intDuration) && numOfTimeSlot > 0) {
                        numOfTimeSlot--;
                        Date2 = increaseDateTime(Date1, intDuration, type);
                        availableTimeSlot.add(new Pair<>(Date1, Date2));
                    }
                }
            }
        }
        if(availableTimeSlot.size() >= 0) {
            Date date1 = sortedDateTime.last();
            Date date2 = increaseDateTime(date1, intDuration, type);
            for(int k = 0; k < numOfTimeSlot; k++) {
                availableTimeSlot.add(new Pair<>(increaseDateTime(date1, k, type),increaseDateTime(date2, k, type)));
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
            if (list.get(i).getType().equals("[D]")) {
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
        deadlineArrList.clear();
        eventArrList.clear();
        return finalSchedule;
    }

    private ArrayList<String> getDeadlineArrList() {
        return this.deadlineArrList;
    }

    private ArrayList<String> getEventArrList() {
        return this.eventArrList;
    }

    /** This method snoozes the task in the ArrayList.
     * @param index Index in the ArrayList of the Task Object to snooze
     * @param dateString New date for the Task Object
     * @return This returns the ArrayList
     * @throws DukeException On invalid input or when wrong input format is entered
     */
    public ArrayList<Task> snoozeTask(ArrayList<Task> list, int index, String dateString, String start, String end) throws DukeException {
        try {
            if (end == dateString) {
                Task temp = list.get(index);
                list.add(new Deadline(temp.getDescription(), dateString, start));
                list.remove(index);
            } else {
                Task temp = list.get(index);
                list.add(new Event(temp.getDescription(), dateString, start, end));
                list.remove(index);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(" OOPS!!! Please check that you only snoozed deadlines and events");
        }
        return list;
    }
}