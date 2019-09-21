package compal.main;

import compal.inputs.Ui;
import compal.logic.parser.ParserManager;
import compal.storage.Storage;
import compal.storage.StorageFile;
import compal.tasks.TaskList;

import javax.swing.text.html.parser.Parser;
import java.util.ArrayList;

import static java.lang.System.exit;

public class Duke {

    //***Class Properties/Variables***--------------------------------------------------------------------------------->

    //objects supporting COMPal.Duke
    public Ui ui;
    public Storage storage;
    public TaskList tasklist;
    public ParserManager parser;

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
        System.out.println("Duke:LOG: In Duke Constructor");
        //Instantiate objects
        tasklist = new TaskList(this);

        storage = new StorageFile();


        //checks if storage is empty. If empty, create new ArrayList for storing Task objects. Else, load the current
        //arraylist stored in the binary file into tasklist.arrlist
        if (storage.loadCompal() == null) {
            tasklist.arrlist = new ArrayList<>();
        } else {
            tasklist.arrlist = storage.loadCompal();

        }

        ui = new Ui(this, tasklist.arrlist);

        //start parsing commands
        parser = new ParserManager(this,tasklist);
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






