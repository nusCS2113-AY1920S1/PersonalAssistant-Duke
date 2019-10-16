package gazeeebo.commands;
import gazeeebo.Storage.Storage;
import gazeeebo.Tasks.Deadline;
import gazeeebo.Tasks.Event;
import gazeeebo.Tasks.Task;
import gazeeebo.UI.Ui;
import java.io.IOException;
import gazeeebo.Exception.DukeException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.*;


public class SnoozeCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws DukeException, ParseException, IOException, NullPointerException {
        try {
        if(ui.FullCommand.length() == 6) {
            throw new DukeException("OOPS!!! The object of a snoozing cannot be null.");
        }else{

            int index = Integer.parseInt(ui.FullCommand.substring(6).trim()) - 1;
            String Description=list.get(index).description;
            System.out.println("You are snoozing this task: "+list.get(index).description);
            System.out.println("Please indicate how much time you want to snooze");
            ui.ReadCommand();
            int year = Integer.parseInt(ui.FullCommand.split(" ")[0]);
            int day = Integer.parseInt(ui.FullCommand.split(" ")[2]);
            int month =Integer.parseInt(ui.FullCommand.split(" ")[1]);
            int hour = Integer.parseInt(ui.FullCommand.split(" ")[3]);

            if (list.get(index).listFormat().contains("by")) {
                String date = list.get(index).toString().split("\\|")[3].substring(4);
                LocalDateTime newDate  = LocalDateTime.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                newDate = newDate.plusYears(year).plusMonths(month).plusDays(day).plusHours(hour);
                String newBy= newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                Task snoozedDeadline = new Deadline(Description,newBy);
                list.remove(index);
                list.add(snoozedDeadline);
                System.out.println("Okay. I've prolonged this task's deadline: ");
                System.out.println(snoozedDeadline.listFormat());
            } else {
                String date = list.get(index).toString().split("\\|")[3].substring(4).split(" ")[0];
                String start = list.get(index).toString().split("\\|")[3].substring(4).split(" ")[1].split("-")[0];
                String end = list.get(index).toString().split("\\|")[3].substring(4).split(" ")[1].split("-")[1];
                LocalDate newDate = LocalDate.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalTime newStart =  LocalTime.parse(start,DateTimeFormatter.ofPattern("HH:mm:ss"));
                LocalTime newEnd = LocalTime.parse(end,DateTimeFormatter.ofPattern("HH:mm:ss"));
                newDate = newDate.plusYears(year).plusMonths(month).plusDays(day);
                newStart = newStart.plusHours(hour);
                newEnd = newEnd.plusHours(hour);
                String newAt = newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+" "+newStart.format(DateTimeFormatter.ofPattern("HH:mm:ss"))+"-"+newEnd.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                Event snoozedEvent = new Event(Description,newAt);
                list.remove(index);
                list.add(snoozedEvent);
                System.out.println("Okay. I've prolonged this task's time: ");
                System.out.println(snoozedEvent.listFormat());
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
                }
                storage.Storages(sb.toString());
            }
        }
        catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }
}