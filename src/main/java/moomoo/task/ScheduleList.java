package moomoo.task;

import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleList {
    public HashMap<String, ArrayList<String>> calendar;

    public ScheduleList() {
        this.calendar = new HashMap<>();
    }

    public ScheduleList(HashMap<String, ArrayList<String>> loadedSchedule) {
        this.calendar = loadedSchedule;
    }

    /**
     * Adds a new item to hashmap if date indicated was not found in hashmap.
     * @param date Pass in the scheduled task's date.
     * @param task Pass in the task to be added.
     */
    public void addToCalendar(String date, String task) {
        ArrayList<String> dayTasks = new ArrayList<>();
        dayTasks.add(task);
        calendar.put(date, dayTasks);
    }

    /**
     * Returns list of scheduled payments due today.
     *
     * @param date Pass in today's date.
     * @return a list of scheduled payments due today.
     */
    public String showSchedule(String date) {
        String output = "Outstanding Payment:\n";
        if (date.length() < 8) {
            date = "0" + date;
        }
        if (calendar.containsKey(date)) {
            for (String n : calendar.get(date)) {
                output += (n + "\n");
            }
        }
        return output;
    }
}
