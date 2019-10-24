package planner.util.commons;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import planner.modules.inherited.Cca;
import planner.modules.data.ModuleTask;
import planner.modules.inherited.Task;

/**
 * Mod Planner based on morphed implementation of Duke.
 */
public class PlannerUi {

    private Scanner scan;
    private static final String LINE = "_______________________________";
    private static Set<String> yes = new HashSet<>(Arrays.asList("y", "yes", "true", "1", "confirm", "t"));
    private static Set<String> no = new HashSet<>(Arrays.asList("n", "no", "false", "0", "f"));

    /**
     * Default constructor for Ui.
     */
    public PlannerUi() {
        scan = new Scanner(System.in);
    }

    public void print(Object object) {
        System.out.print(object.toString());
    }

    public void println(Object object) {
        System.out.println(object.toString());
    }

    public void showLine() {
        System.out.println(LINE);
    }

    private void closeScanner() {
        scan.close();
    }

    public String readCommand() {
        return scan.nextLine().strip();
    }

    /**
     * Confirm user's action.
     * @return true if user confirms else false
     */
    public boolean confirm() {
        boolean result = true;
        while (result) {
            String input = this.readCommand();
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

    public void clearedMsg(String type) {
        System.out.println("Done! Your " + type + " have been cleared");
    }

    public void abortMsg() {
        System.out.println("Aborted! No actions were taken");
    }

    /**
     * Helper function to print any object.
     * @param mod to be printed.
     */
    public void showObject(ModuleTask mod) {
        System.out.println(mod);
    }

    /**
     * Added Message for new mods.
     * @param mod Module Tasks to be added.
     */
    public void addedMsg(ModuleTask mod) {
        System.out.println("Got it, added the following module!");
        showObject(mod);
    }

    /**
     * Added Message for cca.
     * @param cca Cca to be added.
     */
    public void addedMsg(Cca cca) {
        System.out.println("Got it, added the following cca!");
        println(cca);
    }

    /**
     * Delete Message for new mods.
     * @param mod Module Tasks to be added.
     */
    public void deleteMsg(ModuleTask mod) {
        System.out.println("Got it, module will be deleted");
        showObject(mod);
    }

    /**
     * Delete Message for cca.
     * @param cca Cca to be deleted.
     */
    public void deleteMsg(Cca cca) {
        System.out.println("Got it, cca will be deleted");
        println(cca);
    }

    public void listMsg() {
        System.out.println("All modules in the list!");
    }

    public void listCcaMsg() {
        System.out.println("All ccas in the list!");
    }

    /**
     * Start up message upon running mod planner.
     */
    public void helloMsg() {
        showLine();
        System.out.print(
                "Welcome to ModPlanner, your one stop solution to module planning!\n"
                + "Begin typing to get started!\n"
        );
        showLine();
    }

    /**
     * Ending message upon termination.
     */
    public void goodbyeMsg() {
        showLine();
        System.out.print(
                "Thanks for using ModPlanner!\n"
                 + "Your data will be stored in file shortly!\n"
        );
        showLine();
        closeScanner();
    }

    /**
     * Message shown when clearing list.
     */
    public void clearMsg(String toClear) {
        System.out.println("Are you sure you want to clear your " + toClear + "?");
    }

    /**
     * Message shown at start of CapCommand.
     */
    public void capStartMsg() {
        System.out.println(
            "Start typing the module you have taken, along with it's letter grade\n"
            + "Type 'done' when you are ready to calculate your CAP");
    }

    /**
     * Requests input from user for which module to calculate CAP for.
     */
    public void capModStartMsg() {
        System.out.println("Type the module code that you want to predict your CAP for: ");
    }

    /**
     * Prints the module task list with which to calculate CAP from.
     */
    public void capListStartMsg(List<ModuleTask> moduleTasksList) {
        System.out.println("Here is your list of modules to calculate CAP from.");
        int counter = 1;
        for (ModuleTask temp : moduleTasksList) {
            System.out.print(counter++ + " ");
            showObject(temp);
        }
    }

    /**
     * When none of the modules in the ModuleTaskList are graded.
     */
    public void capListErrorMsg() {
        showLine();
        System.out.println("Please input grades into your listed modules using the grade command");
    }

    /**
     * Message to print average CAP to 2 decimal places.
     */
    public void capMsg(double averageCap) {
        showLine();
        System.out.println("Here is your current cumulative/predicted CAP!");
        System.out.printf("%.2f\n", averageCap);
    }

    /**
     * Prints predicted CAP for a module based on its prerequisites.
     */
    public void capModMsg(double predictedCap, String moduleCode) {
        showLine();
        System.out.println("Here is your predicted CAP for " + moduleCode);
        System.out.printf("%.2f\n", predictedCap);
    }

    /**
     * Prints the list of modules that have not been graded/taken for prerequisite of another module.
     */
    public void capModuleIncompleteMsg(List<List<String>> toCalculate) {
        int i = 0;
        showLine();
        System.out.println("Please complete the following prerequisite modules: ");
        while (i < toCalculate.size()) {
            if (!toCalculate.get(i).isEmpty()) {
                for (String x : toCalculate.get(i)) {
                    System.out.print(x + " or ");
                }
                System.out.print("\n");
            }
            i++;
        }
    }

    /**
     * Message to feedback to user that their grading has been added.
     */
    public void gradedMsg(String moduleCode, String letterGrade) {
        showLine();
        System.out.println("Got it, graded " + moduleCode + " with grade: " + letterGrade);
    }


    /**
     * Message to print the sorted module list.
     */
    public void sortMsg(String toSort) {
        System.out.println("Here are your sorted " + toSort + ":");
    }

    /**
     * Sorts by ascending order and prints to the users.
     */
    public void showSorted(List<?> list) {
        showLine();
        for (Object object : list) {
            System.out.println(object);
        }
    }

    /**
     * Message to print out CoreModuleReport.
     */
    public void coreModReport() {
        System.out.println("Here is your list of core modules being added:");
    }

    /**
     * Message to print out the number of core modules left to take.
     */
    public void coreModLeft() {
        System.out.println("\n" + "Number of core modules required to take for graduation:");
    }

    /**
     * Message to print out GEModuleReport.
     */
    public void geModReport() {
        System.out.println("Here is your list of general education modules being added:");
    }

    /**
     * Message to print out the number of ge modules left to take.
     */
    public void geModLeft() {
        System.out.println("\n" + "Number of general education modules required to take for graduation:");
    }

    /**
     * Message to print out UEModuleReport.
     */
    public void ueModReport() {
        System.out.println("Here is your list of unrestricted elective modules being added:");
    }

    /**
     * Message to print out the number of ue modules left to take.
     */
    public void ueModLeft() {
        System.out.println("\n" + "Number of unrestricted elective modules required to take for graduation:");
    }

    /**
     * Prints all tasks in upcomingTasksList.
     *
     * @param upcomingTasksList contains all upcoming tasks.
     */
    public void printUpcomingTasks(List<Task> upcomingTasksList) {
        if (upcomingTasksList.size() > 0) {
            System.out.println(LINE + "You have " + upcomingTasksList.size() + " upcoming tasks!\nHere's the list:");
            this.printTaskList(upcomingTasksList);
            System.out.println(LINE);
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
            System.out.println(count + ". " + temp);
            count++;
        }
    }

    /**
     * Message to print the sorted module list.
     */
    public void sortModuleMsg() {
        System.out.println("Here are your modules in your requested orders!");
    }

    /**
     * Sorts the module by the requested order and prints to the users.
     * @param mods List of modules the student is taking.
     */
    public void showSortedModules(List<ModuleTask> mods) {
        showLine();
        for (ModuleTask hold : mods) {
            System.out.println(hold);
        }
    }
}
