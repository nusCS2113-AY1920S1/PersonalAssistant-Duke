package UI;
import Exception.DukeException;

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
            while(true) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                FullCommand = reader.readLine();
                String[] splitString = FullCommand.split(" ");
                try {
                    if (FullCommand.equals("todo") || FullCommand.equals("deadline") || FullCommand.equals("event") || FullCommand.equals("find") || FullCommand.equals("/require")
                            || FullCommand.equals("reschedule") || FullCommand.equals("schedule") || FullCommand.equals("snooze") || FullCommand.equals("tentative")
                            || FullCommand.equals("confirm") || FullCommand.equals("/between") || FullCommand.equals("/after") || FullCommand.equals("delete")) {

                        throw new DukeException("\t☹ OOPS!!! The description of a " + FullCommand + " cannot be empty.");
                    } else if (!FullCommand.equals("list") && !(splitString[0].equals("done") && splitString.length != 1) && !(FullCommand.equals("bye")) &&
                            !splitString[0].equals("todo") && !splitString[0].equals("deadline") && !splitString[0].equals("event") && !splitString[0].equals("delete")
                            && !splitString[0].equals("find") && !splitString[0].equals("snooze") && !splitString[0].equals("tentative") && !splitString[0].equals("reschedule")
                            && !splitString[0].equals("schedule") && !splitString[0].equals("confirm")) {
                        throw new DukeException("\t☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
                    } else {
                        break;
                    }
                } catch (DukeException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        public String showWelcome () {
            String logo = " ____        _        \n"
                    + "|  _ \\ _   _| | _____ \n"
                    + "| | | | | | | |/ / _ \\\n"
                    + "| |_| | |_| |   <  __/\n"
                    + "|____/ \\__,_|_|\\_\\___|\n";
            System.out.println("Hello from\n" + logo);
            return logo;
        }
        public void UpcomingTask (ArrayList < Task > list) throws ParseException {
            SimpleDateFormat fmt = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            ArrayList<Deadline> DeadlineList = new ArrayList<Deadline>();
            ArrayList<Event> EventList = new ArrayList<Event>();

            for (Task task : list) {
                if (task.getClass().getName().equals("Tasks.Deadline")) {
                    Deadline deadline = new Deadline(task.description, fmt.parse(task.toString().split("by:")[1].trim()));
                    DeadlineList.add(deadline);
                } else if (task.getClass().getName().equals("Tasks.Event")) {
                    Event event = new Event(task.description, fmt.parse(task.toString().split("at:")[1].trim()));
                    EventList.add(event);
                }
            }
            Collections.sort(DeadlineList, Comparator.comparing(u -> u.by));
            Collections.sort(EventList, Comparator.comparing(u -> u.at));
            System.out.println("Upcoming deadlines:");
            for (int i = 0; i < DeadlineList.size(); i++) {
                System.out.println(i + 1 + "." + DeadlineList.get(i).listformat());
            }
            System.out.println("Upcoming events:");
            for (int i = 0; i < EventList.size(); i++) {
                System.out.println(i + 1 + "." + EventList.get(i).listformat());
            }
        }
        public void showDateFormatError () {
            System.err.println("Date Time has to be in YYYY-MM-DD HH:mm:ss format");
        }
        public void showIOErrorMessage (Exception e){
            System.err.println("An IOException was caught :" + e.getMessage());
        }
        public void showErrorMessage (Exception e){
            System.err.println(e.getMessage());
        }

}
