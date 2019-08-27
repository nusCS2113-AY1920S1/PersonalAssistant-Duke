import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String SEPARATOR = "____________________________________________________________";
        System.out.println("Hello from\n" + logo);
        System.out.println(SEPARATOR);
        System.out.println("Hello! I'm Duke"); //introduction
        System.out.println("What can I do for you?");
        System.out.println(SEPARATOR);

        String myString = inputCommand(); //it will either be the description or "done [some number]"
        ArrayList<Task> myList = new ArrayList<>(); //Instantiate an array list of a dynamic size and class Task

        // as long as input is not bye, keep running
        while (!myString.equals("bye")) {
            System.out.println(SEPARATOR);
            if (myString.equals("list")) { //request for list, output the storage
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < myList.size(); i++) { //Standard for-each loop: for (String element: myList)
                    System.out.println((i + 1) + "." + myList.get(i).getStatusIcon());
                }
            }
            //if not requesting for list, check for done, event, todos, deadline
            else {
                //splits the input according to white spaces, limit 2 means it only splits 1 space
                String[] bufferArray = myString.split(" ", 2);


                //check if first word is "done", second word should be an integer if true
                if (bufferArray[0].equals("done")) {
                    int taskNumber = Integer.parseInt(bufferArray[1]); //get the task number as an integer
                    myList.get(taskNumber - 1).markAsDone(); //marks that task number as done
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println((taskNumber) + "." + myList.get(taskNumber - 1).getStatusIcon());
                }
                //First word is not done, hence the user is adding a task
                //check if its a todos, adds a standard task description with no timing
                else if (bufferArray[0].equals("todo")) {
                    Task newTask = new Todo(bufferArray[1]); //Create a new todos with description from user input
                    myList.add(newTask); //adds the task to the list
                    System.out.println("Got it. I've added this task:");
                    System.out.println(newTask.getStatusIcon());
                    System.out.println("Now you have " + myList.size() + " tasks in the list.");

                }
                //check if its a deadline, there will be a "/by "
                else if (bufferArray[0].equals("deadline")) {

                    //Split the input into 2, before and after /by
                    String[] bufferDeadline = myString.split("/by ", 2);
                    //Now, split the first part to exclude the word "deadline"
                    String[] bufferDescription = bufferDeadline[0].split(" ", 2);

                    //Create a new deadline with description from user input
                    Task newTask = new Deadline(bufferDescription[1], bufferDeadline[1]);
                    myList.add(newTask); //adds the task to the list
                    System.out.println("Got it. I've added this task:");
                    System.out.println(newTask.getStatusIcon());
                    System.out.println("Now you have " + myList.size() + " tasks in the list.");
                }
                //check if its an event
                else if (bufferArray[0].equals("event")) {

                    //Split the input into 2, before and after /at
                    String[] bufferEvent = myString.split("/at ", 2);
                    //Now, split the first part to exclude the word "event"
                    String[] bufferDescription = bufferEvent[0].split(" ", 2);

                    //Create a new deadline with description from user input
                    Task newTask = new Event(bufferDescription[1], bufferEvent[1]);
                    myList.add(newTask); //adds the task to the list
                    System.out.println("Got it. I've added this task:");
                    System.out.println(newTask.getStatusIcon());
                    System.out.println("Now you have " + myList.size() + " tasks in the list.");
                }


                //first word is not "done", hence it is a task description
                else {
                    System.out.println("Added: " + myString);
                    Task newTask = new Task(myString); //Create a new task with description from user input
                    myList.add(newTask); //adds the task to the list
                }
            }
            System.out.println(SEPARATOR);
            myString = inputCommand(); //after processing command, wait for new input form user
        }

        //indicates end of duke
        System.out.println(SEPARATOR);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
    }

    private static String inputCommand() { //read input and returns that input to be processed in main
        Scanner input = new Scanner(System.in);

        return input.nextLine();
    }
}


