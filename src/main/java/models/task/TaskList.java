package models.task;

import java.util.ArrayList;
import java.util.Comparator;

public class TaskList {
    private ArrayList<Task> taskList;

    /**
     * Class representing a list with all task sort in the project.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Adds a new task to the list of this project.
     * @param task A new task to be added to the project.
     */
    public void addTask(Task task) {
        taskList.add(task);
    }

    /**
     * Deletes the task from the list using the index number.
     * @param taskIndexNumber The index number of the task to be deleted.
     */
    public void removeTask(int taskIndexNumber) {
        this.taskList.remove(taskIndexNumber - 1);
    }

    /**
     * Returns an ArrayList with String descriptions of task details.
     * @return An ArrayList with String descriptions of task details.
     */
    public ArrayList<String> getAllTaskDetails() {
        ArrayList<String> taskDetails = new ArrayList<>();
        // This method compares the two tasks and sort list in descending order.
        taskList.sort((task1, task2) -> task2.getTaskPriority() - task1.getTaskPriority());
        int taskIndex = 1;
        for (Task task : taskList) {
            taskDetails.add(taskIndex + ". " + task.getDetails());
            taskIndex++;
        }
        return taskDetails;
    }

    /**
     * Returns the list of all tasks.
     * @return An ArrayList with all tasks.
     */
    public ArrayList<Task> getTaskList() {
        return this.taskList;
    }

    public Task getTask(int taskIndex) {
        return this.taskList.get(taskIndex - 1);
    }


    /**
     * Edits details of a task excluding task requirements.
     * @param updatedTaskDetails input command String in the form of (must be this order)
     *                           edit task i/TASK_INDEX [n/TASK_NAME] [p/TASK_PRIORITY]
     *                           [d/TASK_DUEDATE] [c/TASK_CREDIT] [s/STATE]
     */
    public void editTask(String updatedTaskDetails) {
        String[] updatedTaskDetailsArray = updatedTaskDetails.split(" [itpdcs]\\/");
        int taskIndex = Integer.parseInt(updatedTaskDetailsArray[1]);
        boolean checkedTaskName = false;
        boolean checkedTaskPriority = false;
        boolean checkedTaskDueDate = false;
        boolean checkedTaskCredit = false;
        boolean checkedTaskState = false;
        for (int i = 2; i < updatedTaskDetailsArray.length; i++) {
            if (!checkedTaskName && updatedTaskDetails.contains(" t/")) {
                checkedTaskName = true;
                this.taskList.get(taskIndex - 1).setTaskName(updatedTaskDetailsArray[i]);
            } else if (!checkedTaskPriority && updatedTaskDetails.contains(" p/")) {
                checkedTaskPriority = true;
                this.taskList.get(taskIndex - 1).setTaskPriority(Integer.parseInt(updatedTaskDetailsArray[i]));
            } else if (!checkedTaskDueDate && updatedTaskDetails.contains(" d/")) {
                checkedTaskDueDate = true;
                this.taskList.get(taskIndex - 1).setDueDate(updatedTaskDetailsArray[i]);
            } else if (!checkedTaskCredit && updatedTaskDetails.contains(" c/")) {
                checkedTaskCredit = true;
                this.taskList.get(taskIndex - 1).setTaskCredit(Integer.parseInt(updatedTaskDetailsArray[i]));
            } else if (!checkedTaskState && updatedTaskDetails.contains(" s/")) {
                checkedTaskState = true;
                this.taskList.get(taskIndex - 1).setTaskState(updatedTaskDetailsArray[i]);
            }
        }
    }

    public int getSize() {
        return this.taskList.size();
    }
}
