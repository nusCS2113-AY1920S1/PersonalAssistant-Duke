package compal.main;

import compal.inputs.Parser;
import compal.inputs.Storage;
import compal.inputs.Ui;
import compal.tasks.TaskList;
import javafx.application.Application;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.System.exit;

public class Duke {

    //***Class Properties/Variables***--------------------------------------------------------------------------------->

    //objects supporting COMPal.Duke
    public Ui ui;
    public Storage storage;
    public TaskList tasklist;
    public Parser parser;

    //----------------------->





    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->


    /**
     * Constructor.
     * Initializes the supporting objects.
     * Starts off the parser CLI parsing loop.
     *
     */
    public Duke() {
        System.out.println("COMPal.Duke constructor");
        //Instantiate objects
        tasklist = new TaskList(this);

        storage = new Storage();


        //checks if storage is empty. If empty, create new ArrayList for storing Task objects. Else, load the current
        //arraylist stored in the binary file into tasklist.arrlist
        if (storage.loadCompal() == null) {
            tasklist.arrlist = new ArrayList<>();
        } else {
            tasklist.arrlist = storage.loadCompal();

        }

        ui = new Ui(this, tasklist.arrlist);

        //start parsing commands
        parser = new Parser(this);
    }

    //----------------------->




    //***MISC FUNCTIONS***----------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    /**
     * This function handles the exiting/shutdown of the program compal.main.Duke .
     *
     * @UsedIn: parser.processCommands
     */
    public void exitDuke() {
        System.out.println("Bye. Hope to see you again soon!");
        exit(0);
    }


    //----------------------->





    /**
     * This static inner class is the custom exception class extending Exception
     * that overwrites toString() for returning custom exception messages.
     * It is thrown when command is unknown or when there are invalid arguments.
     *
     * @InnerClass Extends Exception
     */
    public static class DukeException extends Exception {

        String description;

        public DukeException(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }




}






