import java.util.Scanner;
import java.util.ArrayList;
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

        String myString = inputCommand();
        ArrayList<String> myList = new ArrayList<>(); //Instantiate an array list of a dynamic size

        // as long as input is not bye, keep running
        while (!myString.equals("bye")) {
            if (myString.equals("list")) { //request for list, output the storage
                System.out.println(separator);
                for (int i = 0; i < myList.size(); i++) { //Standard for-each loop: for (String element: myList)
                    System.out.println((i + 1) + ". " + myList.get(i));
                }
                System.out.println(separator);
                myString = inputCommand();
            }
            else { //if not requesting for list, add command to list
                System.out.println(separator);
                System.out.println("Added: " + myString);
                myList.add(myString); //adds the command to the list
                System.out.println(separator);
                myString = inputCommand();
            }
        }

        //indicates end of duke
        System.out.println(separator);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(separator);
    }

    private static String inputCommand() { //read commands and returns that command to be processed in main
        Scanner input = new Scanner(System.in);

        return input.nextLine();
    }
}


