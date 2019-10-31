package main;

import command.Command;
import degree.DegreeManager;
import exception.DukeException;
import javafx.application.Application;
import javafx.stage.Stage;
import list.DegreeList;
import parser.Parser;
import storage.Storage;
import task.TaskList;
import ui.UI;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

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

    private DegreeManager degreesManager;
    private TaskList myList;
    private Storage storage;
    private UI ui;
    private Parser parse;
    private DegreeList lists;
    private ArrayList<String> mydegrees = new ArrayList<>();

    public ArrayList<String> getTasks() {
        return mydegrees;
    }
    
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
        this.storage = new Storage(filePath);
        try {
            myList = new TaskList(storage.getTaskList());
        } catch (DukeException e) {
            myList = new TaskList();
        }
        try{
            degreesManager = new DegreeManager(this.storage);
        } catch (DukeException e) {
            ui.showLoadingError();
            degreesManager = new DegreeManager();
            System.out.println(e.getLocalizedMessage());
            System.out.println("Degree Information Failed to Load, please contact Administrator");
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
        //System.out.println(output.toString());
        return output.toString();
    }

    /**
     * Method to run the JavaFX.Main.Duke program.
     *
     * @return the string to be printed by JavaFx
     */
    //method output initial reading of save file
    public String run(String line) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(output);
        PrintStream old = System.out;
        System.setOut(ps);
        try {
            ui.showLine();
            Command c = Parser.parse(line);
            c.execute(this.myList, this.ui, this.storage, this.lists, this.degreesManager);
        } catch (DukeException | NullPointerException e) {
            ui.showError(e.getLocalizedMessage());
        } finally {
            ui.showLine();
            System.out.flush();
            System.setOut(old);
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
                c.execute(this.myList, this.ui, this.storage, this.lists, this.degreesManager);
            } catch (DukeException | NullPointerException e) {
                ui.showError(e.getLocalizedMessage());
            } finally {
                ui.showLine();
            }
        }
    }


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

}


