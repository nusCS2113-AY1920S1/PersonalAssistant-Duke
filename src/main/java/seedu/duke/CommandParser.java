package seedu.duke;

import seedu.duke.common.command.HelpCommand;
import seedu.duke.email.command.EmailTagCommand;
import seedu.duke.task.TaskList;
import seedu.duke.task.command.TaskAddCommand;
import seedu.duke.common.command.Command;
import seedu.duke.common.command.Command.Option;
import seedu.duke.common.command.ExitCommand;
import seedu.duke.common.command.FlipCommand;
import seedu.duke.common.command.InvalidCommand;
import seedu.duke.email.EmailList;
import seedu.duke.email.command.EmailFetchCommand;
import seedu.duke.email.command.EmailListCommand;
import seedu.duke.email.command.EmailShowCommand;
import seedu.duke.task.command.TaskDeleteCommand;
import seedu.duke.task.command.TaskDoAfterCommand;
import seedu.duke.task.command.TaskDoneCommand;
import seedu.duke.task.command.TaskFindCommand;
import seedu.duke.task.command.TaskListCommand;
import seedu.duke.task.command.TaskReminderCommand;
import seedu.duke.task.command.TaskSetPriorityCommand;
import seedu.duke.task.command.TaskSnoozeCommand;
import seedu.duke.task.command.TaskUpdateCommand;
import seedu.duke.task.entity.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that contains helper functions used to process user inputs. It also contains UserInputException
 * that is used across the project to handle the unexpected user input.
 */
public class CommandParser {

    private static UI ui = Duke.getUI();

    /**
     * Two types of input, prefix will be displayed according to this in the userInput text field.
     */
    public enum InputType {
        TASK,
        EMAIL
    }

    private static InputType inputType;

    /**
     * Constructor that initializes the input type to TASK.
     */
    public CommandParser() {
        this.inputType = InputType.TASK;    // default input type when initiating the program.
    }

    /**
     * Checks if input command is in the correct format.
     *
     * @param commandString input command.
     * @return true if matches pattern, false otherwise.
     */
    public static boolean isCommandFormat(String commandString) {
        return commandString.matches(
                "(?:task|email\\s)(?:\\s*([\\w]+)[\\s|\\w]*)(?:\\s+"
                        + "(-[\\w]+\\s+[\\w]+[\\s|\\w/]*))*");
    }

    /**
     * Get input prefix for userInput text field in GUI.
     *
     * @return current prefix.
     */
    public static String getInputPrefix() {
        String prefix = "";
        switch (inputType) {
        case TASK:
            prefix = "task ";
            break;
        case EMAIL:
            prefix = "email ";
            break;
        default:
            prefix = "";
        }
        return prefix;
    }

    /**
     * Set to the new input type when it is toggled by "flip" command.
     *
     * @param newInputType the input type that is going to be changed to.
     */
    public static void setInputType(InputType newInputType) {
        inputType = newInputType;
    }

    /**
     * Parses input to retrieve options from command string.
     *
     * @param input command string.
     * @return list of all options specified in the command.
     */
    public static ArrayList<Option> parseOptions(String input) {
        ArrayList<Option> optionList = new ArrayList<>();
        Pattern optionPattern = Pattern.compile(".*(?<key>-[\\w]+)\\s+(?<value>[\\w]+[\\s|\\w/]*)\\s*");
        Matcher optionMatcher = optionPattern.matcher(input);
        while (optionMatcher.matches()) {
            optionList.add(new Option(optionMatcher.group("key").substring(1),
                    optionMatcher.group("value")));
            input = input.replaceAll("\\s*(?<key>-[\\w]+)\\s+(?<value>[\\w]+[\\s|\\w/]*)\\s*$", "");
            optionMatcher = optionPattern.matcher(input);
        }
        return optionList;
    }

    public static String stripOptions(String input) {
        return input.replaceAll("\\s*(?<key>-[\\w]+)\\s+(?<value>[\\w]+[\\s|\\w/]*)\\s*", "");
    }

    /**
     * Parses the user/file input as command. It returns a command that is not yet executed. It also needs to
     * get a UI from Duke to display the messages.
     *
     * @param input the user/file input that is to be parsed to a command
     * @return the parse result, which is a command ready to be executed
     */
    public static Command parseCommand(String input) throws UserInputException {
        TaskList taskList = Duke.getTaskList();
        EmailList emailList = Duke.getEmailList();
        if (!isCommandFormat(input)) {
            if (ui != null) {
                ui.showError("Command is in wrong format");
            }
            return new InvalidCommand();
        }
        ArrayList<Option> optionList = parseOptions(input);
        input = stripOptions(input);
        if (inputType == InputType.TASK) {
            return parseTaskCommand(input, taskList, optionList);
        } else if (inputType == InputType.EMAIL) {
            try {
                return parseEmailCommand(emailList, input, optionList);
            } catch (UserInputException e) {
                ui.showError(e.getMessage());
                return new InvalidCommand();
            }
        } else {
            return new InvalidCommand();
        }
    }

    private static Command parseTaskCommand(String rawInput, TaskList taskList,
                                            ArrayList<Option> optionList) {
        if (rawInput.length() <= 5) {
            return new InvalidCommand();
            //return new HelpTaskCommand();
        }
        String input = rawInput.split("task ", 2)[1].strip();
        if (input.equals("flip")) {
            return new FlipCommand(inputType);
        } else if (input.equals("bye")) {
            return new ExitCommand();
        } else if (input.equals("list")) {
            return new TaskListCommand(taskList);
        } else if (input.equals("help")) {
            return new HelpCommand();
        } else if (input.startsWith("done")) {
            return parseDoneCommand(input, optionList);
        } else if (input.startsWith("delete")) {
            return parseDeleteCommand(input, taskList, optionList);
        } else if (input.startsWith("find")) {
            return parseFindCommand(input, taskList, optionList);
        } else if (input.startsWith("reminder")) {
            return parseReminderCommand(input, taskList, optionList);
        } else if (input.startsWith("doafter")) {
            return parseDoAfterCommand(input, taskList, optionList);
        } else if (input.startsWith("snooze")) {
            return parseSnoozeCommand(input, taskList, optionList);
        } else if (input.startsWith("todo") | input.startsWith("deadline") | input.startsWith("event")) {
            return parseAddTaskCommand(taskList, input, optionList);
        } else if (input.startsWith("update")) {
            return parseUpdateCommand(taskList, input, optionList);
        } else if (input.startsWith("set")) {
            return parsePriorityCommand(input, taskList, optionList);
        }
        return new InvalidCommand();
    }

    /**
     * Parses the specific part of a user/file input that is relevant to email. A successful parsing always
     * returns an email-relevant Command.
     *
     * @param emailList target email list from Duke.
     * @param rawInput  user/file input ready to be parsed.
     * @return an email-relevant Command.
     * @throws UserInputException an exception when the parsing is failed, probably due to the wrong format of
     *                            input.
     */
    public static Command parseEmailCommand(EmailList emailList, String rawInput,
                                            ArrayList<Option> optionList) throws UserInputException {
        if (rawInput.length() <= 6) {
            return new InvalidCommand();
        }
        String input = rawInput.substring(6).strip();
        String emailCommand = input.split(" ")[0];
        switch (emailCommand) {
        case "flip":
            return new FlipCommand(inputType);
        case "bye":
            return new ExitCommand();
        case "help":
            return new HelpCommand();
        case "list":
            return new EmailListCommand(emailList);
        case "show":
            return parseShowEmailCommand(emailList, input);
        case "fetch":
            return new EmailFetchCommand(emailList);
        case "update":
            return parseEmailTagCommand(emailList, optionList, input);
        default:
            throw new CommandParser.UserInputException("OOPS!!! Enter \'email help\' to get list of methods for "
                    + "email.");
        }
    }


    private static Command parseEmailTagCommand(EmailList emailList, ArrayList<Option> optionList,
                                                String input) throws UserInputException {
        Pattern emailTagCommandPattern = Pattern.compile("^update\\s+(?<index>\\d+)\\s*$");
        Matcher emailTagCommandMatcher = emailTagCommandPattern.matcher(input);
        if (!emailTagCommandMatcher.matches()) {
            if (ui != null) {
                ui.showError("Please enter a valid email index after \'update\'");
            }
            return new InvalidCommand();
        }
        ArrayList<String> tags = extractTags(optionList);
        if (tags.size() == 0) {
            if (ui != null) {
                ui.showError("Please enter a tag name after \'-tag\' option");
            }
            return new InvalidCommand();
        }
        try {
            int index = parseEmailIndex(emailTagCommandMatcher.group("index"), emailList);
            return new EmailTagCommand(emailList, index, tags);
        } catch (UserInputException e) {
            throw new UserInputException(e.toString());
        }
    }

    private static int parseEmailIndex(String input, EmailList emailList) throws UserInputException {
        int index = Integer.parseInt(input) - 1;
        if (index < 0 || index >= emailList.size()) {
            throw new CommandParser.UserInputException("Invalid index");
        }
        return index;
    }

    private static Command parseShowEmailCommand(EmailList emailList, String input) throws UserInputException {
        Pattern showCommandPattern = Pattern.compile("^show\\s+(?<index>\\d+)\\s*$");
        Matcher showCommandMatcher = showCommandPattern.matcher(input);
        if (!showCommandMatcher.matches()) {
            if (ui != null) {
                ui.showError("Please enter a valid index of task after \'show\'");
            }
            return new InvalidCommand();
        }
        try {
            int index = parseEmailIndex(showCommandMatcher.group("index"), emailList);
            return new EmailShowCommand(emailList, index);
        } catch (UserInputException e) {
            throw new UserInputException(e.toString());
        }
    }

    private static Command parseDoneCommand(String input, ArrayList<Option> optionList) {
        Pattern doneCommandPattern = Pattern.compile("^done\\s+(?<index>\\d+)\\s*$");
        Matcher doneCommandMatcher = doneCommandPattern.matcher(input);
        if (!doneCommandMatcher.matches()) {
            if (ui != null) {
                ui.showError("Please enter a valid index of task after \'done\'");
            }
            return new InvalidCommand();
        }
        try {
            int index = parseIndex(doneCommandMatcher.group("index"));
            return new TaskDoneCommand(index);
        } catch (NumberFormatException e) {
            if (ui != null) {
                ui.showError("Please enter correct task index: " + doneCommandMatcher.group(
                        "index"));
            }
        }
        return new InvalidCommand();
    }

    private static Command parseDeleteCommand(String input, TaskList taskList, ArrayList<Option> optionList) {
        Pattern deleteCommandPattern = Pattern.compile("^delete\\s+(?<index>\\d+)\\s*$");
        Matcher deleteCommandMatcher = deleteCommandPattern.matcher(input);
        if (!deleteCommandMatcher.matches()) {
            if (ui != null) {
                ui.showError("Please enter a valid index of task after \'delete\'");
            }
            return new InvalidCommand();
        } else {
            try {
                int index = parseIndex(deleteCommandMatcher.group("index"));
                return new TaskDeleteCommand(taskList, index);
            } catch (NumberFormatException e) {
                if (ui != null) {
                    ui.showError("Please enter correct task index: " + deleteCommandMatcher.group(
                            "index"));
                }
            }
        }
        return new InvalidCommand();
    }

    private static int parseIndex(String input) throws NumberFormatException {
        int index = Integer.parseInt(input) - 1;
        if (index < 0) {
            throw new NumberFormatException();
        }
        return index;
    }

    private static Command parseFindCommand(String input, TaskList taskList, ArrayList<Option> optionList) {
        Pattern findCommandPattern = Pattern.compile("^find\\s+(?<keyword>[\\w]+[\\s|\\w]*)\\s*$");
        Matcher findCommandMatcher = findCommandPattern.matcher(input);
        if (!findCommandMatcher.matches()) {
            if (ui != null) {
                ui.showError("Please enter keyword for searching after \'find\'");
            }
        } else {
            String keyword = findCommandMatcher.group("keyword").strip();
            return new TaskFindCommand(taskList, keyword);
        }
        return new InvalidCommand();
    }

    private static Command parseReminderCommand(String input, TaskList taskList, ArrayList<Option> optionList) {
        Pattern reminderCommandPattern = Pattern.compile("^reminder(?:\\s+(?<dayLimit>[\\d]*)\\s*)?");
        Matcher reminderCommandMatcher = reminderCommandPattern.matcher(input);
        if (!reminderCommandMatcher.matches()) {
            if (ui != null) {
                ui.showError("Please enter reminder with or without a number, which is the maximum number "
                        + "of days from now for a task to be considered as near");
            }
            return new InvalidCommand();
        }
        int dayLimit = -1;
        try {
            dayLimit = Integer.parseInt(reminderCommandMatcher.group("dayLimit"));
        } catch (NumberFormatException e) {
            if (ui != null) {
                ui.showError("Reminder day limit in wrong format. Default is used.");
            }
            return new TaskReminderCommand(taskList);
        }
        if (dayLimit < 0) {
            if (ui != null) {
                ui.showError("Reminder day limit cannot be negative. Default is used.");
            }
            return new TaskReminderCommand(taskList);
        } else {
            return new TaskReminderCommand(taskList, dayLimit);
        }
    }

    private static Command parseDoAfterCommand(String input, TaskList taskList, ArrayList<Option> optionList) {
        Pattern doAfterCommandPattern = Pattern.compile("^do[a|A]fter\\s+(?<index>[\\d]+)\\s*$");
        Matcher doAfterCommandMatcher = doAfterCommandPattern.matcher(input);
        if (!doAfterCommandMatcher.matches()) {
            if (ui != null) {
                ui.showError("Please enter doAfter command in the correct format with index and description"
                        + " in -msg option");
            }
            return new InvalidCommand();
        }
        String description = "";
        for (Option option : optionList) {
            if (option.getKey().equals("msg")) {
                description = option.getValue();
                break;
            }
        }
        if (description.equals("")) {
            if (ui != null) {
                ui.showError("Please enter a description of doAfter command after \'-msg \' option");
            }
            return new InvalidCommand();
        }
        try {
            int index = parseIndex(doAfterCommandMatcher.group("index"));
            return new TaskDoAfterCommand(taskList, index, description);
        } catch (NumberFormatException e) {
            if (ui != null) {
                ui.showError("Please enter a valid task index after \'doAfter\'");
            }
            return new InvalidCommand();
        }
    }

    private static Command parsePriorityCommand(String input, TaskList taskList, ArrayList<Option> optionList) {
        Pattern priorityCommandPattern = Pattern.compile("^set\\s+(?<index>[\\d]+)\\s*$");
        Matcher priorityCommandMatcher = priorityCommandPattern.matcher(input);
        if (!priorityCommandMatcher.matches()) {
            if (ui != null) {
                ui.showError("Please enter index after 'set' command and priority level after '-priority' option");
            }
            return new InvalidCommand();
        }
        String priority = "";
        for (Option option : optionList) {
            if (option.getKey().equals("priority")) {
                priority = option.getValue();
                break;
            }
        }
        if (priority.equals("")) {
            if (ui != null) {
                ui.showError("Please enter a priority level to set for the task after \'-priority\' option");
            }
            return new InvalidCommand();
        }
        try {
            int index = parseIndex(priorityCommandMatcher.group("index"));
            return new TaskSetPriorityCommand(taskList, index, priority);
        } catch (NumberFormatException e) {
            if (ui != null) {
                ui.showError("Please enter a valid task index after \'set\'");
            }
            return new InvalidCommand();
        }
    }

    private static Command parseSnoozeCommand(String input, TaskList taskList, ArrayList<Option> optionList) {
        Pattern snoozeCommandPattern = Pattern.compile("^snooze\\s+(?<index>[\\d]+)\\s*");
        Matcher snoozeCommandMatcher = snoozeCommandPattern.matcher(input);
        if (!snoozeCommandMatcher.matches()) {
            if (ui != null) {
                ui.showError("Please enter snooze command with an index");
            }
            return new InvalidCommand();
        }
        try {
            int index = parseIndex(snoozeCommandMatcher.group("index"));
            return new TaskSnoozeCommand(taskList, index);
        } catch (NumberFormatException e) {
            if (ui != null) {
                ui.showError("Please enter a valid task index");
            }
            return new InvalidCommand();
        }
    }

    /**
     * Parses the specific part of a user/file input that is relevant to a task. A successful parsing always
     * returns an AddCommand, as it is assumed that an input starting with a task name is an add command.
     *
     * @param taskList   target task list to which the new task is to be added to
     * @param input      user/file input ready to be parsed
     * @param optionList contains all options specified in input command
     * @return an AddCommand of the task parsed from the input
     * @throws UserInputException an exception when the parsing is failed, probably due to the wrong format of
     *                            input
     */
    public static Command parseAddTaskCommand(TaskList taskList, String input,
                                              ArrayList<Option> optionList) {
        LocalDateTime time;
        String doAfter;
        try {
            doAfter = extractDoAfter(optionList);
        } catch (UserInputException e) {
            if (ui != null) {
                ui.showError(e.getMessage());
            }
            return new InvalidCommand();
        }
        try {
            String timeString = extractTime(optionList);
            String day = null;
            String timing = null;
            if (!timeString.contains("/") && !timeString.isEmpty()) {
                timeString = timeString.substring(0, 1).toUpperCase() + timeString.substring(1).toLowerCase();
                if (timeString.contains(" ")) {
                    String[] tokens = timeString.split("\\s+", 3);
                    day = tokens[0];
                    timing = tokens[1];
                } else {
                    day = timeString;
                }
            }
            if (Task.isCorrectNaturalDate(day)) {
                time = Task.convertNaturalDate(day, timing);
            } else {
                time = Task.parseDate(timeString);
            }
        } catch (UserInputException e) {
            time = null; //todo can tolerate a null time, but not event and deadline
        }
        ArrayList<String> tags = extractTags(optionList);
        String priority;
        try {
            priority = extractPriority(optionList);
        } catch (UserInputException e) {
            if (ui != null) {
                ui.showError(e.getMessage());
            }
            return new InvalidCommand();
        }
        if (input.startsWith("todo")) {
            return parseAddToDoCommand(taskList, input, doAfter, tags, priority);
        } else if (input.startsWith("deadline")) {
            return parseAddDeadlineCommand(taskList, input, time, doAfter, tags, priority);
        } else if (input.startsWith("event")) {
            return parseEventCommand(taskList, input, time, doAfter, tags, priority);
        } else {
            return new InvalidCommand();
        }
    }

    private static Command parseAddToDoCommand(TaskList taskList, String input, String doAfter,
                                               ArrayList<String> tags, String priority) {
        Task.TaskType taskType = Task.TaskType.ToDo;
        Pattern toDoPattern = Pattern.compile("todo\\s+(?<name>\\w+[\\s+\\w+]*)\\s*");
        Matcher toDoMatcher = toDoPattern.matcher(input);
        if (!toDoMatcher.matches()) {
            if (ui != null) {
                ui.showError("Please enter a name after todo");
            }
            return new InvalidCommand();
        }
        String name = toDoMatcher.group("name");
        return new TaskAddCommand(taskList, taskType, name, null, doAfter, tags, priority);
    }

    private static Command parseAddDeadlineCommand(TaskList taskList, String input,
                                                   LocalDateTime time, String doAfter,
                                                   ArrayList<String> tags, String priority) {
        Task.TaskType taskType = Task.TaskType.Deadline;
        Pattern deadlinePattern = Pattern.compile("deadline\\s+(?<name>\\w+[\\s+\\w+]*)\\s*");
        Matcher deadlineMatcher = deadlinePattern.matcher(input);
        if (!deadlineMatcher.matches()) {
            if (ui != null) {
                ui.showDebug(input);
                ui.showError("Please enter a name after \'deadline\'");
            }
            return new InvalidCommand();
        }
        if (time == null) {
            if (ui != null) {
                ui.showError("Please enter a time of correct format after \'-time\'");
            }
            return new InvalidCommand();
        }
        String name = deadlineMatcher.group("name");
        return new TaskAddCommand(taskList, taskType, name, time, doAfter, tags, priority);
    }

    private static Command parseEventCommand(TaskList taskList, String input, LocalDateTime time,
                                             String doAfter, ArrayList<String> tags, String priority) {
        Task.TaskType taskType = Task.TaskType.Event;
        Pattern eventPattern = Pattern.compile("event\\s+(?<name>\\w+[\\s+\\w+]*)\\s*");
        Matcher eventMatcher = eventPattern.matcher(input);
        if (!eventMatcher.matches()) {
            if (ui != null) {
                ui.showError("Please enter a name after \'event\'");
            }
            return new InvalidCommand();
        }
        if (time == null) {
            if (ui != null) {
                ui.showError("Please enter a time of correct format after \'-time\'");
            }
            return new InvalidCommand();
        }
        String name = eventMatcher.group("name");
        return new TaskAddCommand(taskList, taskType, name, time, doAfter, tags, priority);
    }

    private static Command parseUpdateCommand(TaskList taskList, String input, ArrayList<Option> optionList) {
        ArrayList<TaskUpdateCommand.Attributes> attributes = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();
        int index;
        Pattern editPattern = Pattern.compile("^update\\s+(?<index>\\d+)\\s*$");
        Matcher editMatcher = editPattern.matcher(input);
        if (!editMatcher.matches()) {
            if (ui != null) {
                ui.showError("Please enter an index after \'update\'");
            }
            return new InvalidCommand();
        } else {
            try {
                index = parseIndex(editMatcher.group("index"));
            } catch (NumberFormatException e) {
                if (ui != null) {
                    ui.showError("Please enter correct task index: " + editMatcher.group(
                            "index"));
                }
                return new InvalidCommand();
            }
        }

        try {
            if (!extractTime(optionList).equals("")) {
                descriptions.add(extractTime(optionList));
                attributes.add(TaskUpdateCommand.Attributes.time);
            }
            if (!extractDoAfter(optionList).equals("")) {
                descriptions.add(extractDoAfter(optionList));
                attributes.add(TaskUpdateCommand.Attributes.doAfter);
            }
            if (!extractPriority(optionList).equals("")) {
                descriptions.add(extractPriority(optionList));
                attributes.add(TaskUpdateCommand.Attributes.priority);
            }
            return new TaskUpdateCommand(taskList, index, descriptions, attributes);
        } catch (UserInputException e) {
            return new InvalidCommand();
        }
    }

    private static ArrayList<String> extractTags(ArrayList<Option> optionList) {
        ArrayList<String> tagList = new ArrayList<>();
        for (Option option : optionList) {
            if (option.getKey().equals("tag")) {
                tagList.add(option.getValue().strip());
            }
        }
        return tagList;
    }

    private static String extractDoAfter(ArrayList<Option> optionList) throws UserInputException {
        String doafter = "";
        for (Option option : optionList) {
            if (option.getKey().equals("doafter")) {
                if (doafter == "") {
                    doafter = option.getValue();
                } else {
                    throw new UserInputException("Each task can have only one doafter option");
                }
            }
        }
        return doafter;
    }

    private static String extractTime(ArrayList<Option> optionList) throws UserInputException {
        String time = "";
        for (Option option : optionList) {
            if (option.getKey().equals("time")) {
                if (time == "") {
                    time = option.getValue();
                } else {
                    throw new UserInputException("Each task can have only one time option");
                }
            }
        }
        return time;
    }

    private static String extractPriority(ArrayList<Option> optionList) throws UserInputException {
        String priority = "";
        for (Option option : optionList) {
            if (option.getKey().equals("priority")) {
                if (priority == "") {
                    priority = option.getValue();
                } else {
                    throw new UserInputException("Each task can have only one priority");
                }
            }
        }
        return priority;
    }

    /**
     * An type of exception dedicated to handling the unexpected user/file input. The message contains more
     * specific information.
     */
    public static class UserInputException extends Exception {
        private String msg;

        /**
         * Instantiates the exception with a message, which is ready to be displayed by the UI.
         *
         * @param msg the message that is ready to be displayed by UI.
         */
        public UserInputException(String msg) {
            super();
            this.msg = msg;
        }

        /**
         * Converts the exception ot string by returning its message, so that it can be displayed by the UI.
         *
         * @return the message of the exception
         */
        @Override
        public String getMessage() {
            return msg;
        }
    }
}
