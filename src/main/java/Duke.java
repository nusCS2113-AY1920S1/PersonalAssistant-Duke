import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Duke extends Application {

    private TaskList myList;
    private Storage save;
    private UI ui;
    private Parser parse;




    //Method to initialize all important classes and data on startup
    public Duke(String filePath) {

        ui = new UI(); //initialize ui class that handles input from user


        ArrayList<Task> myTasks = new ArrayList<>(); //Instantiate an array list of a dynamic size and class Task
        myList = new TaskList(myTasks); //Initialise tasklist
        save = new Storage(filePath); //initialize the storage class
        parse = new Parser();
        parse.setSave(save); //set location of save file
        parse.setTaskList(myList); //set the list that will be updated


        ui.welcomeMessage(); //Output welcome message

        getSave(myList); //Save file detection


        /*
        String myString = ui.inputCommand(); //get raw input from user

        while (!myString.equals("bye")) {
            parse.parseInput(myString);
            myString = ui.inputCommand();
        }

        ui.byeMessage();
        */


    }

    //method output initial reading of save file
    private void run() {

    }

    String getSave(TaskList myList) {
        // Create a stream to hold the output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(output);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use your special stream
        System.setOut(ps);

        //Java will read whatever is output to the console, and relay it to the chatbox
        save.readSave(myList); //initial reading of save file during startup

        // Put things back
        System.out.flush();
        System.setOut(old);
        // Show what happened
        System.out.println(output.toString());

        return output.toString();
    }


    private static void main(String[] args) {

        new Duke("save.txt").run();

    }


    // This entire start method is to be overwritten by the start method in Main
    @Override
    public void start(Stage stage) {

    }


    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */

    String getResponse(String myString) {
        // Create a stream to hold the output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(output);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use your special stream
        System.setOut(ps);

        //Java will read whatever is output to the console, and relay it to the chatbox
        parse.parseInput(myString);

        // Put things back
        System.out.flush();
        System.setOut(old);
        // Show what happened
        System.out.println(output.toString());

        return output.toString();
    }

}


