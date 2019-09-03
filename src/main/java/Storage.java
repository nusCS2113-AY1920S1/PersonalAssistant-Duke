import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Storage {
    protected String destination; //string containing location of save file

    public Storage (String destination) { //initialization with destination, this will be used throughout the program
        this.destination = destination;
    }

    //Adds to list from save data without spamming "got it..."
    private static void addToListQuietly(Task taskData, ArrayList<Task> myTasks) {
        myTasks.add(taskData);
    }

    //method to read data from a persistent storage and output them to a list provided as argument
    //Should be done in the beginning
    public static void readSave(ArrayList<Task> myTasks) {

        try (BufferedReader br = Files.newBufferedReader(Paths.get("save.txt"))) {

            boolean saveExistence = false;

            // read line by line
            //[D]|[tick]|description|date
            String myString;
            while ((myString = br.readLine()) != null) {
                saveExistence = true;
                String[] bufferLine = myString.split("/");
                //bufferLine[2] and [3] are the task description and date respectively
                //bufferLine[1] is the statusDone icon, and its [1] character should be a tick or cross
                //Check task type to see if any dates are needed
                if (bufferLine[0].equals("[D]")) {
                    Task newTask = new Deadline(bufferLine[2], bufferLine[3]);
                    //Check if the task has already been done
                    if (bufferLine[1].equals("1")) {
                        newTask.markAsDone();
                    }
                    addToListQuietly(newTask, myTasks);
                }
                else if (bufferLine[0].equals("[E]")) {
                    Task newTask = new Event(bufferLine[2], bufferLine[3]);
                    if (bufferLine[1].equals("1")) {
                        newTask.markAsDone();
                    }
                    addToListQuietly(newTask, myTasks);
                }
                //Task type is a todos, no dates
                else {
                    Task newTask = new Todo(bufferLine[2]);
                    if (bufferLine[1].equals("1")) {
                        newTask.markAsDone();
                    }
                    addToListQuietly(newTask, myTasks);
                }
            }

            br.close();

            //once all data has been added to list, display the list
            if (saveExistence) {
                System.out.println("Save detected. Here are your tasks from the previous session: ");
                for (int i = 0; i < myTasks.size(); i++) { //Standard for-each loop: for (String element: myList)
                    System.out.println((i + 1) + "." + myTasks.get(i).getStatusIcon());
                }
            }
            //If there is no save data, print this message
            else {
                System.out.println("No save data detected on save file.");
            }

        } catch (IOException e) {
            System.out.println("No save file exists. Creating new one.");
        }
    }

    //Method to save new tasks to a persistent storage
    public static void saveData(Task newTask) {
        String fileName = "save.txt"; //name to save file

        try {

            File file = new File("save.txt");
            FileWriter fw = new FileWriter(file, true); //appends incoming task to file
            BufferedWriter bw = new BufferedWriter(fw);

            // Note that write() does not automatically
            // append a newline character.
            bw.write(newTask.getType());
            bw.write("/");
            bw.write(newTask.getStatusInt());
            bw.write("/");
            bw.write(newTask.getDescription());
            bw.write("/");
            //if incoming data is deadline or event, additional segment for deadline
            if (newTask.getType().equals("[D]") || newTask.getType().equals("[E]")) {
                bw.write(newTask.getBy());
            }
            bw.newLine(); //new line for next input

            // Always close files.
            bw.close();
            fw.close();
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + fileName + "'");
        }
    }

    //method to update the task completed status within a text file
    public static void updateSave(int taskNumber) throws IOException {

        ArrayList<String> myList = new ArrayList<>(); //list to store save data temporarily

        try (BufferedReader br = Files.newBufferedReader(Paths.get("save.txt"))) {

            // read line by line
            //[D]/1/description/date
            String myString;
            while ((myString = br.readLine()) != null) {
                myList.add(myString);
            }

            br.close();

            //At this point, myList should contain all lines of save data
            //bufferUpdate contains the task that needs to be updated
            String bufferUpdate = myList.get(taskNumber - 1);
            String bufferNew = "";
            //Replace one character in the string
            bufferNew += bufferUpdate.substring(0, 4) + "1" + bufferUpdate.substring(5);

            myList.set(taskNumber - 1, bufferNew);

            File file = new File("save.txt");
            FileWriter clear = new FileWriter(file); //intial write to clear file
            clear.close();

            //Now append to empty file
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);

            //Now, myList contains the updated save data, output everything into save.txt
            for (int n = 0; n < myList.size(); n++) {
                bw.write(myList.get(n));
                bw.newLine();
            }

            //order of closing is important
            bw.close();
            fw.close();

        } catch (IOException e) {
            System.out.println("No save file exists. Creating new one.");
        }

    }

    //method to delete the task completed status within a text file
    public static void deleteSave(int taskNumber) throws IOException {

        ArrayList<String> myList = new ArrayList<>(); //list to store save data temporarily

        try (BufferedReader br = Files.newBufferedReader(Paths.get("save.txt"))) {

            // read line by line
            //[D]/1/description/date
            String myString;
            while ((myString = br.readLine()) != null) {
                myList.add(myString);
            }

            br.close();

            //At this point, myList should contain all lines of save
            //Remove the task at the index position indicated by the user
            myList.remove(taskNumber - 1);

            File file = new File("save.txt");
            FileWriter clear = new FileWriter(file); //intial write to clear file
            clear.close();

            //Now append to empty file
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);

            //Now, myList contains the updated save data, output everything into save.txt
            for (int n = 0; n < myList.size(); n++) {
                bw.write(myList.get(n));
                bw.newLine();
            }

            //order of closing is important
            bw.close();
            fw.close();

        } catch (IOException e) {
            System.out.println("No save file exists. Creating new one.");
        }

    }
}
