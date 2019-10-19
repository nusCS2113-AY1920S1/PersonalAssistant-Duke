package compal.model.tasks;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;


public class TaskList {

    private ArrayList<Task> arrlist;
    private BitSet idBitSet;


    /**
     * Constructs TaskList object.
     */
    public TaskList() {
        BitSet bs = readIdBitSet();
        if (bs != null) {
            idBitSet = (BitSet) readIdBitSet().clone();
        } else {
            System.out.println("TaskList:LOG: No saved idbitset found");
            idBitSet = new BitSet(1_000_000); //bitset of 1,000,000 bits (hard limit of no. of tasks)
        }
    }

    public ArrayList<Task> getArrList() {
        return this.arrlist;
    }

    public void setArrList(ArrayList<Task> arrlist) {
        this.arrlist = arrlist;
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
     */
    public void addTask(Task task) {
        //generate unique ID for task
        int taskID;
        for (int i = 0; i < 1000000; i++) { //search for an unused task ID
            if (!idBitSet.get(i)) {
                idBitSet.set(i);
                taskID = i;
                task.setId(taskID);
                System.out.println("Task assigned id of " + taskID);
                writeIdBitSet();
                break;
            }
        }

        arrlist.add(task);
        sortTask(arrlist);
        /*if (compal.ui.dateState.equals(task.getStringDate())) {
            compal.ui.dateViewRefresh(task.getStringDate());
        }*/
        /*if (!task.getSymbol().equals("D")) {
            compal.ui.dateViewRefresh(task.getStringDate());

        }*/
        //compal.ui.secondaryScreenRefresh(task.getDate());

        //compal.ui.showSize();
    }


    /**
     * Returns a task that has an id value of id.
     */
    public Task getTaskById(int id) {

        //search for task with id of id
        for (Task t : arrlist) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw null;
    }

    /**
     * Removes a task that has an id value of id.
     */
    public Task removeTaskById(int id) {

        //search for task with id of id
        for (Task t : arrlist) {
            if (t.getId() == id) {
                arrlist.remove(t);
            }
        }
        throw null;

    }


    /**
     * Remove a task from the arrayList.
     */
    public void removeTask(int index) {
        arrlist.remove(index);
    }

    /**
     * Clears the current id for future tasks to use (used in deletion of tasks).
     *
     * @param id task id
     */
    public void unsetId(int id) {
        System.out.println("TaskList:LOG:" + id + " unset");
        idBitSet.clear(id);
    }

    public ArrayList<Task> returnTaskList() {
        return this.arrlist;
    }

    /**
     * Sorts all the tasks in arrlist by date.
     *
     * @param arrlist sorted
     */
    public void sortTask(ArrayList<Task> arrlist) {
        boolean sorted = false;
        int arraySize = arrlist.size();
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < arraySize - 1; i++) {
                Date task1Date = arrlist.get(i).getDate();
                Date task2Date = arrlist.get(i + 1).getDate();
                if (task1Date.after(task2Date)) {
                    Task temp = arrlist.get(i);
                    arrlist.set(i, arrlist.get(i + 1));
                    arrlist.set(i + 1, temp);
                    sorted = false;
                }
                if (task1Date.equals(task2Date)) {
                    Date task1Time = arrlist.get(i).getStartTime();
                    Date task2Time = arrlist.get(i + 1).getStartTime();

                    if (task1Time == null) {
                        task1Time = arrlist.get(i).getEndTime();
                    }
                    if (task2Time == null) {
                        task2Time = arrlist.get(i + 1).getEndTime();
                    }
                    if (task1Time.after(task2Time)) {
                        Task temp = arrlist.get(i);
                        arrlist.set(i, arrlist.get(i + 1));
                        arrlist.set(i + 1, temp);
                        sorted = false;
                    }
                }
            }
        }
    }


    /**
     * Writes(saves) the current id bitset to file.
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
     * Reads in the saved idbitset as an object and returns it.
     *
     * @return saved idbitset
     * @author Jaedonkey
     */
    public BitSet readIdBitSet() {
        BitSet bs = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("serial"));
            bs = (BitSet) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return bs;

    }


}
