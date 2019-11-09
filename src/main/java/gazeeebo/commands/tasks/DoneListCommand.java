//@@author e0323290

package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;

import gazeeebo.exception.DukeException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Allows user to see which tasks are done.
 */
public class DoneListCommand extends Command {
    /**
     * This class shows the list done tasks
     * that are completed when called.
     *
     * @param list          List of all tasks
     * @param ui            the object that deals with
     *                      printing things to the user
     * @param storage       The object that deals with storing data
     * @param commandStack
     * @param deletedTask
     * @param triviaManager
     * @throws DukeException  Throws custom exception when
     *                        format of done list command is wrong
     * @throws ParseException Catch error if parsing of command fails
     * @throws IOException    Catch error if the read file fails
     */
    @Override
    public void execute(final ArrayList<Task> list,
                        final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException, IOException {

        ArrayList<Task> doneList = new ArrayList<>();
        try {
            if (ui.fullCommand.equals("done")) {
                throw new DukeException("Command for 'done' cannot be empty.");
            }
            for (Task task : list) {
                if (task.isDone) {
                    doneList.add(task);
                }
            }

            if (ui.fullCommand.equals("done list")) {
                System.out.println("List of tasks that are done:");
                for (int i = 0; i < doneList.size(); i++) {
                    System.out.println(i + 1 + "."
                            + doneList.get(i).listFormat());

                }
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            storage.writeToSaveFile(sb.toString());
        } catch (DukeException e) {
            System.out.println(e.getMessage());

        }
    }

    /**
     * Program does not exit and continues running
     * since command "bye" is not called.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
