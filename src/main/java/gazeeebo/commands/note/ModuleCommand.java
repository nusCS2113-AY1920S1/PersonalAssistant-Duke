package gazeeebo.commands.note;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
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
    public Module findModule(String moduleName) throws DukeException {
        for (Module m : GeneralNotePage.modules) {
            if (m.name.equals(moduleName)) {
                return m;
            }
        }
        throw new DukeException("Sorry there is no such module.");
    }

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        System.out.println("Welcome to your module page! Which module do you want to view/edit?");
        ui.readCommand();
        Module module = null;
        try {
            module = findModule(ui.fullCommand);
        } catch (DukeException d) {
            ui.showErrorMessage(d);
            System.out.println("Going back to note page.");
            return;
        }
        assert module != null : "Bug in ModuleCommand";
        System.out.println("What would you like to do?\n");
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
        System.out.println("10. Exit module notes: esc");
        System.out.println("__________________________________________________________");
        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {
            switch (ui.fullCommand) {
            case "view":
                module.viewModule();
                break;
            case "edit name":
                module.editName(ui);
                break;
            case "add assmt":
                module.addAssessment(ui);
                break;
            case "edit assmt":
                module.editAssessmentName(ui);
                break;
            case "edit weightage":
                module.editAssessmentWeightage(ui);
                break;
            case "delete assmt":
                module.deleteAssessment(ui);
                break;
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
