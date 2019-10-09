package compal.ui;

import compal.commons.Compal;
import compal.model.tasks.Task;

import static compal.commons.Messages.MESSAGE_INIT_REMINDER;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


/*
List of Fonts:
Agency FB
Agency FB Bold
Arial
Arial Black
Arial Black Italic
Arial Bold
Arial Bold Italic
Arial Italic
Arial Narrow
Arial Narrow Bold
Arial Narrow Bold Italic
Arial Narrow Italic
Arial Rounded MT Bold
Arial-SM
Blackadder ITC
Bodoni MT
Bodoni MT Black
Bodoni MT Black Italic
Bodoni MT Bold
Bodoni MT Bold Italic
Bodoni MT Condensed
Bodoni MT Condensed Bold
Bodoni MT Condensed Bold Italic
Bodoni MT Condensed Italic
Bodoni MT Italic
Book Antiqua
Book Antiqua Bold
Book Antiqua Bold Italic
Book Antiqua Italic
Bookman Old Style
Bookman Old Style Bold
Bookman Old Style Bold Italic
Bookman Old Style Italic
Bookshelf Symbol 7
Bradley Hand ITC
Calisto MT
Calisto MT Bold
Calisto MT Bold Italic
Calisto MT Italic
Castellar
Century Gothic
Century Gothic Bold
Century Gothic Bold Italic
Century Gothic Italic
Century Schoolbook
Century Schoolbook Bold
Century Schoolbook Bold Italic
Century Schoolbook Italic
Comic Sans MS
Comic Sans MS Bold
Copperplate Gothic Bold
Copperplate Gothic Light
Courier New
Courier New Bold
Courier New Bold Italic
Courier New Italic
Curlz MT
Dialog.bold
Dialog.bolditalic
Dialog.italic
Dialog.plain
DialogInput.bold
DialogInput.bolditalic
DialogInput.italic
DialogInput.plain
Edwardian Script ITC
Elephant
Elephant Italic
Engravers MT
Eras Bold ITC
Eras Demi ITC
Eras Light ITC
Eras Medium ITC
Estrangelo Edessa
Felix Titling
Forte
Franklin Gothic Book
Franklin Gothic Book Italic
Franklin Gothic Demi
Franklin Gothic Demi Cond
Franklin Gothic Demi Italic
Franklin Gothic Heavy
Franklin Gothic Heavy Italic
Franklin Gothic Medium
Franklin Gothic Medium Cond
Franklin Gothic Medium Italic
French Script MT
Garamond
Garamond Bold
Garamond Italic
Gautami
Georgia
Georgia Bold
Georgia Bold Italic
Georgia Italic
Gigi
Gill Sans MT
Gill Sans MT Bold
Gill Sans MT Bold Italic
Gill Sans MT Condensed
Gill Sans MT Ext Condensed Bold
Gill Sans MT Italic
Gill Sans Ultra Bold
Gill Sans Ultra Bold Condensed
Gloucester MT Extra Condensed
Goudy Old Style
Goudy Old Style Bold
Goudy Old Style Italic
Goudy Stout
Haettenschweiler
Impact
Imprint MT Shadow
Kartika
Latha
Lucida Bright Demibold
Lucida Bright Demibold Italic
Lucida Bright Italic
Lucida Bright Regular
Lucida Console
Lucida Sans Demibold
Lucida Sans Demibold Italic
Lucida Sans Demibold Roman
Lucida Sans Italic
Lucida Sans Regular
Lucida Sans Typewriter Bold
Lucida Sans Typewriter Bold Oblique
Lucida Sans Typewriter Oblique
Lucida Sans Typewriter Regular
Lucida Sans Unicode
MS Outlook
MS Reference Sans Serif
MS Reference Specialty
MV Boli
Maiandra GD
Mangal
Marlett
Microsoft Sans Serif
Monospaced.bold
Monospaced.bolditalic
Monospaced.italic
Monospaced.plain
Monotype Corsiva
OCR A Extended
Palace Script MT
Palatino Linotype
Palatino Linotype Bold
Palatino Linotype Bold Italic
Palatino Linotype Italic
Papyrus
Perpetua
Perpetua Bold
Perpetua Bold Italic
Perpetua Italic
Perpetua Titling MT Bold
Perpetua Titling MT Light
Pristina
Raavi
Rage Italic
Rockwell
Rockwell Bold
Rockwell Bold Italic
Rockwell Condensed
Rockwell Condensed Bold
Rockwell Extra Bold
Rockwell Italic
SansSerif.bold
SansSerif.bolditalic
SansSerif.italic
SansSerif.plain
Script MT Bold
Serif.bold
Serif.bolditalic
Serif.italic
Serif.plain
Shruti
Sylfaen
Symbol
Tahoma
Tahoma Bold
Tera Special
Times New Roman
Times New Roman Bold
Times New Roman Bold Italic
Times New Roman Italic
Trebuchet MS
Trebuchet MS Bold
Trebuchet MS Bold Italic
Trebuchet MS Italic
Tunga
Tw Cen MT
Tw Cen MT Bold
Tw Cen MT Bold Italic
Tw Cen MT Condensed
Tw Cen MT Condensed Bold
Tw Cen MT Condensed Extra Bold
Tw Cen MT Italic
Verdana
Verdana Bold
Verdana Bold Italic
Verdana Italic
Vrinda
Webdings
Wingdings
Wingdings 2
Wingdings 3

 */


/**
 * Represents userinterface.
 */
public class UiPart {

    //***Class Properties/Variables***--------------------------------------------------------------------------------->
    public ScrollPane mainWindow;
    public ScrollPane secondaryWindow;
    public TabPane tabWindow;
    public String dateState;
    private ArrayList<Task> arrlist;
    private Compal compal;
    private String username;
    //----------------------->

    /**
     * Constructs UiPart object.
     *
     * @param d         Compal, main class to be initialised.
     * @param arrayList arrayList of the data to store, display or edit.
     */
    public UiPart(Compal d, ArrayList<Task> arrayList) {
        this.compal = d;
        arrlist = arrayList;
        System.out.println("UI:LOG: UiPart Initialized!");
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
     * Overloaded version of printg which allows you to customize style of text.
     * e.g usage; printg("hello world!", "verdana", 12, Color.RED);
     *
     * @param text Input object received to be print on gui. Any object type can be used, as long as
     *             it has a 'toString()' function defined.
     */
    public void printg(Object text, String font, int size, Color color) {
        VBox vbox = (VBox) mainWindow.getContent();
        vbox.getChildren().addAll(getDialogLabel(text.toString(), font, size, color));
    }

    /**
     * Converts the object into string form using toString()
     * and prints it onto the GUI's secondary display box.
     *
     * @param text Input object received to be print on gui. Any object type can be used, as long as
     *             it has a 'toString()' function defined.
     */
    public void printSecondaryg(Object text, String font, int size, Color color) {
        VBox vbox = (VBox) secondaryWindow.getContent();
        vbox.getChildren().addAll(getDialogLabel(text.toString(), font, size, color));
    }

    /**
     * Shows the number of tasks in the arraylist.
     * Used in TaskList.addTask.
     */
    public void showSize() {
        compal.ui.printg("Now you have " + arrlist.size() + " tasks in the list");
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

        File tmpDir = new File("./prefs.txt");
        boolean saveFileExists = tmpDir.exists();

        if (!saveFileExists) {
            compal.parser.setStatus("init");
            printg("Hello! I'm Compal!!", "verdana", 23, Color.BLACK);
            printg("What is your name?");
        } else {
            username = compal.storage.getUserName();
            printg("Hello again "
                    + username
                    + "! "
                    +
                    "Here are your tasks that are due within a week: \n", "verdana", 15, Color.BLACK);

            //initiate the showing of reminders
            compal.parser.processCmd(MESSAGE_INIT_REMINDER);
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

    /**
     * Refresh view date.
     *
     * @param dateToStore date to view of daily calender
     */
    public void dateViewRefresh(String dateToStore) {
        DailyCal dc = new DailyCal(compal);
        compal.ui.tabWindow.getTabs().remove(1);
        Tab dailyTab = new Tab();
        dailyTab.setText(dateToStore);
        dailyTab.setContent(dc.init(dateToStore));
        compal.ui.tabWindow.getTabs().add(1, dailyTab);
    }

    /**
     * Clears the secondary window on the GUI.
     */
    public void clearSecondary() {
        VBox vbox = (VBox) secondaryWindow.getContent();
        vbox.getChildren().clear();
    }
    //----------------------->

}


