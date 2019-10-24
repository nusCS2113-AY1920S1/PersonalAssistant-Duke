package main;

import command.AddCommand;
import command.Command;
import command.CommandList;
import command.ModCommand;
import exception.DukeException;
import javafx.application.Application;
import javafx.stage.Stage;
import list.DegreeListStorage;
import parser.Parser;
import storage.Storage;
import task.TaskList;
import ui.UI;
import list.DegreeList;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * The JavaFX.Main.Duke class inherits methods from Applications and allows it to be called by another class.
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
    private Storage storage;
    private UI ui;
    private Parser parse;
    private DegreeList lists;
    private CommandList commandList;
    private DegreeListStorage degreeListStorage;
    //private static ArrayList<String> mydegrees = new ArrayList<>();

    public Duke() {

    }

    /*public static ArrayList<String> getTasks() {
        return mydegrees;
    }
    */

    /**
     * The constructor that is called when the GUI is starting up.
     * It will initialise all the classes related to the management of user input and save data.
     * Its output will go to the terminal, which will then be echoed to the UI.UI as daduke.
     * Its initialization requires the file path of the save file in order to save the task data.
     *
     * @param filePath The file path of the save file.
     */
    //Method to initialize all important classes and data on startup
    public Duke(String filePath) {

        ui = new UI(); //initialize ui class that handles input from user
        this.ui = new UI();
        this.commandList = new CommandList();
        this.storage = new Storage(filePath);
        try {
            myList = new TaskList(storage.load());
            DegreeListStorage degreeListStorage = new DegreeListStorage();
            degreeListStorage.ReadFile();
        } catch (DukeException e) {
            ui.showLoadingError();
            myList = new TaskList();
        }
        this.lists = new DegreeList();
    }

    public String reminder() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(output);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use your special stream
        System.setOut(ps);
        myList.printReminders();
        // Put things back
        System.out.flush();
        System.setOut(old);
        // Show what happened
        System.out.println(output.toString());
        return output.toString();
    }

    /**
     * Method to run the JavaFX.Main.Duke program.
     * @return
     */
    //method output initial reading of save file
    public String run(String line) throws DukeException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(output);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use your special stream
        System.setOut(ps);
        //ui.showWelcome();
        boolean isExit = false;
        //while(!this.isExit) {
        Command c;
        try {
            //String line = ui.readCommand();
            ui.showLine();
            Scanner temp = new Scanner(line);
            if (!temp.hasNext()) {
                throw new DukeException("Empty Command!");
            }
            String command = temp.next();
            if (command.matches("undo|redo")) {
                if (temp.hasNextLine()) {
                    throw new DukeException(command + " should not have any other arguments (whitespace acceptable)");
                } else if (command.matches("undo")) {
                    commandList.undo();
                    lists = commandList.getDegreeLists();
                    myList = commandList.getTaskList();
                } else if (command.matches("redo")) {
                    commandList.redo();
                    lists = commandList.getDegreeLists();
                    myList = commandList.getTaskList();
                }
            } else {
                c = Parser.parse(line);
                //check if the command is undoable
                if ((c.getClass() == AddCommand.class) | (c.getClass() == ModCommand.class)) {
                    commandList.addCommand(c, this.myList, this.ui, this.storage, this.lists, line);
                } else {
                    isExit = c.isExit();
                    c.execute(this.myList, this.ui, this.storage, this.lists);
                }
            }
        } catch (DukeException | NullPointerException | IOException e) {
            ui.showError(e.getLocalizedMessage());
        } finally {
            ui.showLine();
            // Put things back
            System.out.flush();
            System.setOut(old);
            // Show what happened
            System.out.println(output.toString());

        }
        return output.toString();
    }

    private void run() {
        ui.showWelcome(this.myList);
        boolean isExit = false;
        while (!isExit) {
            try {
                String line = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(line);
                isExit = c.isExit();
                c.execute(this.myList, this.ui, this.storage, this.lists);
            } catch (DukeException | NullPointerException | IOException e) {
                ui.showError(e.getLocalizedMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * This method is run when the GUI is started up.
     * It reads in data from a list, that is taken from the save file, and outputs it to the terminal.
     * It will then be returned to JavaFX.MainWindow.java to be output to the GUI
     *
     * @param myList The array list to be read from, it should contain the save data.
     * @return The save data as a string to be output to the GUI.
     */

    /*    public String getSave(TaskList myList) {
        // Create a stream to hold the output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(output);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use your special stream
        System.setOut(ps);

        //Java will read whatever is output to the console, and relay it to the chatbox
        storage.readSave(myList); //initial reading of save file during startup

        // Put things back
        System.out.flush();
        System.setOut(old);
        // Show what happened
        System.out.println(output.toString());

        return output.toString();
    }*/

    /**
     * Method to run the duke program with the file path of the save file.
     * To be overwritten.
     *
     * @param args A duke program.
     */
    public static void main(String[] args) {

        new Duke("save.txt").run();
        System.exit(0);
    }


    /**
     * Method to be overwritten in the JavaFX.Main class.
     *
     * @param stage The stage object to be initialized, done in the JavaFX.Main class.
     */
    // This entire start method is to be overwritten by the start method in JavaFX.Main
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
     * @return The string to be written by JavaFX.Main.Duke in the GUI.
     *
     */
    /*
    public String getResponse(String myString) {
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
*/
}


