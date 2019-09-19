package compal.tasks;

import compal.main.Duke;

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

    //***Class Properties/Variables***--------------------------------------------------------------------------------->

    public ArrayList<Task> arrlist;
    public Duke duke;
    private BitSet idBitSet;

    //----------------------->




    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    /**
     * Constructor 1.
     *
     * @param d Duke
     */
    public TaskList(Duke d) {
        this.duke = d;
        //idBitSet = getIdBitSet();
        /*if (idBitSet == null) {
            idBitSet = new BitSet(1_000_000); //bitset of 1,000,000 bits
        }
        */



    }


    //----------------------->







    //***FUNCTIONS FOR ADDING TASKS***----------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->



    /**
     * This function handles the adding of the tasks (Events, Deadlines, Todos).
     * It tests for the event type, then parses it according to the correct syntax
     *
     * @param task The task to be added to the list of tasks.
     * @UsedIn: parser.processCommands
     */
    public int addTask(Task task) {
        arrlist.add(task);
        System.out.println("DoneCommand processing adding of task");
        duke.storage.saveCompal(arrlist);
        duke.ui.showSize();
        return arrlist.size();
    }
        /* duke.ui.printg("Got it. I've added this task:");
        Scanner sc1 = new Scanner(cmd);
        String s = sc1.next(); //get the command string (event/deadline/doafter/todo/recurtask)
        String cs = sc1.nextLine(); //get the description string (what follows after the command string)
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
        case "doaftertask":
            token = "/after";
            description = getDescription(cs, token);
            date = getDate(cs, token);
            arrlist.add(new DoAfterTasks(description,date));
            duke.ui.printg("[DAT][ " + notDone + "] " + description);
            break;
        case "fixeddurationtask":
            token = "/on";
            description = getDescription(cs.substring(0, cs.indexOf("/for")), token);
            date = getDate(cs.substring(0, cs.indexOf("/for")), token);
            int hour = 0;
            int minute = 0;
            hour = getDuration(cs, 1);
            minute = getDuration(cs, 2);
            arrlist.add(new FixedDurationTask(description + "duration: " + hour + " hour(s) " + minute
                    + " minute(s)",date, hour, minute));
            duke.ui.printg("[FDT][" + notDone + "] " + description + "for " + hour + " hour(s) " + minute
                    + " minute(s)");
            break;
        case "recurtask":
            token = "/start";
            date = getDate(cs, token);
            RecurTaskHandler handler = new RecurTaskHandler(duke);
            ArrayList<RecurringTask> recurTaskList = handler.recurTaskPacker(cs, date);
            for (RecurringTask recurTask : recurTaskList) {
                arrlist.add(recurTask);
            }
            duke.ui.printg("The first iteration of this task is: ");
            duke.ui.printg(recurTaskList.get(0).toString());
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + s);
        }

        //at this point, an update is made to the task list, so save to file
        System.out.println("DoneCommand processing adding of task");
        duke.storage.saveCompal(arrlist);
        duke.ui.showSize();
    }*/



    //----------------------->






    //***FUNCTIONS FOR EDITING TASKS***---------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->



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

        duke.ui.printg(t);
        duke.storage.saveCompal(arrlist);
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
        duke.ui.printg("Noted. I've removed this task:");
        Task t = arrlist.remove(sc1.nextInt() - 1);
        t.markAsDone();
        duke.ui.printg(t);
        duke.storage.saveCompal(arrlist);
        duke.ui.showSize();
    }


    //----------------------->






    //***FUNCTIONS FOR GETTING VARIOUS TASK INFO***---------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->



    /**
     * This function builds a description from the description string according to the token (/at or /by etc).
     * Description string is the string before the token.
     *
     * @param cs    description string
     * @param token the separator word of the description
     * @return description
     * @UsedIn: : addTask
     */
    public String getDescription(String cs, String token) {
        int splitPoint = cs.indexOf(token);
        String when = cs.substring(splitPoint + token.length() + 1);

        //call the date parser to parse and return a date string
        String check = dateParse(when);
        if (!check.equals("false")) {
            when = check;
        }

        token = token.replace("/", "");
        String what = cs.substring(0, splitPoint).trim();
        return what + " (" + token + ": " + when + ")";
    }


    /**
     * This function gets the date and time from the description string according to the token (/at or /by etc).
     * Date/time string is the string after the token.
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
        String check = dateParse(when);
        if (!check.equals("false")) {
            when = check;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy hh:mma");
        Date date = formatter.parse(when);
        return date;
    }

    /**
     * This function gets the duration hour and minute of the task.
     * hour or minute is the number in the substring after "/for"
     *
     * @param cs    description string
     * @param i     whether the system is asking for the hour or the minute
     * @return hour if i = 1
     *         minute if i = 2
     * @UsedIn: : addTask
     */
    private int getDuration(String cs, int i) {
        cs = cs.substring(cs.indexOf("/for") + 4);
        Scanner sc1 = new Scanner(cs);
        int time = 0; //to represent hour/minute
        while (i != 0) {
            i--;
            time = sc1.nextInt();
            sc1.next();//ignore the string
        }
        return time;
    }


    /**
     * This function parses the date in the format dd/MM/yyyy HHmm and returns a date in the format
     * dd MMMM yyyy hh:mma .
     *
     * @param when date input to be formatted
     * @return dateString format the date of input when
     * @UsedIn: ui.getDescription
     */
    public static String dateParse(String when) {
        //parse date
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date date = null;
        try {
            date = format.parse(when);
        } catch (ParseException e) {
            return "false";
        }
        format = new SimpleDateFormat("dd MMMM yyyy hh:mma");
        when = format.format(date);
        return when;
    }



    //----------------------->








    //***MISC FUNCTIONS***----------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->


    /**
     * This function handles the searching of tasks.
     *
     * @param cmd used to find the keyword given
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
                duke.ui.printg(t);
            }
        }

    }




    /**
     * Displays reminders for tasks due in the next week when app starts up.
     *
     * @UsedIn: Ui.checkInit
     */
    public void taskReminder() {
        ArrayList<Task> reminder = new ArrayList<>();
        //ArrayList<Task> sortedReminder = new ArrayList<Task>();
        Date currentDate = java.util.Calendar.getInstance().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 7);
        Date dateOneWeekAfter = c.getTime();
        for (Task t : arrlist) {
            Date deadline = t.getDate();
            if ((deadline != null && !t.isDone && deadline.before(dateOneWeekAfter)) || t.isHasReminder()) {
                reminder.add(t);
            }
        }
        Comparator<Task> compareByDateTime = (Task t1, Task t2) -> t1.getDate().compareTo(t2.getDate());
        Collections.sort(reminder, compareByDateTime);

        if (reminder.isEmpty()) {
            duke.ui.printg("You currently have no tasks that have reminders set or are due within a week!");
        } else {
            for (Task t : reminder) {
                duke.ui.printg(t.getDescription());
            }
        }

    }


    /**
     * This function will store a temp ArrayList for looking at that specific date. (Sholihin)
     * e.g. view 01/18/2019
     *
     * @param cmd the command input by used
     * @return the temp array list of the task on that day
     */
    public ArrayList<Task> viewDate(String cmd) {
        ArrayList<Task> viewDay = new ArrayList<Task>();
        Scanner sc1 = new Scanner(cmd);
        sc1.next(); //skip over the 'find'
        String unformattedDate = sc1.next(); //date 12/12/2018
        //duke.ui.printg("Viewing all task on " + unformattedDate);
        unformattedDate += " 0000"; //fake timing so that we can still use dateParse function
        String formattedDate = dateParse(unformattedDate);
        formattedDate = formattedDate.substring(0, formattedDate.length() - 8); // depending on the length remove timing


        for (Task t : arrlist) {
            if (t.getDescription().contains(formattedDate)) {
                viewDay.add(t);
            }
        }

        if (viewDay.isEmpty()) {
            duke.ui.printg("No tasks found on " + formattedDate + "!");
            //return null;
        }

        return viewDay;
    }




    /**
     * Saves the current bitset to file. For assignment of task IDs.

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
     * @return BitSet

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
     */



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
                //writeIdBitSet();
                break;
            }
        }

    }



    //----------------------->








}
