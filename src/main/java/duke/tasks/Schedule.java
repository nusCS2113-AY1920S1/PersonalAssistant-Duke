package duke.tasks;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

/**
 * Schedule is a public class that stores tasks in the same month in chronological order
 * @author Foo Chi Hen
 */

public class Schedule {
    protected ArrayList[][] schedule = new ArrayList[31][24];

    public Schedule() {
        for (int i = 0; i < 31; i += 1) {
            for (int j = 0; j < 24; j += 1) {
                this.schedule[i][j] = new ArrayList<Task>();
            }
        }
    }

    /**
     * update updates the task into the appropriate timeslot
     * @params task the task object to be inserted
     * @author Foo Chi Hen
     */
    public void update(Task task){
        LocalDate now = LocalDate.now();
        if (task.getType() == "D" || task.getType() == "E") {
            if (task.getDate() != null) {
                if (now.getYear() == task.getDate().get(Calendar.YEAR)) {
                    if (now.getMonthValue() == task.getDate().get(Calendar.MONTH) + 1) {
                        int taskDate = task.getDate().get(Calendar.DAY_OF_MONTH);
                        int taskHour = task.getDate().get(Calendar.HOUR_OF_DAY);
                        this.schedule[taskDate - 1][taskHour].add(task);
                    }
                }
            }
        }
    }

    /**
     * remindMe outputs the tasks to be done with the user defined hour
     * @params hour number of hours with reference to current time
     * @author Foo Chi Hen
     */

    public ArrayList<Task> remindMe(int hour){
        LocalDate nowDay = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        int currentDay = nowDay.getDayOfMonth();
        int currentHour = nowTime.getHour();
        ArrayList<Task> result = new ArrayList<Task>();
        ArrayList<Task> current;
        int counter = 0;
        for (int i = 0; i < hour; i += 1) {
            current = this.schedule[currentDay + counter - 1][(currentHour + i)%24];
            if (currentHour + i > (counter + 1) * 24 - 1){
                counter += 1;
            }
            for (int j = 0; j < current.size(); j += 1) {
                if (current.get(j).getType() == "D" && current.get(j).getisDone() == false) {
                    result.add(current.get(j));
                }
                else if (current.get(j).getType() == "E") {
                    result.add(current.get(j));
                }
            }
        }
        return result;
    }

}
