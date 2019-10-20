package gazeeebo.commands.note;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.notes.GeneralNotePage;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class GeneralNoteCommand extends Command {

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        System.out.println("Welcome to your note page! What would you like to do?\n");
        System.out.println("__________________________________________________________");
        System.out.println("1. View goal and list of modules: view");
        System.out.println("2. Edit goal: edit goal");
        System.out.println("3. Add module: add module");
        System.out.println("4. Delete module: delete module");
        System.out.println("5. View/edit a particular module: module");
        System.out.println("6. Exit note page: esc");
        System.out.println("__________________________________________________________");
        ui.readCommand();
        GeneralNotePage gnp = new GeneralNotePage();
        while (!ui.fullCommand.equals("esc")) {
            switch (ui.fullCommand) {
            case "view":
                gnp.viewGeneralNotePage();
                break;
            case "edit goal":
                gnp.editGoal(ui);
                break;
            case "add module":
                gnp.addModule(ui);
                break;
            case "delete module":
                gnp.deleteModule(ui);
                break;
            case "module":
                (new ModuleCommand()).execute(null, ui, null, null, null, null);
                break;
            case "help":
                (new HelpCommand()).execute(null, ui, null, null, null, null);
                break;
            default:
                ui.showDontKnowErrorMessage();
                break;
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
