package Storage;

import myTasks.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * The class that handles save data.
 * It creates a save object that contains the file path to the save file, and methods to edit the save file.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.0
 */
public class Storage {
    protected String destination; //string containing location of save file

    /**
     * Constructer of a save object.
     * This save object has a specific save file, which is taken in as argument during construction.
     *
     * @param destination The file path of the save file.
     */
    public Storage (String destination) { //initialization with destination, this will be used throughout the program
        this.destination = destination;
    }


    /**
     * Method to read data from a persistent storage and output them to the taskList specified in the argument.
     * The save file has already been declared during construction of this object, hence only the taskList is needed.
     * It will also output the content of the save data in a format friendly to the reader.
     *
     * @param myTasks The taskList that will contain the save data from the save file.
     */
    //method to read data from a persistent storage and output them to a list provided as argument
    //Should be done in the beginning
    public void readSave(TaskList myTasks) {

        try (BufferedReader br = Files.newBufferedReader(Paths.get(this.destination))) {

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
                    myTasks.addToListQuietly(newTask);
                }
                else if (bufferLine[0].equals("[E]")) {
                    Task newTask = new Event(bufferLine[2], bufferLine[3]);
                    if (bufferLine[1].equals("1")) {
                        newTask.markAsDone();
                    }
                    myTasks.addToListQuietly(newTask);
                }
                //Task type is a todos, no dates
                else {
                    Task newTask = new Todo(bufferLine[2]);
                    if (bufferLine[1].equals("1")) {
                        newTask.markAsDone();
                    }
                    myTasks.addToListQuietly(newTask);
                }
            }

            br.close();

            //once all data has been added to list, display the list
            if (saveExistence) {
                System.out.println("Save detected. Here are your tasks from the previous session: ");
                for (int i = 0; i < myTasks.getSize(); i++) { //Standard for-each loop: for (String element: myList)
                    System.out.println((i + 1) + "." + myTasks.getTask(i).getStatusIcon());
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

    /**
     * Method to read the save file and output them into the taskList specified as an argument.
     * This will not output any text regarding the existence of the save file.
     * It will also not output any tasks from the save data.
     * This is more a method to be used in the background without spamming the user.
     *
     * @param myTasks The taskList to be written to.
     */
    //method to read data from a persistent storage and output them to a list provided as argument
    //This reads in quietly without outputting anything
    public void readSaveQuietly(TaskList myTasks) {

        try (BufferedReader br = Files.newBufferedReader(Paths.get(this.destination))) {

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
                    myTasks.addToListQuietly(newTask);
                }
                else if (bufferLine[0].equals("[E]")) {
                    Task newTask = new Event(bufferLine[2], bufferLine[3]);
                    if (bufferLine[1].equals("1")) {
                        newTask.markAsDone();
                    }
                    myTasks.addToListQuietly(newTask);
                }
                //Task type is a todos, no dates
                else {
                    Task newTask = new Todo(bufferLine[2]);
                    if (bufferLine[1].equals("1")) {
                        newTask.markAsDone();
                    }
                    myTasks.addToListQuietly(newTask);
                }
            }

            br.close();

            //If there is no save data, print this message
            if (!saveExistence){
                System.out.println("No save data detected on save file.");
            }

        } catch (IOException e) {
            System.out.println("No save file exists. Creating new one.");
        }
    }

    /**
     * This method takes in a new task and saves them to the save file.
     * It will append this new task in a new line of the save file.
     *
     * @param newTask The new task to be saved in the save file.
     */
    //Method to save new tasks to a persistent storage
    public void saveData(Task newTask) {

        try {

            File file = new File(this.destination);
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
            System.out.println("Error writing to file '" + this.destination + "'");
        }
    }

    /**
     * This method will update the status of a particular task in the save file.
     * From not done to done.
     * It will place the entire save file into a temporary array list so that the save file can be written to.
     * The array list will then have the specified task updated to reflect its new status.
     * Finally the array list will then be written to the save file.
     * This will overwrite the contents of the save file instead of updating only one line, however I do not know an
     * alternative.
     *
     * @param taskNumber The number of the task to be updated.
     * @throws IOException When there is no save file, this error message will be thrown.
     */
    //method to update the task completed status within a text file
    public void updateSave(int taskNumber) throws IOException {

        ArrayList<String> myList = new ArrayList<>(); //list to store save data temporarily

        try (BufferedReader br = Files.newBufferedReader(Paths.get(this.destination))) {

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

            File file = new File(this.destination);
            FileWriter clear = new FileWriter(file); //initial write to clear file
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

    /**
     * Method to delete a particular task in a save file.
     * The entire save file is saved into a temporary array list.
     * One of the elements of the array list corresponding to the task number is then deleted.
     * Then the whole array list is written to the save file, overwriting its current contents.
     *
     * @param taskNumber The number of the task to be deleted.
     * @throws IOException Error thrown when there is no save file detected.
     */
    //method to delete the task completed status within a text file
    public void deleteSave(int taskNumber) throws IOException {

        ArrayList<String> myList = new ArrayList<>(); //list to store save data temporarily

        try (BufferedReader br = Files.newBufferedReader(Paths.get(this.destination))) {

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

            File file = new File(this.destination);
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
