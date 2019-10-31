package command;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * command.Storage that saves and loads the tasklist of the user.
 */
public class Storage {
    private static String filepath;

    /**
     * Creates a command.Storage instance with the required attributes.
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
    public ArrayList<String> load() {
        try {
            String line;
            ArrayList<String> list = new ArrayList<>();

            //file needs to be in same directory as Storage
            InputStream is = getClass().getResourceAsStream("duke.txt");


            //FileReader fileReader = new FileReader(filepath);
            //BufferedReader bufferedReader = new BufferedReader(fileReader);


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
            is.close();
            bufferedReader.close();

            return list;
            /*FileInputStream file = new FileInputStream(filepath);
            ObjectInputStream out = new ObjectInputStream(file);

            ArrayList<String> commandList =  (ArrayList) out.readObject();

            out.close();
             */

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filepath + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filepath + "'");
        }
        return new ArrayList<>();
        /*catch (EOFException e) {
            System.out.println("File is empty");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return new ArrayList<String>();
        */
    }

    /**
     * Saves the tasklist of the user as an ArrayList containing the task object.
     *
     */
    public void save(String str) {
        try {
            FileWriter fileWriter = new FileWriter(filepath, true);
            //InputStream is = getClass().getResourceAsStream("duke.txt");

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            //BufferedWriter bufferedWriter = new BufferedWriter(new InputStreamWriter(is));
            bufferedWriter.newLine();
            /*for (int i = 0; i < tasklist.size(); i = i + 1) {
                bufferedWriter.write(tasklist.get(i));
            }
             */

            bufferedWriter.write(str);

            //is.close();
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

    /**
     * Saves the tasklist of the user as an ArrayList containing the task object.
     * @param str TODO
     *
     */
    public static void remove(String str){
        //TODO
    }
}