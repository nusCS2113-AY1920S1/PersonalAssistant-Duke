package compal.model.tasks;

import compal.commons.LogUtils;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.logging.Logger;

//@@author jaedonkey
public class TaskIdManager {

    private BitSet idBitSet;
    private static final Logger logger = LogUtils.getLogger(TaskIdManager.class);

    TaskIdManager() {
        BitSet bs = readIdBitSet();
        if (bs != null) {
            idBitSet = (BitSet) readIdBitSet().clone();
        } else {
            logger.info("No saved idbitset found");
            idBitSet = new BitSet(1_000_000); //bitset of 1,000,000 bits (hard limit of no. of tasks)
        }
    }

    //@@author jaedonkey
    /**
     * Synchronizes all edits made to the user file to the serial file holding the bitmap.
     * @param tl tasklist holding the arraylist of tasks
     */
    public void synchronizeTaskIds(TaskList tl) {
        idBitSet.clear();
        for (Task t:tl.getArrList()) {
            idBitSet.set(t.getId());
        }
    }

    //@@author jaedonkey
    /**
     * Assigns an ID to the task passed into it.
     * @param t task to be assigned an id
     */
    public void generateAndSetId(Task t) {
        int taskID;
        for (int i = 0; i < 1000000; i++) { //search for an unused task ID
            if (!idBitSet.get(i)) {
                idBitSet.set(i);
                taskID = i;
                t.setId(taskID);
                //System.out.println("Task assigned id of " + taskID);
                writeIdBitSet();
                break;
            }
        }
        //if reached this point means bitmap full
    }

    //@@author jaedonkey
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

    //@@author jaedonkey
    public void clearId(int id) {
        idBitSet.clear(id);
    }

    //@@author jaedonkey
    /**
     * Reads in the saved idbitset as an object and returns it.
     *
     * @return saved idbitset
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
