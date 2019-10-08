package models.temp.tasks;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class TaskList implements Serializable {
    private static final int DAYS_FROM_NOW = 7;
    private ArrayList<ITask> listOfTasks;
    private ArrayList<IRecurring> listOfRecurringTasks;
    private ArrayList<ITask> schedule;

    public TaskList() {
        listOfTasks = new ArrayList<>();
        listOfRecurringTasks = new ArrayList<>();
    }

    /**
     * Adds the task to the tasklist.
     * @param newTask : A new task that is added by the user. Task is created by Factory.
     * @return : Boolean value which gives status of anomaly detection.
     */
    public boolean addToList(ITask newTask) {
        if (!detectAnomalies(newTask)) {
            this.listOfTasks.add(newTask);
            return false;
        }
        return true;
    }

    /**
     * Adds a Task to Recurring List.
     *
     * @param newRecurringTask : A new Recurring Task
     * @param newTask : The same recurring Task but its polymorphic ITask
     * @return : Returns a boolean for detection of anomalies
     */
    public boolean addToRecurringList(IRecurring newRecurringTask, ITask newTask) {
        if (!detectAnomalies(newTask)) {
            this.listOfRecurringTasks.add(newRecurringTask);
            return false;
        }
        return true;
    }

    public void deleteFromList(ITask oldTask) {
        listOfTasks.remove(oldTask);
    }

    public void deleteFromRecurring(IRecurring oldTask) {
        listOfRecurringTasks.remove(oldTask);
    }

    public ArrayList<ITask> getAllTasks() {
        return this.listOfTasks;
    }

    public ArrayList<IRecurring> getAllRecurringTasks() {
        return this.listOfRecurringTasks;
    }

    public ITask getTask(int index) {
        return this.listOfTasks.get(index);
    }

    public int getNumOfTasks() {
        return this.listOfTasks.size();
    }

    /**
     * Method that returns tasks that match with search keywords.
     * @param input : Keyword that users wish to search for
     * @return : Returns an ArrayList of ITask that matches keyword
     */
    public ArrayList<ITask> getSearchedTasks(String input) {
        String [] allInputs = input.split(" ");
        ArrayList<ITask> searchedTasks = new ArrayList<>();
        for (ITask task : listOfTasks) {
            if (task.getDescription().contains(allInputs[1])) {
                searchedTasks.add(task);
            }
        }
        return searchedTasks;
    }

    /**
     * Returns the schedule for the date.
     * @param date : The date input by the user.
     * @return : Returns an ArrayList of ITask which are in the schedule
     */
    public ArrayList<ITask> getSchedule(String date) {
        schedule = new ArrayList<>();
        try {
            for (ITask task : listOfTasks) {
                if (task.getDateTime().contains(date)) {
                    schedule.add(task);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Schedule for that day is empty.");
        }
        return schedule;
    }

    /**
     * Checks for anomalies with the current schedule.
     * @param newTask : A new task that is added by the user. Task is created by Factory.
     * @return : Boolean value which gives status of anomaly detection.
     */
    private boolean detectAnomalies(ITask newTask) {
        if (newTask instanceof ToDos || newTask instanceof  DoAfter || newTask instanceof Tentative) {
            return false;
        }
        schedule = getSchedule(newTask.getDateTime());
        if (schedule.isEmpty()) {
            return false;
        }
        for (ITask task : schedule) {
            if (task.getDateTime().equals(newTask.getDateTime())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns upcoming tasks within the next 7 days by default, or within a date and time given by the user.
     *
     * @param limit The date and time limit given by the user.
     * @return The list of tasks from the current time until the time limit.
     * @throws ParseException Parsing error (If date and time are not entered in dd/MM/yyyy HHmm)
     */
    public ArrayList<ITask> getUpcomingTasks(String limit) throws ParseException {
        ArrayList<ITask> upcomingTasks = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy hh.mm a");

        Date remindWithin;
        Date currentDateTime = new Date(System.currentTimeMillis());

        //If no limit given by user, by default will remind user of tasks in coming 7 days
        if (("").equals(limit)) {
            Calendar c = Calendar.getInstance();
            c.setTime(currentDateTime);
            c.add(Calendar.DATE, DAYS_FROM_NOW);
            remindWithin = c.getTime();
        } else {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
            remindWithin = inputDateFormat.parse(limit);
        }

        for (ITask task: this.listOfTasks) {
            String taskInitial = task.getInitials();
            if ("T".equals(taskInitial)) {
                continue;
            }
            Date taskDate = dateFormat.parse(task.getDateTime());
            if (taskDate.compareTo(currentDateTime) >= 0 && taskDate.compareTo(remindWithin) <= 0) {
                upcomingTasks.add(task);
            }
        }
        return upcomingTasks;
    }


    /**
     * Returns a list of tasks in sorted order, with the amount of free time in between tasks.
     * @param limit The time given by the user.
     * @return A list of tasks in sorted order, with the free time between each task.
     * @throws ParseException if date and time does not adhere to given format of dd/MM/yyyy
     */
    public ArrayList<String> findFreeSlots(String limit) throws ParseException {
        ArrayList<ITask> upcomingTasks = getUpcomingTasks(limit);
        upcomingTasks.sort(Comparator.comparing(ITask::getDateTimeObject));

        ArrayList<String> tasksAndFreeSlots = new ArrayList<>();

        Date currentDateTime = new Date(System.currentTimeMillis());
        long diff;
        for (ITask nextTaskInList : upcomingTasks) {
            diff = Math.abs(nextTaskInList.getDateTimeObject().getTime() - currentDateTime.getTime());
            long diffInHours = diff / (60 * 60 * 1000);
            long diffInDays = diffInHours / 24;
            long remainingHours = diffInHours % 24;
            String timeRemaining = "    Free time before next task: " + diffInDays + " day(s) "
                    + remainingHours + " hour(s)" + "\n";
            tasksAndFreeSlots.add(timeRemaining);
            String fullTaskDescription = "[" + nextTaskInList.getInitials() + "][" + nextTaskInList.getStatusIcon()
                    + "] " + nextTaskInList.getDescription();
            tasksAndFreeSlots.add(fullTaskDescription);
            currentDateTime = nextTaskInList.getDateTimeObject();
        }
        return tasksAndFreeSlots;
    }
}
