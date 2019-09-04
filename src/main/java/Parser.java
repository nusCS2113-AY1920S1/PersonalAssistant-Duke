import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Parser {
    protected Storage save; //initialize the storage class
    protected TaskList myList;



    public Parser() {

    }

    //Set the save used by the parse
    public void setSave(Storage save) {
        this.save = save;
    }

    //Currently, Parser will take in the entire raw input and taskList
    public void setTaskList(TaskList myList) {
        this.myList = myList;
    }

    //The entire point of this class is to understand and perform what the user does
    public void parseInput(String userInput) {


        // as long as input is not bye, keep running
        if (userInput.equals("list")) { //request for list, output the storage
            myList.getList();
        }
        //if not requesting for list, check for done, event, todos, deadline
        else {
            //splits the input according to white spaces, limit 2 means it only splits 1 space
            String[] bufferArray = userInput.split(" ", 2);


            //check if first word is "done", second word should be an integer if true
            if (bufferArray[0].equals("done")) {
                try {
                    int taskNumber = Integer.parseInt(bufferArray[1]); //get the task number as an integer
                    myList.updateTask(taskNumber - 1); //marks that task number as done
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println((taskNumber) + "." + myList.getTask(taskNumber - 1).getStatusIcon());
                    save.updateSave(taskNumber); //Updates task status in save file
                } catch (ArrayIndexOutOfBoundsException | IOException e) {
                    System.out.println("Error! 'Done' must be followed by a number. Please type 'list' to display " +
                            "the list of tasks and their numbers.");
                } catch (IndexOutOfBoundsException d) {
                    System.out.println("Error! Task list does not contain that task number.");
                }
            }

            //check if first word is "delete", second word should be an integer if true
            else if (bufferArray[0].equals("delete")) {
                try {
                    int taskNumber = Integer.parseInt(bufferArray[1]); //get the task number as an integer
                    //Buffer string to bait errors before doing any deletion
                    Task buffer = myList.getTask(taskNumber - 1);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(myList.getTask(taskNumber - 1).getStatusIcon());
                    myList.removeFromList(taskNumber - 1); //remove the element from the list
                    System.out.println("Now you have " + myList.getSize() + " task(s) in the list.");
                    save.deleteSave(taskNumber); //Updates task status in save file
                } catch (ArrayIndexOutOfBoundsException | IOException e) {
                    System.out.println("Error! 'Delete' must be followed by a number. Please type 'list' to display " +
                            "the list of tasks and their numbers.");
                } catch (IndexOutOfBoundsException d) {
                    System.out.println("Error! Task list does not contain that task number.");
                }
            }

            //check if first word is "find", bufferArray[1] should contain 1 word if true
            else if (bufferArray[0].equals("find")) {
                try {
                    int count = 1; //counter to count number of matching tasks
                    boolean foundFlag = false;
                    for (int i = 0; i < myList.getSize(); i++) {
                        //Checks if a word exists in task description
                        if (myList.getTask(i).getDescription().toLowerCase().contains(bufferArray[1])) {
                            //This ensures that matching tasks is only run once and only when task is matched
                            if (!foundFlag) {
                                System.out.println("Here are the matching tasks in your list:");
                                foundFlag = true;
                            }
                            System.out.println((count) + "." + myList.getTask(i).getStatusIcon());
                            count++; //increment counter by 1 everytime a task matches
                        }
                    }

                    //If there are no tasks that matches the keyword
                    if (!foundFlag) {
                        System.out.println(("Sorry! Unable to find any tasks matching that keyword"));
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Error! 'find' must be followed by a word.");
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
                        myList.addToList(newTask);
                        save.saveData(newTask);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error! Description of a Todo task must not be empty.");
                    }

                }
                //check if its a deadline, there will be a "/by "
                else if (bufferArray[0].equals("deadline")) {

                    try {
                        //Split the input into 2, before and after /by
                        String[] bufferDeadline = userInput.split("/by ", 2);
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
                        myList.addToList(newTask);
                        save.saveData(newTask);

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
                        String[] bufferEvent = userInput.split("/at ", 2);
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
                        myList.addToList(newTask);
                        save.saveData(newTask);

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

    }
}
