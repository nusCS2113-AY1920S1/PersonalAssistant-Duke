package seedu.duke.task.parser;

import javafx.util.Pair;
import seedu.duke.common.parser.CommandParseHelper;
import seedu.duke.common.command.InvalidCommand;
import seedu.duke.task.command.TaskLinkCommand;
import seedu.duke.common.command.HelpCommand;
import seedu.duke.common.command.Command;
import seedu.duke.common.command.FlipCommand;
import seedu.duke.common.command.ExitCommand;
import seedu.duke.common.model.Model;
import seedu.duke.task.TaskList;
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
import seedu.duke.task.command.TaskSortCommand;
import seedu.duke.task.command.TaskUpdateCommand;
import seedu.duke.task.entity.Task;
import seedu.duke.ui.UI;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.duke.common.parser.CommandParseHelper.extractTags;
import static seedu.duke.common.parser.CommandParseHelper.extractTime;
import static seedu.duke.common.parser.CommandParseHelper.isNumberTooLarge;

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
        } else if (input.startsWith("sort")) {
            return parseSortCommand(input);
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
            return new InvalidCommand("Please enter a valid index (positive integer equal or less than the "
                    + "number of tasks) of task after \'done\'");
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
            return new InvalidCommand("Please enter a valid index (positive integer equal or less than the "
                    + "number of tasks) of task after \'delete\'");
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
        if (isNumberTooLarge(input)) {
            throw new TaskParseException("Invalid index. Index should be integer of range 1 ~ 99999.");
        }
        int index = Integer.parseInt(input) - 1;
        if (index < 0 || index >= Model.getInstance().getTaskListLength()) {
            throw new TaskParseException("Index " + (index + 1) + " out of bounds of 1 to "
                    + Model.getInstance().getTaskListLength());
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
            return new TaskReminderCommand(dayLimit);
        } catch (NumberFormatException e) {
            showError("Reminder day limit in wrong format. Default is used.");
            return new TaskReminderCommand();
        } catch (TaskParseException e) {
            showError(e.getMessage());
            return new TaskReminderCommand();
        }
    }

    private static int extractDayLimit(Matcher reminderCommandMatcher)
            throws NumberFormatException, TaskParseException {
        int dayLimit;
        String dayLimitString = reminderCommandMatcher.group("dayLimit");
        if (dayLimitString == null) {
            throw new TaskParseException("Day limit not specified. Default is used.");
        }
        if (dayLimitString.length() > 6) {
            throw new TaskParseException("Reminder day limit only accept positive integer from 1 to 99999. "
                    + "Default is used.");
        } else {
            dayLimit = Integer.parseInt(dayLimitString);
        }
        if (dayLimit <= 0) {
            throw new TaskParseException("Reminder day limit must be positive. Default is used.");
        }
        return dayLimit;
    }

    private static Command parseSortCommand(String input) {
        Matcher sortCommandMatcher = prepareCommandMatcher(input, "^sort\\s+(?<sortBy>[\\w]*)\\s*");
        if (!sortCommandMatcher.matches()) {
            return new InvalidCommand("Please enter how you want the task list to be sorted after \'sort\'");
        }
        String sortBy = sortCommandMatcher.group("sortBy").strip();
        TaskList.SortBy sortType = TaskSortCommand.getSortType(sortBy);
        if ("".equals(sortBy)) {
            return new InvalidCommand("Please enter \'TIME\', \'PRIORITY\' or \'STATUS\' after "
                    + "\'sort\' command");
        } else if (sortType == null) {
            return new InvalidCommand("Invalid sorting type");
        }
        return new TaskSortCommand(sortType);
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
            return new InvalidCommand("Please enter doAfter command in the correct format with index "
                    + "(positive integer equal or less than the number of tasks) and description"
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
            return new InvalidCommand("Please enter task index (positive integer equal or less than the "
                    + "number of tasks) after 'set' and priority level after '-priority' option");
        }
        try {
            String priority = extractPriority(optionList);
            Task.Priority level = Task.getPriorityLevel(priority);
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
            return new InvalidCommand("Please enter a valid task index (positive integer equal or less "
                    + "then the number of tasks) after \'set\'");
        }
    }

    private static Command parseSnoozeCommand(String input, ArrayList<Command.Option> optionList) {
        Matcher snoozeCommandMatcher = prepareCommandMatcher(input, "^snooze\\s+(?<index>[\\d]+)\\s*$");
        if (!snoozeCommandMatcher.matches()) {
            return new InvalidCommand("Please enter a valid task index (positive integer equal or less "
                    + "then the number of tasks) after \'snooze\' and duration to snooze after \'-by\' ");
        }
        int index = -1;
        try {
            index = parseTaskIndex(snoozeCommandMatcher.group("index"));
            int snoozeDuration = extractSnooze(optionList);
            return new TaskSnoozeCommand(index, snoozeDuration);
        } catch (NumberFormatException e) {
            return new InvalidCommand("Please enter a valid number of days for snooze (positive integer "
                    + "from 1 to 99999)");
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

            if (descriptions.isEmpty()) {
                return new InvalidCommand("Please enter at least one valid attribute to update");
            }
            return new TaskUpdateCommand(index, descriptions, attributes);
        } catch (NumberFormatException e) {
            return new InvalidCommand("Please enter a valid task index (positive integer equal or less "
                    + "than the number of tasks)");
        } catch (TaskParseException e) {
            return new InvalidCommand(e.getMessage());
        } catch (CommandParseHelper.CommandParseException e) {
            return new InvalidCommand("Index out of bounds");
        }
    }

    private static void addTimeToUpdateCommand(ArrayList<Command.Option> optionList,
                                               ArrayList<TaskUpdateCommand.Attributes> attributes,
                                               ArrayList<String> descriptions)
            throws CommandParseHelper.CommandParseException {
        if (!"".equals(extractTime(optionList))) {
            descriptions.add(extractTime(optionList));
            attributes.add(TaskUpdateCommand.Attributes.TIME);
        }
    }

    private static void addDoAfterToUpdateCommand(ArrayList<Command.Option> optionList,
                                                  ArrayList<TaskUpdateCommand.Attributes> attributes,
                                                  ArrayList<String> descriptions)
            throws TaskParseException {
        if (!extractDoAfter(optionList).equals("")) {
            String doafter = extractDoAfter(optionList);
            System.out.println(doafter);
            descriptions.add(doafter);
            attributes.add(TaskUpdateCommand.Attributes.DO_AFTER);
        }
    }

    private static void addPriorityToUpdateCommand(ArrayList<Command.Option> optionList,
                                                   ArrayList<TaskUpdateCommand.Attributes> attributes,
                                                   ArrayList<String> descriptions) throws TaskParseException {
        String priority = extractPriority(optionList);
        Task.Priority level = Task.getPriorityLevel(priority);
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
                if ("".equals(doafter)) {
                    doafter = option.getValue();
                } else {
                    throw new TaskParseException("Each task can have only one doafter option");
                }
            }
        }
        return doafter;
    }

    private static String extractPriority(ArrayList<Command.Option> optionList) throws
            TaskParseException {
        String priority = "";
        for (Command.Option option : optionList) {
            if (option.getKey().equals("priority")) {
                if ("".equals(priority)) {
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
            if (priority.name().equals(input) && !"NULL".equals(input)) {
                return true;
            }
        }
        return false;
    }

    private static int extractSnooze(ArrayList<Command.Option> optionList) {
        String snoozeString = "";
        for (Command.Option option : optionList) {
            if (option.getKey().equals("by") && snoozeString.equals("")) {
                snoozeString = option.getValue();
            }
        }
        if ("".equals(snoozeString)) {
            return -1;
        } else {
            return Integer.parseInt(snoozeString);
        }
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
            String timeString = extractTime(optionList);
            if (input.startsWith("todo") && (!"".equals(timeString))) {
                return new InvalidCommand("Date Time not allowed in todo tasks");
            }
            return constructAddCommandByType(input, doAfter, time, tags, priority, links);
        } catch (TaskParseException e) {
            return new InvalidCommand(e.getMessage());
        } catch (CommandParseHelper.CommandParseException e) {
            return null;
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
            LocalDateTime dateTime = TaskParseNaturalDateHelper.getDate(timeString);
            return dateTime;
        } catch (CommandParseHelper.CommandParseException e) {
            return null;
        }
    }

    private static Command constructAddCommandByType(String input, String doAfter, LocalDateTime time,
                                                     ArrayList<String> tags, String priority,
                                                     ArrayList<String> links) {
        Task.Priority level = Task.getPriorityLevel(priority);
        if ("".equals(priority)) {
            return constructByType(input, doAfter, time, tags, Task.Priority.NULL, links);
        } else if (validPriority(priority)) {
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
            return new InvalidCommand("Please enter a time of correct format (dd/mm/yyyy HHMM) or "
                    + "(day HHMM) after \'-time\'");
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
            return new InvalidCommand("Please enter a time of correct format (dd/mm/yyyy HHMM) or "
                    + "(day HHMM) after \'-time\'");
        }
        String name = eventMatcher.group("name");
        return new TaskAddCommand(Task.TaskType.EVENT, name, time, doAfter, tags, priority, links);
    }

    private static Command parseLinkCommand(String input, ArrayList<Command.Option> optionList) {
        Matcher linkCommandMatcher = prepareCommandMatcher(input, "^link\\s+(?<index>[\\d]*)\\s*?");
        if (!linkCommandMatcher.matches()) {
            return new InvalidCommand("Please enter a valid task index (positive integer equal or less then "
                    + "the number of tasks) and email indexes (optional)");
        }
        try {
            int taskIndex = parseTaskIndex(linkCommandMatcher.group("index"));
            ArrayList<Integer> emailIndexList = extractEmails(optionList);
            ArrayList<Integer> deleteIndexList = extractDelete(optionList);
            return new TaskLinkCommand(taskIndex, emailIndexList, deleteIndexList);
        } catch (TaskParseException e) {
            return new InvalidCommand("Please enter a valid task index");
        } catch (EmailParseException e) {
            return new InvalidCommand("Please ensure all email indexes are valid");
        }
    }

    /**
     * Extracts email index from the option list.
     *
     * @param optionList the list of options where the parameters are extracted
     * @return the ArrayList of email index
     */
    public static ArrayList<Integer> extractEmails(ArrayList<Command.Option> optionList)
            throws EmailParseException {
        ArrayList<Integer> emailList = new ArrayList<>();
        for (Command.Option option : optionList) {
            if (option.getKey().equals("email")) {
                int index = Integer.parseInt(option.getValue().strip()) - 1;
                if (index < 0 || index >= Model.getInstance().getEmailListLength()) {
                    throw new EmailParseException("Index out of bounds.");
                }
                emailList.add(index);
            }
        }
        return emailList;
    }

    /**
     * Extracts email index from the option list.
     *
     * @param optionList the list of options where the parameters are extracted
     * @return the ArrayList of email index
     */
    public static ArrayList<Integer> extractDelete(ArrayList<Command.Option> optionList) {
        ArrayList<Integer> deleteList = new ArrayList<>();
        for (Command.Option option : optionList) {
            if (option.getKey().equals("delete")) {
                int index = Integer.parseInt(option.getValue().strip()) - 1;
                deleteList.add(index);
            }
        }
        return deleteList;
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

    private static class EmailParseException extends CommandParseHelper.CommandParseException {

        /**
         * Instantiates the exception with a message, which is ready to be displayed by the UI.
         *
         * @param msg the message that is ready to be displayed by UI.
         */
        public EmailParseException(String msg) {
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