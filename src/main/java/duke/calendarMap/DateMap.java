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

        TimeMap timeMap = this.get(key);
        timeMap.remove(time, task);

        this.replace(key, timeMap);
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
}
