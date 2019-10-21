package util.factories;

import models.task.ITask;
import models.task.NullTask;
import models.task.Task;
import models.task.TaskState;
import util.ParserHelper;
import util.date.DateTimeHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class TaskFactory {
    private ParserHelper parserHelper;
    private DateTimeHelper dateTimeHelper;

    public TaskFactory() {
        parserHelper = new ParserHelper();
        dateTimeHelper = new DateTimeHelper();
    }

    /**
     * Method to create a new task and put it into a list.
     * @param input Input containing the information about the task and priority.
     * @return Task as an object
     */
    public ITask createTask(String input) throws ParseException {
        if (!input.contains("t/") || !input.contains("p/") || !input.contains("c/")) {
            return new NullTask();
        }

        String [] taskDetails = input.split("[r]\\/");
        ArrayList<String> taskRequirements = new ArrayList<>();
        if (taskDetails.length > 1) {
            taskRequirements = parseTaskRequirements(taskDetails,1);
        }

        ArrayList<String> newTaskDetails = parserHelper.parseTaskDetails(taskDetails[0]);
        String newTaskName = newTaskDetails.get(0);
        int newTaskPriority = Integer.parseInt(newTaskDetails.get(1));
        Date newTaskDate = null;
        if (newTaskDetails.get(2) != null) {
            newTaskDate = dateTimeHelper.formatDate(newTaskDetails.get(2));
        }
        int newTaskCredit = Integer.parseInt(newTaskDetails.get(3));
        TaskState newTaskState = TaskState.OPEN;
        if (!("NONE".equals(newTaskDetails.get(4)))) {
            newTaskState = convertStringToTaskState(newTaskDetails.get(4));
        }
        return new Task(newTaskName, newTaskPriority, newTaskDate, newTaskCredit, newTaskState, taskRequirements);
    }

    /**
     * Creates ArrayList of Strings containing task requirements.
     * @param taskDetails  Details of new task.
     * @param startIndexOfTaskRequirements index from which the content in taskDetails will be task requirements.
     * @return ArrayList of type String containing task requirements.
     */
    private ArrayList<String> parseTaskRequirements(String[] taskDetails, int startIndexOfTaskRequirements) {
        ArrayList<String> taskRequirements = new ArrayList<>();
        for (int i = startIndexOfTaskRequirements; i < taskDetails.length; i++) {
            taskRequirements.add(taskDetails[i].trim());
        }
        return taskRequirements;
    }

    /**
     * Converts string input for state into enum TaskState object.
     * @param inputState String input of state.
     * @return enum TaskState object.
     */
    private TaskState convertStringToTaskState(String inputState) {
        String inputStateLowerCase = inputState.toLowerCase();
        switch (inputStateLowerCase) {
        case "done":
            return TaskState.DONE;
        case "todo":
            return TaskState.TODO;
        case "doing":
            return TaskState.DOING;
        default:
            return TaskState.OPEN;
        }
    }
}
