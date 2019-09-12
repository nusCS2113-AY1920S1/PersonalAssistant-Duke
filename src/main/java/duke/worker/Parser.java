package duke.worker;

import duke.command.*;
import duke.task.Task;
import duke.task.TaskType;

public class Parser {

    public static final String parseMarker = "@|@";

    /**
     * Constructor for 'Parser' Class.
     */
    public Parser() {
    }

    // -- User Input Parsing

    /**
     * Parses the user input and returns the Command specified.
     *
     * @param userInput User input from the CLI
     * @return Command subclass
     */
    public static Command parse(String userInput) {
        CommandType commandType;
        commandType = parseCommandType(userInput);
        Command c;
        switch (commandType) {
        case BLANK:
            c = new CommandBlank();
            break;

        case TASK:
            c = new CommandNewTask(userInput);
            break;

        case LIST:
            c = new CommandList();
            break;

        case BYE:
            c = new CommandBye();
            break;

        case DELETE:
            c = new CommandDelete(userInput);
            break;

        case FIND:
            c = new CommandFind(userInput);
            break;

        case DONE:
            c = new CommandMarkDone(userInput);
            break;

        default:
            c = new CommandError();
            break;
        }
        return c;
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
        if (userInput.trim() == "") {
            return CommandType.BLANK;
        }
        String commandStr = parseForEnum(userInput, CommandType.getNames());
        // Default
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
            return TaskType.PARENT;
        }
        return TaskType.valueOf(taskStr);
    }

    /**
     * Removes the Command Literal from the user input string.
     *
     * @param commandStr Command Literal to remove
     * @param userInput  User Input to be parsed
     * @return String with command literal removed
     */
    public static String removeStr(String commandStr, String userInput) {
        //(?i) is regex which tells Java to be case-Insensitive
        return userInput.replaceFirst("(?i)" + commandStr, "").trim();
    }

    /**
     * Parses the user input for the task description given the Task Type
     * @param taskType TaskType enumeration
     * @param userInput User Input to parse
     * @return String[3] that contains taskName, detailDesc and taskDetails.
    public String[] parseTask(TaskType taskType, String userInput) {
    // TODO: Currently in 'Task' RecordTaskDetails
    }
     */

    // -- Storage-Parsing

    /**
     * Parses through the Stored task to return the Task type, Task Description and Task isDone as a String Array.
     * ONLY FOR FORMATTED INPUT
     *
     * @param userInput User Input to be parsed
     * @return String Array containing task type, task description and task isDone
     */
    public static String[] parseStoredTask(String userInput) {
        String[] returnArray = new String[3];
        String[] holder = userInput.split(parseMarker, 2);
        returnArray[0] = String.valueOf(parseTaskType(holder[0]));
        returnArray[1] = holder[0].replace(returnArray[0], "").trim();
        returnArray[2] = holder[1].replace(parseMarker.substring(1), "").trim();
        return returnArray;
    }

    /**
     * Encodes the Task for Storage.
     *
     * @param task Task Object
     * @return String to be stored/saved
     */
    public static String encodeTask(Task task) {
        String strSave = "";
        strSave += task.taskType.name()
                + " "
                + task.taskName;
        if (task.detailDesc != null && task.taskDetails != null) {
            strSave += '/';
        }
        if (task.detailDesc != null) {
            strSave += task.detailDesc;
        }
        strSave += " ";
        if (task.taskDetails != null) {
            strSave += task.taskDetails;
        }
        strSave += parseMarker + task.isDone.toString() + "\n";
        return strSave;
    }
}
