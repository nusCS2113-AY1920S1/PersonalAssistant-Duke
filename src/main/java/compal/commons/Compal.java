package compal.commons;

import compal.storage.StorageManager;
import compal.ui.UiPart;
import compal.logic.parser.ParserManager;
import compal.storage.Storage;
import compal.model.tasks.TaskList;

import java.util.ArrayList;

import static java.lang.System.exit;

/**
 * Main class.
 */
public class Compal {

    //***Class Properties/Variables***--------------------------------------------------------------------------------->
    //objects supporting COMPal.Compal
    public UiPart ui;
    public Storage storage;
    public TaskList tasklist;
    public ParserManager parser;
    //----------------------->

    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    /**
     * Constructs Compal object.
     * Initializes supporting objects.
     * Starts off the parser CLI parsing loop.
     */
    public Compal() {
        System.out.println("Compal:LOG: In Compal Constructor");
        //Instantiate objects
        tasklist = new TaskList(this);

        storage = new StorageManager();

        /*
         * Checks if storage is empty. If empty, create new ArrayList for storing Task objects. Else, load the current
         * arraylist stored in the binary file into tasklist.arrlist.
         */
        if (storage.loadCompal() == null) {
            tasklist.arrlist = new ArrayList<>();
        } else {
            tasklist.arrlist = storage.loadCompal();
        }

        ui = new UiPart(this, tasklist.arrlist);

        //start parsing commands
        parser = new ParserManager(this, tasklist);
    }
    //----------------------->


    //***MISC FUNCTIONS***----------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    /**
     * This function handles the exiting/shutdown of the program Compal.main.Compal.
     * Used in parser.processCommands
     */
    public void exitDuke() {
        exit(0);
    }
    //----------------------->

    /**
     * This static inner class is the custom exception class extending Exception
     * that overwrites toString() for returning custom exception messages.
     * It is thrown when command is unknown or when there are invalid arguments.
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






