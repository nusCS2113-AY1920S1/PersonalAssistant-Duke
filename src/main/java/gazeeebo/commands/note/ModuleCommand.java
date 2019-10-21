package gazeeebo.commands.note;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.notes.GeneralNotePage;
import gazeeebo.notes.Module;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class ModuleCommand extends Command {
    private Module findModule(String moduleName) throws DukeException {
        for (Module m : GeneralNotePage.modules) {
            if (m.name.equals(moduleName)) {
                return m;
            }
        }
        throw new DukeException("Sorry there is no such module.");
    }

    private void showListOfCommands() {
        System.out.println("__________________________________________________________");
        System.out.println("1. View module notes: view");
        System.out.println("2. Edit module name: edit name");
        System.out.println("3. Add assessment: add assmt");
        System.out.println("4. Edit assessment name: edit assmt");
        System.out.println("5. Edit assessment weightage: edit weightage");
        System.out.println("6. Delete assessment: delete assmt");
        System.out.println("7. Add miscellaneous information: add msc");
        System.out.println("8. Edit miscellaneous information: edit msc");
        System.out.println("9. Delete miscellaneous information: delete msc");
        System.out.println("10. View list of commands for module page: commands");
        System.out.println("11. View help page: help");
        System.out.println("12. View individual help: help COMMAND_NAME");
        System.out.println("13. Exit module notes: esc");
        System.out.println("__________________________________________________________");
    }

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        System.out.println("Welcome to your module page! Which module do you want to view/edit?");
        ui.readCommand();
        Module module;
        try {
            module = findModule(ui.fullCommand);
        } catch (DukeException d) {
            ui.showErrorMessage(d);
            System.out.println("Going back to notes page.");
            return;
        }
        System.out.println("What would you like to do?\n");
        showListOfCommands();
        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals("view")) {
                module.viewModule();
            } else if (ui.fullCommand.equals("edit name")) {
                module.editName(ui);
            } else if (ui.fullCommand.equals("add assmt")) {
                module.addAssessment(ui);
            } else if (ui.fullCommand.equals("edit assmt")) {
                module.editAssessmentName(ui);
            } else if (ui.fullCommand.equals("edit weightage")) {
                module.editAssessmentWeightage(ui);
            } else if (ui.fullCommand.equals("delete assmt")) {
                module.deleteAssessment(ui);
            } else if (ui.fullCommand.equals("add msc")) {
                module.addMiscellaneous(ui);
            } else if (ui.fullCommand.equals("edit msc")) {
                module.editMiscellaneous(ui);
            } else if (ui.fullCommand.equals("delete msc")) {
                module.deleteMiscellaneous(ui);
            } else if (ui.fullCommand.equals("commands")) {
                showListOfCommands();
            } else if (ui.fullCommand.split(" ")[0].equals("help")) {
                (new HelpCommand()).execute(null, ui, null, null, null, null);
            } else {
                ui.showDontKnowErrorMessage();
            }
            ui.readCommand();
        }
        System.out.println("Going back to notes page.");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
