package commands;
<<<<<<< HEAD
=======

>>>>>>> f4d56ffa7287b3411536cd5032d0656f1f1e0185
import Tasks.Task;
import UI.Ui;
import Storage.Storage;
import java.io.IOException;
import Tasks.*;
import Exception.DukeException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class TimeboundCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException, NullPointerException {
        String description = "";
        String period = "";
        String[] inputs = ui.FullCommand.split("/between");
        description = inputs[0];
        period = inputs[1];

      Timebound tb = new Timebound(description, period);
        list.add(tb);
        System.out.println("Got it. I've added this task:");
        System.out.println(tb.listformat());
        System.out.println("Now you have " + list.size() + " tasks in the list.");
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
<<<<<<< HEAD
=======

>>>>>>> f4d56ffa7287b3411536cd5032d0656f1f1e0185
}
