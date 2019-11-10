//@@author jessteoxizhi

package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Shows the list of tasks recorded.
 */
public class ListCommand extends Command {
    /**
     * Displays the list of tasks when called.
     *
     * @param list          Task list
     * @param ui            The object that deals with
     *                      printing things to the user.
     * @param storage       The object that deals with
     *                      storing data to the Save.txt file.
     * @param commandStack the stack of previous commands.
     * @param deletedTask the list of deleted task.
     * @param triviaManager the object for triviaManager
     * @throws ParseException Catch error if parsing of command fails
     * @throws IOException    Catch error if the read file fails
     */
    @Override
    public void execute(final ArrayList<Task> list,
                        final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws ParseException, IOException {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + "." + list.get(i).listFormat());
        }
        ui.showProgessiveBar(list);
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
