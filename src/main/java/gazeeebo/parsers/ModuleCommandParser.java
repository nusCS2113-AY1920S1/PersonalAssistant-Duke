//@@author yueyuu

package gazeeebo.parsers;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.notes.GeneralNotePage;
import gazeeebo.notes.Module;
import gazeeebo.parsers.GeneralNoteCommandParser;
import gazeeebo.storage.NotePageStorage;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Deals with the commands input on the module page.
 */
public class ModuleCommandParser extends Command {

    private static final String VIEW = "view";
    private static final String EDIT_NAME = "edit name";
    private static final String ADD_ASSMT = "add assmt";
    private static final String EDIT_ASSMT = "edit assmt";
    private static final String EDIT_WEIGHTAGE = "edit weightage";
    private static final String DELETE_ASSMT = "delete assmt";
    private static final String ADD_MSC = "add msc";
    private static final String EDIT_MSC = "edit msc";
    private static final String DELETE_MSC = "delete msc";
    private static final String COMMANDS = "commands";
    private static final String HELP = "help";
    private static final String ESC = "esc";

    /**
     * Checks if the module specified exist.
     *
     * @param moduleName the module to be found
     * @return the Module if found
     * @throws DukeException if the module does not exist
     */
    private Module findModule(String moduleName) throws DukeException {
        for (Module m: GeneralNotePage.modules) {
            if (m.name.equals(moduleName)) {
                return m;
            }
        }
        throw new DukeException("Sorry there is no such module.");
    }

    private void showListOfCommands() {
        System.out.println("__________________________________________________________");
        System.out.println("1. View module notes: " + VIEW);
        System.out.println("2. Edit module name: " + EDIT_NAME);
        System.out.println("3. Add an assessment: " + ADD_ASSMT); //add assmt /n nameofassmt /w weightage
        System.out.println("4. Edit an assessment name: " + EDIT_ASSMT);
        System.out.println("5. Edit an assessment's weightage: " + EDIT_WEIGHTAGE);
        System.out.println("6. Delete an assessment: " + DELETE_ASSMT);
        System.out.println("7. Add a miscellaneous information: " + ADD_MSC);
        System.out.println("8. Edit a miscellaneous information: " + EDIT_MSC);
        System.out.println("9. Delete a miscellaneous information: " + DELETE_MSC);
        System.out.println("10. View list of commands for module page: " + COMMANDS);
        System.out.println("11. View help page: " + HELP);
        System.out.println("12. View individual help: help COMMAND_NAME");
        System.out.println("13. Exit module notes: " + ESC);
        System.out.println("__________________________________________________________");
    }

    /** Decodes the commands input on the module page. */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack,
                        ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException,
            ParseException, IOException, NullPointerException {
        //System.out.println("Welcome to your module page! Which module do you want to view/edit?");
        //ui.readCommand();
        Module module;
        if (ui.fullCommand.isEmpty()) {
            throw new DukeException("Please input a module name.");
        }
        module = findModule(ui.fullCommand);
        System.out.println("Welcome to your module page! What would you like to do?\n");
        showListOfCommands();
        ui.readCommand();
        while (!ui.fullCommand.equals(ESC)) {
            String[] commands = ui.fullCommand.split(" /n", 2);
            try {
                if (ui.fullCommand.equals(VIEW)) {
                    module.viewModule();
                } else if (commands[0].equals(EDIT_NAME)) {
                    module.editName(commands[1].trim());
                    NotePageStorage.writeToModulesFile();
                } else if (commands[0].equals(ADD_ASSMT)) {
                    module.addAssessment(commands[1].trim());
                    NotePageStorage.writeToModulesFile();
                } else if (commands[0].equals(EDIT_ASSMT)) {
                    module.editAssessmentName(commands[1].trim());
                    NotePageStorage.writeToModulesFile();
                } else if (commands[0].equals(EDIT_WEIGHTAGE)) {
                    module.editAssessmentWeightage(commands[1].trim());
                    NotePageStorage.writeToModulesFile();
                } else if (commands[0].equals(DELETE_ASSMT)) {
                    module.deleteAssessment(commands[1].trim());
                    NotePageStorage.writeToModulesFile();
                } else if (commands[0].equals(ADD_MSC)) {
                    module.addMiscellaneous(commands[1].trim());
                    NotePageStorage.writeToModulesFile();
                } else if (commands[0].equals(EDIT_MSC)) {
                    module.editMiscellaneous(commands[1].trim());
                    NotePageStorage.writeToModulesFile();
                } else if (commands[0].equals(DELETE_MSC)) {
                    module.deleteMiscellaneous(commands[1].trim());
                    NotePageStorage.writeToModulesFile();
                } else if (commands[0].equals(COMMANDS)) {
                    showListOfCommands();
                } else if (commands[0].equals(HELP)) {
                    (new HelpCommand()).execute(null, ui, null, null, null, null);
                } else {
                    ui.showDontKnowErrorMessage();
                }
            } catch (IndexOutOfBoundsException i) {
                /*
                switch (commands[0]) {
                case EDIT_GOAL:
                    System.out.println("Please input the command " +
                            "in the format \'edit /n NEW_GOAL\'.");
                    break;
                case ADD_MODULE:
                    System.out.println("Please input the command " +
                            "in the format \'add /n MODULE_CODE\'.");
                    break;
                case DELETE_MODULE:
                    System.out.println("Please input the command " +
                            "in the format \'delete /n MODULE_CODE\'.");
                    break;
                default:
                    ui.showDontKnowErrorMessage();
                    break;
                }

                 */
                System.out.println("format error message.");
            } catch (DukeException d) {
                ui.showErrorMessage(d);
            }
            ui.readCommand();
        }
        System.out.println("Going back to notes page...\n");
        GeneralNoteCommandParser.showListOfCommands();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
