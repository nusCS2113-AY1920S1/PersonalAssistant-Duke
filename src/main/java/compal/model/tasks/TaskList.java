package compal.model.tasks;

import compal.commons.Compal;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.BitSet;


public class TaskList {

    //***Class Properties/Variables***--------------------------------------------------------------------------------->
    public ArrayList<Task> arrlist;
    public Compal compal;
    private BitSet idBitSet;

    //----------------------->
    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    /**
     * Constructs TaskList object.
     *
     * @param d Compal.
     */
    public TaskList(Compal d) {
        this.compal = d;
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
     * Handles the adding of the tasks.
     * It tests for the task type, then parses it according to the correct syntax.
     * Used in parser.processCommands.
     *
     * @param task Task to be added to the list of tasks.
     * @return Size of arrayList.
     */
    public int addTask(Task task) {
        arrlist.add(task);
        compal.storage.saveCompal(arrlist);
        /*if (compal.ui.dateState.equals(task.getStringDate())) {
            compal.ui.dateViewRefresh(task.getStringDate());
        }*/
        if (!task.getSymbol().equals("D")) {
            compal.ui.dateViewRefresh(task.getStringDate());
        }
        compal.ui.secondaryScreenRefresh(task.getDate());

        compal.ui.showSize();
        return arrlist.size();
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
     *
     * @param currentStage Current stage status.
     * @param value        Input value.

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
     */
    //----------------------->
}
