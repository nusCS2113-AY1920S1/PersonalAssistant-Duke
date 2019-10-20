package compal.model.tasks;

import java.io.*;
import java.util.BitSet;

public class TaskIdManager {

    private BitSet idBitSet;

    TaskIdManager() {
        BitSet bs = readIdBitSet();
        if (bs != null) {
            idBitSet = (BitSet) readIdBitSet().clone();
        } else {
            System.out.println("TaskList:LOG: No saved idbitset found");
            idBitSet = new BitSet(1_000_000); //bitset of 1,000,000 bits (hard limit of no. of tasks)
        }
    }



    public void generateAndSetId(Task t) {
        int taskID;
        for (int i = 0; i < 1000000; i++) { //search for an unused task ID
            if (!idBitSet.get(i)) {
                idBitSet.set(i);
                taskID = i;
                t.setId(taskID);
                System.out.println("Task assigned id of " + taskID);
                writeIdBitSet();
                break;
            }
        }
        //if reached this point means bitmap full
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

    public void clearId(int id) {
        idBitSet.clear(id);
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
