package utils;

import java.util.ArrayList;

import tasks.Task;

public class TasksCounter {
    private ArrayList<Task> tasks;

	/**
	 * Initilises a new TaskCounter with given arraylist of tasks.
	 * For counting number of completed tasks in given set
	 *
	 * @param tasks task set to be counted
	 */
	public TasksCounter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

	/**
	 * Gets percentage of tasks marked "done" in given set of tasks
	 *
	 * @return integer of percentage marked done
	 */
	public int getPercCompleted() {
        float totalCompleted = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getIsDone())
                totalCompleted += 1;
        }
        return (int) (totalCompleted / (float) tasks.size() * 100f);
    }
}
