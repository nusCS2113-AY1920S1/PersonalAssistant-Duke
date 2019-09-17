package duke.calendarMap;

import duke.task.Task;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class DateMap extends TreeMap<LocalDate, TimeMap> {

    public void deleteTask(String date, Task task) {
        String[] arrStr = date.trim().split(" ");
        String time = arrStr[1].trim();

        arrStr = arrStr[0].trim().split("/");
        int yr = Integer.parseInt(arrStr[2]);
        int mth = Integer.parseInt(arrStr[1]);
        int day = Integer.parseInt(arrStr[0]);

        LocalDate key = LocalDate.of(yr, mth, day);

        switch(checkCondition(key, time, task)) {
            case 4:
                TimeMap timeMap = this.get(key);
                timeMap.remove(time, task);
                this.replace(key, timeMap);
                break;
            // can throw error exception to show that task cannot be deleted
            default:
                break;
        }
    }

    public void addTask(String date, Task task) {
        String[] arrStr = date.trim().split(" ");
        String time = arrStr[1].trim();

        arrStr = arrStr[0].trim().split("/");
        int yr = Integer.parseInt(arrStr[2]);
        int mth = Integer.parseInt(arrStr[1]);
        int day = Integer.parseInt(arrStr[0]);

        LocalDate key = LocalDate.of(yr, mth, day);
        TimeMap timeMap;

        switch(checkCondition(key, time, task)){
            case 1:
            case 2:
                timeMap = new TimeMap();
                timeMap.put(time, task);
                this.put(key, timeMap);
                break;
            case 5:
                timeMap = this.get(key);
                timeMap.put(time, task);
                this.replace(key, timeMap);
            default:
                break;
        }

        if (this.isEmpty()) {
            timeMap = new TimeMap();
            Task addedTask = timeMap.put(time, task);
            this.put(key, timeMap);
        } else {
            timeMap = this.get(LocalDate.of(yr, mth, day));
            Task addedTask = timeMap.put(time, task);
            this.replace(key, timeMap);
        }
    }

    public void viewSchedule(String date) {
        String[] arrStr = date.trim().split("/");
        int yr = Integer.parseInt(arrStr[2]);
        int mth = Integer.parseInt(arrStr[1]);
        int day = Integer.parseInt(arrStr[0]);

        LocalDate viewDate = LocalDate.of(yr, mth, day);

        String message = String.format("Here are your tasks for %d/%d/%d: \n", day, mth, yr);

        int counter = 1;

        for(Map.Entry<String, Task> taskOfTheDay : this.get(viewDate).entrySet()) {
            Task task = taskOfTheDay.getValue();
            message += counter + ". " + task.toString();
            counter++;
        }
    }

    private int checkCondition(LocalDate key, String time, Task task) {
        // DateMap is empty
        if (this.isEmpty()) {
            return 1;
        // DateMap does not have specified key
        } else if (!this.containsKey(key)) {
            return 2;
        // TimeMap have specified key
        } else if (this.get(key).containsKey(time)) {
            // TimeMap does not have specified Task
            if(this.get(key).get(time).equals(task)) {
                return 3;
            // TimeMap have specified Task
            } else {
                return 4;
            }
        // TimeMap does not have specified key.
        } else {
            return 5;
        }
    }
}
