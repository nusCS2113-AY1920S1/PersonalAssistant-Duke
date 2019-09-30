package UI;

import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Ui {
    public String FullCommand;


    public void ReadCommand() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        FullCommand = reader.readLine();
    }

    public String showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        return logo;
    }

    public void UpcomingTask(ArrayList<Task> list) throws ParseException {
        ArrayList<Deadline> DeadlineList = new ArrayList<Deadline>();
        ArrayList<Event> EventList = new ArrayList<Event>();

        for (Task task : list) {
            if (task.getClass().getName().equals("Tasks.Deadline")) {
                Deadline deadline = new Deadline(task.description, task.toString().split("by:")[1].trim());
                DeadlineList.add(deadline);
            } else if (task.getClass().getName().equals("Tasks.Event")) {
                Event event = new Event(task.description, task.toString().split("at:")[1].trim());
                EventList.add(event);
            }
        }
        Collections.sort(DeadlineList, Comparator.comparing(u -> u.by));
        Collections.sort(EventList, Comparator.comparing(u -> u.date));
        System.out.println("Upcoming deadlines:");
        for (int i = 0; i < DeadlineList.size(); i++) {
            System.out.println(i + 1 + "." + DeadlineList.get(i).listFormat());
        }
        System.out.println("Upcoming events:");
        for (int i = 0; i < EventList.size(); i++) {
            System.out.println(i + 1 + "." + EventList.get(i).listFormat());
        }
    }

    public void showDateFormatError() {
        System.err.println("Date Time has to be in YYYY-MM-DD HH:mm:ss format");
    }

    public static void showDeadlineDateFormatError() {
        System.out.println("Date Time has to be in YYYY-MM-DD HH:mm:ss format");
    }

    public static void showEventDateFormatError() {
        System.out.println("Date Time has to be in YYYY-MM-DD HH:mm:ss-HH:mm:ss format");
    }

    public void showIOErrorMessage(Exception e) {
        System.err.println("An IOException was caught :" + e.getMessage());
    }

    public void showErrorMessage(Exception e) {
        System.err.println(e.getMessage());
    }

}