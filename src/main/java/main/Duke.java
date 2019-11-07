package main;

import command.*;
import degree.Degree;
import command.Command;
import degree.DegreeManager;
import exception.DukeException;
import javafx.application.Application;
import javafx.stage.Stage;
import list.DegreeList;
import list.DegreeListStorage;
import parser.Parser;
import statistics.CohortSize;
import storage.Storage;
import task.NUSEvents;
import statistics.GraduateEmployment;
import task.NUSEvents;
import task.TaskList;
import ui.UI;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
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
    private Map<String, List<String>> degrees = new HashMap<>();
    private Map<String, Degree> degreeInfo = new HashMap<>();
    private ArrayList<String> mydegrees = new ArrayList<>();

    private NUSEvents universityTaskHandler = new NUSEvents();
    private GraduateEmployment graduateEmployment = new GraduateEmployment();
    private CohortSize cohortSize = new CohortSize();

    private NUSEvents NUSEvents = new NUSEvents();

    private DegreeListStorage DegreeListStorage = new DegreeListStorage();
    private CommandList commandList = new CommandList();
    private Boolean typoFlag;
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
    public Duke(String filePath, String another_filePath) {

        ui = new UI(); //initialize ui class that handles input from user
        this.storage = new Storage(filePath, another_filePath);
        try {
            myList = new TaskList(storage.getTaskList());
        } catch (DukeException e) {
            myList = new TaskList();
            ui.showLoadingError();
        }
        try{
            degreesManager = new DegreeManager(this.storage);
        } catch (DukeException e) {
            ui.showLoadingError();
            degreesManager = new DegreeManager();
            System.out.println(e.getLocalizedMessage());
            System.out.println("Degree Information Failed to Load, please contact Administrator");
        }
        try {
            NUSEvents.loadDegreeTasks(storage.fetchListOutput("degreeTasks")); //loads information from degreeTasks.txt
        } catch (DukeException e) {
            System.out.println(e.getLocalizedMessage());
        }
        try {
            graduateEmployment.loadDegreeEmploymentStats(storage.fetchListOutput("stats_employer"));
        } catch (DukeException e) {
            System.out.println(e.getLocalizedMessage());
        }
        try {
            cohortSize.loadCohortStats(storage.fetchListOutput("cohort"));
        } catch (DukeException e) {
            System.out.println(e.getLocalizedMessage());
        }
        this.lists = new DegreeList();
        DegreeListStorage.ReadFile(storage.fetchListOutput("savedegree"));

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
    public String run(String line) throws DukeException {
        typoFlag = false;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(output);
        PrintStream old = System.out;
        System.setOut(ps);
        try {
            ui.showLine();
            Scanner temp = new Scanner(line);
            String command;
            if (!temp.hasNext()) {
                throw new DukeException("Empty Command!");
            } else {
                command = temp.next();
            }

            if (command.matches("undo")) {
                commandList.undo();
                this.myList = commandList.getTaskList();
                this.lists = commandList.getDegreeLists();
            } else if (command.matches("redo")) {
                commandList.redo();
                this.myList = commandList.getTaskList();
                this.lists = commandList.getDegreeLists();
            } else {
                Command c = Parser.parse(line);

                if ((c.getClass() == AddCommand.class) | (c.getClass() == ModCommand.class)
                        | (c.getClass() == SortCommand.class) | (c.getClass() == SwapCommand.class)) {
                    commandList.addCommand(c, this.myList, this.ui, this.storage, this.lists, this.degreesManager, line);
                } else if ((c.getClass() == BadCommand.class) || c.getClass() == null) {
                    typoFlag = true; //when the user enters a command not understood by the program, trigger flag
                    c.execute(this.myList, this.ui, this.storage, this.lists, this.degreesManager);
                } else {
                    c.execute(this.myList, this.ui, this.storage, this.lists, this.degreesManager);
                }
            }
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

        new Duke("save.txt", "savedegree.txt").run();
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
     * Method to return the current task list in duke, for use in javafx
     *
     * @return the task list to be used by javafx
     */
    public TaskList getTaskList() {
        return this.myList;
    }

    /**
     * Method to return the current choices of degree list in duke, for use in javafx
     *
     * @return the choices of degrees list to be used by javafx
     */
    public DegreeList getDegreeList() {
        return this.lists;
    }

    /**
     * Method to check if the most recent input has a type and thus the command is not accepted
     *
     * @return the flag to javafx to check for user typos
     */
    public Boolean getTypoFlag () {
        return this.typoFlag;
    }

    /**
     * Method to return the degree information stored in duke, for use in javafx
     *
     * @return the degree manager storing the degree information, of use in javafx
     */
    public DegreeManager getDegreesManager () {
        return this.degreesManager;
    }

}


