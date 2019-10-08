package UI;

import Storage.Storage;
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

    /**
     * This method prompts the user to input the password to login into GAZEEEBO and print a logo and message to welcome the user when he successfully log in.
     * @return the logo
     * @throws IOException if tDate doesn't get updated.
     */
    public String showWelcome() throws IOException {
        System.out.println("Input password to enter Gazeebo:");
        String logo = " ___   ___  ___  ___  ___  ___  ___   ___ \n"
                + "|     |   |   / |    |    |    |   \\ |   |\n"
                + "|  __ |__ |  /  |___ |___ |___ |___| |   |\n"
                + "|___| |   | /__ |___ |___ |___ |___/ |___|";
        String welcomemessage = "\nWelcome to Gazeebo"
                + "\n__________________________________________\n"
                + logo
                + "\n__________________________________________\n";

        while (true) {
            ReadCommand();
            ArrayList<String> password_list;
            Storage store = new Storage();
            password_list = store.Password();
            if (FullCommand.equals(password_list.get(0))) {
                System.out.println(welcomemessage);
                break;
            } else {
                System.out.println("Incorrect password, please try again:");
            }
        }
        return welcomemessage;
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