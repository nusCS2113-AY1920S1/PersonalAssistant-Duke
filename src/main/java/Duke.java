import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Duke {
    public static void main(String[] args) {

        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        System.out.println("Hello from\n" + logo);
        separator();
        System.out.println("Hello! I'm Duke"); //introduction
        System.out.println("What can I do for you?");
        separator();

        ArrayList<Task> myList = new ArrayList<>(); //Instantiate an array list of a dynamic size and class Task
        readSave(myList);
        separator();

        String myString = inputCommand(); //get raw input from user

        // as long as input is not bye, keep running
        while (!myString.equals("bye")) {
            separator();
            if (myString.equals("list")) { //request for list, output the storage
                getList(myList);
            }
            //if not requesting for list, check for done, event, todos, deadline
            else {
                //splits the input according to white spaces, limit 2 means it only splits 1 space
                String[] bufferArray = myString.split(" ", 2);


                //check if first word is "done", second word should be an integer if true
                if (bufferArray[0].equals("done")) {
                    try {
                        int taskNumber = Integer.parseInt(bufferArray[1]); //get the task number as an integer
                        myList.get(taskNumber - 1).markAsDone(); //marks that task number as done
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println((taskNumber) + "." + myList.get(taskNumber - 1).getStatusIcon());
                        updateSave(taskNumber); //Updates task status in save file
                    } catch (ArrayIndexOutOfBoundsException | IOException e) {
                        System.out.println("Error! 'Done' must be followed by a number. Please type 'list' to display " +
                                "the list of tasks and their numbers.");
                    } catch (IndexOutOfBoundsException d) {
                        System.out.println("Error! Task list does not contain that task number.");
                    }
                }

                //check if first word is "delete", second word should be an integer if true
                if (bufferArray[0].equals("delete")) {
                    try {
                        int taskNumber = Integer.parseInt(bufferArray[1]); //get the task number as an integer
                        System.out.println("Noted. I've removed this task:");
                        System.out.println(myList.get(taskNumber - 1).getStatusIcon());
                        myList.remove(taskNumber - 1); //remove the element from the list
                        System.out.println("Now you have " + myList.size() + " task(s) in the list.");
                        deleteSave(taskNumber); //Updates task status in save file
                    } catch (ArrayIndexOutOfBoundsException | IOException e) {
                        System.out.println("Error! 'Delete' must be followed by a number. Please type 'list' to display " +
                                "the list of tasks and their numbers.");
                    } catch (IndexOutOfBoundsException d) {
                        System.out.println("Error! Task list does not contain that task number.");
                    }
                }

                //First word is not 'done', hence the user is adding a task
                //check if its a todos, adds a standard task description with no timing
                //After adding a new task to list, save this data to a save file
                else {
                    if (bufferArray[0].equals("todo")) {

                        try {
                            Task newTask = new Todo(bufferArray[1]); //Create a new todos with description from user input
                            addToList(newTask, myList);
                            saveData(newTask);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Error! Description of a Todo task must not be empty.");
                        }

                    }
                    //check if its a deadline, there will be a "/by "
                    else if (bufferArray[0].equals("deadline")) {

                        try {
                            //Split the input into 2, before and after /by
                            String[] bufferDeadline = myString.split("/by ", 2);
                            //Now, split the first part to exclude the word "deadline"
                            String[] bufferDescription = bufferDeadline[0].split(" ", 2);
                            //Remove the space after task description and /by for easier formatting
                            bufferDescription[1] = bufferDescription[1].substring(0, bufferDescription[1].length() - 1);

                            //Have to check whether date format is correct
                            //This provides the pattern of the date input
                            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HHmm");
                            //This reads the date input in the format given
                            Date newDate = dateFormatter.parse(bufferDeadline[1]);

                            //Create a new deadline with description from user input
                            Task newTask = new Deadline(bufferDescription[1], bufferDeadline[1]);
                            addToList(newTask, myList);
                            saveData(newTask);

                            //Error to be displayed when /by is not in deadline input
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Error! Deadline tasks must contain '/by' followed by the deadline.");
                        } catch (ParseException c) {
                            System.out.println("Error! Please enter date in the format DD-MM-YYYY 2359.");
                        }
                    }
                    //check if its an event, there should be a "/at "
                    else if (bufferArray[0].equals("event")) {

                        try {
                            //Split the input into 2, before and after /at
                            String[] bufferEvent = myString.split("/at ", 2);
                            //Now, split the first part to exclude the word "event"
                            String[] bufferDescription = bufferEvent[0].split(" ", 2);
                            //Remove the space after task description and /by for easier formatting
                            bufferDescription[1] = bufferDescription[1].substring(0, bufferDescription[1].length() - 1);

                            //Have to check whether date format is correct
                            //This provides the pattern of the date input
                            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HHmm");
                            //This reads the date input in the format given
                            Date newDate = dateFormatter.parse(bufferEvent[1]);

                            //Create a new deadline with description from user input
                            Task newTask = new Event(bufferDescription[1], bufferEvent[1]);
                            addToList(newTask, myList);
                            saveData(newTask);

                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Error! Event tasks must contain '/at' followed by the time of event.");
                        } catch (ParseException c) {
                            System.out.println("Error! Please enter date in the format DD-MM-YYYY 2359.");
                        }
                    }


                    //Error when first word is not any of the above keywords
                    else {
                        System.out.println("Error! I do not understand what that mean.");
                    }
                }
            }
            separator();
            myString = inputCommand(); //after processing command, wait for new input form user
        }

        //indicates end of duke
        separator();
        System.out.println("Bye. Hope to see you again soon!");
        separator();
    }

    private static String inputCommand() { //read input and returns that input to be processed in main
        Scanner input = new Scanner(System.in);

        return input.nextLine();
    }

    private static void separator() {
        System.out.println("____________________________________________________________");
    }

    //Method to add a task to a list and output the size of list
    private static void addToList(Task taskData, ArrayList<Task> myTasks) {
        myTasks.add(taskData);
        System.out.println("Got it. I've added this task:");
        System.out.println(taskData.getStatusIcon());
        System.out.println("Now you have " + myTasks.size() + " task(s) in the list.");
    }

    //Adds to list from save data without spamming "got it..."
    private static void addToListQuietly(Task taskData, ArrayList<Task> myTasks) {
        myTasks.add(taskData);
    }

    //Method to get the tasks in a list
    private static void getList(ArrayList<Task> myTasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < myTasks.size(); i++) { //Standard for-each loop: for (String element: myList)
            System.out.println((i + 1) + "." + myTasks.get(i).getStatusIcon());
        }
    }

    //Method to save new tasks to a persistent storage
    private static void saveData(Task newTask) {
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
        }
        catch(IOException ex) {
            System.out.println("Error writing to file '"+ fileName + "'");
        }
    }

    //method to read data from a persistent storage and output them to a list
    //Should be done in the beginning
    private static void readSave(ArrayList<Task> myTasks) {

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

    //method to update the task completed status within a text file
    private static void updateSave(int taskNumber) throws IOException {

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
    private static void deleteSave(int taskNumber) throws IOException {

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


