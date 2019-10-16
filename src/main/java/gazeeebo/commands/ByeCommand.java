package gazeeebo.commands;
import gazeeebo.Tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.Storage.Storage;
import gazeeebo.Exception.DukeException;
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
     * @return Void.
     * @throws DukeException | ParseException | IOException
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        System.out.println("Bye! Hope to see you again soon!");
    }
    @Override
    public boolean isExit() {
        return true;
    }
}
