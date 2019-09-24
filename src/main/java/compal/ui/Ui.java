package compal.ui;

import compal.compal.Compal;
import compal.tasks.Task;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Represents user.
 */
public class Ui {

    //***Class Properties/Variables***--------------------------------------------------------------------------------->
    public ScrollPane mainWindow;
    public ScrollPane secondaryWindow;
    private ArrayList<Task> arrlist;
    private Compal compal;
    private String username;
    //----------------------->

    /**
     * Constructs Ui object.
     *
     * @param d         Compal, main class to be initialised.
     * @param arrayList arrayList of the data to store, display or edit.
     */
    public Ui(Compal d, ArrayList<Task> arrayList) {
        this.compal = d;
        arrlist = arrayList;
        System.out.println("UI:LOG: Ui Initialized!");
    }

    //***OUTPUT FUNCTIONS***--------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    /**
     * Converts the object into string form using toString()
     * and prints it onto the GUI's primary display box.
     *
     * @param text Input object received to be print on gui. Any object type can be used, as long as
     *             it has a 'toString()' function defined.
     */
    public void printg(Object text) {
        VBox vbox = (VBox) mainWindow.getContent();
        vbox.getChildren().addAll(getDialogLabel(text.toString()));
    }

    /**
     * Converts the object into string form using toString()
     * and prints it onto the GUI's secondary display box.
     *
     * @param text Input object received to be print on gui. Any object type can be used, as long as
     *             it has a 'toString()' function defined.
     */
    public void printSecondaryg(Object text) {
        VBox vbox = (VBox) secondaryWindow.getContent();
        vbox.getChildren().addAll(getDialogLabel(text.toString(), "verdana", 12, Color.RED));
    }

    /**
     * Shows the number of tasks in the arraylist.
     * Used in TaskList.addTask.
     */
    public void showSize() {
        compal.ui.printg("Now you have " + arrlist.size() + " tasks in the list");
    }

    /**
     * Prints array of tasks.
     *
     * @param viewDay Input array to print.
     */
    public void printTemp(ArrayList<Task> viewDay) {
        int count = 1;
        for (Task t : viewDay) {
            printg(count++ + "." + t.toString());
        }
    }
    //----------------------->

    //***MISC FUNCTIONS***----------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    /**
     * Returns a label (node) with the text as text.
     *
     * @param text Dialog text label received.
     * @return Label (Node) with the text as text.
     */
    private Label getDialogLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("verdana", FontWeight.LIGHT, FontPosture.REGULAR, 15));
        label.setWrapText(true);
        return label;
    }

    /**
     * Returns a label (node) with the input text, font, fontsize and color.
     * Used when function is overloaded.
     *
     * @param text  Dialog text label received.
     * @param font  Font of text.
     * @param size  Fontsize of text.
     * @param color Color of text.
     * @return Label (Node) with the text as text.
     */
    private Label getDialogLabel(String text, String font, int size, Color color) {
        Label label = new Label(text);
        label.setFont(Font.font(font, FontWeight.LIGHT, FontPosture.REGULAR, size));
        label.setTextFill(color);
        label.setWrapText(true);
        return label;
    }

    /**
     * Clears the display viewport on the GUI.
     * Parser calls this function when it receives a 'clear' command.
     */
    public void clearPrimary() {
        VBox vbox = (VBox) mainWindow.getContent();
        vbox.getChildren().clear();
    }
    //----------------------->

    //***FIRST-TIME INITIALIZATION FUNCTIONS***-------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    /**
     * Initializes the text in the GUI's secondary display box.
     * Checks if user is a first-time user. If he/she is, name of user is asked.
     *
     * @throws ParseException       If an error reaches when parsing.
     * @throws Compal.DukeException If an error is encountered.
     */
    public void checkInit() throws ParseException, Compal.DukeException {
        //print the changelog for developers. todo: Remove when releasing build.
        printSecondaryg("CHANGELOG V1.1:\n (REMOVE BEFORE PACKAGING AS JAR)");
        printSecondaryg("\n+ viewing of tasks on a specific date\n"
                + "   displays the tasks for that date\n"
                + "   usage: view <dd/mm/yyyy>\n\n\n"
                + "+ reminders\n"
                + "   ComPAL shows reminders of tasks due within a week and tasks with reminders set\n"
                + "   NOTE: setting reminders is not yet implemented\n\n\n"
                + "+ new task type added: doaftertask\n"
                + "   task that can be done only after a certain date\n"
                + "   usage: doaftertask < descriptive name> /after <dd/mm/yyyy hhmm>\n\n\n"
                + "+ clearing terminal\n"
                + "   clears Compal's primary terminal\n"
                + "   usage: clear\n\n\n"
                + "+ new task type added: fixeddurationtask\n"
                + "  task that have a fixed duration\n"
                + "   usage: fixeddurationtask < descriptive name> /on <dd/mm/yyyy hhmm> /for "
                + "< number of hours> hours < number of minutes> minutes");

        File tmpDir = new File("./prefs.txt");
        boolean saveFileExists = tmpDir.exists();

        if (!saveFileExists) {
            compal.parser.setStatus("init");
            printg("Hello! I'm COMPal\n");
            printg("What is your name?");
        } else {
            username = compal.storage.getUserName();
            printg("Hello again "
                    + username
                    + "! "
                    +
                    "Here are your tasks that are due within a week: \n");
            compal.parser.processCmd("reminder");
        }
    }

    /**
     * Performs first time initialization for new users.
     * Consists of 2 steps(stages).Parser holds the current stage number.
     *
     * @param value Name of user.
     * @param stage Stage number of user.
     * @throws Compal.DukeException If an error is encountered.
     */
    public void firstTimeInit(String value, int stage) throws Compal.DukeException {
        switch (stage) {
        case 0:
            printg(value + "? Did I say it correctly? [Yes or No]");
            username = value;
            break;
        case 1:
            if (value.matches("(y|Y).*")) {
                printg("Hello " + username + "! Great to meet you!");
                compal.parser.setStatus("normal");
                compal.storage.storeUserName(username); //save the user's name
                break;
            } else {
                printg("Okay, what is your name then?");
                compal.parser.setStatus("init");
                break;
            }
        default:
            System.out.println("Unknown init stage");
        }
    }
    //----------------------->
}


