package commands;

import Storage.Storage;
import Tasks.Task;
import UI.Ui;
import exception.DukeException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class UndoCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException, NullPointerException {
        try {
            if (ui.FullCommand.equals("undo")) {
                throw new DukeException("The undo task number cannot be empty.");
            }
            int numberCheck = Integer.parseInt(ui.FullCommand.substring(5)) - 1;
            if (list.get(numberCheck).isDone == false) {
                System.out.println("Cannot undo a task that is not done.");
            } else if (list.get(numberCheck).isDone == true) {
               list.get(numberCheck).isDone = false;
               System.out.println("I've marked this task as undone: ");
               System.out.println(list.get(numberCheck).listFormat());
           }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            storage.Storages(sb.toString());
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
