package duke.tasks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.time.YearMonth;
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
    public boolean update(Task task){
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
        LocalDate now = LocalDate.now();
        if (task.getType() == "D" || task.getType() == "E") {
            if (task.getDate() != null) {
                if (now.getYear() == task.getDate().get(Calendar.YEAR)) {
                    if (now.getMonthValue() == task.getDate().get(Calendar.MONTH) + 1) {
                        int taskDate = task.getDate().get(Calendar.DAY_OF_MONTH);
                        int taskHour = task.getDate().get(Calendar.HOUR_OF_DAY);
                        boolean checkForAnomaly = detectAnomalies(this.schedule[taskDate - 1][taskHour]);
                        if (checkForAnomaly) {
                            System.out.println("There is already an event task at " + dateFormat.format(task.getDate().getTime()));
                            return false;
                        }
                        else {
                            this.schedule[taskDate - 1][taskHour].add(task);
                        }
                    }
                }
            }
        }
        return true;
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

    public boolean detectAnomalies(ArrayList<Task> activity){
        for (int i = 0; i < activity.size(); i += 1) {
            if (activity.get(i).getType().equals("E")) {
                return true;
            }
        }
        return false;
    }

    public void findFreeTime(int hour) {
        LocalDate nowDay = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        int currentDay = nowDay.getDayOfMonth();
        int currentHour = nowTime.getHour();
        int currentYear = nowDay.getDayOfMonth();
        int currentMonth = nowDay.getMonthValue();
        YearMonth yearMonthObject = YearMonth.of(currentYear , currentMonth);
        int daysInMonth = yearMonthObject.lengthOfMonth(); //28
        boolean flag = false;
        boolean freeFlag = false;
        boolean dayFreeFlag = false;
        for (int i = currentDay; i <= daysInMonth; i += 1) {
            System.out.println(nowDay.toString());
            System.out.println("_____________");
            for (int j = ((flag)? 0 : currentHour); j < 24; j += 1){
                if (this.schedule[i-1][j].size() == 0) {
                    for (int k = 0; k < hour; k += 1) {
                        if (i + (j + k) / 24 > daysInMonth) {
                            break;
                        }
                        if (this.schedule[i - 1 + (j+k)/24][(j+k)%24].size() != 0){
                            break;
                        }
                        if (k == hour - 1){
                            freeFlag = true;
                        }
                    }
                }
                if (freeFlag == true) {
                    LocalTime formatter = LocalTime.of(j,0);
                    System.out.println(formatter.toString());
                    dayFreeFlag = true;
                }
                freeFlag = false;
            }
            if (dayFreeFlag == false){
                System.out.println("You are not free on " + nowDay.toString());
            }
            nowDay.plusDays(1);
            flag = true;
            System.out.println("_____________");
        }
    }
}
