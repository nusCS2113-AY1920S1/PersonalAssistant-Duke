package main;

import command.Command;
import degree.Degree;
import exception.DukeException;
import javafx.application.Application;
import javafx.stage.Stage;
import parser.Parser;
import storage.Storage;
import task.TaskList;
import ui.UI;
import list.DegreeList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, List<String>> degrees = new HashMap<>();
    private Map<String, Degree> degreeInfo = new HashMap<>();
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
            ui.showLoadingError();
        }
        try{
            setDegrees(storage.fetchListOutput("listdegrees"));
            loadDegrees();
        } catch (DukeException e) {
            degrees.clear();
            degreeInfo.clear();
            System.out.println(e.getLocalizedMessage());
            System.out.println("Degree Information Failed to Load, please contact Administrator");
        }
        this.lists = new DegreeList();
    }

    /**
     * According to each item in degrees list, fetch data from storage and add it
     *
     * @throws DukeException is thrown when unable to fetch the related data
     */
    private void loadDegrees() throws DukeException {
        for(Map.Entry<String, List<String>> pair : degrees.entrySet())
        {
            initDegree(pair.getKey(), pair.getValue());
        }
    }

    private void initDegree(String degree, List<String> options) throws DukeException {
        try {
            if (options == null) {
                degreeInfo.put(degree, new Degree(storage.fetchListOutput(degree)));
            } else {
                for (String x : options) {
                    //Mysterious Java Lang Reflect InvocationTarget Exception
                    //List of Strings can be fetched
                    //Degree cannot be created for any multi option Degree (at all)
                    //degreeInfo.put(x, new Degree(storage.fetchListOutput(x)));
                }
            }
        } catch (DukeException e) {
            throw new DukeException("Degree: " + degree + ": " + e.getLocalizedMessage());
        }
    }


    /**
     * Sets up the degrees which are available to the user from storage
     *
     * @param listdegrees is the csv file containing the information of each degree file
     *                     a degree can have multiple options, in that case, it is mapped to non null
     * @throws DukeException is thrown when listdegrees csv cannot be found
     */
    private void setDegrees(List<String> listdegrees) throws DukeException {
        if(listdegrees == null)
            throw new DukeException("listdegrees.csv file not found");
        for (String listdegree : listdegrees) {
            String[] split = listdegree.split(",");
            addDegree(split);
        }

    }

    /**
     * Adds a new Degree to the list of degree information
     *
     * @param split is the comma separated file containing information about the degree and its options
     * @throws DukeException is thrown if there is no degree information in the first column
     */
    private void addDegree(String[] split) throws DukeException {
        if(split.length == 1)
            degrees.put(split[0], null);
        else
        {
            if(split[0].isBlank())
                throw new DukeException("Unable to find main degree");
            List<String> temp = new ArrayList<>();
            for(int i = 1; i < split.length; i ++)
            {
                if(!split[i].isBlank())
                    temp.add(split[i]);
            }
            degrees.put(split[0], temp);
        }
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
/*            Degree temp = new Degree(storage.fetchListOutput("ISEP1"));
            for(Map.Entry<String, List<String>> pair : degrees.entrySet())
            {
                String degree = pair.getKey();
                List<String> options = pair.getValue();
                if(options == null)
                    System.out.println(degree);
                else
                {
                    System.out.print(degree + ": ");
                    for (String option: options) {
                        System.out.print(option + " ");
                    }
                    System.out.println();
                }
            }*/
            Command c = Parser.parse(line);
            c.execute(this.myList, this.ui, this.storage, this.lists);
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
                c.execute(this.myList, this.ui, this.storage, this.lists);
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


