package interpreter;

import executor.command.CommandType;

import executor.task.Task;

/**
 * Parser will parse through user inputs.
 * User inputs will follow the following Structure:
 * "CommandType PrimaryInput /flag1 flag1Details /flag2 flag2Details ..."
 * Parser will NOT return a changed user input.
 */
public class Parser {

    public static final String PARSE_MARKER_IS_DONE = "####";
    public static final String PARSE_MARKER_TASK = "-->>";

    /**
     * Constructor for 'Parser' Class.
     */
    public Parser() {
    }

    /**
     * Parses the input to classify the Command requested.
     * @param userInput The input that the user types from the CLI
     * @return CommandType enum that identifies the Command requested
     */
    public static CommandType parseForCommandType(String userInput) {
        // User enters an empty command (like a blank)
        if (userInput.trim() == "") {
            return CommandType.BLANK;
        }
        String commandStr = parseForEnum(userInput, CommandType.getNames());
        // Type of command not as specified inside the enum types
        if (commandStr == "") {
            return null;
        }
        return CommandType.valueOf(commandStr);
    }

    /**
     * Parses the input to extract the primary input of the user-command.
     * @param commandType CommandType of the user-command being parsed
     * @param userInput String representing the user-command
     * @return String representing the PrimaryInput of the user-command
     */
    public static String parseForPrimaryInput(CommandType commandType, String userInput) {
        String primaryInput = Parser.removeStr(commandType.toString(), userInput);
        if (primaryInput.contains("/")) {
            primaryInput = primaryInput.substring(0, primaryInput.indexOf("/")).trim();
        }
        return primaryInput;
    }

    /**
     * Parses the input to extract a particular flag from the user-command.
     * @param flag String denoting the flag to be found
     * @param userInput String representing the user-command
     * @return String representing the flagDetails if flag is found, null otherwise
     */
    public static String parseForFlag(String flag, String userInput) {
        flag = "/" + flag;
        if (!userInput.contains(flag)) {
            return null;
        }
        int indexFlag = userInput.indexOf(flag) + flag.length();
        int indexEndFlag = userInput.indexOf("/", indexFlag);
        if (indexEndFlag == -1) {
            indexEndFlag = userInput.length();
        }
        return userInput.substring(indexFlag, indexEndFlag).trim();
    }

    /**
     * Abstract Function that searches for a specific Enum type given an Enum.
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

        returnArray[0] = String.valueOf(parseForCommandType(holder[0]));
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
    public static Boolean containsForwardSlash(String userInput) {
        if (userInput.contains("/")) {
            return true;
        }
        return false;
    }
}
