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
     * @throws IOException catch the error if the read file fails.
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
            password_list = store.readFromPasswordFile();
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

    /**
     * List of major features
     */
    public void MajorCategories() {
        ArrayList<String> majorCategories = new ArrayList<>();
        majorCategories.add("help");
        majorCategories.add("contacts");
        majorCategories.add("expenses");
        majorCategories.add("places");
        majorCategories.add("tasks");
        majorCategories.add("cap");
        majorCategories.add("spec");
        majorCategories.add("moduleplanner");
        majorCategories.add("notes");

        System.out.println("\nContent Page:");
        System.out.println("------------------ "
                + "");
        for (int i = 0; i < majorCategories.size(); i++) {
            System.out.println(i + 1 + ". " + majorCategories.get(i));
        }

        System.out.println("To exit: bye");
    }

    public void upcomingTask(final ArrayList<Task> list) throws ParseException {
        ArrayList<Deadline> deadlineList = new ArrayList<Deadline>();
        ArrayList<Event> eventList = new ArrayList<Event>();

        for (Task task : list) {
            if (task.getClass().getName().equals("gazeeebo.tasks.Deadline") && !task.isDone) {
                Deadline deadline = new Deadline(task.description, task.toString().split("by:")[1].trim());
                deadline.isDone = task.isDone;
                deadlineList.add(deadline);
            } else if (task.getClass().getName().equals("gazeeebo.tasks.Event") && !task.isDone) {
                Event event = new Event(task.description, task.toString().split("at:")[1].trim());
                event.isDone = task.isDone;
                eventList.add(event);
            }
        }
        Collections.sort(deadlineList, Comparator.comparing(u -> u.by));
        Collections.sort(eventList, Comparator.comparing(u -> u.date));
        System.out.println("Upcoming deadlines:");
        for (int i = 0; i < deadlineList.size(); i++) {
            System.out.println(i + 1 + "." + deadlineList.get(i).listFormat());
        }
        System.out.println("Upcoming events:");
        for (int i = 0; i < eventList.size(); i++) {
            System.out.println(i + 1 + "." + eventList.get(i).listFormat());
        }
    }

    public void showProgessiveBar(final ArrayList<Task> list) throws IOException {
        int undoneNumber = 0;
        int doneNumber = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isDone) {
                doneNumber++;
            } else {
                undoneNumber++;
            }
        }
//        System.out.println(undoneNumber+" "+doneNumber);
        double progressPercentageTemp = (doneNumber * 1.00 / (doneNumber + undoneNumber) * 1.00) * 100.000;
        int progressPercentage = (int) progressPercentageTemp;
//        System.out.println(progressPercentageTemp+" "+progressPercentage);
        StringBuilder progressivebar = new StringBuilder();
        for (int i = 0; i < progressPercentage / 2; i++) {
            progressivebar.append("/");
        }
        for (int i = 0; i < (100 - progressPercentage) / 2; i++) {
            progressivebar.append("_");
        }
        System.out.println("Task progressive: " + progressivebar.toString() + "(" + progressPercentage + "%)");
    }

    public void showDateFormatError() {
        System.err.println("Date Time has to be in YYYY-MM-DD HH:mm:ss format");
    }

    public static void showDeadlineDateFormatError() {
        System.out.println("Date Time has to be in YYYY-MM-DD HH:mm:ss format");
    }
    /** */
    public static void showEventDateFormatError() {
        System.out.println("Date Time has to be in YYYY-MM-DD HH:mm:ss-HH:mm:ss format");
    }

    public void showIOErrorMessage(final Exception e) {
        System.err.println("An IOException was caught :" + e.getMessage());
    }

    public void showErrorMessage(final Exception e) {
        System.out.println(e.getMessage());
    }

    public void showDontKnowErrorMessage() {
        System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }

}