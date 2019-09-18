package commands;

import Tasks.Task;
import UI.Ui;
import Storage.Storage;
import Exception.DukeException;
import java.io.IOException;
import Tasks.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EventCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException, NullPointerException {
        String description = "";
        if(ui.FullCommand.length() == 5) {
            throw new DukeException("OOPS!!! The description of an event cannot be empty.");
        }
        else{
            description = ui.FullCommand.split("/")[0].substring(6);
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Event ev = new Event(description, fmt.parse(ui.FullCommand.split("/")[1].substring(3)));

        //CHECKING FOR SCHEDULE ANOMALIES------------------------------------------------------------------
        ArrayList <Event> clash = new ArrayList<Event>(); //to store events that clash with the incoming event
        for (Task t : list) {
            if (t.getClass().getName().equals("Tasks.Event") && ((Event)t).at.equals(ev.at)) {
                clash.add((Event)t);
            }
        }
        if (!clash.isEmpty()) {
            System.out.println("The following event(s) clash with your current event:");
            for (int i = 0; i < clash.size(); i++) {
                System.out.println((i+1) + "." + clash.get(i).listformat());
            }
            System.out.println("");
        }
        //--------------------------------------------------------------------------------------------------

        list.add(ev);
        System.out.println("Got it. I've added this task:");
        System.out.println(ev.listformat());
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
