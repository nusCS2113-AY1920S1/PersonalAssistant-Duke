//@@author jessteoxizhi

package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.storage.TasksPageStorage;
import gazeeebo.storage.TriviaStorage;
import gazeeebo.tasks.Task;
import gazeeebo.triviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.tasks.Todo;
import gazeeebo.storage.Storage;
import gazeeebo.exception.DukeException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class TodoCommand extends Command {

    /**
     * Adding a task of todo.
     *
     * @param list          List of all tasks
     * @param ui            the object that deals with
     *                      printing things to the user
     * @param storage       The object that deals with storing data
     * @param commandStack the stack of previous commands.
     * @param deletedTask the list of deleted task.
     * @param triviaManager the object for triviaManager
     * @throws DukeException  Throws custom exception when
     *                        format of command is wrong
     * @throws ParseException Catch error if parsing of command fails
     * @throws IOException    Catch error if the read file fails
     */

    @Override
    public void execute(ArrayList<Task> list, final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack, final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager) throws DukeException, ParseException, IOException {
        String description = "";
        try {
            TriviaStorage triviaStorage = new TriviaStorage();
            if (ui.fullCommand.length() <= 4) {
                throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
            } else {
                description = ui.fullCommand.substring(5);
                triviaManager.learnInput(ui.fullCommand, triviaStorage);
            }
            Todo to = new Todo(description);
            list.add(to);
            System.out.println("Got it. I've added this task:");
            System.out.println(to.listFormat());
            System.out.println("Now you have " + list.size() + " tasks in the list.");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            TasksPageStorage tasksPageStorage = new TasksPageStorage();
            tasksPageStorage.writeToSaveFile(sb.toString());
        } catch (DukeException e) {
            System.out.println(e.getMessage());
            triviaManager.showPossibleInputs("todo");
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