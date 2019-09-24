package commands;

import Tasks.Task;
import UI.Ui;
import Storage.Storage;
import Exception.DukeException;

import java.io.IOException;

import Tasks.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EventCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException, NullPointerException {
        String description;
        description = ui.FullCommand.split("/at ")[0].substring(6);

        String at = ui.FullCommand.split("/at ")[1];
        Event ev = new Event(description, at);

        //CHECKING FOR SCHEDULE ANOMALIES------------------------------------------------------------------
        ArrayList<Event> clash = new ArrayList<Event>(); //to store events that clash with the incoming event
        for (Task t : list) {
            if (t.getClass().getName().equals("Tasks.Event") && ((Event) t).date.equals(ev.date) &&
                    ((ev.start.isBefore(((Event) t).start) && ev.end.isAfter(((Event) t).start)) ||
                            ev.start.equals(((Event) t).start) ||
                            (ev.start.isAfter(((Event) t).start) && ev.start.isBefore(((Event) t).end)))) {
                clash.add((Event) t);
            }
        }
        if (!clash.isEmpty()) {
            System.out.println("The following event(s) clash with your current event:");
            for (int i = 0; i < clash.size(); i++) {
                System.out.println((i + 1) + "." + clash.get(i).listFormat());
            }
            System.out.println("");
        }
        //--------------------------------------------------------------------------------------------------
        list.add(ev);
        System.out.println("Got it. I've added this task:");
        System.out.println(ev.listFormat());
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
}

