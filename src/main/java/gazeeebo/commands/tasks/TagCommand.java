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

/**
 * Groups tasks of the same tags together after
 * a task is tagged.
 */
public class TagCommand extends Command {
    /**
     * Adds a tag e.g. #study to a task.
     *
     * @param list          Task lists
     * @param ui            The object that deals with
     *                      printing things to the user.
     * @param storage       The object that deals with storing data.
     * @param commandStack  Stores the stack of previous commands
     * @param deletedTask   Stores the list of deleted tasks
     * @param triviaManager The object for TriviaManager
     * @throws DukeException  Throws custom exception when
     *                        format of tag command is wrong
     * @throws ParseException Catch error if parsing of commands fails
     * @throws IOException Catch error if reading of file fails
     */
    @Override
    public void execute(final ArrayList<Task> list,
                        final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException, IOException {
        try {
            if (ui.fullCommand.equals("#") || ui.fullCommand.equals("# ")) {
                throw new DukeException("The tag description cannot be empty.");
            } else {
                ArrayList<Task> tagList = new ArrayList<>();
                String tag = ui.fullCommand.substring(1);
                for (Task it : list) {
                    if (it.description.contains("#")) {
                        if (it.description.split("#")[1].trim().equals(tag)) {
                            tagList.add(it);
                        }
                    }
                }
                System.out.println("Here are the matching tags in your list:");
                for (int i = 0; i < tagList.size(); i++) {
                    System.out.println(i + 1 + "."
                            + tagList.get(i).listFormat());
                }
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
