package compal.tasks;

import compal.main.Duke;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

public class TaskList {

    public ArrayList<Task> arrlist;
    public Duke duke;
    private BitSet idBitSet;

    /**
     * Constructor for class.
     *
     * @param d Duke
     */
    public TaskList(Duke d) {
        this.duke = d;
        idBitSet = getIdBitSet();
        if (idBitSet == null) {
            idBitSet = new BitSet(1_000_000); //bitset of 1,000,000 bits
        }


    }

    /**
     * Saves the current bitset to file. For assignment of task IDs.
     */
    public void writeIdBitSet() {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("serial"));
            oos.writeObject(idBitSet);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads the current bitset saved on file and returns it.
     *
     * @return
     */
    public BitSet getIdBitSet() {
        BitSet b = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("serial"));
            b = (BitSet) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }


    /**
     * Draft function for adding tasks to ComPAL. Currently not in use.
     */
    public void addTaskTest(int currentStage, String value) {

        Scanner sc1 = new Scanner(value);
        String s = sc1.next(); //get the command string
        String taskType = sc1.next(); //get the taskType
        String dateString = sc1.next(); //get the date
        String timeString = sc1.next(); //get the time
        String name = sc1.next(); //get the name
        String description = sc1.nextLine(); //get the description
        int taskID = -1;
        for (int i = 0; i < 1000000; i++) { //search for an unused task ID
            if (!idBitSet.get(i)) {
                taskID = i;
                System.out.println("Task assigned id of " + taskID);
                writeIdBitSet();
                break;
            }
        }

    }


    /**
     * This function handles the adding of the tasks (Events, Deadlines, Todos).
     * It tests for the event type, then parses it according to the correct syntax
     *
     * @param cmd to tell the function what command is to be executed
     * @Function
     * @calls dateParse(String when)
     * @UsedIn: parser.processCommands
     */
    public void addTask(String cmd) throws ParseException {
        duke.ui.printg("Got it. I've added this task:");
        Scanner sc1 = new Scanner(cmd);
        String s = sc1.next(); //get the command string
        String cs = sc1.nextLine(); //get the description string
        String token;
        String description;
        Date date;
        char notDone = '\u2718';

        switch (s) {
        case "todo":
            arrlist.add(new Todo(cs.trim()));
            duke.ui.printg("[T][ " + notDone + "] " + cs);
            break;
        case "event":
            token = "/at";
            description = getDescription(cs, token);
            date = getDate(cs, token);
            arrlist.add(new Event(description, date));
            duke.ui.printg("[E][ " + notDone + "] " + description);
            break;
        case "deadline":
            token = "/by";
            description = getDescription(cs, token);
            date = getDate(cs, token);
            arrlist.add(new Deadline(description, date));
            duke.ui.printg("[D][ " + notDone + "] " + description);
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + s);
        }

        //at this point, an update is made to the task list, so save to file
        System.out.println("Done processing adding of task");
        duke.storage.saveCompal(arrlist);
        duke.ui.showSize();
    }


    /**
     * This function handles the deletion of tasks.
     *
     * @param cmd to tell the function which number is to be remove.
     * @Function
     * @UsedIn: parser.processCommands
     */
    public void deleteTask(String cmd) {
        Scanner sc1 = new Scanner(cmd);
        sc1.next(); //skip over the 'delete'
        System.out.println("Noted. I've removed this task:");
        Task t = arrlist.remove(sc1.nextInt() - 1);
        t.markAsDone();
        duke.ui.showTask(t);
        duke.storage.saveCompal(arrlist);
        duke.ui.showSize();
    }


    /**
     * This function handles the completion of tasks by marking them as done.
     *
     * @param cmd Mark the numbered task to be completed.
     * @Function
     * @UsedIn: parser.processCommands
     */
    public void taskDone(String cmd) {
        Scanner sc1 = new Scanner(cmd);
        sc1.next(); //skip over the 'done'
        duke.ui.printg("Nice! I've marked this task as done:");
        Task t = arrlist.get(sc1.nextInt() - 1);
        t.markAsDone();

        duke.ui.showTask(t);
        duke.storage.saveCompal(arrlist);
    }


    /**
     * This function handles the searching of tasks.
     *
     * @param cmd used to find the keyword given
     * @Function
     * @UsedIn: parser.processCommands
     */
    public void findTask(String cmd) {
        Scanner sc1 = new Scanner(cmd);
        sc1.next(); //skip over the 'find'
        String pattern = sc1.next();
        System.out.println("Here are the matching tasks in your list:");

        int count = 1;

        for (Task t : arrlist) {
            if (t.getDescription().contains(pattern)) {
                System.out.print(count++ + ".");
                duke.ui.showTask(t);
            }
        }

    }


    /**
     * This function builds a description from the description string according to the token (/at or /by etc).
     *
     * @param cs    description string
     * @param token the separator word of the description
     * @return description
     * @Function
     * @UsedIn: : addTask
     */
    public String getDescription(String cs, String token) {
        int splitPoint = cs.indexOf(token);
        String when = cs.substring(splitPoint + token.length() + 1);

        //call the date parser to parse and return a date string
        String check = Duke.dateParse(when);
        if (!check.equals("false")) {
            when = check;
        }

        token = token.replace("/", "");
        String what = cs.substring(0, splitPoint).trim();
        return what + " (" + token + ": " + when + ")";
    }

    /**
     * This function gets the date and time from the description string according to the token (/at or /by etc).
     *
     * @param cs    description string
     * @param token the separator word of the description
     * @return date and time
     * @Function
     * @UsedIn: : addTask
     */
    public Date getDate(String cs, String token) throws ParseException {
        int splitPoint = cs.indexOf(token);
        String when = cs.substring(splitPoint + token.length() + 1);

        //call the date parser to parse and return a date string
        String check = Duke.dateParse(when);
        if (!check.equals("false")) {
            when = check;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy hh:mma");
        Date date = formatter.parse(when);
        return date;
    }

    /**
     * This function displays the tasks due in the next week.
     *
     * @UsedIn: Ui.checkInit
     */
    public void taskReminder() {
        ArrayList<Task> reminder = new ArrayList<Task>();
        //ArrayList<Task> sortedReminder = new ArrayList<Task>();
        Date currentDate = java.util.Calendar.getInstance().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 7);
        Date dateOneWeekAfter = c.getTime();
        for (Task t : arrlist) {
            Date deadline = t.getDateTime();
            if (deadline.before(dateOneWeekAfter) || t.isHasReminder()) {
                reminder.add(t);
            }
        }
        Comparator<Task> compareByDateTime = (Task t1, Task t2) -> t1.getDateTime().compareTo(t2.getDateTime());
        Collections.sort(reminder, compareByDateTime);
        for (Task t : reminder) {
            duke.ui.printg(t.getDescription());
        }
    }
}
