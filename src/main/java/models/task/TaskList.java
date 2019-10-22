package models.task;

import util.ParserHelper;
import util.SortHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class TaskList {
    private ArrayList<Task> taskList;
    private ParserHelper parserHelper;
    private SortHelper sortHelper;

    /**
     * Class representing a list with all task sort in the project.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
        this.parserHelper = new ParserHelper();
        this.sortHelper = new SortHelper();
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
        // after implementing task index, change "/PRIORITY" to "/INDEX"
        return this.parserHelper.parseSortTaskDetails(taskList,"/PRIORITY");
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
     * @param updatedTaskDetails input command String in the form of (tasks to be edited can be in any order)
     *                           edit task i/TASK_INDEX [n/TASK_NAME] [p/TASK_PRIORITY]
     *                           [d/TASK_DUEDATE] [c/TASK_CREDIT] [s/STATE]
     */
    public void editTask(int taskIndexNumber, String updatedTaskDetails) {
        ArrayList<String> taskDetails = parserHelper.parseTaskDetails(updatedTaskDetails);
        String taskName = taskDetails.get(0);

        Task task = taskList.get(taskIndexNumber - 1);
        if (!("--".equals(taskName))) {
            task.setTaskName(taskName);
        }
        String taskPriority = taskDetails.get(1);
        if (!("-1".equals(taskPriority))) {
            task.setTaskPriority(Integer.parseInt(taskPriority));
        }
        String taskDueDate = taskDetails.get(2);
        if (taskDueDate != null) {
            task.setDueDate(taskDueDate);
        }
        String taskCredit = taskDetails.get(3);
        if (!("-1".equals(taskCredit))) {
            task.setTaskCredit(Integer.parseInt(taskCredit));
        }
        String taskState = taskDetails.get(4);
        if (!("NONE".equals(taskState))) {
            task.setTaskState(taskState);
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

    public boolean contains(Task task) {
        return (taskList.contains(task));
    }

    /**
     * Get details of task with closest deadline and return in String array form.
     * @return String array of details of task which has the closest deadline.
     */
    public String[] getClosestDeadlineTask() {
        ArrayList<String> sortedTaskList = sortHelper.sortTaskDueDate(this.taskList);
        for (String task : sortedTaskList) {
            if (!task.contains("State: DONE")) {
                return task.substring(3).split(" [|] ");
            }
        }
        String[] message = new String[2];
        message[0] = "";
        message[1] = "No deadlines left -";
        return message;
    }

    /**
     * Method to calculate progress and store in presentable String format.
     * @return String array containing output for overall progress to be printed in table.
     */
    public String[] getOverallProgress() {
        int totalCredits = 0;
        double creditsOpen = 0;
        double creditsTodo = 0;
        double creditsDoing = 0;
        double creditsDone = 0;
        for (Task task : this.taskList) {
            totalCredits += task.getTaskCredit();
            if (task.getTaskState() == TaskState.DONE) {
                creditsDone += task.getTaskCredit();
            } else if (task.getTaskState() == TaskState.DOING) {
                creditsDoing += task.getTaskCredit();
            } else if (task.getTaskState() == TaskState.TODO) {
                creditsTodo += task.getTaskCredit();
            } else {
                creditsOpen += task.getTaskCredit();
            }
        }
        ArrayList<String> progressDetails = new ArrayList<>();
        String percentageDone = Integer.toString((int)(creditsDone / totalCredits * 100));
        progressDetails.add("Completed: " + percentageDone + "%");
        String percentageInProgress = Integer.toString((int)(creditsDoing / totalCredits * 100));
        progressDetails.add("In Progress: " + percentageInProgress + "%");
        String percentageNotDone = Integer.toString((int)((creditsTodo + creditsOpen) / totalCredits * 100));
        progressDetails.add("Not Done: " + percentageNotDone + "%");
        return progressDetails.toArray(new String[0]);
    }
}
