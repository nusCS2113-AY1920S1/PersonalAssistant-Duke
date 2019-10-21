package duke.util;

import duke.modules.Cca;

import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleTask;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Mod Planner inherits functionality from Original Duke Ui.
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
        System.out.println("Got it, added the follow module!");
        showObject(mod);
    }

    /**
     * Added Message for cca.
     * @param cca Cca to be added.
     */
    public void addedMsg(Cca cca) {
        System.out.println("Got it, added the follow cca!");
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
        System.out.println("Got it, module will be deleted");
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
        System.out.println(
                "Welcome to ModPlanner, your one stop solution to module planning!\n"
                + "Begin typing to get started!"
        );
        showLine();
    }

    /**
     * Ending message upon termination.
     */
    public void goodbyeMsg() {
        showLine();
        System.out.println(
                "Thanks for using ModPlanner!\n"
                 + "Your data will be stored in file shortly!\n"
        );
        showLine();
        closeScanner();
    }

    public void clearMsg(String toClear) {
        System.out.println("Are you sure you want to clear your " + toClear + "?");
    }

    /**
     * Message shown at start of CapCommand.
     */
    public void capStartMsg() {
        System.out.println("Start typing the module you have taken, along with it's letter grade");
        System.out.println("Type 'done' when you are ready to calculate your CAP");
    }

    /**
     * Message to print average CAP to 2 decimal places.
     */
    public void capMsg(double averageCap) {
        showLine();
        System.out.println("Here is your current cumulative/predicted CAP");
        System.out.printf("%.2f\n", averageCap);
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
     * Message to print out GEModuleReport.
     */
    public void geModReport() {
        System.out.println("Here is your list of general education modules being added:");
    }

    /**
     * Message to print out GEModuleReport.
     */
    public void ueModReport() {
        System.out.println("Here is your list of unrestricted elective modules being added:");
    }
}
