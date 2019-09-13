package COMPal;

import Inputs.Parser;
import Inputs.Storage;
import Inputs.Ui;
import Tasks.TaskList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.exit;

public class Duke extends Application{

    private static final String saveFilePath="./duke.txt";

    //objects supporting COMPal.Duke
    public Ui ui;
    public Storage storage;
    public TaskList tasklist;
    public Parser parser;


    /**
     * @ClassConstructor
     * No Params, No Return Values
     * Initializes the supporting objects
     * Starts off the parser CLI parsing loop
     */
    public Duke(){
        System.out.println("COMPal.Duke constructor");
        //Instantiate objects
        tasklist = new TaskList(this);

        ui = new Ui(this,tasklist.arrlist);

        storage = new Storage(saveFilePath);
        storage.loadDuke(tasklist.arrlist); //load from the file into the arraylist, if any thing to load at all

        //start parsing commands
        parser = new Parser(this);
    }

    /**
     * @Function
     * @param stage
     * @throws Exception
     * This function is for setting up the JavaFX(GUI) stage, overridden from javafx.application.Application
     * @UsedIn: COMPal.Launcher.java (indirect call)
     */
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Start");
        ui.start(stage);
        ui.showWelcome();

    }



    /**
     * @Function
     * No Params, No Return Value
     * This function handles the exiting/shutdown of the program COMPal.Duke
     * @UsedIn: parser.processCommands
     * TODO: Save data on exit
     */
    public void exitDuke(){
        System.out.println("Bye. Hope to see you again soon!");
        exit(0);
    }



    /**
     * @Function
     * @param when
     * @return dateString
     * @UsedIn: ui.getDescription
     * This function parses the date in the format dd/MM/yyyy HHmm and returns a date in the format
     * dd MMMM yyyy hh:mma
     */
    public static String dateParse(String when){
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
     * @InnerClass
     * Extends Exception
     *
     * This static inner class is the custom exception class extending Exception
     * that overwrites toString() for returning custom exception messages
     *
     * It is thrown when command is unknown or when there are invalid arguments
     */
    public static class DukeException extends Exception{

        String description;
        public DukeException(String description){
            this.description=description;
        }

        @Override
        public String toString() {
            return description;
        }
    }



}



/**
 * @Class
 * This class is a dialog box used for implementing custom control

class DialogBox extends HBox {

    private Label text;
    private ImageView displayPicture;

    public DialogBox(Label l, ImageView iv){
        text = l;
        displayPicture = iv;

        text.setWrapText(true);
        displayPicture.setFitWidth(100);
        displayPicture.setFitHeight(100);

        this.setAlignment(Pos.TOP_RIGHT);
        this.getChildren().addAll(text,displayPicture);
    }

    private void flip(){
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    public static DialogBox getUserDialog(Label l, ImageView iv){
        return new DialogBox(l,iv);
    }

    public static DialogBox getDukeDialog(Label l, ImageView iv){
        var db = new DialogBox(l,iv);
        db.flip();
        return db;
    }


}


 */

