package compal.inputs;

import compal.main.Duke;
import compal.tasks.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Ui {

    private ArrayList<Task> arrlist;
    private Duke duke;


    //JavaFX testing
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;


    /**
     * Set up the Ui parameter.
     *
     * @param d duke main class to be initialise
     * @param arrayList of the data to store,display or edit .
     * @ClassConstructor
     */
    public Ui(Duke d, ArrayList<Task> arrayList) {
        this.duke = d;
        arrlist = arrayList;
    }


    /**
     * This function is for setting up the JavaFX(GUI) stage, overridden from javafx.application.Application.
     *
     * @param stage the stage to read settings from
     * @throws Exception throw error found from javafx
     *
     * @Function
     * @UsedIn: COMPal.Launcher.java (indirect call)
     */
    public void start(Stage stage) {

        //make GUI components
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);
        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        final Scene scene = new Scene(mainLayout);
        duke.ui.printg("Displaying GUI!");

        stage.setTitle("COMPal.Duke");

        //Setting dimensions of components/stage
        stage.setResizable(true);
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        mainLayout.setPrefSize(400, 400);

        scrollPane.setPrefSize(385, 385);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325);
        sendButton.setPrefWidth(55);

        //set the constraints of the 3 UI elements to the parent (AnchorPane)
        AnchorPane.setTopAnchor(scrollPane, userInput.getHeight() + 30.0);
        AnchorPane.setTopAnchor(userInput, 1.0);
        AnchorPane.setTopAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(userInput, 20.0);


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
     * This function converts the object into string form using toString() and prints it onto the GUI.
     *
     * @param text input object received to be print on gui
     * @Function
     */
    public void printg(Object text) {
        dialogContainer.getChildren().addAll(getDialogLabel(text.toString()));
    }


    /**
     * This function creates 2 dialog boxes, 1 echoing user input and the other containing a processed reply
     * from COMPal.Duke. Clears userinput box after processing
     *
     * @Function
     * @UsedIn: sendButton.setOnMouseClicked
     */
    private void handleUserInput() {
        String cmd = userInput.getText();

        //send to parser to parse
        duke.parser.processCommands(cmd);

        userInput.clear();

    }


    /**
     * This function returns a label (node) with the text as text.
     *
     * @param text Dialog text label received
     * @return Label (node) with the text as text
     *
     * @Function
     * @UsedIn: Application.start()
     */
    private Label getDialogLabel(String text) {
        Label label = new Label(text);
        label.setWrapText(true);

        return label;
    }


    /**
     * Simply prints the welcome message for COMPal.Duke
     */
    public void showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        printg("Hello there!\n" + logo);
        printg("Hello! I'm COMPal\n"
                + "     What can I do for you?");
    }


    /**
     * This function simply shows the number of tasks in the arraylist.
     *
     * @Function No Params, No Return Value
     * @UsedIn: tasklist.addTask
     */
    public void showSize() {
        duke.ui.printg("Now you have " + arrlist.size() + " tasks in the list");
    }

    /**
     * This function simply displays the task passed into it.
     *
     * @Function No Params, No Return Value
     * @UsedIn: tasklist.taskDone, tasklist.deleteTask
     */
    public void showTask(Task t) {
        duke.ui.printg("[" + t.getSymbol() + "]" + "[" + t.getStatusIcon() + "] " + t.description);
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
