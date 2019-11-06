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

public class ByeCommand extends Command {
    /**
     * Returns void execute function for gazeeebo.commands.ByeCommand.
     *
     * @param list An array list of type gazeeebo.Tasks.Task.
     * @param ui Class ui
     * @param storage class storage
     * @param commandStack
     * @param deletedTask
     * @throws DukeException | ParseException | IOException
     */
    @Override
    public void execute(final ArrayList<Task> list, final Ui ui, final Storage storage, final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask, final TriviaManager triviaManager) throws DukeException, ParseException,
            IOException {
        System.out.println("Bye! Hope to see you again soon!");
    }

    /**
     * Boolean to exit the program
     *
     * @return true to exit program
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
