//@@author yueyuu
package gazeeebo.parsers;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.commands.note.ModuleCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.notes.GeneralNotePage;
import gazeeebo.storage.NotePageStorage;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import javax.swing.text.View;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Deals with the commands input at the note page.
 */
public class GeneralNoteCommandParser extends Command {

    private static final String ESC = "esc";
    private static final String VIEW = "view";
    private static final String EDIT_GOAL = "edit goal";
    private static final String ADD_MODULE = "add module";
    private static final String DELETE_MODULE = "delete module";
    private static final String MODULE = "module";
    private static final String COMMANDS = "commands";
    private static final String HELP = "help";

    private void showListOfCommands() {
        System.out.println("__________________________________________________________");
        System.out.println("1. View goal and list of modules: " + VIEW);
        System.out.println("2. Edit goal: " + EDIT_GOAL);
        System.out.println("3. Add a module: " + ADD_MODULE);
        System.out.println("4. Delete a module: " + DELETE_MODULE);
        System.out.println("5. View/edit a particular module: " + MODULE);
        System.out.println("6. View list of commands for note page: " + COMMANDS);
        System.out.println("7. View help page: " + HELP);
        System.out.println("8. View individual help: help COMMAND_NAME");
        System.out.println("9. Exit note page: " + ESC);
        System.out.println("__________________________________________________________");
    }

    /** Decodes the command input in the note page. */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        System.out.println("Welcome to your note page! What would you like to do?\n");
        showListOfCommands();
        ui.readCommand();
        GeneralNotePage gnp = new GeneralNotePage();
        while (!ui.fullCommand.equals(ESC)) {
            if (ui.fullCommand.equals(VIEW)) {
                gnp.viewGeneralNotePage();
            } else if (ui.fullCommand.equals(EDIT_GOAL)) {
                gnp.editGoal(ui);
                NotePageStorage.writeToGoalFile();
            } else if (ui.fullCommand.equals(ADD_MODULE)) {
                gnp.addModule(ui);
                NotePageStorage.writeToModulesFile();
            } else if (ui.fullCommand.equals(DELETE_MODULE)) {
                gnp.deleteModule(ui);
                NotePageStorage.writeToModulesFile();
            } else if (ui.fullCommand.equals(MODULE)) {
                (new ModuleCommand()).execute(null, ui, null, null, null, null);
            } else if (ui.fullCommand.equals(COMMANDS)) {
                showListOfCommands();
            } else if (ui.fullCommand.split(" ")[0].equals(HELP)) {
                (new HelpCommand()).execute(null, ui, null, null, null, null);
            } else {
                ui.showDontKnowErrorMessage();
            }
            ui.readCommand();
        }
        System.out.println("Go back to Main Menu...\n" +
                "Content Page:\n" +
                "------------------ \n" +
                "1. help\n" +
                "2. contacts\n" +
                "3. expenses\n" +
                "4. places\n" +
                "5. tasks\n" +
                "6. cap\n" +
                "7. spec\n" +
                "8. moduleplanner\n" +
                "9. notes\n");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
