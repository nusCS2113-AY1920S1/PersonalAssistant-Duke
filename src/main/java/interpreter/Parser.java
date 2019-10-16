package interpreter;

import executor.command.Command;
import executor.command.CommandType;
import executor.command.CommandSchedule;
import executor.command.CommandBlank;
import executor.command.CommandBye;
import executor.command.CommandNewTask;
import executor.command.CommandFind;
import executor.command.CommandDelete;
import executor.command.CommandMarkDone;
import executor.command.CommandList;
import executor.command.CommandQueue;
import executor.command.CommandReminder;

import executor.task.Task;
import executor.task.TaskType;

public class Parser {

    public static final String PARSE_MARKER_IS_DONE = "####";
    public static final String PARSE_MARKER_TASK = "-->>";

    /**
     * Constructor for 'Parser' Class.
     */
    public Parser() {
    }

    /**
     * Abstract Function that searches for a specific Enum type given an Enum.
     *
     * @param userInput The String to search
     * @param enumTypes String Array containing the Enum Types
     * @return String value of the Enum Type
     */
    private static String parseForEnum(String userInput, String[] enumTypes) {
        int minIndex = 999;
        int testIndex;
        String enumType = "";
        for (String typeIter : enumTypes) {
            testIndex = userInput.toUpperCase().indexOf(typeIter);
            if (minIndex > testIndex && testIndex >= 0) {
                enumType = typeIter;
                minIndex = testIndex;
            }
        }
        return enumType;
    }

    /**
     * Parses the input to classify the Command requested.
     *
     * @param userInput The input that the user types from the CLI
     * @return CommandType enum that identifies the Command requested
     */
    public static CommandType parseCommandType(String userInput) {
        //user enter an empty command ( like a blank )
        if (userInput.trim() == "") {
            return CommandType.BLANK;
        }
        String commandStr = parseForEnum(userInput, CommandType.getNames());
        // Default
        // type of command not as specified inside the enum types
        if (commandStr == "") {
            return CommandType.TASK;
        }
        return CommandType.valueOf(commandStr);
    }

    /**
     * Parses the input to decide what 'Task' subclass is requested.
     *
     * @param userInput The input that the user types from the CLI
     * @return taskType TaskType enum that specifies the subclass to create
     */
    public static TaskType parseTaskType(String userInput) {
        String taskStr = parseForEnum(userInput, TaskType.getNames());
        // Default
        if (taskStr == "") {
            return TaskType.BLANK;
        }
        return TaskType.valueOf(taskStr);
    }

    /**
     * Removes the Command Literal from the user input string.*
     * @param commandStr Command Literal to remove
     * @param userInput  User Input to be parsed
     * @return String with command literal removed
     */
    public static String removeStr(String commandStr, String userInput) {
        //(?i) is regex which tells Java to be case-Insensitive
        return userInput.replaceFirst("(?i)" + commandStr, "").trim();
    }

    /** Work in progress.
    public String[] parseTask(TaskType taskType, String userInput) {
    // TODO: Currently in 'Task' RecordTaskDetails
    }
     */

    // -- Storage-Parsing
    public static String[] parseStoredTask(String encodedTask) {
        return encodedTask.split(PARSE_MARKER_TASK);
    }

    /**
     * Parses through the Stored task to return the Task type, Task Description and Task isDone as a String Array.
     * ONLY FOR FORMATTED INPUT
     *
     * @param taskString User Input to be parsed
     * @return String Array containing task type, task description and task isDone
     */
    public static String[] parseStoredTaskDetails(String taskString) {
        String[] returnArray = new String[3];
        String[] holder = taskString.split(PARSE_MARKER_IS_DONE, 2);

        returnArray[0] = String.valueOf(parseTaskType(holder[0]));
        returnArray[1] = holder[0].replace(returnArray[0], "").trim();
        returnArray[2] = holder[1].replace(PARSE_MARKER_IS_DONE.substring(1), "").trim();
        return returnArray;
    }

    /**
     * Encodes the Task for Storage.
     * FORMAT: (taskType) (taskName)/(detailDesc) (taskDetails)(PARSE_MARKER_TASK)(isDone)(PARSE_MARKER_TASK)(...)
     * @param task Task Object
     * @return String to be stored/saved
     */
    public static String encodeTask(Task task) {
        StringBuilder strSave = new StringBuilder();
        strSave.append(encodeMainTask(task));
        strSave.append(encodeQueuedTasks(task));
        strSave.append("\n");
        return strSave.toString();
    }

    private static String encodeMainTask(Task task) {
        String strSave = "";
        strSave += task.getTaskType().name()
                + " "
                + task.getTaskName();
        if (task.getDetailDesc() != null && task.getTaskDetails() != null) {
            strSave += '/';
        }
        if (task.getDetailDesc() != null) {
            strSave += task.getDetailDesc();
        }
        strSave += " ";
        if (task.getTaskDetails() != null) {
            strSave += task.getTaskDetails();
        }
        strSave += PARSE_MARKER_IS_DONE + task.getIsDone().toString();
        return strSave;
    }

    private static String encodeQueuedTasks(Task task) {
        if (!task.isQueuedTasks()) {
            return "";
        }
        StringBuilder queuedTaskString = new StringBuilder();
        for (Task queuedTask : task.getQueuedTasks().getList()) {
            queuedTaskString.append(PARSE_MARKER_TASK);
            queuedTaskString.append(encodeTask(queuedTask));
        }
        return queuedTaskString.toString();
    }

    /**
     * Boolean to check if the user input has a slash.
     * @param userInput this is the user input
     * @return true if there is a slash in the user input
     */
    public static Boolean checkSlash(String userInput) {
        if (userInput.contains("/")) {
            return true;
        }
        return false;
    }
}
