package gazeeebo.commands.note;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
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
        System.out.println("1. Edit goal: edit goal");
        System.out.println("2. Add module: add module");
        System.out.println("3. Delete module: delete module");
        System.out.println("4. View/edit module: module");
        System.out.println("5. Exit note page: esc");
        System.out.println("__________________________________________________________");
        ui.readCommand();
        GeneralNotePage gnp = new GeneralNotePage();
        while (!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals("edit goal")) {
                gnp.editGoal(ui);
            } else if (ui.fullCommand.equals("add module")) {
                gnp.addModule(ui);
            } else if (ui.fullCommand.equals("delete module")) {
                gnp.deleteModule(ui);
            } else if (ui.fullCommand.equals("module")) {
                //go to ModuleCommand
            } else {
                ui.showDontKnowErrorMessage();
            }
            ui.readCommand();
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
