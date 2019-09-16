package compal.inputs;

import compal.tasks.Deadline;
import compal.tasks.Event;
import compal.tasks.Task;
import compal.tasks.Todo;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    
    private static final String userPreferencesFilePath = "./prefs.txt";

    public Storage() {
        System.out.println("Storage Initialized!");
    }


    public ArrayList<Task> loadCompal(){
        ArrayList<Task> list2=null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("binary"));
            list2 = (ArrayList<Task>) ois.readObject();
            for(Task t:list2){
                System.out.println("LoadCompal:");
                System.out.println(t.getDescription());
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list2;
    }



    public void saveCompal(ArrayList<Task> tasks){

        try {
            ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream("binary"));
            ois.writeObject(tasks);
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


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
     * Returns the user's name as a String.
     *
     * @return
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

    /**
     * Returns the user's name as a String.
     *
     * @return
     */
    public void storeUserName(String name) {
        saveString(name, userPreferencesFilePath);
    }


}
