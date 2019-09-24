package commands;

import Storage.Storage;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import UI.Ui;

import java.io.IOException;

import Exception.DukeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SnoozeCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException, NullPointerException {

        int index = Integer.parseInt(ui.FullCommand.substring(6).trim()) - 1;
        String Decription = list.get(index).description;
        System.out.println("Please indicate how much time you want to snooze");
        ui.ReadCommand();
        int year = Integer.parseInt(ui.FullCommand.split(" ")[0]);
        int day = Integer.parseInt(ui.FullCommand.split(" ")[2]);
        int month = Integer.parseInt(ui.FullCommand.split(" ")[1]);
        int hour = Integer.parseInt(ui.FullCommand.split(" ")[3]);
        if (list.get(index).listformat().contains("by")) {
            SimpleDateFormat fmt = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            Date initial = fmt.parse(list.get(index).toString().split("\\|")[3].substring(3).trim());
//                System.out.println("Success" + initial);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(initial);
            if (year > 0) rightNow.add(Calendar.YEAR, year);
            if (month > 0) rightNow.add(Calendar.MONTH, month);
            if (day > 0) rightNow.add(Calendar.DAY_OF_YEAR, day);
            if (hour > 0) rightNow.add(Calendar.HOUR, hour);
            Date after = rightNow.getTime();
//                System.out.println("Success"+after);
            Task snoozedDeadline = new Deadline(Decription, after);
            list.remove(index);
            list.add(snoozedDeadline);
            System.out.println("Okay. I've prolonged this task's deadline: ");
            System.out.println(snoozedDeadline.listformat());
        } else {
            SimpleDateFormat fmt = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            Date initial = fmt.parse(list.get(index).toString().split("\\|")[3].substring(3).trim());
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(initial);
            if (year > 0) rightNow.add(Calendar.YEAR, year);
            if (month > 0) rightNow.add(Calendar.MONTH, month);
            if (day > 0) rightNow.add(Calendar.DAY_OF_YEAR, day);
            if (hour > 0) rightNow.add(Calendar.HOUR, hour);
            Date after = rightNow.getTime();
            Event snoozedEvent = new Event(Decription, after);
            list.remove(index);
            list.add(snoozedEvent);
            System.out.println("Okay. I've prolonged this task's time: ");
            System.out.println(snoozedEvent.listformat());
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getClass().getName().equals("Tasks.Deadline")) {
                sb.append(list.get(i).toString() + "\n");
            } else if (list.get(i).getClass().getName().equals("Tasks.Event")) {
                sb.append(list.get(i).toString() + "\n");
            } else {
                sb.append(list.get(i).toString() + "\n");
            }
        }
        storage.Storages(sb.toString());

    }

}