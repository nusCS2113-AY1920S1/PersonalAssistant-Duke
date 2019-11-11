package planner.ui.cli;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import planner.logic.modules.TaskList;
import planner.logic.modules.legacy.task.Task;
import planner.logic.modules.legacy.task.TaskWithMultipleWeeklyPeriod;
import planner.logic.modules.module.ModuleTask;
import planner.ui.gui.MainWindow;

/**
 * Mod Planner based on morphed implementation of Duke.
 */
public class PlannerUi {

    private static final String LINE = "_______________________________";
    private static Set<String> yes = new HashSet<>(Arrays.asList("y","yes", "true", "1",
                                                                 "confirm", "t", "yup", "yeah", "positive"));
    private static Set<String> no = new HashSet<>(Arrays.asList("n", "no", "false", "0", "f",
                                                                "nope", "nah", "negative"));
    private InputStream inputStream;
    private MainWindow mainWindow;

    /**
     * Default constructor for Ui.
     * @param window gui window
     */
    public PlannerUi(MainWindow window) {
        this.setInput(System.in);
        this.setOutput(System.out, System.err);
        mainWindow = window;
        setupGui();
    }

    public PlannerUi() {
        this(null);
    }

    /**
     * Print Object to String.
     * @param object to be printed
     * @param update whether to update GUI
     */
    public void print(Object object, boolean update) {
        System.out.print(object.toString());
        if (update) {
            updateGui();
        }
    }

    public void print(Object object) {
        print(object, false);
    }

    public void close() throws IOException {
        inputStream.close();
    }

    /**
     * Println Object to String.
     * @param object to be printed
     * @param update whether to update GUI
     */
    public void println(Object object, boolean update) {
        System.out.println(object.toString());
        if (update) {
            updateGui();
        }
    }

    public void println(Object object) {
        println(object, true);
    }

    /**
     * Printf.
     * @param s format to be printed
     * @param args arguments
     * @param update whether to update GUI
     */
    public void printf(boolean update, String s, Object... args) {
        System.out.printf(s, args);
        if (update) {
            updateGui();
        }
    }

    public void printf(String s, Object... args) {
        printf(false, s, args);
    }

    private void setupGui() {
        if (mainWindow != null) {
            setInput(mainWindow.getInput());
            setOutput(mainWindow.getOutput(), mainWindow.getOutput());
        }
    }

    /**
     * Update GUI dialog.
     */
    public void updateGui() {
        if (mainWindow != null) {
            mainWindow.addModPlanDialog();
        }
    }

    /**
     * Show LINE, hidden in GUI mode.
     */
    public void showLine() {
        if (mainWindow == null) {
            println(LINE);
        } else {
            updateGui();
        }
    }

    public void setInput(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Set output stream and error stream.
     * @param outStream output stream
     * @param errStream error stream
     */
    public void setOutput(PrintStream outStream, PrintStream errStream) {
        this.setOut(outStream);
        this.setErr(errStream);
    }

    public void setOutput(ByteArrayOutputStream output, ByteArrayOutputStream error) {
        this.setOutput(new PrintStream(output), new PrintStream(error));
    }

    public void setOut(PrintStream stream) {
        System.setOut(stream);
    }

    public void setOut(ByteArrayOutputStream output) {
        this.setOut(new PrintStream(output));
    }

    public void setErr(PrintStream stream) {
        System.setErr(stream);
    }

    public void setErr(ByteArrayOutputStream error) {
        this.setErr(new PrintStream(error));
    }

    public String readInput() {
        return readInput(inputStream);
    }

    /**
     * Read input from custom stream.
     * @param stream stream to read from
     * @return input by line
     */
    public String readInput(InputStream stream) {
        try {
            if (stream == null) {
                return null;
            }
            StringBuilder input = new StringBuilder();
            for (char c = (char) stream.read();
                 c != '\n' && c != '\uFFFF';
                 c = (char) stream.read()) {
                if (c != '\r') {
                    input.append(c);
                }
            }
            if (input.length() == 0) {
                return null;
            }
            return input.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String readPassword() {
        return this.readInput(); // No good way to do this yet
    }

    /**
     * Confirm user's action.
     * @param message message to display
     * @return true if user confirms else false
     */
    public boolean confirm(String message) {
        if (message != null) {
            println(message);
        }
        boolean result = true;
        while (result) {
            String input = this.readInput();
            if (yes.contains(input)) {
                break;
            } else if (no.contains(input)) {
                result = false;
            } else {
                this.println("Please enter a valid response!");
            }
        }
        return result;
    }

    public boolean confirm() {
        return confirm(null);
    }

    /**
     * Prompt user for input.
     * @param message message to display to user beforehand
     * @param allowEmpty whether to allow empty input
     * @param secure whether to display user input
     * @return user input
     */
    public String prompt(String message, boolean allowEmpty, boolean secure) {
        this.println(message);
        String input;
        if (secure) {
            input = this.readPassword();
        } else {
            input = this.readInput();
        }
        while (!allowEmpty && input.isBlank()) {
            input = this.invalidResponsePrompt(false, secure);
        }
        return input;
    }

    public String prompt(String message) {
        return this.prompt(message, false, false);
    }

    public int yearPrompt() {
        return this.intPrompt("Please enter your current year (i.e. 1, 2, ...):", 1, 2, 3, 4, 5);
    }

    public int semesterPrompt() {
        return this.intPrompt("Please enter your current semester (1 or 2):", 1, 2);
    }

    public int intPrompt(String message) {
        return this.intPrompt(message, (List<Integer>) null);
    }

    public int intPrompt(String message, Integer... validOptions) {
        return this.intPrompt(message, Arrays.asList(validOptions));
    }

    /**
     * Prompt user for integer input.
     * @param message message to display beforehand
     * @param validOptions valid input numbers
     * @return input integer
     */
    public int intPrompt(String message, List<Integer> validOptions) {
        String number = this.prompt(message);
        while (true) {
            try {
                int result = Integer.parseInt(number);
                if (validOptions == null || validOptions.contains(result)) {
                    return result;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ignored) {
                number = this.invalidResponsePrompt();
            }
        }
    }

    public String invalidResponsePrompt() {
        return this.invalidResponsePrompt(false, false);
    }

    public String invalidResponsePrompt(boolean allowEmpty, boolean secure) {
        return this.prompt("Invalid response, please try again!", allowEmpty, secure);
    }

    public String loginPrompt() {
        return this.prompt("Please login to continue! Enter 'login' to login\n"
                + "Not registered? Just enter 'register' and I will help you setup!");
    }

    public String userExistPrompt() {
        return this.prompt("That username is taken, please try something else!");
    }

    public String noSuchUserPrompt() {
        return this.prompt("Username not found, please try again!");
    }

    public void clearedMsg(String type) {
        println("Done! Your " + type + " have been cleared");
    }

    public void abortMsg() {
        println("Aborted! No actions were taken");
    }

    /**
     * Helper function to print any object.
     * @param object to be printed.
     */
    public void showObject(Object object) {
        println(object);
    }

    /**
     * Added Message for new task.
     * @param task Task to be added.
     */
    public void addedMsg(Task task) {
        println("Got it, added the follow " + task.type() + "!");
        showObject(task);
    }

    /**
     * Delete Message for task.
     * @param task Task to be deleted.
     */
    public void deleteMsg(Task task) {
        println("Got it, " + task.type() + " will be deleted");
        showObject(task);
    }

    public void listMsg() {
        println("All modules in the list!");
    }

    public void listCcaMsg() {
        println("All ccas in the list!");
    }

    /**
     * Start up message upon running mod planner.
     */
    public void helloMsg() {
        showLine();
        print(
                "Welcome to ModPlan, your one stop solution to module planning!\n"
                + "Begin typing to get started!\n"
        );
        showLine();
    }

    /**
     * Ending message upon termination.
     */
    public void goodbyeMsg() {
        showLine();
        print(
                "Thanks for using ModPlan!\n"
                 + "Your data will be stored in file shortly!\n"
        );
        showLine();
    }

    /**
     * Message shown when clearing list.
     */
    public void clearMsg(String toClear) {
        println("Are you sure you want to clear your " + toClear + "?");
    }

    /**
     * Message shown at start of CapCommand.
     */
    public void capStartMsg() {
        println(
            "Start typing the module you have taken, along with it's letter grade\n"
            + "Type 'done' when you are ready to calculate your CAP");
    }

    /**
     * Requests input from user for which module to calculate CAP for.
     */
    public void capModStartMsg() {
        println("Type the module code that you want to predict your CAP for: ");
    }

    /**
     * Prints the module task list with which to calculate CAP from.
     */
    public void capListStartMsg(TaskList<ModuleTask> moduleTasksList) {
        println("Here is your list of modules to calculate CAP from");
        int counter = 1;
        for (ModuleTask temp : moduleTasksList) {
            print(counter++ + " ");
            showObject(temp);
        }
    }

    /**
     * When none of the modules in the ModuleTaskList are graded.
     */
    public void capListErrorMsg() {
        showLine();
        println("Please input grades into your listed modules using the grade command");
    }

    /**
     * Message to print average CAP to 2 decimal places.
     */
    public void capMsg(double averageCap) {
        showLine();
        println("Here is your current cumulative/predicted CAP", false);
        printf("%.2f\n", averageCap);
    }

    /**
     * Prints predicted CAP for a module based on its prerequisites.
     */
    public void capModMsg(double predictedCap, String moduleCode) {
        showLine();
        println("Here is your predicted CAP for "
            +
            moduleCode
            +
            " based on the modules you have taken.");
        printf("%.2f\n", predictedCap);
    }

    /**
     * Prints the list of modules that have not been graded/taken for prerequisite of another module.
     */
    public void capModuleIncompleteMsg(List<String> toCalculate) {
        int i = 0;
        showLine();
        println("Please complete any/all of the following prerequisite modules and add them to your list: ");
        while (i < toCalculate.size()) {
            println(toCalculate.get(i));
            i++;
        }
        println("If you have completed any of the above modules preclusions/co-requisites/equivalents,"
            +
                "please add the above modules to your module list with the same grade you obtained and try again.");
    }

    /**
     * Message to feedback to user that their grading has been added.
     */
    public void gradedMsg(String moduleCode, String letterGrade) {
        showLine();
        println("Got it, graded " + moduleCode + " with grade: " + letterGrade);
    }

    /**
     * Message to print the sorted module list.
     */
    public void sortMsg(String toSort) {
        println("Here are your sorted "
            +
            toSort
            +
            ":");
    }

    /**
     * Sorts by the order the user indicates and prints to the users.
     */
    public void showSorted(List<?> list) {
        showLine();
        for (Object object : list) {
            println(object);
        }
    }

    /**
     * Prints activities on the given dayOfWeek.
     */
    public void showSortedTimes(List<TaskWithMultipleWeeklyPeriod> list, DayOfWeek dayOfWeek) {
        showLine();
        for (TaskWithMultipleWeeklyPeriod task : list) {
            String taskNameAndPeriods = task.getName() + task.onWeekDayToString(dayOfWeek);
            println(taskNameAndPeriods);
        }
    }

    /**
     * Prints all tasks in upcomingTasksList.
     *
     * @param upcomingTasksList contains all upcoming tasks.
     */
    public void printUpcomingTasks(List<Task> upcomingTasksList) {
        if (upcomingTasksList.size() > 0) {
            println(LINE
                                +
                                "You have "
                                +
                                upcomingTasksList.size()
                                +
                                " upcoming tasks!\nHere's the list:");
            this.printTaskList(upcomingTasksList);
            println(LINE);
        }
    }

    /**
     * Prints every item supplied in the taskList parameter.
     *
     * @param taskList to be printed to user.
     */
    private <E extends Task> void printTaskList(List<E> taskList) {
        int count = 1;
        for (Task temp : taskList) {
            println(count + ". " + temp);
            count++;
        }
    }

    public void showUpdatedMsg() {
        println("Your module data files has been updated!");
    }

    //@@author kyawtsan99

    /**
     * Message to print out CoreModuleReport.
     */
    public void coreModReport() {
        println("Here is your list of core modules being added:");
    }

    /**
     * Message to print out the number of core modules left to take.
     */
    public void coreModLeft() {
        println("\n"
                +
                "Number of core modules required to take for graduation:");
    }

    /**
     * Message to print out GEModuleReport.
     */
    public void geModReport() {
        println("Here is your list of general education modules being added:");
    }

    /**
     * Message to print out the number of ge modules left to take.
     */
    public void geModLeft() {
        println("\n"
                +
                "Number of general education modules required to take for graduation:");
    }

    /**
     * Message to print out UEModuleReport.
     */
    public void ueModReport() {
        println("Here is your list of unrestricted elective modules being added:");
    }

    /**
     * Message to print out the number of ue modules left to take.
     */
    public void ueModLeft() {
        println("\n"
                +
                "Number of unrestricted elective modules required to take for graduation:");
    }

    /**
     * Message to print the reminder message.
     */
    public void reminderMsg() {
        showLine();
        println("Please remember to update your module information!\n"
                            +
                            "To do so, you can input the update command in the following format:\n"
                            +
                            "update module");
    }

    /**
     * Message to print the list of reminder options.
     */
    public void reminderList() {
        showLine();
        println("Would you like to set your reminder to every:\n"
                            +
                            "1) for 10 seconds\n"
                            +
                            "2) for 30 seconds\n"
                            +
                            "3) for 1 minute\n"
                            +
                            "4) for 2 minutes\n"
                            +
                            "*helpline* : for 1), enter 'reminder one'");
    }

}
