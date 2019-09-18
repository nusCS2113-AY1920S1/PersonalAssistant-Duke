package commands;

import Tasks.Task;
import UI.Ui;
import Storage.Storage;
import java.io.IOException;
import Tasks.*;
import Exception.DukeException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DeadlineCommand extends Command
{
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException, NullPointerException {
        String description = "";
        if(ui.FullCommand.length() == 8) {
            throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
        }
        else{
             description = ui.FullCommand.split("/")[0].substring(9);
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Deadline d = new Deadline(description, fmt.parse(ui.FullCommand.split("/")[1].substring(3)));
        list.add(d);
        System.out.println("Got it. I've added this task:");
        System.out.println(d.listformat());
        System.out.println("Now you have " + list.size() + " tasks in the list.");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getClass().getName().equals("Tasks.Deadline")) {
                sb.append(list.get(i).toString()+"\n");
            }
            else if(list.get(i).getClass().getName().equals("Tasks.Event")){
                sb.append(list.get(i).toString()+"\n");
            }
            else if(list.get(i).getClass().getName().equals("Tasks.FixedDuration")) {
                sb.append(list.get(i).toString()+"\n");
            }
            else if(list.get(i).getClass().getName().equals("Tasks.DoAfter")) {
                sb.append(list.get(i).toString()+"\n");
            }
            else{
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
