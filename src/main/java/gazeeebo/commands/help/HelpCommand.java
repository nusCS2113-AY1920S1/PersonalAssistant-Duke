//@@author yueyuu

package gazeeebo.commands.help;

import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.commands.Command;
import gazeeebo.help.HelpText;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class HelpCommand extends Command {

    //private static final String TASKS_PAGE = "tasks page";
    private static final String TASKS = "tasks";
    private static final String PLACES = "places";
    private static final String NOTES = "notes";
    private static final String MODULE = "module";
    private static final String CONTACTS = "contacts";
    private static final String CAP = "cap";
    private static final String EXPENSES = "expenses";
    private static final String SPECIALIZATION = "spec";

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, IOException, NullPointerException {
        //gazeeebo.help COMMAND or just gazeeebo.help
        //description of a gazeeebo.help can be empty
        HelpText help = new HelpText();
        String description;
        String[] command = ui.fullCommand.split(" ", 2);
        assert command.length != 0 : "Bug in parser that affects HelpCommand";
        if (command.length == 1) {
            description = HelpText.COMMAND_FORMAT + System.lineSeparator() + System.lineSeparator() +
                    HelpText.COMMANDS_HEADER +
                    HelpText.TASKS_PAGE +
                    HelpText.PLACES_PAGE +
                    HelpText.NOTES_PAGE +
                    HelpText.MODULE_PAGE +
                    HelpText.CONTACTS_PAGE +
                    HelpText.CAP_PAGE +
                    HelpText.EXPENSES_PAGE +
                    HelpText.SPECIALIZATION_PAGE;
        } else {
            switch (command[1]) {
            case TASKS: description = HelpText.TASKS_PAGE;
                break;
            case PLACES: description = HelpText.PLACES_PAGE;
                break;
            case NOTES: description = HelpText.NOTES_PAGE;
                break;
            case MODULE: description = HelpText.MODULE_PAGE;
                break;
            case CONTACTS: description = HelpText.CONTACTS_PAGE;
                break;
            case CAP: description = HelpText.CAP_PAGE;
                break;
            case EXPENSES: description = HelpText.EXPENSES_PAGE;
                break;
            case SPECIALIZATION: description = HelpText.SPECIALIZATION_PAGE;
                break;
            default:
                description = "OOPS!!! There is no such command.";
                break;
            }
        }
        System.out.println(description);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
