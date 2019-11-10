//@@author jessteoxizhi

package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;

import java.io.IOException;

import gazeeebo.exception.DukeException;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * This class creates and adds a new deadline task.
 */
public class DeadlineCommand extends Command {
    /**
     * The string "deadline" has 6 characters.
     */
    static final int DEADLINE_CHAR_COUNT = 8;
    /**
     * The string "deadline " has 9 characters.
     */
    static final int DEADLINE_AND_SPACE_CHAR_COUNT = 9;

    /**
     * Adds deadline tasks to the list of tasks when called.
     *
     * @param list          List of all tasks
     * @param ui            the object that deals with
     *                      printing things to the user
     * @param storage       The object that deals with storing data
     * @param commandStack the stack of previous commands.
     * @param deletedTask the list of deleted task.
     * @param triviaManager the object for triviaManager
     * @throws DukeException  Throws custom exception when
     *                        format of deadline command is wrong
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
        String description;
        try {
            if (ui.fullCommand.length() == DEADLINE_CHAR_COUNT) {
                throw new DukeException("OOPS!!! The description"
                        + "of a deadline cannot be empty.");
            } else {
                description = ui.fullCommand.split("/by ")[0]
                        .substring(DEADLINE_AND_SPACE_CHAR_COUNT);
                triviaManager.learnInput(ui.fullCommand, storage);
            }
            Deadline d = new Deadline(description,
                    ui.fullCommand.split("/by ")[1]);
            list.add(d);
            System.out.println("Got it. I've added this task:");
            System.out.println(d.listFormat());
            System.out.println("Now you have " + list.size()
                    + " tasks in the list.");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            storage.writeToSaveFile(sb.toString());
        } catch (DukeException e) {
            System.out.println(e.getMessage());
            triviaManager.showPossibleInputs("deadline");
        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException a) {
            Ui.showDeadlineDateFormatError();
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
