package compal.inputs;

import compal.main.Duke;
import compal.tasks.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.util.ArrayList;

public class Ui {

    //***Class Properties/Variables***--------------------------------------------------------------------------------->
    public ScrollPane sp;
    private ArrayList<Task> arrlist;
    private Duke duke;
    private String username;

    //----------------------->




    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->


    /**
     * Constructor.
     *
     * @param d         duke main class to be initialise
     * @param arrayList of the data to store,display or edit .
     */
    public Ui(Duke d, ArrayList<Task> arrayList) {
        this.duke = d;
        arrlist = arrayList;
    }

    //----------------------->




    //***OUTPUT FUNCTIONS***--------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    /**
     * This function converts the object into string form using toString()
     * and prints it onto the GUI's primary display box.
     *
     * @param text input object received to be print on gui
     */
    public void printg(Object text) {
        VBox vbox = (VBox) sp.getContent();
        vbox.getChildren().addAll(getDialogLabel(text.toString()));
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
     * Handles the list command which lists the tasks currently in COMPal.Duke's tracking
     * It will display the task symbol (T,E,D), the status (done or not done) and the description string
     */
    public void listTasks() {
        int count = 1;
        duke.ui.printg("Here are the tasks in your list:");
        for (Task t : arrlist) {
            printg(count++ + ".");
            showTask(t);
        }
    }


    /**
     * Used to print temp array!.
     */
    public void printTemp(ArrayList<Task> viewDay) {
        int count = 1;

        for (Task t : viewDay) {
            printg(count++ + ".");
            showTask(t);
        }
    }

    //----------------------->



    //***MISC FUNCTIONS***----------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->
    /**
     * Returns a label (node) with the text as text.
     *
     * @param text Dialog text label received
     * @return Label (node) with the text as text
     */
    private Label getDialogLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("verdana", FontWeight.LIGHT, FontPosture.REGULAR, 15));
        label.setWrapText(true);

        return label;
    }

    //----------------------->





    //***FIRST-TIME INITIALIZATION FUNCTIONS***-------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->


    /**
     * Checks if user is a new user.
     * No Params, No Return Value
     */
    public void checkInit() {
        File tmpDir = new File("./prefs.txt");
        boolean saveFileExists = tmpDir.exists();
        if (!saveFileExists) {
            duke.parser.setStatus("init");
            printg("Hello! I'm COMPal\n");
            printg("What is your name?");
        } else {
            username = duke.storage.getUserName();
            printg("Hello again "
                    + username
                    + "! "
                    +
                    "Here are your tasks that are due within a week: \n");
            duke.tasklist.taskReminder();
        }
    }


    /**
     * Performs first time initialization for new users.
     * Consists of 2 steps(stages).Parser holds the current stage number.
     *
     * @param stage int
     * @param value String
     */
    public void firstTimeInit(String value, int stage) {
        switch (stage) {
            case 0:
                printg(value + "? Did I say it correctly? [Yes or No]");
                username = value;
                break;
            case 1:
                if (value.matches("(y|Y).*")) {
                    printg("Hello " + username + "! Great to meet you!");
                    duke.parser.setStatus("normal");
                    duke.storage.storeUserName(username); //save the user's name
                    break;
                } else {
                    printg("Okay, what is your name then?");
                    duke.parser.setStatus("init");
                    break;
                }
            default:
                System.out.println("Unknown init stage");

        }

    }


    //----------------------->






}


