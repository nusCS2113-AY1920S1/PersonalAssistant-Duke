package moomoo.task;

import java.util.ArrayList;

public class ScheduleList {
    public ArrayList<SchedulePayment> fullSchedule;

    public ScheduleList() {
        this.fullSchedule = new ArrayList<>();
    }

    public ScheduleList(ArrayList<SchedulePayment> loadedSchedule) {
        this.fullSchedule = loadedSchedule;
    }

    public void addToCalendar(SchedulePayment list) {
        fullSchedule.add(list);
    }

    /**
     * Returns list of scheduled payments due today.
     *
     * @param date Pass in today's date.
     * @return a list of scheduled payments due today.
     */
    public String showSchedule(String date) {
        String output = "Outstanding Payment:\n";
        for (SchedulePayment p : fullSchedule) {
            if (date.equals(p.date)) {
                output += (p.tasks + "\n");
            }
        }
        return output;
    }
}
