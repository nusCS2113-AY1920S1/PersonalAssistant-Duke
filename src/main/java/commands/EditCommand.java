package commands;

import Storage.Storage;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.Todo;
import UI.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class EditCommand extends Command {
    /**
     * @param list    task lists
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that deals with storing data to the Save.txt file.
     * @throws IOException
     * @throws NullPointerException if tDate doesn't get updated.
     */

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws IOException, NullPointerException {
        String[] input = ui.FullCommand.split(" ");
        System.out.println("Edit description/time/both ?");
        int listno_index = Integer.parseInt(input[1]) - 1;
        ui.ReadCommand();
        if (ui.FullCommand.equals("description")) {
            new EditDescriptionCommand(list, ui, storage, listno_index);
        } else if (ui.FullCommand.equals("time")) {
            new EditTimeCommand(list, ui, storage, listno_index);
        } else {
            new EditBothCommand(list, ui, storage, listno_index);
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
