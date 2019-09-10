import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * The Duke class inherits methods from Applications and allows it to be called by another class.
 * It is no longer the main class, but rather it will initialize the parameters, save files and all other classes
 * not related to the GUI.
 * It also acts as a liaison between the GUI and the terminal, as it will echo whatever is in the terminal to
 * the GUI.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.0
 */
public class Duke extends Application {

    private TaskList myList;
    private Storage save;
    private UI ui;
    private Parser parse;


    /**
     * The constructor that is called when the GUI is starting up.
     * It will initialise all the classes related to the management of user input and save data.
     * Its output will go to the terminal, which will then be echoed to the UI as daduke.
     * Its initialization requires the file path of the save file in order to save the task data.
     *
     * @param filePath The file path of the save file.
     */
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

        save.readSaveQuietly(myList); //Save file detection


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

    /**
     * This method is run when the GUI is started up.
     * It reads in data from a list, that is taken from the save file, and outputs it to the terminal.
     * It will then be returned to MainWindow.java to be output to the GUI
     *
     * @param myList The array list to be read from, it should contain the save data.
     * @return The save data as a string to be output to the GUI.
     */

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
     * A function to generate a response to user input.
     * This method acts as a liaison between the GUI and the terminal.
     * It receives a string from the GUI, which is the user input, and will parse that input.
     * Whatever is output in the terminal is recorded as printstream
     * This is then returned to the GUI to be printed there
     *
     * @param myString The string to be parsed.
     * @return The string to be written by Duke in the GUI.
     *
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


