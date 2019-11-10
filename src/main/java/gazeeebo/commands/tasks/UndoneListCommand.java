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

public class UndoneListCommand extends Command {
    /**
     * Displays the list of tasks that are marked undone.
     *
     * @param list          List of all tasks
     * @param ui            the object that deals with
     *                      printing things to the user
     * @param storage       The object that deals with storing data
     * @param commandStack  the stack of previous commands.
     * @param deletedTask   the list of deleted task.
     * @param triviaManager the object for triviaManager
     * @throws DukeException  Throws custom exception when
     *                        format of command is wrong
     * @throws ParseException Catch error if parsing of command fails
     * @throws IOException    Catch error if the read file fails
     */
    @Override
    public void execute(final ArrayList<Task> list, final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack, final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException, IOException {
        ArrayList<Task> undoneList = new ArrayList<>();
        try {
            if (ui.fullCommand.equals("undone")) {
                throw new DukeException("Command for 'undone' cannot be empty.");
            }

            for (Task task : list) {
                if (!task.isDone) {
                    undoneList.add(task);
                }
            }
            if (ui.fullCommand.equals("undone list")) {
                System.out.println("List of tasks that are undone:");
                for (int i = 0; i < undoneList.size(); i++) {
                    System.out.println(i + 1 + "." + undoneList.get(i).listFormat());
                }
            }
            StringBuilder sb = new StringBuilder();
            for (Task task : list) {
                sb.append(task.toString()).append("\n");
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
