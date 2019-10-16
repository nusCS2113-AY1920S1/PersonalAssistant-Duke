package gazeeebo.commands.Edit;

import gazeeebo.Storage.Storage;
import gazeeebo.Tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.Exception.DukeException;
import gazeeebo.commands.Command;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class EditCommand extends Command {
    /**
     * This method will receive the user's input on which list index to edit and then receive another user's input, checking he/she wants to edit the
     * task's description or time or both and execute them in the following methods respectively.
     * @param list    task lists
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that deals with storing data to the Save.txt file.
     * @param commandStack
     * @param deletedTask
     * @throws IOException
     * @throws NullPointerException if tDate doesn't get updated.
     */

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        String[] input = ui.FullCommand.split(" ");
        System.out.println("Edit description/time/both ?");
        int listno_index = Integer.parseInt(input[1]) - 1;
        ui.ReadCommand();
        if (ui.FullCommand.equals("description")) {
            new EditDescriptionCommand(list, ui, listno_index);
        } else if (ui.FullCommand.equals("time")) {
            new EditTimeCommand(list, ui, listno_index);
        } else {
            new EditBothCommand(list, ui, listno_index);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + "\n");
        }
        storage.Storages(sb.toString());
    }

    /**
     * Tells the main Duke class that the system should not exit and continue running
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
