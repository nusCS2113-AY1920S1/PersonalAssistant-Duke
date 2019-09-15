package compal.main;

import compal.inputs.Parser;
import compal.inputs.Storage;
import compal.inputs.Ui;
import compal.tasks.TaskList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.exit;

public class Duke extends Application {

    //objects supporting COMPal.Duke
    public Ui ui;
    public Storage storage;
    public TaskList tasklist;
    public Parser parser;


    /**
     * Initializes the supporting objects.
     * Starts off the parser CLI parsing loop.
     *
     * @ClassConstructor No Params, No Return Values
     */
    public Duke() {
        System.out.println("COMPal.Duke constructor");
        //Instantiate objects
        tasklist = new TaskList(this);

        ui = new Ui(this, tasklist.arrlist);

        storage = new Storage();

        storage.loadDuke(tasklist.arrlist); //load from the file into the arraylist, if any thing to load at all

        //start parsing commands
        parser = new Parser(this);
    }

    /**
     * This function parses the date in the format dd/MM/yyyy HHmm and returns a date in the format
     * dd MMMM yyyy hh:mma .
     *
     * @param when date input to be formatted
     * @return dateString format the date of input when
     * @Function
     * @UsedIn: ui.getDescription
     */
    public static String dateParse(String when) {
        //parse date
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date date = null;
        try {
            date = format.parse(when);
        } catch (ParseException e) {
            return "false";
        }
        format = new SimpleDateFormat("dd MMMM yyyy hh:mma");
        when = format.format(date);
        return when;
    }

    /**
     * This function is for setting up the JavaFX(GUI) stage, overridden from javafx.application.Application .
     *
     * @param stage the stage to read settings from
     * @throws Exception throw error message of javaFX
     * @Function
     * @UsedIn: COMPal.Launcher.java (indirect call)
     */
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Start");
        ui.checkInit();
    }

    /**
     * This function handles the exiting/shutdown of the program compal.main.Duke .
     *
     * @Function No Params, No Return Value
     * @UsedIn: parser.processCommands
     */
    public void exitDuke() {
        System.out.println("Bye. Hope to see you again soon!");
        exit(0);
    }

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


/**
 * Don't delete. For future reference
 *
 * @Class This class is a dialog box used for implementing custom control
 * <p>
 * class DialogBox extends HBox {
 * <p>
 * private Label text;
 * private ImageView displayPicture;
 * <p>
 * public DialogBox(Label l, ImageView iv){
 * text = l;
 * displayPicture = iv;
 * <p>
 * text.setWrapText(true);
 * displayPicture.setFitWidth(100);
 * displayPicture.setFitHeight(100);
 * <p>
 * this.setAlignment(Pos.TOP_RIGHT);
 * this.getChildren().addAll(text,displayPicture);
 * }
 * <p>
 * private void flip(){
 * this.setAlignment(Pos.TOP_LEFT);
 * ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
 * FXCollections.reverse(tmp);
 * this.getChildren().setAll(tmp);
 * }
 * <p>
 * public static DialogBox getUserDialog(Label l, ImageView iv){
 * return new DialogBox(l,iv);
 * }
 * <p>
 * public static DialogBox getDukeDialog(Label l, ImageView iv){
 * var db = new DialogBox(l,iv);
 * db.flip();
 * return db;
 * }
 * <p>
 * <p>
 * }
 */

