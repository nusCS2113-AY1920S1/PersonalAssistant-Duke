package Inputs;

import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.Todo;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    private String saveFilePath;

    public Storage(String filePath){
        saveFilePath=filePath;
    }


    /***
     * @Function
     * This function saves the arraylist of tasks to a file called duke.txt in the current directory.
     * It writes all the properties of Tasks.Task t (which are strings) to the file using PrintWriter
     * @UsedIn: tasklist.addTask, tasklist.taskDone
     *
     */
    public void saveDuke(ArrayList<Task> tasks){

        try {
            File f = new File(saveFilePath);
            PrintWriter pw = new PrintWriter(f);
            for(Task t:tasks){
                pw.printf("%s %s %s\n",t.symbol,t.isDone,t.description);
            }
            pw.close();

        } catch (FileNotFoundException e) {
            System.out.println("Save-file not found. Will generate new one.");
        }
    }


    /***
     * @Function
     * This function loads from the textfile the list of tasks into the arraylist on startup.
     * It creates new Tasks.Task objects based on the symbol read i.e if E, then Tasks.Task t = new Tasks.Event(description);
     * and then we add the task to the arraylist
     *
     * @UsedIn: COMPal.Duke Constructor
     *
     */
    public void loadDuke(ArrayList<Task> tasks){

        try {
            File f = new File(saveFilePath);
            FileReader fr = new FileReader(f);

            //read into a char array
            StringBuilder sb = new StringBuilder();
            int c;
            while((c=fr.read())!=-1){
                sb.append((char)c);
            }

            //Set up a scanner to read and parse the strings
            String cmds=sb.toString();
            Scanner strScanner = new Scanner(cmds);
            while(strScanner.hasNext()){
                switch(strScanner.next()){
                    case "E":
                        String done = strScanner.next().trim();
                        Task t = new Event(strScanner.nextLine().strip());
                        if(done.equals("true")){
                            t.isDone=true;
                        }
                        tasks.add(t);
                        break;
                    case "D":
                        String done1 = strScanner.next().trim();
                        Task t1 = new Deadline(strScanner.nextLine().strip());
                        if(done1.equals("true")){
                            t1.isDone=true;
                        }
                        tasks.add(t1);
                        break;
                    case "T":
                        String done2 = strScanner.next().trim();
                        Task t2 = new Todo(strScanner.nextLine().strip());
                        if(done2.equals("true")){
                            t2.isDone=true;
                        }
                        tasks.add(t2);
                        break;

                }


            }


        } catch (IOException e) {
            System.out.println("Save File not found. Will create new save file.");
        }
    }



}
