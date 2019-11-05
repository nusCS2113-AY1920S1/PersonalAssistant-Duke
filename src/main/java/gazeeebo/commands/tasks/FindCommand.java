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

public class FindCommand extends Command {
    /**
     * The string "find" has 4 characters.
     */
    static final int FIND_CHAR_COUNT = 4;
    /**
     * The string "find " has 5 characters.
     */
    static final int FIND_AND_SPACE_CHAR_COUNT = 5;


    /**
     * This class finds a task from the list of tasks when called.
     *
     * @param list          List of all tasks
     * @param ui            the object that deals with
     *                      printing things to the user
     * @param storage       The object that deals with storing data
     * @param commandStack
     * @param deletedTask
     * @param triviaManager
     * @throws DukeException  Throws custom exception when
     *                        format of find command is wrong
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
        try {
            if (ui.fullCommand.length() == FIND_AND_SPACE_CHAR_COUNT
            || ui.fullCommand.length() == FIND_CHAR_COUNT) {
                throw new DukeException("OOPS!!! The description of a"
                        + "search cannot be empty.");
            } else {
                ArrayList<Task> searchedlist = new ArrayList<Task>();
                for (Task it : list) {
                    if (it.description.contains(
                            ui.fullCommand.substring(FIND_CHAR_COUNT).trim())) {
                        searchedlist.add(it);
                    }
                }
                System.out.println("Here are the"
                        + "matching tasks in your list:");
                for (int i = 0; i < searchedlist.size(); i++) {
                    System.out.println(i + 1 + "."
                            + searchedlist.get(i).listFormat());
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
