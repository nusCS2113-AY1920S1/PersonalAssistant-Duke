//@@author e0323290

package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class UndoneCommand extends Command {
    /**
     * Allows users to mark their done tasks to undone if
     * they have marked the tasks done by mistake.
     *
     * @param list          List of all tasks
     * @param ui            the object that deals with
     *                      printing things to the user
     * @param storage       The object that deals with storing data
     * @param commandStack  the stack of previous commands.
     * @param deletedTask   the list of deleted task.
     * @param triviaManager the object for triviaManager
     * @throws DukeException  Throws custom exception when undo
     *                        format of command is wrong
     * @throws ParseException Catch error if parsing of command fails
     * @throws IOException    Catch error if the read file fails
     */
    @Override
    public void execute(final ArrayList<Task> list, final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack, final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException, IOException {
        int numberCheck = Integer.parseInt(ui.fullCommand.substring(6).trim()) - 1;
        try {
            if (ui.fullCommand.equals("undone")) {
                throw new DukeException("The undo task number cannot be empty.");
            } else if (ui.fullCommand.contains("undone")) {
                if (numberCheck < 0) {
                    throw new DukeException("Task number cannot be negative.");
                } else if (numberCheck > list.size() - 1) {
                    throw new DukeException("Task number does not exist.");
                }
                if (list.get(numberCheck).isDone) {
                    list.get(numberCheck).isDone = false;
                }

                System.out.println("Nice! I've marked this task as undone: ");
                System.out.println(list.get(numberCheck).listFormat());

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.writeToSaveFile(sb.toString());
            }
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
