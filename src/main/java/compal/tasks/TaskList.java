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

    //***FUNCTIONS FOR GETTING VARIOUS TASK INFO***---------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->


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
