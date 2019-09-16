package compal.inputs;

import compal.tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import java.util.ArrayList;

public class Storage {

    //***Class Properties/Variables***--------------------------------------------------------------------------------->
    private static final String saveFilePath = "./duke.txt";
    private static final String binarySaveFilePath = "binary";
    private static final String userPreferencesFilePath = "./prefs.txt";

    //----------------------->




    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    public Storage() {
        System.out.println("Storage Initialized!");
    }

    //----------------------->




    //***LOADING FUNCTIONS***-------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->


    /**
     * Use to preload the arraylist as a binary stream if there is anything to load at all.
     *
     * @return list2 the arraylist of stored item found in file.
     */
    public ArrayList<Task> loadCompal() {
        ArrayList<Task> list2 = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(binarySaveFilePath));
            //noinspection unchecked
            list2 = (ArrayList<Task>) ois.readObject();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list2;
    }



    /**
     * Loads and returns the user's name as a String.
     *
     * @return String username
     */
    public String getUserName() {
        File f = new File(userPreferencesFilePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            return br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown User";
    }


    //----------------------->





    //***SAVING FUNCTIONS***--------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->


    /**
     * Used to save the arraylist of task into the file.
     *
     * @param tasks ArrayList of task stored
     */
    public void saveCompal(ArrayList<Task> tasks) {

        try {
            ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(binarySaveFilePath));
            ois.writeObject(tasks);
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    /**
     * Saves the string toSave to the saveFilePath just as it is.
     *
     * @param toSave String
     */
    public void saveString(String toSave, String filePath) {
        try {
            File f = new File(filePath);
            PrintWriter pw = new PrintWriter(f);
            pw.printf("%s\n", toSave);
            pw.close();

        } catch (FileNotFoundException e) {
            System.out.println("Save-file not found. Will generate new one.");
        }
    }



    /**
     * Stores username in prefs.txt.
     *
     */
    public void storeUserName(String name) {
        saveString(name, userPreferencesFilePath);
    }

    //----------------------->




    //***STORAGE PARSING FUNCTIONS***-----------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->



    /**
     * Takes in varargs strings containing details of a task (DateAndTime, Task ID, Task Type, Task Name and etc).
     * Returns a fully joined string (each component string is joined by underscores)
     */
    public String generateStorageString(String... properties) {

        StringBuilder sb = new StringBuilder();

        //cat the strings with underscore separating them
        for (String property : properties) {
            sb.append("_");
            sb.append(property);
        }

        return sb.toString();

    }


    //----------------------->







}
