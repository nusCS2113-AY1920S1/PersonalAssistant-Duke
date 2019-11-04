package models.task;

import models.member.Member;
import util.ParserHelper;
import util.SortHelper;
import util.date.DateTimeHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class TaskList {
    private ArrayList<Task> taskList;
    private ParserHelper parserHelper;
    private SortHelper sortHelper;
    private DateTimeHelper dateTimeHelper;

    /**
     * Class representing a list with all task sort in the project.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
        this.parserHelper = new ParserHelper();
        this.sortHelper = new SortHelper();
        this.dateTimeHelper = new DateTimeHelper();
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
     * @param tasksAndAssignedMembers HashMap containing tasks with assigned members.
     * @return An ArrayList with String descriptions of task details sorted by name by default.
     */
    public ArrayList<String> getAllTaskDetails(HashMap<Task, ArrayList<Member>> tasksAndAssignedMembers) {
        // after implementing task index, change "/PRIORITY" to "/INDEX"
        return this.parserHelper.parseSortTaskDetails(tasksAndAssignedMembers,taskList,"/PRIORITY");
    }

    /**
     * Returns an ArrayList with String descriptions of task details to be presented in table format.
     * @param tasksAndAssignedMembers HashMap containing tasks with assigned members.
     * @return An ArrayList with String descriptions of task details sorted by name by default to be presented in table
     *         format.
     */
    public ArrayList<String> getAllTaskDetailsForTable(HashMap<Task, ArrayList<Member>> tasksAndAssignedMembers,
                                                       String sortCriteria) {
        ArrayList<String> allTaskDetailsForTable = new ArrayList<>();
        if (this.taskList.size() == 0) {
            allTaskDetailsForTable.add(" - There are currently no tasks! -");
        } else {
            ArrayList<String> allTaskDetails = this.parserHelper.parseSortTaskDetails(tasksAndAssignedMembers,
                    taskList, sortCriteria);
            if (sortCriteria.substring(0, 5).equals("/WHO-") && allTaskDetails.size() == 0) {
                allTaskDetailsForTable.add(" - There are no tasks assigned to " + sortCriteria.substring(5) + "! -");
            } else if ("/DATE".equals(sortCriteria) && allTaskDetails.size() == 0) {
                allTaskDetailsForTable.add(" - There are no tasks with deadlines! -");
            } else {
                for (String s : allTaskDetails) {
                    String[] indivTaskDetails = s.split(" [|] ");
                    allTaskDetailsForTable.add(indivTaskDetails[0]);

                    for (int i = 1; i < indivTaskDetails.length; i++) {
                        allTaskDetailsForTable.add("   - " + indivTaskDetails[i]);
                    }
                    allTaskDetailsForTable.add("");
                }
                allTaskDetailsForTable.remove(allTaskDetailsForTable.size() - 1);
            }
        }
        return allTaskDetailsForTable;
    }

    /**
     * Returns an ArrayList with String descriptions of task details sorted by the criteria specified by the user.
     * @param tasksAndAssignedMembers HashMap containing tasks with assigned members.
     * @param sortCriteria Criteria to sort chosen by user.
     * @return An ArrayList with String descriptions of task details sorted by the criteria specified by the user.
     */
    public ArrayList<String> getAllSortedTaskDetails(HashMap<Task, ArrayList<Member>> tasksAndAssignedMembers,
                                                     String sortCriteria) {
        return this.parserHelper.parseSortTaskDetails(tasksAndAssignedMembers, taskList, sortCriteria);
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
     * @param taskIndexNumber Index of task to be edited
     * @param updatedTaskDetails input command String in the form of (tasks to be edited can be in any order)
     *                           edit task i/TASK_INDEX [n/TASK_NAME] [p/TASK_PRIORITY]
     *                           [d/TASK_DUEDATE] [c/TASK_CREDIT] [s/STATE]
     * @return String array containing messages to the user to be printed.
     */
    public String[] editTask(int taskIndexNumber, String updatedTaskDetails) {
        ArrayList<String> taskDetails = parserHelper.parseTaskDetails(updatedTaskDetails);
        String taskName = taskDetails.get(0);
        ArrayList<String> messagesForUser = new ArrayList<>();
        ArrayList<String> successMessages = new ArrayList<>();
        ArrayList<String> errorMessages = new ArrayList<>(parserHelper.getErrorMessages());

        Task task = taskList.get(taskIndexNumber - 1);
        if (!("--".equals(taskName))) {
            task.setTaskName(taskName);
            successMessages.add("The name of this task has been changed to '" + taskName + "'!");
        }
        String taskPriority = taskDetails.get(1);
        if (!("-1".equals(taskPriority))) {
            try {
                int newTaskPriority = Integer.parseInt(taskPriority);
                if (newTaskPriority < 1 || newTaskPriority > 5) {
                    errorMessages.add("Task priority should only be from 1 to 5!");
                } else {
                    task.setTaskPriority(newTaskPriority);
                    successMessages.add("The priority for this task has been set to " + newTaskPriority + "!");
                }
            } catch (NumberFormatException e) {
                errorMessages.add("Input for new task priority is not a number!");
            }
        }
        String taskDueDate = taskDetails.get(2);
        if (taskDueDate != null) {
            try {
                Date newDueDate = dateTimeHelper.formatDate(taskDueDate);
                task.setDueDate(newDueDate);
                successMessages.add("The new due date for this task has been set to "
                        + dateTimeHelper.formatDateForDisplay(newDueDate) + "!");
            } catch (ParseException e) {
                errorMessages.add("Input for new task due date is invalid! Please input the date in the "
                        + "form 'dd/mm/yyyy'.");
            }
        }
        String taskCredit = taskDetails.get(3);
        if (!("-1".equals(taskCredit))) {
            try {
                int newTaskCredit = Integer.parseInt(taskCredit);
                if (newTaskCredit < 1 || newTaskCredit > 100) {
                    errorMessages.add("Credits for a task should only be from 1 to 100!");
                } else {
                    task.setTaskCredit(newTaskCredit);
                    successMessages.add("The credit for this task has been set to " + newTaskCredit + "!");
                }
            } catch (NumberFormatException e) {
                errorMessages.add("Input for new task credit is not a number!");
            }

        }
        String taskState = taskDetails.get(4);
        if (!("NONE".equals(taskState))) {
            String newTaskStateLowerCase = taskState.toLowerCase();
            if ("open".equals(taskState) || "doing".equals(taskState)
                    || "done".equals(taskState) || "todo".equals(taskState)) {
                task.setTaskState(newTaskStateLowerCase);
                successMessages.add("The state of this task has been set to " + taskState.toUpperCase() + "!");
            } else {
                errorMessages.add("There are only 4 possible states of a task: 'open', 'todo', 'doing' and 'done'. "
                        + "Please give a valid state!");
            }
        }

        if (successMessages.size() != 0) {
            messagesForUser.add("Success!");
            messagesForUser.addAll(successMessages);
            if (errorMessages.size() != 0) {
                messagesForUser.add("");
                messagesForUser.add("Errors...");
                messagesForUser.addAll(errorMessages);
            }
        } else {
            messagesForUser.add("Errors...");
            messagesForUser.addAll(errorMessages);
        }

        return messagesForUser.toArray(new String[0]);
    }

    public int getSize() {
        return this.taskList.size();
    }

    /**
     * Edits the task requirements of a specific task by adding or removing them.
     * @param taskIndexNumber Index of task to be edited.
     * @param updatedTaskRequirements Array containing indexes of task requirements to be removed and
     *                                task requirements to be added.
     * @return String array of success and error messages to be printed.
     */
    public String[] editTaskRequirements(int taskIndexNumber, String updatedTaskRequirements) {
        ArrayList<String> successMessages = new ArrayList<>();
        successMessages.add("Success!");
        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add("Errors...");

        ArrayList<String> newTaskRequirementDetails = parserHelper.parseTaskRequirementDetails(updatedTaskRequirements);
        errorMessages.addAll(parserHelper.getErrorMessages());

        String taskReqIndexesToBeRemoved = newTaskRequirementDetails.get(0);
        if (!taskReqIndexesToBeRemoved.equals("--")) {
            ArrayList<String> results = removeTaskRequirements(taskIndexNumber, taskReqIndexesToBeRemoved);
            if (!results.contains("error start")) {
                successMessages.addAll(results);
            } else {
                while (!"error start".equals(results.get(0))) {
                    successMessages.add(results.get(0));
                    results.remove(0);
                }
                results.remove(0); //remove "error start"
                errorMessages.addAll(results);
            }
        }
        newTaskRequirementDetails.remove(0);

        if (newTaskRequirementDetails.size() != 0) {
            successMessages.addAll(addTaskRequirements(taskIndexNumber, newTaskRequirementDetails));
        }

        //combining success messages and error messages into one array
        if (successMessages.size() == 1) {
            successMessages.remove(0);
        } else {
            successMessages.add("");
        }
        if (errorMessages.size() == 1) {
            successMessages.add("There were no errors!");
        } else {
            successMessages.addAll(errorMessages);
        }

        return successMessages.toArray(new String[0]);
    }

    /**
     * Removes task requirement with input index.
     * @param taskIndexNumber Index of task to be edited.
     * @param taskReqIndexesToBeRemoved String containing indexes of task requirements to be removed.
     * @return String array of success and error messages
     */
    private ArrayList<String> removeTaskRequirements(int taskIndexNumber, String taskReqIndexesToBeRemoved) {
        String[] indexesToBeRemoved = taskReqIndexesToBeRemoved.split(" ");
        ArrayList<String> successMessages = new ArrayList<>();
        ArrayList<String> errorMessagesForInvalidIndexes = new ArrayList<>();
        Arrays.sort(indexesToBeRemoved);
        ArrayList<Integer> listOfUsedIntegers = new ArrayList<>();
        int startingNumOfTaskRequirements = this.taskList.get(taskIndexNumber - 1).getNumOfTaskRequirements();
        for (int i = indexesToBeRemoved.length - 1; i >= 0; i--) {
            try {
                int indexToBeRemoved = Integer.parseInt(indexesToBeRemoved[i]);
                if (indexToBeRemoved > 0
                        && indexToBeRemoved <= startingNumOfTaskRequirements) {
                    if (!listOfUsedIntegers.contains(indexToBeRemoved)) {
                        successMessages.add("'"
                                + this.taskList.get(taskIndexNumber - 1).getTaskRequirements().get(indexToBeRemoved)
                                .substring(3)
                                + "' is no longer a requirement of this task!");
                        listOfUsedIntegers.add(indexToBeRemoved);
                        this.taskList.get(taskIndexNumber - 1).removeTaskRequirement(indexToBeRemoved);
                    } else {
                        errorMessagesForInvalidIndexes.add("Index '" + indexToBeRemoved + "' was repeated!");
                    }
                } else {
                    errorMessagesForInvalidIndexes.add("'" + indexToBeRemoved
                            + "' is not a valid task requirement index!");
                }
            } catch (NumberFormatException e) {
                errorMessagesForInvalidIndexes.add("'" + indexesToBeRemoved[i] + "' is not a number!");
            }
        }

        successMessages.add("error start");
        successMessages.addAll(errorMessagesForInvalidIndexes);
        return successMessages;
    }

    /**
     * Adds new task requirements to a specific task.
     * @param taskIndexNumber Index of task to be edited.
     * @param newTaskRequirements Array containing indexes of task requirements to be added.
     * @return Arraylist of String containing success or error messages
     *
     */
    private ArrayList<String> addTaskRequirements(int taskIndexNumber, ArrayList<String> newTaskRequirements) {
        ArrayList<String> successMessages = new ArrayList<>();
        for (String s : newTaskRequirements) {
            this.taskList.get(taskIndexNumber - 1).addTaskRequirement(s);
            successMessages.add("'" + s + "' has been successfully added as a new requirement of this task!");
        }
        return successMessages;
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
