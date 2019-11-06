package duchess.ui;

import duchess.model.Grade;
import duchess.model.Module;
import duchess.model.calendar.CalendarEntry;
import duchess.model.task.Task;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.stream.Collectors;

public class Ui {

    /**
     * The following final strings are used to print out duchessCalendar.
     */
    public final int horizontalLength = 161;
    public final int horizontalBlock = 141; // 9 + 150
    public final int longLength = 150;
    private final int blockLength = 21;
    private final String emptyBlock = "                     |";
    public final String calendarHeader = "|  TIME  |         MON         |         TUE         |         WED         "
            + "|         THUR        |         FRI         |         SAT         |         SUN         |";
    public final String blockSeparator = "+--------+---------------------+---------------------+---------------------"
            + "+---------------------+---------------------+---------------------+---------------------+";
    public final String blockShort = "+--------+------------------------------------------------------"
            + "+---------------------------------------------------------------------------------------+";
    public final String plainSeparator = "+---------------------------------------------------------------------------"
            + "---------------------------------------------------------------------------------------+";
    public final String plainShort = "+----------------------------------------------------------------"
            + "---------------------------------------------------------------------------------------+";

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
        printIndented("Hello! I'm Duchess");
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
     * @param grade newly added grade
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
     * @return shortened or padded-with-whitespace string
     */
    private String processDescription(String str) {
        if (str == null) {
            return emptyBlock;
        } else if (str.length() > blockLength) {
            str = str.substring(0, blockLength - 3) + "...";
        } else {
            while (str.length() <= blockLength) {
                str += " ";
            }
        }
        return str + "|";
    }

    /**
     * Pads the header for the calendar.
     *
     * @param str string containing academic year + semester + week
     * @return string with academic context centered and padded with whitespace
     */
    public String processCentred(String str, int length) {
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
    public String processRow(String time, String[] strArr) {
        return "|  " + time + "  |"
                + Arrays.stream(strArr)
                .map(this::processDescription)
                .collect(Collectors.joining());
    }

    /**
     * Joins together the time column and description column for each event.
     *
     * @param t Task
     * @return joined string of time + description of event
     */
    public String processRow(Task t) {
        String time = t.getTimeFrame().getStart().toLocalTime().toString().replaceAll(":", "");
        String description = processCentred(t.toString(), horizontalBlock);
        return "|  " + time + "  " + description;
    }

    /**
     * Prints out weekly calendar displaying only event tasks.
     *
     * @param flatCalendar flattened duchessCalendar
     * @param context      academic year + semester + week
     */
    public void displayCalendar(SortedMap<LocalTime, String[]> flatCalendar, String context) {
        printIndented(plainSeparator);
        printIndented(processCentred(context, horizontalLength));
        printIndented(blockSeparator);
        printIndented(calendarHeader);
        printIndented(blockSeparator);
        for (Map.Entry<LocalTime, String[]> entry : flatCalendar.entrySet()) {
            String time = entry.getKey().toString().replaceAll(":", "");
            String[] strArr = entry.getValue();
            printIndented(processRow(time, strArr));
            printIndented(blockSeparator);
        }
    }

    /**
     * Prints out the a day view of the calendar.
     *
     * @param ce calendar entry
     */
    public void displayCalendar(CalendarEntry ce, String context) {
        printIndented(plainShort);
        printIndented(processCentred(context, longLength));
        printIndented(plainShort);
        String dayOfWeek = ce.getDate().getDayOfWeek().toString();
        printIndented("|  TIME  " + processCentred(dayOfWeek, horizontalBlock));
        printIndented(blockShort);
        for (Task t : ce.getDateTasks()) {
            printIndented(processRow(t));
            printIndented(blockShort);
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