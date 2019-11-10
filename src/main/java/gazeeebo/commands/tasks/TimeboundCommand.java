//@@author e0323290

package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;

import java.io.IOException;

import gazeeebo.exception.DukeException;
import gazeeebo.tasks.Timebound;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Adds tasks that has a time bound when called.
 */
public class TimeboundCommand extends Command {
    @Override
    /**
     * Executes commands to add task that has a time bound when called.
     *
     * @param list          Task list
     * @param ui            The object that deals with
     *                      printing things to the user.
     * @param storage       The object that deals with
     *                      storing data to the Save.txt file.
     * @param commandStack
     * @param deletedTask
     * @param triviaManager
     * @throws DukeException  Throws custom exception when
     *                        format of fixed duration command is wrong
     * @throws ParseException Catch error if parsing of command fails
     * @throws IOException    Catch error if the read file fails
     */
    public void execute(final ArrayList<Task> list,
                        final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException, IOException {
        final int periodCharacterCount = 6;
        final int totalCharacterCountOfTimeAndDate = 33;
        String description = "";
        String duration = ui.fullCommand.split("/")[1];
        try {
            if (duration.length() > periodCharacterCount
                    && duration.length() < totalCharacterCountOfTimeAndDate) {
                throw new DukeException("OOPS!!! There is no proper duration "
                        + "of time allocated for this task.");
            } else {
                description = ui.fullCommand.split("/between ")[0];
            }
            String period = ui.fullCommand.split("/between ")[1];

            Timebound tb = new Timebound(description, period);
            list.add(tb);
            System.out.println("Got it. I've added this task:");
            System.out.println(tb.listFormat());
            System.out.println("Now you have " + list.size()
                    + " tasks in the list.");
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
