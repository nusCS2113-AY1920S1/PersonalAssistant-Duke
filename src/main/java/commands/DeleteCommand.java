package commands;

import Storage.Storage;
import Tasks.Task;
import UI.Ui;
import java.io.IOException;
import Exception.DukeException;
import java.text.ParseException;
import java.util.ArrayList;

public class DeleteCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException, NullPointerException {
        try {
            if (ui.FullCommand.length() == 6) {
                throw new DukeException("OOPS!!! The description of a deletion cannot be empty.");
            } else {
                int index = Integer.parseInt(ui.FullCommand.substring(6).trim()) - 1;
                String taskremoved = list.get(index).listformat();
                list.remove(index);
                System.out.println("Noted. I've removed this task: ");
                System.out.println(taskremoved);
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getClass().getName().equals("Tasks.Deadline")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Event")) {

                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.FixedDuration")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.DoAfter")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Timebound")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else {
                        sb.append(list.get(i).toString() + "\n");
                    }
                }
                storage.Storages(sb.toString());
            }
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }

}
