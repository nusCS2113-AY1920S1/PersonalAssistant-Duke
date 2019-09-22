package wallet.task;

import wallet.record.Record;

import java.util.ArrayList;

public class ScheduleList {
    /**
     * Data structure to hold list of schedules of the user.
     * [TODO] FOR NOW: SCHEDULES REFER TO EXPENSES AND LOANS.
     */
    private ArrayList<Record> scheduleList;

    /**
     * Constructs a new ArrayList object.
     */
    public ScheduleList() {
        scheduleList = new ArrayList<Record>();
    }

    /**
     * Returns the list of schedules in the task list.
     *
     * @return The list of schedules.
     */

    public ArrayList<Record> getScheduleList() {
        return scheduleList;
    }

    /**
     * Add the given schedule into the task list.
     *
     * @param record The schedule to be added.
     */
    public void addSchedule(Record record) {
        scheduleList.add(record);
    }

    /**
     * Retrieve the schedules at the given index of the schedules list.
     *
     * @param index The index of the schedules in the list.
     * @return The schedules at the given index.
     */
    public Record getSchedule(int index) {
        return scheduleList.get(index);
    }

    /**
     * Removes the schedule at the given index of the schedules list.
     *
     * @param index The index of the schedules in the list.
     */
    public void deleteSchedule(int index) {
        scheduleList.remove(index);
    }

    /**
     * Get the current number of schedules in the schedules list.
     *
     * @return The number of schedules in the list.
     */
    public int getScheduleListSize() {
        return scheduleList.size();
    }

    /**
     * Prints the schedule list.
     */
    public void printScheduleList() {
        for (int i = 0; i < scheduleList.size(); i++) {
            System.out.println(scheduleList.get(i));
        }
    }
}
