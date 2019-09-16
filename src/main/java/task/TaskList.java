package task;
import java.util.ArrayList;
/**
 * The TaskList class handles all operations performed on the TaskList as well as stores the TaskList.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class TaskList {

    private ArrayList<Task> listOfTasks;

    public TaskList(ArrayList<Task> listOfTasks) {
        this.listOfTasks = listOfTasks;
    }

    /**
     * This function allows the use to delete a particular task.
     *
     * @param task contains the task that needs to be added.
     */
    public void add(Task task) {
        listOfTasks.add(task);
    }

    /**
     * This function allows the use to delete a particular task.
     *
     * @param indexOfTask this is the index of the task which needs to be deleted.
     */
    public Task delete(int indexOfTask) {
        Task task = listOfTasks.get(indexOfTask);
        listOfTasks.remove(task);
        return task;
    }

    /**
     * This function allows the user to find tasks with a particular keyword.
     *
     * @param keyWord this string contains the keyword the user is searching for.
     */
    public ArrayList<Task> find(String keyWord) {
        ArrayList<Task> holdFoundTasks = new ArrayList<>();
        boolean check = true;
        for (int i = 0; i < listOfTasks.size(); i++) {
            String find_match = listOfTasks.get(i).toStringForCheck();
            if (find_match.contains(keyWord)) {
                holdFoundTasks.add(listOfTasks.get(i));
            }
        }
        return holdFoundTasks;
    }
    /**
     * Performs a check as to if the task being added has a clash with another event. (CONTAINS A STEP BY STEP GUIDE)
     *
     * @param tasks contains all the tasks already in the database
     * @param userDateTime contains the string input by the user.
     * @param command contains the command to determine the action to perform.
     * @return boolean true if there is a clash, false if there is not clash.
     */
    public boolean isClash(TaskList tasks, String userDateTime, String command) {

        if (command.contains("event")) {
            // Step 1 is to extract the duration part eg. <1700-1800>
            String extractDuration = userDateTime.split(" ", 2)[1].trim();
            // Step 2 is to extract the start time of the event eg. 1700
            String extractDurationStart = extractDuration.split("-", 2)[0].trim();
            // Step 3 is to extract the end time of the event eg. 1800
            String extractDurationEnd = extractDuration.split("-", 2)[1].trim();
            // Step 4 extract the date to speed things up by checking for clashes only with events with the same date
            String extractDate = userDateTime.split(" ", 2)[0].trim();
            // Step 5 find all events and deadlines on the same day!
            ArrayList<Task> holdClashingTasks = tasks.find(extractDate);
            if (holdClashingTasks.size() == 0) {
                return false;
            } else {
                // Step 6 run through all those events/deadlines to find clashes.
                for (int i = 0; i < holdClashingTasks.size(); i++) {
                    String findMatch = holdClashingTasks.get(i).toStringForCheck();
                    // Step 7 if you are looking at an event you need to check if there is an overlap between to time
                    // periods so extract the start and end times.
                    if (findMatch.contains("[E]")) {
                        String extractTestDuration = findMatch.split(" ", 3)[2].trim();
                        String extractTestDurationStart = extractTestDuration.split("-", 2)[0].trim();
                        String extractTestDurationEnd = findMatch.split("-", 2)[1].trim();
                        // Step 8 if this condition is met there is some overlap!
                        if (Integer.parseInt(extractDurationStart) < Integer.parseInt(extractTestDurationEnd) &&
                                Integer.parseInt(extractTestDurationStart) < Integer.parseInt(extractDurationEnd)) {
                            return true;
                        }
                    } else {
                        // Step 9 if you are not looking at an event you need to check if the deadline is within the
                        // duration of the event!
                        String extractTestDuration = findMatch.split(" ", 3)[2].trim();
                        if (Integer.parseInt(extractDurationStart) < Integer.parseInt(extractTestDuration) &&
                                Integer.parseInt(extractDurationEnd) < Integer.parseInt(extractTestDuration)) {
                            return true;
                        }
                    }
                }
            }
        } else {
            // Step 1 this is the starting point if a deadline task has a clash with any of your events/deadlines
            // so obtain the time.
            String extractTime = userDateTime.split(" ", 2)[1].trim();
            // Step 2 get the date to speed up the process!
            String extractDate = userDateTime.split(" ", 2)[0].trim();
            // Step 3 obtain all events and deadlines that are scheduled on that day.
            ArrayList<Task> holdClashingTasks = tasks.find(extractDate);
            if (holdClashingTasks.size() == 0) {
                return false;
            } else {
                // Step 4 run through all those events/deadlines to find clashes.
                for (int i = 0; i < holdClashingTasks.size(); i++) {
                    String findMatch = holdClashingTasks.get(i).toStringForCheck();
                    // Step 5 if you are looking at an event you need to check if there is an overlap between to time
                    // periods so extract the start and end times.
                    if (findMatch.contains("[E]")) {
                        String extractTestDuration = findMatch.split(" ", 3)[2].trim();
                        String extractTestDurationStart = extractTestDuration.split("-", 2)[0].trim();
                        String extractTestDurationEnd = findMatch.split("-", 2)[1].trim();
                        // Step 6 As you are checking for an deadline you need to check if it lies within the
                        // duration of the event!
                        if (Integer.parseInt(extractTestDurationStart) < Integer.parseInt(extractTime) &&
                                Integer.parseInt(extractTestDurationEnd) > Integer.parseInt(extractTime)) {
                            return true;
                        }
                    } else {
                        // Step 7 if you are not looking at an event you need to check if the deadline starts at exactly
                        // the same time as another deadline.
                        String extractTestDuration = findMatch.split(" ", 3)[2].trim();
                        if (Integer.parseInt(extractTime) == Integer.parseInt(extractTestDuration)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * This function allows the user to mark a particular task as done.
     *
     * @param indexOfTask this is the index of the task which needs to be marked as done.
     */
    public Task markAsDone(int indexOfTask) {
        Task task = listOfTasks.get(indexOfTask);
        task.markAsDone();
        return task;
    }

    public ArrayList<Task> getTasks() {
        return listOfTasks;
    }

    public int getSize() {
        return listOfTasks.size();
    }
}
