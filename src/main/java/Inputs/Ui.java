package Inputs;

import COMPal.Duke;
import Tasks.Task;
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
    private Duke d;


    //JavaFX testing
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;


    /**
     * @ClassConstructor
     * @param d
     * @param arrayList
     */
    public Ui(Duke d, ArrayList<Task> arrayList){
        this.d=d;
        arrlist=arrayList;
    }


    /**
     * @Function
     * @param stage
     * @throws Exception
     * This function is for setting up the JavaFX(GUI) stage, overridden from javafx.application.Application
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
        mainLayout.getChildren().addAll(scrollPane,userInput,sendButton);

        Scene scene = new Scene(mainLayout);
        d.ui.printg("Displaying GUI!");

        stage.setTitle("COMPal.Duke");

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
     * @param text
     * This function converts the object into string form using toString() and prints it onto the GUI
     */
    public void printg(Object text){
        dialogContainer.getChildren().addAll(getDialogLabel(text.toString()));
    }




    /**
     * @Function
     * This function creates 2 dialog boxes, 1 echoing user input and the other containing a processed reply
     * from COMPal.Duke. Clears userinput box after processing
     * @UsedIn: sendButton.setOnMouseClicked
     */
    private void handleUserInput(){
        String cmd = userInput.getText();

        //send to parser to parse
        d.parser.processCommands(cmd);

        userInput.clear();

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
     * Simply prints the welcome message for COMPal.Duke
     */
    public void showWelcome(){
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        printg("Hello there!\n" + logo);
        printg("Hello! I'm COMPal\n" +
                "     What can I do for you?");
    }



    /**
     * @Function
     * No Params, No Return Value
     * This function simply shows the number of tasks in the arraylist
     * @UsedIn: tasklist.addTask
     */
    public void showSize(){
        d.ui.printg("Now you have "+arrlist.size()+" tasks in the list");
    }

    /**
     * @Function
     * No Params, No Return Value
     * This function simply displays the task passed into it
     * @UsedIn: tasklist.taskDone,tasklist.deleteTask
     */
    public void showTask(Task t){
        d.ui.printg("[" + t.getSymbol() + "]" + "[" + t.getStatusIcon() + "] " + t.description);
    }


    /**
     * @Function
     * No Params, No Return Value
     * This function handles the list command which lists the tasks currently in COMPal.Duke's tracking
     * It will display the task symbol (T,E,D), the status (done or not done) and the description string
     * @UsedIn: parser.processCommands
     */
    public void listTasks(){
        int count=1;
        d.ui.printg("Here are the tasks in your list:");
        for (Task t : arrlist) {
            System.out.print(count+++".");
            showTask(t);
        }
    }
}
