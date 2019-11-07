
package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.storage.Storage;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

import gazeeebo.tasks.Task;
import gazeeebo.tasks.DoAfter;

/**
 * Allows user to set a reminder task to be done after a certain task.
 */
public class DoAfterCommand extends Command {
    /**
<<<<<<< HEAD
     * @param list         task lists
     * @param ui           the object that deals with printing things to the user.
     * @param storage      the object that deals with storing data.
     * @param commandStack keep stack of previous commands.
     * @throws ParseException catch error if parse string to date fails.
     * @throws IOException catch error if read file fails.
     * @throws NullPointerException if tDate doesn't get updated.
=======
     * Creates a task to be done after the previous task
     * and sets a reminder when the previous task is done.
     *
     * @param list         Task lists
     * @param ui           The object that deals with
     *                     printing things to the user.
     * @param storage      The object that deals with storing data.
     * @param commandStack
     * @throws DukeException  Throws custom exception when
     *                        format of do after command is wrong
     * @throws ParseException
     * @throws IOException
>>>>>>> 7b39e99af9747527697bf0b67af93c7dfcdfe446
     */
    @Override
    public void execute(final ArrayList<Task> list,
                        final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException, IOException {

        String before = "";
        String after = "";
        String[] splitstring = ui.fullCommand.split("/after");
        before = splitstring[1];
        after = splitstring[0];
        DoAfter to = new DoAfter(before, before, after);
        list.add(to);
        System.out.println("Got it. I've added this task:");
        System.out.println(to.listFormat());

        System.out.println("Now you have "
                + list.size() + " tasks in the list.");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + "\n");
        }
        storage.writeToSaveFile(sb.toString());
    }

    /**
     * Program does not exit and continues running
     * since command "bye" is not called.
     * <p>
     * Tells the main Duke class that
     * the system should not exit and continue running.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
