import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String separator = "____________________________________________________________";
        System.out.println("Hello from\n" + logo);
        System.out.println(separator);
        System.out.println("Hello! I'm Duke"); //introduction
        System.out.println("What can I do for you?");
        System.out.println(separator);

        String myString = inputCommand(); //it will either be the description or "done [some number]"
        ArrayList<Task> myList = new ArrayList<>(); //Instantiate an array list of a dynamic size and class Task

        // as long as input is not bye, keep running
        while (!myString.equals("bye")) {
            if (myString.equals("list")) { //request for list, output the storage
                System.out.println(separator);
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < myList.size(); i++) { //Standard for-each loop: for (String element: myList)
                    System.out.println((i + 1) + ".[" + myList.get(i).getStatusIcon() + "] " + myList.get(i).getDescription());
                }
                System.out.println(separator);
            }
            //if not requesting for list, check for number or task description
            else {
                String[] bufferArray = myString.split(" ", 2); //splits the input according to white spaces, limit 2 means it only splits 1 space

                if (bufferArray[0].equals("done")) { //check if first word is "done", second word should be an integer if true
                    System.out.println(separator);
                    int taskNumber = Integer.parseInt(bufferArray[1]); //get the task number as an integer
                    myList.get(taskNumber - 1).markAsDone(); //marks that task number as done
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println((taskNumber) + ".[" + myList.get(taskNumber - 1).getStatusIcon() + "] " + myList.get(taskNumber - 1).getDescription());
                    System.out.println(separator);
                }
                //first word is not "done", hence it is a task description
                else {
                    System.out.println(separator);
                    System.out.println("Added: " + myString);
                    Task newTask = new Task(myString); //Create a new task with description from user input
                    myList.add(newTask); //adds the task to the list
                    System.out.println(separator);
                }
            }
            myString = inputCommand();
        }

        //indicates end of duke
        System.out.println(separator);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(separator);
    }

    private static String inputCommand() { //read input and returns that input to be processed in main
        Scanner input = new Scanner(System.in);

        return input.nextLine();
    }
}


