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

public class Duke extends Application {

    private static final String saveFilePath="./duke.txt";

    //JavaFX testing
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private Image trump;

    {
        try {
            trump = new Image(new FileInputStream("/root/IdeaProjects/duke/src/main/trump.jpeg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Image kim;

    {
        try {
            kim = new Image(new FileInputStream("/root/IdeaProjects/duke/src/main/Kim_Jong-un_IKS_2018.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //objects supporting Duke
    Ui ui;
    Storage storage;
    TaskList tasklist;
    Parser parser;


    /**
     * @ClassConstructor
     * No Params, No Return Values
     * Initializes the supporting objects
     * Starts off the parser CLI parsing loop
     */
    public Duke(){

        //Instantiate objects
        tasklist = new TaskList(this);

        ui = new Ui(tasklist.arrlist);
        ui.showWelcome();

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
     * @UsedIn: Launcher.java (indirect call)
     */
    @Override
    public void start(Stage stage) throws Exception {

        //make GUI components
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);
        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane,userInput,sendButton);

        Scene scene = new Scene(mainLayout);
        System.out.println("Displaying GUI!");

        stage.setTitle("Duke");

        //Setting dimensions of components/stage
        stage.setResizable(true);
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        mainLayout.setPrefSize(400,400);

        scrollPane.setPrefSize(385,385);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325);
        sendButton.setPrefWidth(55);

        //set the constraints of the 3 UI elements to the parent (AnchorPane)
        AnchorPane.setTopAnchor(scrollPane,userInput.getHeight()+30.0);
        AnchorPane.setTopAnchor(userInput,1.0);
        AnchorPane.setTopAnchor(sendButton,1.0);
        AnchorPane.setRightAnchor(userInput,20.0);


        //on clicking the send button
        sendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleUserInput();
            }
        });

        //on user pressing enter while focus is on textfield
        userInput.setOnAction(actionEvent -> handleUserInput());

        dialogContainer.heightProperty().addListener(observable -> scrollPane.setVvalue(1.0));

        stage.setScene(scene);
        stage.show();

    }



    /**
     * @Function
     * This function creates 2 dialog boxes, 1 echoing user input and the other containing a processed reply
     * from Duke. Clears userinput box after processing
     * @UsedIn: sendButton.setOnMouseClicked
     */
    private void handleUserInput(){
        String cmd = userInput.getText();

        //send to parser to parse
        parser.processCommands(cmd);

        //show the command in the dialog container. todo: Just temporary. Remove as needed.
        dialogContainer.getChildren().addAll(getDialogLabel(cmd));

        userInput.clear();

    }


    /**
     * @Function
     * @param userSaid
     * @return
     * This function is Duke's response to what the user typed.
     */
    private String getResponse(String userSaid){
        return "Kim agrees when you say "+ userSaid;
    }



    /**
     * @Function
     * @param text
     * @return
     * This function returns a label (node) with the text as text
     * @UsedIn: Application.start()
     */
    private Label getDialogLabel(String text){
        Label label = new Label(text);
        label.setWrapText(true);

        return label;
    }


    /**
     * @Function
     * No Params, No Return Value
     * This function handles the exiting/shutdown of the program Duke
     * @UsedIn: parser.processCommands
     * TODO: Save data on exit
     */
    public void exitDuke(){
        System.out.println("Bye. Hope to see you again soon!");
        exit(0);
    }




    /**
     * @Function
     * This function returns the number cardinal when passed an integer
     * @param n, No Return Value
     * @UsedIn:
     */
    private static String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }


    /**
     * @Function
     * @param when
     * @return dateString
     * @UsedIn: ui.getDescription
     * This function parses the date in the format dd/MM/yyyy HHmm and returns a date in the format
     * dd MMMM yyyy hh:mma
     */
    protected static String dateParse(String when){
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



    //Test Client
    public static void main(String[] args) {

        //start the program flow
        new Duke();

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
    static class DukeException extends Exception{

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
 */
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




