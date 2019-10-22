package gazeeebo.UI;

import gazeeebo.storage.Storage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Ui {
    public String fullCommand;


    public void readCommand() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        fullCommand = reader.readLine().trim();
    }

    /**
     * This method prompts the user to input the password to login into GAZEEEBO and print a logo and message to welcome the user when he successfully log in.
     *
     * @return the logo
     * @throws IOException if tDate doesn't get updated.
     */
    public String showWelcome() throws IOException {
        System.out.println("Input password to enter Gazeeebo:");
        String logo = " ___   ___  ___  ___  ___  ___  ___   ___ \n"
                + "|     |   |   / |    |    |    |   \\ |   |\n"
                + "|  __ |__ |  /  |___ |___ |___ |___| |   |\n"
                + "|___| |   | /__ |___ |___ |___ |___/ |___|";
        String welcomemessage = "\nWelcome to Gazeeebo"
                + "\n__________________________________________\n"
                + logo
                + "\n__________________________________________\n";

        while (true) {
            readCommand();
            ArrayList<StringBuilder> password_list;
            Storage store = new Storage();
            password_list = store.Password();
            if (fullCommand.equals(password_list.get(0).toString())) {
                System.out.println(welcomemessage);
                LocalDate a = LocalDate.now();
                System.out.println("Today is " + a.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
                break;
            } else {
                System.out.println("Incorrect password, please try again:");
            }
        }
        return welcomemessage;
    }

    public void MajorCategories() {
        ArrayList<String> majorCategories = new ArrayList<>();
        majorCategories.add("help");
        majorCategories.add("contacts");
        majorCategories.add("expenses");
        majorCategories.add("places");
        majorCategories.add("tasks");
        majorCategories.add("gpa");
        majorCategories.add("moduleplanner");
        majorCategories.add("notes");
        System.out.println("\nContent Page:");
        System.out.println("------------------ " +
                "");
        for(int i = 0; i < majorCategories.size(); i++) {
            System.out.println(i+1 + ". " + majorCategories.get(i));
        }
    }

    public void UpcomingTask(ArrayList<Task> list) throws ParseException {
        ArrayList<Deadline> DeadlineList = new ArrayList<Deadline>();
        ArrayList<Event> EventList = new ArrayList<Event>();

        for (Task task : list) {
            if (task.getClass().getName().equals("gazeeebo.Tasks.Deadline") && !task.isDone) {
                Deadline deadline = new Deadline(task.description, task.toString().split("by:")[1].trim());
                deadline.isDone = task.isDone;
                DeadlineList.add(deadline);
            } else if (task.getClass().getName().equals("gazeeebo.Tasks.Event") && !task.isDone) {
                Event event = new Event(task.description, task.toString().split("at:")[1].trim());
                event.isDone = task.isDone;
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

    public void showProgessiveBar(ArrayList<Task> list) throws IOException {
        int UndoneNumber = 0;
        int DoneNumber = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isDone) {
                DoneNumber++;
            } else {
                UndoneNumber++;
            }
        }
//        System.out.println(UndoneNumber+" "+DoneNumber);
        double ProgressPercentageTemp = (DoneNumber * 1.00 / (DoneNumber + UndoneNumber) * 1.00) * 100.000;
        int ProgressPercentage = (int) ProgressPercentageTemp;
//        System.out.println(ProgressPercentageTemp+" "+ProgressPercentage);
        StringBuilder progressivebar = new StringBuilder();
        for (int i = 0; i < ProgressPercentage / 2; i++) {
            progressivebar.append("/");
        }
        for (int i = 0; i < (100 - ProgressPercentage) / 2; i++) {
            progressivebar.append("_");
        }
        System.out.println("Task progressive: " + progressivebar.toString() + "(" + ProgressPercentage + "%)");
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
        System.out.println(e.getMessage());
    }

    public void showDontKnowErrorMessage() {
        System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }

}