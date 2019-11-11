//@@author JasonLeeWeiHern

package gazeeebo.commands.tasks.edit;

import gazeeebo.storage.Storage;
import gazeeebo.triviamanager.TriviaManager;
import gazeeebo.ui.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.commands.Command;
import gazeeebo.storage.TasksPageStorage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class EditCommand extends Command {
    /**
     * Receive the user's input on which
     * list index to edit and then receive another
     * user's input, checking he/she wants to edit the
     * task's description or time or both and
     * execute them in the following methods respectively.
     *
     * @param list         task lists
     * @param ui           the object that
     *                     deals with printing things to the user.
     * @param storage      the object that
     *                     deals with storing data to the Save.txt file.
     * @param commandStack keep stack of previous commands.
     * @param deletedTask  keep stack of deleted tasks.
     * @throws IOException catch the error if the read file fails.
     * @throws NullPointerException if tDate doesn't get updated.
     */
    @Override
    public void execute(final ArrayList<Task> list,
                        final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException,
            IOException, NullPointerException {
        try {
            String[] input = ui.fullCommand.split(" ");
            int listnoIndex = Integer.parseInt(input[1]) - 1;
            if (listnoIndex < list.size()) {
                System.out.println("Edit description/time/both ?");
                ui.readCommand();
                if (ui.fullCommand.equals("description")) {
                    new EditDescriptionCommand(list, ui, listnoIndex);
                } else if (ui.fullCommand.equals("time")) {
                    new EditTimeCommand(list, ui, listnoIndex);
                } else if (ui.fullCommand.equals("both")) {
                    new EditBothCommand(list, ui, listnoIndex);
                } else {
                    throw new ArrayIndexOutOfBoundsException();
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                TasksPageStorage tasksPageStorage = new TasksPageStorage();
                tasksPageStorage.writeToSaveFile(sb.toString());
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Please input the correct format.");
        }
    }

    /**
     * Tells the main Duke class that the
     * system should not exit and continue running.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
