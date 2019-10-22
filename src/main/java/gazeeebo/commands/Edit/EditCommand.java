package gazeeebo.commands.edit;

import gazeeebo.storage.Storage;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.commands.Command;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class EditCommand extends Command {
    /**
     * This method will receive the user's input on which list index to edit and then receive another user's input, checking he/she wants to edit the
     * task's description or time or both and execute them in the following methods respectively.
     *
     * @param list         task lists
     * @param ui           the object that deals with printing things to the user.
     * @param storage      the object that deals with storing data to the Save.txt file.
     * @param commandStack
     * @param deletedTask
     * @throws IOException
     * @throws NullPointerException if tDate doesn't get updated.
     */
    @Override
    public void execute(final ArrayList<Task> list, final Ui ui, final Storage storage, final Stack<String> commandStack, final ArrayList<Task> deletedTask, final TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        String[] input = ui.fullCommand.split(" ");
        System.out.println("Edit description/time/both ?");
        int listnoIndex = Integer.parseInt(input[1]) - 1;
        ui.readCommand();
        if (ui.fullCommand.equals("description")) {
            new EditDescriptionCommand(list, ui, listnoIndex);
        } else if (ui.fullCommand.equals("time")) {
            new EditTimeCommand(list, ui, listnoIndex);
        } else {
            new EditBothCommand(list, ui, listnoIndex);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + "\n");
        }
        storage.writeToSaveFile(sb.toString());
    }

    /**
     * Tells the main Duke class that the system should not exit and continue running.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
