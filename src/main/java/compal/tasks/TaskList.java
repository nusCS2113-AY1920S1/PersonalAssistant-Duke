package compal.tasks;

import compal.main.Duke;

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



    //***FUNCTIONS FOR GETTING VARIOUS TASK INFO***---------------------------------------------------------------------
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
        duke.storage.saveCompal(arrlist);
        duke.ui.showSize();
        return arrlist.size();
    }

    /**
     * Displays reminders for tasks due in the next week when app starts up.
     *
     * @UsedIn: Ui.checkInit
     */
    public void taskReminder() {
        ArrayList<Task> reminder = new ArrayList<>();
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
                duke.ui.printg(t.toString());
            }
        }

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
