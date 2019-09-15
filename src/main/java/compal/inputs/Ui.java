package compal.inputs;

import compal.main.Duke;
import compal.tasks.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.ArrayList;

public class Ui {

    private ArrayList<Task> arrlist;
    private Duke duke;
    private boolean isNewUser;
    private int initStage;
    private String username;

    //JavaFX testing
    private ScrollPane primaryScrollPane;
    private ScrollPane secondaryScrollPane;
    private VBox mainDisplay;
    private VBox secondaryDisplay;
    private TextField userInput;
    private Button sendButton;


    /**
     * Set up the Ui parameter.
     *
     * @param d         duke main class to be initialise
     * @param arrayList of the data to store,display or edit .
     * @ClassConstructor
     */
    public Ui(Duke d, ArrayList<Task> arrayList) {
        this.duke = d;
        arrlist = arrayList;
        isNewUser = false;
        initStage = 0;
    }


    /**
     * * This function is for setting up the JavaFX(GUI) stage, overridden from javafx.application.Application.
     *
     * @param stage the stage to read settings from
     * @throws Exception throw error found from javafx
     * @Function
     * @UsedIn: COMPal.Launcher.java (indirect call)
    todo:Refactor and clean up code for this function
    todo:Beautify the GUI and add cooler GUI stuff
     */
    public void start(Stage stage) {

        stage.initStyle(StageStyle.TRANSPARENT);


        //make GUI components
        primaryScrollPane = new ScrollPane();
        secondaryScrollPane = new ScrollPane();
        mainDisplay= new VBox();
        secondaryDisplay = new VBox();
        primaryScrollPane.setContent(mainDisplay);
        secondaryScrollPane.setContent(secondaryDisplay);
        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(primaryScrollPane, userInput, sendButton,secondaryDisplay);


        //SETTING COLORS --------------------------------------------------------------------->

        mainLayout.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,
                CornerRadii.EMPTY, Insets.EMPTY)));

        secondaryScrollPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,
                CornerRadii.EMPTY, Insets.EMPTY)));

        mainDisplay.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,
                CornerRadii.EMPTY, Insets.EMPTY)));



        // --------------------------------------------------------------------------->



        final Scene scene = new Scene(mainLayout);
        System.out.println("Displaying GUI!");

        stage.setTitle("COMPal.Duke");

        //Setting dimensions of components/stage
        stage.setResizable(true);
        stage.setMinHeight(700);
        stage.setMinWidth(1000);
        mainLayout.setPrefSize(700, 500);

        primaryScrollPane.setPrefSize(400, 500);
        primaryScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        primaryScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        primaryScrollPane.setVvalue(1.0);
        primaryScrollPane.setFitToWidth(true);

        secondaryScrollPane.setPrefSize(250, 500);
        secondaryScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        secondaryScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        secondaryScrollPane.setVvalue(1.0);
        secondaryScrollPane.setFitToWidth(true);

        mainDisplay.setPrefHeight(Region.USE_COMPUTED_SIZE);
        secondaryDisplay.setPrefHeight(Region.USE_COMPUTED_SIZE);


        userInput.setPrefWidth(600);
        sendButton.setPrefWidth(55);

        //set the constraints of the 3 UI elements to the parent (AnchorPane)
        AnchorPane.setTopAnchor(primaryScrollPane, userInput.getHeight() + 30.0);
        AnchorPane.setTopAnchor(secondaryDisplay, userInput.getHeight() + 30.0);
        AnchorPane.setTopAnchor(userInput, 1.0);
        AnchorPane.setTopAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(userInput, 0.0);
        AnchorPane.setRightAnchor(secondaryDisplay, 20.0);


        //on clicking the send button
        sendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleUserInput();
            }
        });

        //on user pressing enter while focus is on textfield
        userInput.setOnAction(actionEvent -> handleUserInput());

        mainDisplay.heightProperty().addListener(observable -> primaryScrollPane.setVvalue(1.0));
        secondaryDisplay.heightProperty().addListener(observable -> secondaryScrollPane.setVvalue(1.0));

        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

    }


    /**
     * This function converts the object into string form using toString() and prints it onto the GUI.
     *
     * @param text input object received to be print on gui
     */
    public void printg(Object text) {
        mainDisplay.getChildren().addAll(getDialogLabel(text.toString()));
    }


    /**
     * Creates 2 dialog boxes, 1 echoing user input and the other containing a processed reply
     * from COMPal.Duke. Clears userinput box after processing.
     * @UsedIn: sendButton.setOnMouseClicked
     */
    public void handleUserInput() {
        String cmd = userInput.getText();

        if (isNewUser) {
            firstTimeInit(initStage, cmd);
        } else {
            //send to parser to parse
            duke.parser.processCommands(cmd);
        }

        userInput.clear();
    }


    /**
     * Returns a label (node) with the text as text.
     * @param text Dialog text label received
     * @return Label (node) with the text as text
     * @UsedIn: Application.start()
     */
    private Label getDialogLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        label.setWrapText(true);

        return label;
    }



    /**
     * Checks if user is a new user.
     * No Params, No Return Value
     * @UsedIn:
     */
    public void checkInit() {
        File tmpDir = new File("./prefs.txt");
        boolean saveFileExists = tmpDir.exists();
        if (!saveFileExists) {
            isNewUser = true;
            printg("Hello! I'm COMPal\n");
            printg("May I have the honour of knowing your name?");
        } else {
            username = duke.storage.getUserName();
            printg("Hello again "
                    + username
                    + "! "
                    +
                    "Here are your tasks that are due soon! I've sorted it in order of importance :)");
            //todo: Implement displaying of tasks, sorted according to priority
        }
    }


    /**
     * Performs first time initialization for new users.
     *
     * @param stage int
     * @param value String
     * @UsedIn:
     */
    public void firstTimeInit(int stage, String value) {
        switch (stage) {
        case 0:
            printg(value + "? Did I say it correctly? [Yes or No]");
            username = value;
            initStage = 1;
            break;
        case 1:
            if (value.matches("(y|Y).*")) {
                printg("Hello " + username + "! What a lovely name!");
                isNewUser = false;
                duke.storage.storeUserName(username); //save the user's name
                break;
            } else {
                printg("Okay, what is your name then?");
                initStage = 0;
                break;
            }
        default:
            System.out.println("Unkown init stage");

        }

    }


    /**
     * Simply shows the number of tasks in the arraylist.
     *
     * @Function No Params, No Return Value
     * @UsedIn: tasklist.addTask
     */
    public void showSize() {
        duke.ui.printg("Now you have " + arrlist.size() + " tasks in the list");
    }

    /**
     * Simply displays the details of the task passed into it.
     *
     * @Function No Params, No Return Value
     * @UsedIn: tasklist.taskDone, tasklist.deleteTask
     */
    public void showTask(Task t) {
        duke.ui.printg("[" + t.getSymbol() + "]" + "[" + t.getStatusIcon() + "] " + t.getDescription());
    }


    /**
     * This function handles the list command which lists the tasks currently in COMPal.Duke's tracking
     * It will display the task symbol (T,E,D), the status (done or not done) and the description string
     *
     * @Function No Params, No Return Value.
     * @UsedIn: parser.processCommands
     */
    public void listTasks() {
        int count = 1;
        duke.ui.printg("Here are the tasks in your list:");
        for (Task t : arrlist) {
            System.out.print(count++ + ".");
            showTask(t);
        }
    }
}
