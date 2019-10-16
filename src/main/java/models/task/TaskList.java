package models.task;

import util.ParserHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class TaskList {
    private ArrayList<Task> taskList;
    private ParserHelper parserHelper;

    /**
     * Class representing a list with all task sort in the project.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
        this.parserHelper = new ParserHelper();
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
     * @return An ArrayList with String descriptions of task details sorted by name by default.
     */
    public ArrayList<String> getAllTaskDetails() {
        // after implementing task index, change "/NAME" to "/INDEX"
        return this.parserHelper.parseSortTaskDetails(taskList,"/NAME");
    }

    /**
     * Returns an ArrayList with String descriptions of task details sorted by the criteria specified by the user.
     * @param sortCriteria Criteria to sort chosen by user.
     * @return An ArrayList with String descriptions of task details sorted by the criteria specified by the user.
     */
    public ArrayList<String> getAllSortedTaskDetails(String sortCriteria) {
        return this.parserHelper.parseSortTaskDetails(taskList,sortCriteria);
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

    /**
     * Edits the task requirements of a specific task by adding or removing them.
     * @param taskIndexNumber Index of task to be edited.
     * @param updatedTaskRequirements Array containing indexes of task requirements to be removed and
     *                                task requirements to be added.
     * @param haveRemove boolean value to indicate if command wants to remove task requirements.
     */
    public void editTaskRequirements(int taskIndexNumber, String[] updatedTaskRequirements, boolean haveRemove) {
        if (haveRemove) {
            String[] indexesToBeRemoved = updatedTaskRequirements[2].split(" ");
            Arrays.sort(indexesToBeRemoved);
            for (int i = indexesToBeRemoved.length - 1; i >= 0; i--) {
                int indexToBeRemoved = Integer.parseInt(indexesToBeRemoved[i]);
                this.taskList.get(taskIndexNumber - 1).removeTaskRequirement(indexToBeRemoved);
            }
            if (updatedTaskRequirements.length > 3) {
                addTaskRequirements(taskIndexNumber, updatedTaskRequirements, 3);
            }
        } else {
            if (updatedTaskRequirements.length > 2) {
                addTaskRequirements(taskIndexNumber, updatedTaskRequirements, 2);
            }
        }
    }

    /**
     * Adds new task requirements to a specific task.
     * @param taskIndexNumber Index of task to be edited.
     * @param updatedTaskRequirements Array containing indexes of task requirements to be removed and
     *                                task requirements to be added.
     * @param indexOfFirstTaskReq Index in updatedTaskRequirements from which the contents are new task requirements
     *                            to be added.
     */
    private void addTaskRequirements(int taskIndexNumber, String[] updatedTaskRequirements, int indexOfFirstTaskReq) {
        for (int i = indexOfFirstTaskReq; i < updatedTaskRequirements.length; i++) {
            this.taskList.get(taskIndexNumber - 1).addTaskRequirement(updatedTaskRequirements[i]);
        }
    }
}
