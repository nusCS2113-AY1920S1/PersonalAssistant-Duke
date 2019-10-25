package gazeeebo.notes;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A note page that can record a goal and module information.
 */
public class GeneralNotePage {
    private static String goal = "";
    public static ArrayList<Module> modules = new ArrayList<>();

    /**
     * Prints out the goal and the list of existing module names.
     */
    public void viewGeneralNotePage() {
        System.out.println("Goal: " + goal);
        System.out.print("\n");
        System.out.println("Modules:");
        for (int i = 0; i < modules.size(); i++) {
            System.out.println((i+1) + ". " + modules.get(i).name);
        }
    }

    /**
     * Edits the goal to what the user specifies.
     *
     * @param ui to read the user's input
     * @throws IOException if the command input by the user cannot be read
     */
    public void editGoal(Ui ui) throws IOException {
        System.out.println("What is your new goal?");
        ui.readCommand();
        goal = ui.fullCommand;
        System.out.println("Okay we have successfully updated your goal to:");
        System.out.println(goal);
    }

    /**
     * Adds a module to the general note page. Prevents duplicate modules from being added.
     *
     * @param ui to read the user's input
     * @throws IOException if the command input by the user cannot be read
     */
    public void addModule(Ui ui) throws IOException {
        System.out.println("What module do you want to add?");
        ui.readCommand();
        for (Module m : modules) {
            if (m.name.equals(ui.fullCommand)) {
                System.out.println("You already have a module with the same name. Please add a module with a different name.");
                return;
            }
        }
        modules.add(new Module(ui.fullCommand));
        System.out.println("Okay we have successfully added this module:");
        System.out.println(ui.fullCommand);
    }

    /**
     * Deletes a module and all its content, if it exists, from the general note page.
     *
     * @param ui to read the user's input
     * @throws IOException if the command input by the user cannot be read
     */
    public void deleteModule(Ui ui) throws IOException {
        System.out.println("Which module do you want to delete?");
        ui.readCommand(); //input module name here
        for (Module m : modules) {
            if (m.name.equals(ui.fullCommand)) {
                modules.remove(m);
                System.out.println("Okay we have successfully deleted this module:");
                System.out.println(ui.fullCommand);
                return;
            }
        }
        System.out.println("Sorry there is no such module.");
    }
}
