//@@author yueyuu
package gazeeebo.notes;

import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;

import java.util.ArrayList;

/**
 * A note page that can record a goal and module information.
 */
public class GeneralNotePage {
    public static String goal = "";
    public static ArrayList<Module> modules = new ArrayList<>();
    private static final String EMPTY_DESCRIPTION = "The description of the command cannot be empty.";


    /**
     * Prints out the goal and the list of existing module names.
     */
    public void viewGeneralNotePage() {
        System.out.println("Goal: " + goal);
        System.out.print("\n");
        System.out.println("Modules:");
        for (int i = 0; i < modules.size(); i++) {
            System.out.println((i + 1) + ". " + modules.get(i).name);
        }
    }

    /**
     * Edits the goal to what the user specifies.
     *
     * @param userGoal the new goal the user want to use
     * @throws DukeException if the command inputted is in the wrong format
     */
    public void editGoal(String userGoal) throws DukeException {
        if (userGoal.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        goal = userGoal.trim();
        System.out.println("Okay we have successfully updated your goal to:");
        System.out.println(goal);
    }

    /**
     * Adds a module to the general note page. Prevents duplicate modules from being added.
     *
     * @param moduleName the module that the user wants to add
     * @throws DukeException if the command inputted is in the wrong format
     */
    public void addModule(String moduleName) throws DukeException {
        if (moduleName.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        for (Module m : modules) {
            if (m.name.equals(moduleName)) {
                System.out.println("You already have a module with the same name. Please add a module with a different name.");
                return;
            }
        }
        modules.add(new Module(moduleName));
        System.out.println("Okay we have successfully added this module:");
        System.out.println(moduleName);
    }

    /**
     * Deletes a module and all its content, if it exists, from the general note page.
     *
     * @param ui to read the user's input
     * @throws DukeException if the command inputted by the user is in the wrong format
     */
    public void deleteModule(String moduleName) throws DukeException {
        //System.out.println("Which module do you want to delete?");
        //ui.readCommand(); //input module name here
        if (moduleName.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        for (Module m : modules) {
            if (m.name.equals(moduleName)) {
                modules.remove(m);
                System.out.println("Okay we have successfully deleted this module:");
                System.out.println(moduleName);
                return;
            }
        }
        System.out.println("Sorry there is no such module.");
    }

}
