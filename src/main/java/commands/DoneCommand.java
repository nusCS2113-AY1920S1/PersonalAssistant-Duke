package commands;
import Tasks.Task;
import UI.Ui;
import Storage.Storage;
import Exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class DoneCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException, NullPointerException {
        if (ui.FullCommand.equals("done")){
            throw new DukeException("The task done number cannot be empty.");
        }
        int numbercheck = Integer.parseInt(ui.FullCommand.substring(5)) - 1;
        list.get(numbercheck).isDone = true;

        System.out.println("Nice! I've marked this task as done: ");
        System.out.println("[" + list.get(numbercheck).getStatusIcon() + "]" + list.get(numbercheck).description);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getClass().getName().equals("Tasks.Deadline")) {
                sb.append(list.get(i).toString()+"\n");
            }

            else if(list.get(i).getClass().getName().equals("Tasks.Event")) {

                sb.append(list.get(i).toString()+"\n");
            }
            else if(list.get(i).getClass().getName().equals("Tasks.Timebound")) {
                sb.append(list.get(i).toString() + "\n");
            } else{
                sb.append(list.get(i).toString()+"\n");
            }
        }
        storage.Storages(sb.toString());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
