package commands;

import Storage.Storage;
import Tasks.DoAfter;
import Tasks.Task;
import UI.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class DoAfterCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws  ParseException, IOException, NullPointerException{
        String before = "";
        String after = "";
        String[] splitstring = ui.FullCommand.split("/after");
        before = splitstring[1];
        after = splitstring[0];

        DoAfter to = new DoAfter(before, before, after);
        list.add(to);
        System.out.println("Got it. I've added this task:");
        System.out.println(to.listformat());
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
