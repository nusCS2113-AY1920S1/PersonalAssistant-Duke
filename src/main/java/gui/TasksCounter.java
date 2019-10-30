package gui;

import model.Task;

import java.util.ArrayList;

//@@author AugGust
public class TasksCounter {
    private ArrayList<Task> tasks;

    /**
     * Initilises a new TaskCounter with given arraylist of model.tasks.
     * For counting number of completed model.tasks in given set
     *
     * @param tasks task set to be counted
     */
    public TasksCounter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Gets percentage of model.tasks marked "done" in given set of model.tasks
     *
     * @return float value of percentage marked done
     */
    public float getPercCompleted() {
        if (tasks.size() == 0) {
            return 0f;
        }

        float totalCompleted = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).isDone()) {
                totalCompleted += 1;
            }
        }
        return totalCompleted / (float) tasks.size() * 100f;
    }
}
