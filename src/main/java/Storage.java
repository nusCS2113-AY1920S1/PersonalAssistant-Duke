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
     * It writes all the properties of Task t (which are strings) to the file using PrintWriter
     * @UsedIn: tasklist.addTask, tasklist.taskDone
     *
     */
    protected void saveDuke(ArrayList<Task> tasks){
        File f = new File(saveFilePath);
        try {
            PrintWriter pw = new PrintWriter(f);
            for(Task t:tasks){
                pw.printf("%s %s %s\n",t.symbol,t.isDone,t.description);
            }
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /***
     * @Function
     * This function loads from the textfile the list of tasks into the arraylist on startup.
     * It creates new Task objects based on the symbol read i.e if E, then Task t = new Event(description);
     * and then we add the task to the arraylist
     *
     * @UsedIn: Duke Constructor
     *
     */
    protected void loadDuke(ArrayList<Task> tasks){
        File f = new File(saveFilePath);
        try {
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
            e.printStackTrace();
        }
    }



}
