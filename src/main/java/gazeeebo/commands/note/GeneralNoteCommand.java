package gazeeebo.commands.note;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.notes.GeneralNotePage;
import gazeeebo.storage.NotePageStorage;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Deals with the commands input at the note page.
 */
public class GeneralNoteCommand extends Command {

    private void showListOfCommands() {
        System.out.println("__________________________________________________________");
        System.out.println("1. View goal and list of modules: view");
        System.out.println("2. Edit goal: edit goal");
        System.out.println("3. Add a module: add module");
        System.out.println("4. Delete a module: delete module");
        System.out.println("5. View/edit a particular module: module");
        System.out.println("6. View list of commands for note page: commands");
        System.out.println("7. View help page: help");
        System.out.println("8. View individual help: help COMMAND_NAME");
        System.out.println("9. Exit note page: esc");
        System.out.println("__________________________________________________________");
    }

    /** Decodes the command input in the note page. */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        System.out.println("Welcome to your note page! What would you like to do?\n");
        showListOfCommands();
        ui.readCommand();
        GeneralNotePage gnp = new GeneralNotePage();
        while (!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals("view")) {
                gnp.viewGeneralNotePage();
            } else if (ui.fullCommand.equals("edit goal")) {
                gnp.editGoal(ui);
                NotePageStorage.writeToGoalFile();
            } else if (ui.fullCommand.equals("add module")) {
                gnp.addModule(ui);
                NotePageStorage.writeToModulesFile();
            } else if (ui.fullCommand.equals("delete module")) {
                gnp.deleteModule(ui);
                NotePageStorage.writeToModulesFile();
            } else if (ui.fullCommand.equals("module")) {
                (new ModuleCommand()).execute(null, ui, null, null, null, null);
            } else if (ui.fullCommand.equals("commands")) {
                showListOfCommands();
            } else if (ui.fullCommand.split(" ")[0].equals("help")) {
                (new HelpCommand()).execute(null, ui, null, null, null, null);
            } else {
                ui.showDontKnowErrorMessage();
            }
            ui.readCommand();
        }
        System.out.println("Going back to Main Menu.");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
