package compal.inputs;

import compal.tasks.Deadline;
import compal.tasks.Event;
import compal.tasks.Task;
import compal.tasks.Todo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    private String saveFilePath;

    public Storage(String filePath) {
        saveFilePath = filePath;
    }


    /**
     * This function saves the arraylist of tasks to a file called duke.txt in the current directory.
     * It writes all the properties of Tasks.Task t (which are strings) to the file using PrintWriter.
     *
     * @Function
     * @UsedIn: tasklist.addTask, tasklist.taskDone
     */
    public void saveDuke(ArrayList<Task> tasks) {

        System.out.println("Saving has been disabled temporarily as we slowly morph ComPAL from Duke.");
        /*try {
            File f = new File(saveFilePath);
            PrintWriter pw = new PrintWriter(f);
            for (Task t : tasks) {
                pw.printf(generateStorageString());
            }
            pw.close();

        } catch (FileNotFoundException e) {
            System.out.println("Save-file not found. Will generate new one.");
        }
        */
    }


    /**
     * This function loads from the textfile the list of tasks into the arraylist on startup.
     * It creates new Tasks.Task objects based on the symbol read i.e if E,
     * then Tasks.Task t = new Tasks.Event(description);and then we add the task to the arraylist
     *
     * @Function
     * @UsedIn: COMPal.Duke Constructor
     */
    public void loadDuke(ArrayList<Task> tasks) {

        try {
            File f = new File(saveFilePath);
            FileReader fr = new FileReader(f);

            //read into a char array
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = fr.read()) != -1) {
                sb.append((char) c);
            }

            //Set up a scanner to read and parse the strings
            String cmds = sb.toString();
            Scanner strScanner = new Scanner(cmds);
            while (strScanner.hasNext()) {
                switch (strScanner.next()) {
                case "E":
                    String done = strScanner.next().trim();
                    Task t = new Event(strScanner.nextLine().strip());
                    if (done.equals("true")) {
                        t.isDone = true;
                    }
                    tasks.add(t);
                    break;
                case "D":
                    String done1 = strScanner.next().trim();
                    Task t1 = new Deadline(strScanner.nextLine().strip());
                    if (done1.equals("true")) {
                        t1.isDone = true;
                    }
                    tasks.add(t1);
                    break;
                case "T":
                    String done2 = strScanner.next().trim();
                    Task t2 = new Todo(strScanner.nextLine().strip());
                    if (done2.equals("true")) {
                        t2.isDone = true;
                    }
                    tasks.add(t2);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + strScanner.next());
                }


            }


        } catch (IOException e) {
            System.out.println("Save File not found. Will create new save file.");
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
     * @param toSave String
     */
    public void saveString(String toSave) {
        try {
            File f = new File(saveFilePath);
            PrintWriter pw = new PrintWriter(f);
            pw.printf("%s\n", toSave);
            pw.close();

        } catch (FileNotFoundException e) {
            System.out.println("Save-file not found. Will generate new one.");
        }
    }

    /**
     * Returns the user's name as a String.
     * @return
     */
    public String getUserName() {
        File f = new File(saveFilePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            return br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Username Unknown";
    }


}
