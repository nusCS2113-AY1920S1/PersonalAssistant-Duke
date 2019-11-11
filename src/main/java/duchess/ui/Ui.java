package duchess.ui;

import duchess.model.Grade;
import duchess.model.Module;
import duchess.model.calendar.CalendarEntry;
import duchess.model.task.Task;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Ui {

    /**
     * The following final strings are used to print out duchessCalendar.
     */
    private final int horizontalLength = 161;
    private final int horizontalBlock = 111; // 9 + 150
    private final int longLength = 120; // -30
    private final int blockLength = 21;
    private final int numOfDays = 7;
    private final String emptyBlock = "                     |";
    private final String calendarHeader = "|  TIME  |         MON         |         TUE         |         WED         |"
            + "         THUR        |         FRI         |         SAT         |         SUN         |";
    private final String blockSeparator = "+--------+---------------------+---------------------+---------------------+"
            + "---------------------+---------------------+---------------------+---------------------+";
    private final String blockShort = "+--------+----------------------------------------------------------------------"
            + "------------------------------------------+";
    private final String plainSeparator = "+---------------------------------------------------------------------------"
            + "---------------------------------------------------------------------------------------+";
    private final String plainShort = "+-------------------------------------------------------------------------------"
            + "------------------------------------------+";

    /**
     * Reference to Scanner.
     */
    private Scanner sc;

    /**
     * Instantiates Scanner class.
     */
    public Ui() {
        sc = new Scanner(System.in);
    }

    /**
     * Prints an indented line.
     */
    public void beginBlock() {
        printHR();
    }

    /**
     * Prints an indented line.
     */
    public void endBlock() {
        printHR();
    }

    /**
     * Prints welcome message.
     */
    public void showWelcome() {
        beginBlock();
        printIndented("8888888b. 888     888 .d8888b. 888    8888888888888 .d8888b.  .d8888b.");
        printIndented("888  \"Y88b888     888d88P  Y88b888    888888       d88P  Y88bd88P  Y88b");
        printIndented("888    888888     888888    888888    888888       Y88b.     Y88b.");
        printIndented("888    888888     888888       88888888888888888    \"Y888b.   \"Y888b.");
        printIndented("888    888888     888888       888    888888           \"Y88b.    \"Y88b.");
        printIndented("888    888888     888888    888888    888888             \"888      \"888");
        printIndented("888  .d88PY88b. .d88PY88b  d88P888    888888       Y88b  d88PY88b  d88P");
        printIndented("8888888P\"  \"Y88888P\"  \"Y8888P\" 888    8888888888888 \"Y8888P\"  \"Y8888P");
        printIndented("");
        printIndented("Welcome to Duchess, where we live to serve your every need.");
        printIndented("What can I do for you?");
        endBlock();
    }

    /**
     * Prints farewell message.
     */
    public void showBye() {
        printIndented("Bye. Hope to see you again soon!");
    }

    /**
     * Prints error to user.
     *
     * @param message Error message to be displayed
     */
    public void showError(String message) {
        printIndented(message);
    }

    /**
     * Displays the newly added module as well as other modules.
     *
     * @param module  newly added module
     * @param modules existing modules
     */
    public void showModuleAdded(Module module, List<Module> modules) {
        printIndented("I've added this module:");
        printIndented("  " + module);
        printIndented("Here are all your modules:");
        showModules(modules);
    }

    /**
     * Displays the newly added grade as well as other grades.
     *
     * @param module module to which grade is added
     * @param grade  newly added grade
     * @param grades existing grades
     */

    public void showGradeAdded(Module module, Grade grade, List<Grade> grades) {
        printIndented("I've added this grade");
        printIndented("  " + grade);
        printIndented("Here are all your grades for " + module);
        showGrades(grades);
    }

    public void showDeletedGrade(String gradeDescription, Module module) {
        printIndented("You've deleted " + gradeDescription + " for " + module + ".");
    }

    /**
     * Displays grades in the list given.
     *
     * @param grades list of grades
     */
    public void showGrades(List<Grade> grades) {
        int counter = 1;
        for (Grade grade : grades) {
            printIndented(counter++ + ". " + grade.toString());
        }
    }

    /**
     * Displays grades in the given module.
     *
     * @param grades list of grades
     * @param module module containing grades
     */
    public void showGradeList(List<Grade> grades, Module module) {
        printIndented("Here are your grades for " + module + ":");
        showGrades(grades);
    }

    /**
     * Prints the task that was added.
     *
     * @param tasks List of all tasks
     * @param task  Added task
     */
    public void showTaskAdded(List<Task> tasks, Task task) {
        printIndented("Got it . I've added this task:");
        printIndented("  " + task);
        showNumTasks(tasks);
    }

    /**
     * Displays Task objects in list given.
     *
     * @param tasks List of Task objects
     */
    public void showTaskList(List<Task> tasks) {
        printIndented("Here are the tasks in your list:");
        showTasks(tasks);
    }

    /**
     * Displays the user's modules.
     *
     * @param modules list of modules
     */
    public void showModuleList(List<Module> modules) {
        if (modules.size() == 0) {
            printIndented("You've no modules.");
            printIndented("You can add modules using the `add module` command.");
            return;
        }

        printIndented("Here are your modules:");
        showModules(modules);
    }

    /**
     * Displays search results to user.
     *
     * @param tasks List containing tasks from user
     */
    public void showSearchResult(List<Task> tasks) {
        printIndented("Here are the matching tasks in your list:");
        showTasks(tasks);
    }

    /**
     * Displays schedule of a single day to user.
     * Informs user if there are ongoing events.
     *
     * @param tasks List of tasks to show
     * @param date  Date
     */
    public void showScheduleResult(List<Task> tasks, String date) {
        printIndented("Here is your schedule for " + date + ":");
        int counter = 1;
        for (Task t : tasks) {
            printIndented(counter++ + ". " + t.toString());
        }
    }

    /**
     * Inform user calendar has been saved as test file.
     *
     * @param filePath filePath
     */
    public void showFinishedExport(String filePath) {
        printIndented("Your calendar has finished exporting to " + filePath);
    }

    /**
     * Process block to be printed in calendar.
     *
     * @param str string of null value or containing description of task
     * @param length max length of string
     * @return shortened or padded-with-whitespace string
     */
    private String processDescription(String str, int length) {
        if (str == null) {
            return emptyBlock;
        } else if (str.length() > length) {
            str = str.substring(0, length - 3) + "...";
        } else {
            StringBuilder strBuilder = new StringBuilder(str);
            while (strBuilder.length() <= length) {
                strBuilder.append(" ");
            }
            str = strBuilder.toString();
        }
        return str + "|";
    }

    /**
     * Pads the header for the calendar.
     *
     * @param str string containing academic year + semester + week
     * @return string with academic context centered and padded with whitespace
     */
    private String processCentred(String str, int length) {
        int partition = (int) Math.floor((length - str.length()) / (double) 2);
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(" ".repeat(Math.max(0, partition)));
        strBuilder.append(str);
        strBuilder.append(" ".repeat(Math.max(0, length - strBuilder.length() + 1)));
        return "|" + strBuilder.toString() + "|";
    }

    /**
     * Joins together the various blocks for the week.
     *
     * @param time   time
     * @param strArr array containing descriptions
     * @return joined string of descriptions or empty blocks
     */
    private String processRow(String time, String[] strArr) {
        return "|  " + time + "  |"
                + Arrays.stream(strArr)
                .map(str -> processDescription(str, blockLength))
                .collect(Collectors.joining());
    }

    /**
     * Joins together the time column and description column for each event.
     *
     * @param t Task
     * @return joined string of time + description of event
     */
    private String processRow(Task t) {
        String time = t.getTimeFrame().getStart().toLocalTime().toString().replaceAll(":", "");
        String description = t.toString();
        if (description.length() > horizontalBlock) {
            description = description.substring(0, horizontalBlock - 5);
            description += "...";
        }
        description = processCentred(description, horizontalBlock);
        return "|  " + time + "  " + description;
    }

    /**
     * Returns an array of size seven (for the days in a week).
     * Array contains strings of either null value or description
     * of an event-type task.
     *
     * @param flatCalendar duchessCalendar
     * @param time         key in treeMap storing flatCalendar
     * @param str          description or null string
     * @param i            the day of week being processed
     * @return string array of size seven
     */
    private String[] processArr(SortedMap<LocalTime, String[]> flatCalendar,
                                LocalTime time,
                                String str,
                                int i) {
        String[] strArr;
        if (!flatCalendar.containsKey(time)) {
            strArr = new String[numOfDays];
        } else {
            strArr = flatCalendar.get(time);
        }
        strArr[i] = str;
        return strArr;
    }

    /**
     * Sorts the duchess calendar into a 2-D map of timings mapped to their event descriptions.
     *
     * @param query filtered duchess calendar
     * @return sorted map of duchess calendar
     */
    private SortedMap<LocalTime, String[]> flattenCalendar(List<CalendarEntry> query) {
        SortedMap<LocalTime, String[]> flatCalendar = new TreeMap<>();
        for (CalendarEntry ce : query) {
            List<Task> taskList = ce.getDateTasks();
            int index = ce.getDate().getDayOfWeek().getValue() - 1;
            for (Task t : taskList) {
                LocalTime time = t.getTimeFrame().getStart().toLocalTime();
                String description = t.toString();
                String[] updateArr = processArr(flatCalendar, time, description, index);
                flatCalendar.put(time, updateArr);
            }
        }
        return flatCalendar;
    }

    /**
     * Store the display calendar as strings in an array.
     *
     * @param ceList  list of calendar entries
     * @param context academic year information
     * @return array list of the display calendar string by string
     */
    public List<String> stringCalendar(List<CalendarEntry> ceList, String context) {
        List<String> display = new ArrayList<>();
        display.add(plainSeparator);
        display.add(processCentred(context, horizontalLength));
        display.add(blockSeparator);
        display.add(calendarHeader);
        display.add(blockSeparator);
        SortedMap<LocalTime, String[]> flattened = flattenCalendar(ceList);
        for (Map.Entry<LocalTime, String[]> entry : flattened.entrySet()) {
            String time = entry.getKey().toString().replaceAll(":", "");
            String[] strArr = entry.getValue();
            display.add(processRow(time, strArr));
            display.add(blockSeparator);
        }
        return display;
    }

    /**
     * Prints out the a day view of the calendar.
     *
     * @param ce calendar entry
     */
    public List<String> stringCalendar(CalendarEntry ce, String context) {
        List<String> display = new ArrayList<>();
        display.add(plainShort);
        display.add(processCentred(context, longLength));
        display.add(blockShort);
        String dayOfWeek = ce.getDate().getDayOfWeek().toString();
        display.add("|  TIME  " + processCentred(dayOfWeek, horizontalBlock));
        display.add(blockShort);
        for (Task t : ce.getDateTasks()) {
            display.add(processRow(t));
            display.add(blockShort);
        }
        return display;
    }

    /**
     * Prints out the a week view of the calendar.
     *
     * @param ceList  calendar
     * @param context academic year information
     */
    public void displayCalendar(List<CalendarEntry> ceList, String context) {
        List<String> display = stringCalendar(ceList, context);
        for (String s : display) {
            printIndented(s);
        }
    }

    /**
     * Prints out the a day view of the calendar.
     *
     * @param ce      calendar entry
     * @param context academic year information
     */
    public void displayCalendar(CalendarEntry ce, String context) {
        List<String> display = stringCalendar(ce, context);
        for (String s : display) {
            printIndented(s);
        }
    }

    /**
     * Shows the task that was just snoozed.
     *
     * @param task The task that was just snoozed
     */
    public void showSnoozedTask(Task task) {
        printIndented("Noted. I've snoozed this task:");
        printIndented("  " + task);
    }

    /**
     * Shows the task that was just deleted.
     *
     * @param tasks List of all tasks
     * @param task  The task that was just deleted
     */
    public void showDeletedTask(List<Task> tasks, Task task) {
        printIndented("Noted. I've removed this task:");
        printIndented("  " + task);
        showNumTasks(tasks);
    }

    /**
     * Prints message for task completion.
     *
     * @param task Completed task
     */
    public void showDoneTask(Task task) {
        printIndented("Nice! I've marked this task as done:");
        printIndented("  " + task);
    }

    /**
     * Prints message to show unmarked task.
     *
     * @param task unmarked task
     */
    public void showUnmarkedTask(Task task) {
        printIndented("I've unmarked this task as done:");
        printIndented("  " + task);
    }

    /**
     * Prints message for grade completion.
     *
     * @param grade Completed grade
     */
    public void showCompletedGrade(Grade grade) {
        printIndented("Nice! I've marked this grade as complete:");
        printIndented("  " + grade);
    }

    /**
     * Gets next line from user inputs.
     *
     * @return String containing user input
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Prints a string accompanied with indentation.
     *
     * @param line String containing description to be indented
     */
    private void printIndented(String line) {
        System.out.println("    " + line);
    }

    /**
     * Displays the tasks in a list.
     *
     * @param tasks List containing user tasks
     */
    private void showNumTasks(List<Task> tasks) {
        printIndented("Now you have "
                + tasks.size()
                + (tasks.size() > 1 ? " tasks" : " task")
                + " in the list.");
    }

    /**
     * Displays tasks in a list.
     *
     * @param tasks List containing user tasks
     */
    private void showTasks(List<Task> tasks) {
        int counter = 1;
        for (Task task : tasks) {
            printIndented(counter++ + ". " + task);
        }
    }

    /**
     * Displays modules in a list.
     *
     * @param modules list containing modules
     */
    private void showModules(List<Module> modules) {
        int counter = 1;
        for (Module module : modules) {
            printIndented(counter++ + ". " + module);
        }
    }

    /**
     * Shows no deadlines present.
     */
    public void showNoDeadlines() {
        printIndented("You have no pending deadlines.");
    }

    /**
     * Displays the list of deadlines present in user list.
     *
     * @param tasks List of all tasks
     */
    public void showDeadlines(List<Task> tasks) {
        printIndented("You currently have these deadlines:");
        showTasks(tasks);
    }

    /**
     * Displays user history.
     *
     * @param log list of user log
     */
    public void showUserHistory(List<String> log) {
        printIndented("These are the commands you entered:");
        int counter = 1;
        for (String userInput : log) {
            printIndented(counter++ + ". " + userInput);
        }
    }

    /**
     * Displays number of undone actions to user.
     *
     * @param undoCounter number of undo operations
     */
    public void showUndo(int undoCounter) {
        if (undoCounter == 1) {
            printIndented("The last command has been undone.");
        } else if (undoCounter == 0) {
            printIndented("There's nothing to undo.");
        } else {
            printIndented("The last few commands have been undone.");
        }
    }

    /**
     * Displays number of undone actions to user.
     *
     * @param redoCounter number of undo operations
     */
    public void showRedo(int redoCounter) {
        if (redoCounter == 1) {
            printIndented("I redid the last command.");
        } else if (redoCounter == 0) {
            printIndented("There's nothing to redo.");
        } else {
            printIndented("I redid the last few commands.");
        }
    }

    /**
     * Displays an error message indicating why the module can't be deleted.
     *
     * <p>The function also prints the tasks that are associated with the module
     * being deleted.</p>
     *
     * @param tasks list of tasks belonging to the module being deleted
     */
    public void showUnableToDeleteModuleMsg(List<Task> tasks) {
        printIndented("You can't delete the module without deleting all of the associated tasks.");
        printIndented("The following tasks are associated with the module.");
        showTasks(tasks);
    }

    /**
     * Displays a confirmation message stating that the module has been deleted.
     *
     * @param module the module that was just deleted
     */
    public void showDeletedModule(Module module) {
        printIndented("You've deleted " + module + ".");
    }

    /**
     * Displays a message stating lessons of a module have been deleted.
     *
     * @param moduleCode module code of required module
     */
    public void showDeletedLesson(String moduleCode) {
        printIndented("You have deleted lessons for " + moduleCode + ".");
    }

    /**
     * Displays a message when no lessons are deleted for a module.
     *
     * @param moduleCode module code of required module
     */
    public void showNoDeletedLesson(String moduleCode) {
        printIndented("There are no lessons to be deleted for " + moduleCode + ".");
    }

    /**
     * Displays a message when lessons are added for a module.
     *
     * @param moduleCode module code of required module
     */
    public void showLessonsAdded(String moduleCode) {
        printIndented("Lessons have been added for " + moduleCode + ".");
    }

    /**
     * Displays a message when lessons are not added for a module.
     *
     * @param moduleCode module code of required module
     */
    public void showNoLessonsAdded(String moduleCode) {
        printIndented("Lessons have not been added for " + moduleCode + ".");
    }

    /**
     * Displays a given message.
     *
     * @param msg the message to display
     */
    public void displayMessage(String msg) {
        printIndented(msg);
    }

    /**
     * Prints a straight line.
     */
    private void printHR() {
        printIndented("_______________________________________________________________");
    }
}