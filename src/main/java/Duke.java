import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
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
        Storage save = new Storage("save.txt"); //initialize the storage class
        save.readSave(myList);
        separator();

        String myString = inputCommand(); //get raw input from user
        Parser parse = new Parser(myString);

    }

    private static String inputCommand() { //read input and returns that input to be processed in main
        Scanner input = new Scanner(System.in);

        return input.nextLine();
    }

    public static void separator() {
        System.out.println("____________________________________________________________");
    }



}


