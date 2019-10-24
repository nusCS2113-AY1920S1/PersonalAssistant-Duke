package command;

import java.io.*;

import task.Task;
import java.util.ArrayList;

/**
 * Storage that saves and loads the tasklist of the user.
 */
public class Storage {
    private static String filepath;

    /**
     * Creates a Storage instance with the required attributes.
     *
     * @param filepath Filepath to the storage file.
     */
    public Storage(String filepath) {
        Storage.filepath = filepath;
    }

    /**
     * Loads an ArrayList containing the Task object from the storage file.
     *
     * @return The ArrayList containing the Task object.
     */
    public static ArrayList<String> load() {
        try {
            String line;
            ArrayList<String> List = new ArrayList<String>();
            FileReader fileReader = new FileReader(filepath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                List.add(line);
            }

            bufferedReader.close();
            return List;
            /*FileInputStream file = new FileInputStream(filepath);
            ObjectInputStream out = new ObjectInputStream(file);

            ArrayList<String> commandList =  (ArrayList) out.readObject();

            out.close();
             */

        } /*catch (EOFException e) {
            System.out.println("File is empty");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return new ArrayList<String>();
        */ catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filepath + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filepath + "'");
        }
        return new ArrayList<String>();
    }

    /**
     * Saves the tasklist of the user as an ArrayList containing the task object.
     *
     */
    public static void save(String str) {
        try {
            FileWriter fileWriter = new FileWriter(filepath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            /*for (int i = 0; i < tasklist.size(); i = i + 1) {
                bufferedWriter.write(tasklist.get(i));
            }
             */
            bufferedWriter.write(str);
            bufferedWriter.close();
            /*FileOutputStream file = new FileOutputStream(filepath);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(tasklist);
            out.close();
            file.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
             */
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + filepath + "'");
        }
    }
}