package seedu.duke.task.parser;

import javafx.util.Pair;
import seedu.duke.common.parser.CommandParseHelper;
import seedu.duke.common.command.InvalidCommand;
import seedu.duke.common.command.LinkCommand;
import seedu.duke.common.command.HelpCommand;
import seedu.duke.common.command.Command;
import seedu.duke.common.command.FlipCommand;
import seedu.duke.common.command.ExitCommand;
import seedu.duke.common.model.Model;
import seedu.duke.task.command.TaskAddCommand;
import seedu.duke.task.command.TaskClearListCommand;
import seedu.duke.task.command.TaskDeleteCommand;
import seedu.duke.task.command.TaskDoAfterCommand;
import seedu.duke.task.command.TaskDoneCommand;
import seedu.duke.task.command.TaskFindCommand;
import seedu.duke.task.command.TaskListCommand;
import seedu.duke.task.command.TaskParseNaturalDateHelper;
import seedu.duke.task.command.TaskReminderCommand;
import seedu.duke.task.command.TaskSetPriorityCommand;
import seedu.duke.task.command.TaskSnoozeCommand;
import seedu.duke.task.command.TaskUpdateCommand;
import seedu.duke.task.entity.Task;
import seedu.duke.ui.UI;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.duke.common.parser.CommandParseHelper.extractTags;
import static seedu.duke.common.parser.CommandParseHelper.extractTime;

public class TaskCommandParseHelper {
    private static UI ui = UI.getInstance();

    /**
     * Parses a task command based on user input.
     *
     * @param rawInput   the raw user input without the options
     * @param optionList the options that are extracted from the raw input
     * @return the command parsed
     */
    public static Command parseTaskCommand(String rawInput,
                                           ArrayList<Command.Option> optionList) {
        if (rawInput.length() <= 5) {
            return new InvalidCommand();
        }
        String input = rawInput.split("task ", 2)[1].strip();
        if ("flip".equals(input)) {
            return new FlipCommand();
        } else if ("bye".equals(input)) {
            return new ExitCommand();
        } else if ("list".equals(input)) {
            return new TaskListCommand();
        } else if ("help".equals(input)) {
            return new HelpCommand();
        } else if (input.startsWith("done")) {
            return parseDoneCommand(input);
        } else if (input.startsWith("delete")) {
            return parseDeleteCommand(input);
        } else if (input.startsWith("find")) {
            return parseFindCommand(input);
        } else if (input.startsWith("reminder")) {
            return parseReminderCommand(input);
        } else if (input.startsWith("doafter")) {
            return parseDoAfterCommand(input, optionList);
        } else if (input.startsWith("snooze")) {
            return parseSnoozeCommand(input, optionList);
        } else if (input.startsWith("todo") | input.startsWith("deadline") | input.startsWith("event")) {
            return parseAddTaskCommand(input, optionList);
        } else if (input.startsWith("update")) {
            return parseUpdateCommand(input, optionList);
        } else if (input.startsWith("set")) {
            return parsePriorityCommand(input, optionList);
        } else if (input.startsWith("link")) {
            return parseLinkCommand(input, optionList);
        } else if ("clear".equals(input)) {
            return new TaskClearListCommand();
        }
        return new InvalidCommand("Invalid command word. Please enter \'help\' for more information");
    }

    private static Matcher prepareCommandMatcher(String input, String s) {
        Pattern commandPattern = Pattern.compile(s);
        return commandPattern.matcher(input);
    }

    private static Command parseDoneCommand(String input) {
        Matcher doneCommandMatcher = prepareCommandMatcher(input, "^done\\s+(?<index>\\d+)\\s*$");
        if (!doneCommandMatcher.matches()) {
            return new InvalidCommand("Please enter a valid index of task after \'done\'");
        }
        try {
            int index = parseTaskIndex(doneCommandMatcher.group("index"));
            return new TaskDoneCommand(index);
        } catch (TaskParseException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    private static Command parseDeleteCommand(String input) {
        Matcher deleteCommandMatcher = prepareCommandMatcher(input, "^delete\\s+(?<index>\\d+)\\s*$");
        if (!deleteCommandMatcher.matches()) {
            return new InvalidCommand("Please enter a valid index of task after \'delete\'");
        } else {
            try {
                int index = parseTaskIndex(deleteCommandMatcher.group("index"));
                return new TaskDeleteCommand(index);
            } catch (TaskParseException e) {
                return new InvalidCommand(e.getMessage());
            }
        }
    }

    private static int parseTaskIndex(String input) throws TaskParseException {
        String strippedInput = input.strip().replaceAll("^0+", "");
        if (strippedInput.length() >= 6) {
            throw new TaskParseException("Invalid index. Index should be integer of range 1 ~ 99999.");
        }
        int index = Integer.parseInt(strippedInput) - 1;
        if (index < 0 || index >= Model.getInstance().getTaskListLength()) {
            throw new TaskParseException("Index out of bounds. ");
        }
        return index;
    }

    private static Command parseFindCommand(String input) {
        Matcher findCommandMatcher = prepareCommandMatcher(input, "^find\\s+(?<keyword>[\\w]+[\\s|\\w]*)\\s*$");
        if (!findCommandMatcher.matches()) {
            return new InvalidCommand("Please enter keyword for searching after \'find\'");
        } else {
            String keyword = findCommandMatcher.group("keyword").strip();
            return new TaskFindCommand(keyword);
        }
    }

    private static Command parseReminderCommand(String input) {
        Matcher reminderCommandMatcher = prepareCommandMatcher(input, "^reminder(?:\\s+(?<dayLimit>[\\d]*)\\s*)?");
        if (!reminderCommandMatcher.matches()) {
            return new InvalidCommand("Please enter reminder with or without a number, which is the maximum number "
                    + "of days from now for a task to be considered as near");
        }
        try {
            int dayLimit = extractDayLimit(reminderCommandMatcher);
            if (dayLimit < 0) {
                showError("Reminder day limit cannot be negative. Default is used.");
                return new TaskReminderCommand();
            } else {
                return new TaskReminderCommand(dayLimit);
            }
        } catch (NumberFormatException e) {
            showError("Reminder day limit in wrong format. Default is used.");
            return new TaskReminderCommand();
        }
    }

    private static int extractDayLimit(Matcher reminderCommandMatcher) throws NumberFormatException {
        int dayLimit = -1;
        String dayLimitString = reminderCommandMatcher.group("dayLimit");
        if (dayLimitString.length() > 6) {
            showError("Reminder day limit too large. Default is used.");
        } else {
            dayLimit = Integer.parseInt(dayLimitString);
        }
        return dayLimit;
    }

    /**
     * Extracts linked emails from the option list.
     *
     * @param optionList the list of options where the tags are extracted
     * @return the ArrayList of strings
     */
    public static ArrayList<String> extractLinks(ArrayList<Command.Option> optionList) {
        ArrayList<String> linksList = new ArrayList<>();
        for (Command.Option option : optionList) {
            if (option.getKey().equals("link")) {
                linksList.add(option.getValue().strip());
            }
        }
        return linksList;
    }

    private static Command parseDoAfterCommand(String input, ArrayList<Command.Option> optionList) {
        Matcher doAfterCommandMatcher = prepareCommandMatcher(input, "^do[a|A]fter\\s+(?<index>[\\d]+)\\s*$");
        if (!doAfterCommandMatcher.matches()) {
            return new InvalidCommand("Please enter doAfter command in the correct format with index and description"
                    + " in -msg option");
        }
        String description = extractMsg(optionList);
        if ("".equals(description)) {
            return new InvalidCommand("Please enter a description of doAfter command after \'-msg \' option");
        }
        try {
            int index = parseTaskIndex(doAfterCommandMatcher.group("index"));
            return new TaskDoAfterCommand(index, description);
        } catch (TaskParseException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    private static String extractMsg(ArrayList<Command.Option> optionList) {
        String description = "";
        for (Command.Option option : optionList) {
            if (option.getKey().equals("msg")) {
                description = option.getValue();
                break;
            }
        }
        return description;
    }

    private static Command parsePriorityCommand(String input, ArrayList<Command.Option> optionList) {
        Matcher priorityCommandMatcher = prepareCommandMatcher(input, "^set\\s+(?<index>[\\d]+)\\s*$");
        if (!priorityCommandMatcher.matches()) {
            return new InvalidCommand("Please enter task index after 'set' and priority level "
                    + "after '-priority' option");
        }
        try {
            String priority = extractPriority(optionList);
            Task.Priority level = getPriorityLevel(priority);
            if ("".equals(priority)) {
                return new InvalidCommand("Please enter a priority level to set for the task after"
                        + " \'-priority\' option");
            } else if (!validPriority(priority)) {
                return new InvalidCommand("Invalid priority");
            }
            int index = parseTaskIndex(priorityCommandMatcher.group("index"));
            return new TaskSetPriorityCommand(index, level);
        } catch (TaskParseException e) {
            return new InvalidCommand(e.getMessage());
        } catch (NumberFormatException e) {
            return new InvalidCommand("Please enter a valid task index after \'set\'");
        }
    }

    private static Command parseSnoozeCommand(String input, ArrayList<Command.Option> optionList) {
        Matcher snoozeCommandMatcher = prepareCommandMatcher(input, "^snooze\\s+(?<index>[\\d]+)\\s*$");
        if (!snoozeCommandMatcher.matches()) {
            return new InvalidCommand("Please enter task index after 'snooze' and duration to "
                    + "snooze after \'-by\' ");
        }
        try {
            String snooze = extractSnooze(optionList);
            if (snooze.equals("")) {
                snooze = "3";
            }
            int index = parseTaskIndex(snoozeCommandMatcher.group("index"));
            int duration = Integer.parseInt(snooze);
            return new TaskSnoozeCommand(index, duration);
        } catch (TaskParseException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    private static Command parseUpdateCommand(String input, ArrayList<Command.Option> optionList) {
        ArrayList<TaskUpdateCommand.Attributes> attributes = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();
        Matcher editMatcher = prepareCommandMatcher(input, "^update\\s+(?<index>\\d+)\\s*$");
        if (!editMatcher.matches()) {
            return new InvalidCommand("Please enter an index after \'update\'");
        }
        try {
            final int index = parseTaskIndex(editMatcher.group("index"));
            addTimeToUpdateCommand(optionList, attributes, descriptions);
            addDoAfterToUpdateCommand(optionList, attributes, descriptions);
            addPriorityToUpdateCommand(optionList, attributes, descriptions);
            addTagsToUpdateCommand(optionList, attributes, descriptions);
            return new TaskUpdateCommand(index, descriptions, attributes);
        } catch (NumberFormatException e) {
            return new InvalidCommand("Please enter correct task index: " + editMatcher.group(
                    "index"));
        } catch (CommandParseHelper.CommandParseException e) {
            return new InvalidCommand("Index out of bound");
        }
    }

    private static void addTimeToUpdateCommand(ArrayList<Command.Option> optionList,
                                               ArrayList<TaskUpdateCommand.Attributes> attributes,
                                               ArrayList<String> descriptions)
            throws CommandParseHelper.CommandParseException {
        if (!"".equals(CommandParseHelper.extractTime(optionList))) {
            descriptions.add(CommandParseHelper.extractTime(optionList));
            attributes.add(TaskUpdateCommand.Attributes.TIME);
        }
    }

    private static void addDoAfterToUpdateCommand(ArrayList<Command.Option> optionList,
                                                  ArrayList<TaskUpdateCommand.Attributes> attributes,
                                                  ArrayList<String> descriptions)
            throws TaskParseException {
        if (!extractDoAfter(optionList).equals("")) {
            descriptions.add(extractDoAfter(optionList));
            attributes.add(TaskUpdateCommand.Attributes.DO_AFTER);
        }
    }

    private static void addPriorityToUpdateCommand(ArrayList<Command.Option> optionList,
                                                   ArrayList<TaskUpdateCommand.Attributes> attributes,
                                                   ArrayList<String> descriptions) throws TaskParseException {
        String priority = extractPriority(optionList);
        Task.Priority level = getPriorityLevel(priority);
        if (validPriority(priority)) {
            descriptions.add(level.name());
            attributes.add(TaskUpdateCommand.Attributes.PRIORITY);
        }
    }

    private static void addTagsToUpdateCommand(ArrayList<Command.Option> optionList,
                                               ArrayList<TaskUpdateCommand.Attributes> attributes,
                                               ArrayList<String> descriptions) {
        ArrayList<String> tags = extractTags(optionList);
        if (!tags.isEmpty()) {
            for (String tag : tags) {
                descriptions.add(tag);
                attributes.add(TaskUpdateCommand.Attributes.TAG);
            }

        }
    }

    private static String extractDoAfter(ArrayList<Command.Option> optionList)
            throws TaskParseException {
        String doafter = "";
        for (Command.Option option : optionList) {
            if (option.getKey().equals("doafter")) {
                if (doafter.equals("")) {
                    doafter = option.getValue();
                } else {
                    throw new TaskParseException("Each task can have only one doafter option");
                }
            }
        }
        return doafter;
    }

    private static String extractPriority(ArrayList<Command.Option> optionList) throws TaskParseException {
        String priority = "";
        for (Command.Option option : optionList) {
            if (option.getKey().equals("priority")) {
                if (priority.equals("")) {
                    priority = option.getValue();
                } else {
                    throw new TaskParseException("Each task can have only one priority");
                }
            }
        }
        return priority.toUpperCase();
    }

    /**
     * Checks if the input priority is valid.
     *
     * @param input priority extracted from input
     * @return true is priority is valid
     */
    public static boolean validPriority(String input) {
        for (Task.Priority priority : Task.Priority.values()) {
            if (priority.name().equals(input)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the priority level of task by user input.
     *
     * @param input user input
     * @return priority level of task.
     */
    public static Task.Priority getPriorityLevel(String input) {
        Task.Priority level = null;
        if (level.HIGH.name().equals(input)) {
            return level.HIGH;
        } else if (level.MEDIUM.name().equals(input)) {
            return level.MEDIUM;
        } else if (level.LOW.name().equals(input)) {
            return level.LOW;
        } else {
            return null;
        }
    }

    private static String extractSnooze(ArrayList<Command.Option> optionList) {
        String snooze = "";
        for (Command.Option option : optionList) {
            if (option.getKey().equals("by") && snooze.equals("")) {
                snooze = option.getValue();
            }
        }
        return snooze;
    }

    /**
     * Parses the specific part of a user/file input that is relevant to a task. A successful parsing always
     * returns an AddCommand, as it is assumed that an input starting with a task name is an add command.
     *
     * @param input      user/file input ready to be parsed
     * @param optionList contains all options specified in input command
     * @return an AddCommand of the task parsed from the input
     */
    private static Command parseAddTaskCommand(String input,
                                               ArrayList<Command.Option> optionList) {
        try {
            String doAfter = extractDoAfter(optionList);
            LocalDateTime time = parseTaskTime(optionList);
            ArrayList<String> tags = extractTags(optionList);
            String priority = extractPriority(optionList);
            ArrayList<String> links = extractLinks(optionList);
            return constructAddCommandByType(input, doAfter, time, tags, priority, links);
        } catch (TaskParseException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    /**
     * Gets time in LocalDateTime format from string extracted.
     *
     * @param optionList contains all options specified in input command
     * @return time in LocalDateTime format
     */
    public static LocalDateTime parseTaskTime(ArrayList<Command.Option> optionList) {
        try {
            String timeString = extractTime(optionList);
            return TaskParseNaturalDateHelper.getDate(timeString);
        } catch (CommandParseHelper.CommandParseException e) {
            return null;
        }
    }

    private static Command constructAddCommandByType(String input, String doAfter, LocalDateTime time,
                                                     ArrayList<String> tags, String priority,
                                                     ArrayList<String> links) {
        if ("".equals(priority)) {
            return constructByType(input, doAfter, time, tags, null, links);
        } else if (validPriority(priority)) {
            Task.Priority level = getPriorityLevel(priority);
            return constructByType(input, doAfter, time, tags, level, links);
        }
        return new InvalidCommand("Invalid priority");
    }

    private static Command constructByType(String input, String doAfter, LocalDateTime time,
                                           ArrayList<String> tags, Task.Priority priority,
                                           ArrayList<String> links) {
        if (input.startsWith("todo")) {
            return parseAddToDoCommand(input, doAfter, tags, priority, links);
        } else if (input.startsWith("deadline")) {
            return parseAddDeadlineCommand(input, time, doAfter, tags, priority, links);
        } else if (input.startsWith("event")) {
            return parseEventCommand(input, time, doAfter, tags, priority, links);
        } else {
            return new InvalidCommand("Invalid task type. Only todo, deadline, event are accepted. ");
        }
    }


    private static Command parseAddToDoCommand(String input, String doAfter,
                                               ArrayList<String> tags, Task.Priority priority,
                                               ArrayList<String> links) {
        Task.TaskType taskType = Task.TaskType.TODO;
        Matcher toDoMatcher = prepareCommandMatcher(input, "todo\\s+(?<name>\\w+[\\s+\\w+]*)\\s*");
        if (!toDoMatcher.matches()) {
            return new InvalidCommand("Please enter a name after todo");
        }
        String name = toDoMatcher.group("name");
        return new TaskAddCommand(taskType, name, null, doAfter, tags, priority, links);
    }

    private static Command parseAddDeadlineCommand(String input, LocalDateTime time, String doAfter,
                                                   ArrayList<String> tags, Task.Priority priority,
                                                   ArrayList<String> links) {
        Matcher deadlineMatcher = prepareCommandMatcher(input, "deadline\\s+(?<name>\\w+[\\s+\\w+]*)\\s*");
        if (!deadlineMatcher.matches()) {
            return new InvalidCommand("Please enter a name after \'deadline\'");
        }
        if (time == null) {
            return new InvalidCommand("Please enter a time of correct format after \'-time\'");
        }
        String name = deadlineMatcher.group("name");
        return new TaskAddCommand(Task.TaskType.DEADLINE, name, time, doAfter, tags, priority, links);
    }

    private static Command parseEventCommand(String input, LocalDateTime time, String doAfter,
                                             ArrayList<String> tags, Task.Priority priority,
                                             ArrayList<String> links) {
        Matcher eventMatcher = prepareCommandMatcher(input, "event\\s+(?<name>\\w+[\\s+\\w+]*)\\s*");
        if (!eventMatcher.matches()) {
            return new InvalidCommand("Please enter a name after \'event\'");
        }
        if (time == null) {
            return new InvalidCommand("Please enter a time of correct format after \'-time\'");
        }
        String name = eventMatcher.group("name");
        return new TaskAddCommand(Task.TaskType.EVENT, name, time, doAfter, tags, priority, links);
    }

    private static Command parseLinkCommand(String input, ArrayList<Command.Option> optionList) {
        Matcher linkCommandMatcher = prepareCommandMatcher(input, "^link(?:\\s+(?<index>[\\d]*)\\s*)?");
        if (!linkCommandMatcher.matches()) {
            showError("Please enter a task index and at least one email index");
            return new InvalidCommand();
        }
        try {
            int index = parseTaskIndex(linkCommandMatcher.group("index"));
            ArrayList<Integer> emailIndexList = extractEmails(optionList);
            ArrayList<Integer> taskIndexList = new ArrayList<>();
            taskIndexList.add(index);
            return new LinkCommand(taskIndexList, emailIndexList);
        } catch (NumberFormatException | TaskParseException e) {
            showError("Unable to find task/email.");
            return new InvalidCommand();
        }
    }

    /**
     * Extracts email index from the option list.
     *
     * @param optionList the list of options where the parameters are extracted
     * @return the ArrayList of email index
     */
    public static ArrayList<Integer> extractEmails(ArrayList<Command.Option> optionList) {
        ArrayList<Integer> emailList = new ArrayList<>();
        for (Command.Option option : optionList) {
            if (option.getKey().equals("email")) {
                emailList.add(Integer.parseInt(option.getValue().strip()) - 1);
            }
        }
        return emailList;
    }

    /**
     * Wraps around ui.showError by checking whether ui is null. This avoids the problem that ui is not
     * initialized during unit test.
     *
     * @param errorMessage the error message to be shown
     */
    private static void showError(String errorMessage) {
        if (ui != null) {
            ui.showError(errorMessage);
        }
    }

    private static class TaskParseException extends CommandParseHelper.CommandParseException {

        /**
         * Instantiates the exception with a message, which is ready to be displayed by the UI.
         *
         * @param msg the message that is ready to be displayed by UI.
         */
        public TaskParseException(String msg) {
            super(msg);
        }

    }

    /**
     * Parses timeString to get day and time respectively.
     *
     * @param timeString the input string
     * @return a pair containing day and time
     */
    public static Pair<String, String> checkTimeString(String timeString) {
        Pair<String, String> dateTime = new Pair<>(null, null);
        String day = dateTime.getKey();
        String timing = dateTime.getValue();
        if (!timeString.contains("/") && !timeString.isEmpty()) {
            String timeStr = timeString.substring(0, 1).toUpperCase() + timeString.substring(1).toLowerCase();
            if (timeStr.contains(" ")) {
                String[] tokens = timeStr.split("\\s+", 3);
                day = tokens[0];
                timing = tokens[1];
            } else {
                day = timeStr;
            }
        }
        dateTime = new Pair<>(day, timing);
        return dateTime;
    }
}