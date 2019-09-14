package task;

import java.util.ArrayList;

public class ScheduleList {
    /**
     * Data structure to hold list of schedules of the user
     * [TODO] FOR NOW: SCHEDULES REFER TO EXPENSES AND LOANS
     */
    ArrayList<Task> scheduleList;

    /**
     * Constructs a new ArrayList object
     */
    public ScheduleList() {
        scheduleList = new ArrayList<Task>();
    }

    /**
     * Returns the list of schedules in the task list.
     *
     * @return The list of schedules
     */

    public ArrayList<Task> getScheduleList() {
        return scheduleList;
    }

    /**
     * Add the given schedule into the task list.
     *
     * @param task The schedule to be added.
     */
    public void addSchedule(Task task) {
        scheduleList.add(task);
    }

    /**
     * Retrieve the schedules at the given index of the schedules list.
     *
     * @param index The index of the schedules in the list.
     * @return The schedules at the given index.
     */
    public Task getSchedule(int index) {
        return scheduleList.get(index);
    }

    /**
     * Removes the schedule at the given index of the schedules list.
     *
     * @param index The index of the schedules in the list
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

    public void printScheduleList()
    {
        for (int i = 0; i < scheduleList.size(); i++)
        {
            System.out.println(scheduleList.get(i));
        }
    }
}
