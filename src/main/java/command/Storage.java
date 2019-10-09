package command;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import task.Task;
import java.util.ArrayList;

/**
 * Storage that saves and loads the tasklist of the user.
 */
public class Storage {
    private static String filepath;

    /**
     * Creates a Storage instance with the required attributes.
     * @param filepath Filepath to the storage file.
     */
    public Storage(String filepath) {
        Storage.filepath = filepath;
    }

    /**
     * Loads an ArrayList containing the Task object from the storage file.
     * @return The ArrayList containing the Task object.
     */
    public static ArrayList<Task> load() {
        try {

            FileInputStream file = new FileInputStream(filepath);
            ObjectInputStream out = new ObjectInputStream(file);

            ArrayList<Task> arraylist =  (ArrayList) out.readObject();

            out.close();
            file.close();

            return arraylist;
        } catch (EOFException e) {
            System.out.println("File is empty");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return new ArrayList<Task>();
    }

    /**
     * Saves the tasklist of the user as an ArrayList containing the task object.
     * @param tasklist Tasklist of the user.
     */
    public static void save(ArrayList<Task> tasklist) {
        try {
            FileOutputStream file = new FileOutputStream(filepath);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(tasklist);
            out.close();
            file.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
