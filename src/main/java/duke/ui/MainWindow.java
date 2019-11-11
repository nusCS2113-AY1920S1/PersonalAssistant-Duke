package duke.ui;

import duke.Duke;
import duke.Main;
import duke.command.Command;
import duke.command.ExitCommand;
import duke.command.BackupCommand;
import duke.command.FilterCommand;
import duke.command.HelpCommand;
import duke.enums.Numbers;
import duke.dukeexception.DukeException;
import duke.task.TaskList;
import duke.task.Task;
import duke.task.BudgetList;
import duke.task.Reminders;
import duke.task.FilterList;
import duke.task.ContactList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tooltip;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private ListView<Task> listT;
    @FXML
    private Label labelSelectedTask;
    @FXML
    private Button btnDone;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    private VBox vboxUpdate;
    @FXML
    private ComboBox<String> cbupdateType;
    @FXML
    private TextField tfnewDesc;
    @FXML
    private TextField tfnewDateTime;
    @FXML
    private ComboBox<String> cbtaskType;
    @FXML
    private TabPane tpTabs;
    @FXML
    private SingleSelectionModel<Tab> selectedTab;


    private Duke duke;
    private Tooltip toolTip = new Tooltip();
    private Reminders remind = new Reminders();

    private int refreshType = Numbers.ZERO.value;
    private static final int TIMER_DELAY = 500;
    private static final int VBOX_WIDTH = 200;
    private static final double TOOLTIP_SHOWDELAY = 70.0;
    private static final double TOOLTIP_SHOWDURATION = 0.001;
    private static final double COORD_OFFSET = 120;
    private static final Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/myUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/myBot.png"));

    //@@author talesrune-reused
    //Reused from https://github.com/nusCS2113-AY1920S1/duke/blob/master/tutorials/javaFxTutorialPart4.md with minor modifications
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Setting up Duke GUI.
     * @param d The object of Duke.
     */
    public void setDuke(Duke d) {
        duke = d;
        updateGui();
        setVboxWidth(false);
        setButtonsVisibility(true);
        selectedTab =  tpTabs.getSelectionModel();
        listT.setTooltip(toolTip);
        toolTip.setShowDelay(Duration.millis(TOOLTIP_SHOWDELAY));
        toolTip.setShowDuration(Duration.millis(TOOLTIP_SHOWDURATION));

        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(duke.checkSampleUsed() + Ui.showWelcomeGui(), dukeImage)
        );

        TaskList items = duke.getTaskList();
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(remind.getReminders(Numbers.THREE.value, items).getListGui(), dukeImage)
        );
    }

    Timer timer = new Timer();
    TimerTask exitDuke = new TimerTask() {
        public void run() {
            System.exit(Numbers.ZERO.value);
        }
    };

    private Date createTimerDelay() {
        return new Date(System.currentTimeMillis() + TIMER_DELAY);
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response;
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
        try {
            Command cmd = duke.getCommand(input);
            if (cmd instanceof ExitCommand) {
                duke.saveState(cmd);
                response = Ui.showByeGui();
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog(response, dukeImage)
                );
                timer.schedule(exitDuke, createTimerDelay());
            }  else if (cmd instanceof BackupCommand) {
                duke.saveState(cmd);
                response = Ui.showBackupMessageGui();
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog(response, dukeImage)
                );
            } else if (cmd instanceof HelpCommand) {
                duke.saveState(cmd);
                response = Ui.showHelpGui();
                createHelpWindow();
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog(response, dukeImage)
                );
            } else {
                if (cmd instanceof FilterCommand) {
                    refreshType = Numbers.ONE.value;
                }
                response = duke.executeCommand(cmd);
                dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
                );
                updateGui();
            }
        } catch (DukeException e) {
            response = Ui.showErrorMsgGui(e.getMessage());
            logr.log(Level.WARNING, response, e);

            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
        } catch (Exception e) {
            response = Ui.showErrorMsgGui("     New error, please read console:")
                    +  Ui.showErrorMsgGui("     Duke will continue as per normal.");
            logr.log(Level.WARNING, response, e);
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            e.printStackTrace();
        }
        userInput.clear();
    }

    //@@author talesrune
    @FXML
    protected void handleUserEvent(String input) {
        String response;
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
        try {
            Command cmd = duke.getCommand(input);
            response =  duke.executeCommand(cmd);
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );

        } catch (DukeException e) {
            response = Ui.showErrorMsgGui(e.getMessage());
            logr.log(Level.WARNING, response, e);
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
        } catch (Exception e) {
            response = Ui.showErrorMsgGui("     New error, please read console:")
                    +  Ui.showErrorMsgGui("     Duke will continue as per normal.");
            logr.log(Level.WARNING, response, e);
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            e.printStackTrace();
        }
        userInput.clear();
    }

    @FXML
    private void onMouseClick_ListView(MouseEvent mouseEvent) {
        labelSelectedTask.setText("Selected Task: " + listT.getSelectionModel().getSelectedItem());
        Task taskObj = listT.getSelectionModel().getSelectedItem();
        toolTip.setText("Notes: " + taskObj.getNotes());
        Node node = (Node) mouseEvent.getSource();
        toolTip.show(node, mouseEvent.getScreenX() + COORD_OFFSET, mouseEvent.getScreenY());


        if (taskObj.isDone()) {
            btnDone.setDisable(true);
        } else {
            btnDone.setDisable(false);
        }
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @FXML
    private void onMouseClickTabs() {
        String str = selectedTab.getSelectedItem().getText();
        if (str.toLowerCase().equals("all")) {
            handleUserEvent("list");
        } else {
            handleUserEvent("filter " + str.toLowerCase());
            refreshType = Numbers.ONE.value;
        }
        updateGui();
    }


    @FXML
    private void onMouseClickDone() {
        Task taskObj = listT.getSelectionModel().getSelectedItem();
        TaskList items = duke.getTaskList();
        int itemNumber = items.getIndex(taskObj) + Numbers.ONE.value;
        handleUserEvent("done " + itemNumber);
        updateGui();
    }

    @FXML
    private void onMouseClickDelete() {
        Task taskObj = listT.getSelectionModel().getSelectedItem();
        TaskList items = duke.getTaskList();
        int itemNumber = items.getIndex(taskObj) + Numbers.ONE.value;
        handleUserEvent("delete " + itemNumber);
        updateGui();
    }

    @FXML
    private void onMouseClick_DeleteNotes() {
        Task taskObj = listT.getSelectionModel().getSelectedItem();
        TaskList items = duke.getTaskList();
        int itemNumber = items.getIndex(taskObj) + Numbers.ONE.value;
        handleUserEvent("notes " + itemNumber + " /delete");
        updateGui();
    }

    @FXML
    private void onMouseClickUpdate() {
        setVboxWidth(true);
        setButtonsVisibility(false);
        cleanUp();
        cbupdateType.getItems().addAll(
                "Description",
                "Date/Time",
                "Type of Task"

        );
        cbtaskType.getItems().addAll(
                "Todo",
                "Deadline",
                "Fixed Duration"
        );
    }

    @FXML
    private void onMouseClickOK() {
        Task taskObj = listT.getSelectionModel().getSelectedItem();
        TaskList items = duke.getTaskList();
        int itemNumber = items.getIndex(taskObj) + Numbers.ONE.value;
        if (cbupdateType.getSelectionModel().getSelectedItem().equals("Description")) {
            handleUserEvent("update " + itemNumber + " /desc " + tfnewDesc.getText().trim());
        } else if (cbupdateType.getSelectionModel().getSelectedItem().equals("Date/Time")) {
            handleUserEvent("update " + itemNumber + " /date " + tfnewDateTime.getText().trim());
        } else if (cbupdateType.getSelectionModel().getSelectedItem().equals("Type of Task")) {
            String typeStr = "";
            if (cbtaskType.getSelectionModel().getSelectedItem().equals("Todo")) {
                typeStr = "todo";
            } else  if (cbtaskType.getSelectionModel().getSelectedItem().equals("Deadline")) {
                typeStr = "deadline";
            } else  if (cbtaskType.getSelectionModel().getSelectedItem().equals("Fixed Duration")) {
                typeStr = "fixedduration";
            }
            handleUserEvent("update " + itemNumber + " /type " + typeStr);
        }
        updateGui();
        setVboxWidth(false);
        setButtonsVisibility(true);
    }

    @FXML
    private void onMouseClickCancel() {
        setVboxWidth(false);
        setButtonsVisibility(true);
    }

    @FXML
    private void cleanUp() {
        cbupdateType.getItems().clear();
        cbtaskType.getItems().clear();
        tfnewDateTime.clear();
        tfnewDesc.clear();
    }

    @FXML
    private void setVboxWidth(boolean isEnabled) {
        if (isEnabled) {
            vboxUpdate.setPrefWidth(VBOX_WIDTH);
            vboxUpdate.setVisible(true);
        } else {
            vboxUpdate.setPrefWidth(Numbers.ZERO.value);
            vboxUpdate.setVisible(false);
        }
    }

    @FXML
    private void setButtonsVisibility(boolean isVisible) {
        if (isVisible) {
            btnOK.setVisible(false);
            btnCancel.setVisible(false);
            btnDone.setVisible(true);
            btnUpdate.setVisible(true);
            btnDelete.setVisible(true);
        } else {
            btnOK.setVisible(true);
            btnCancel.setVisible(true);
            btnDone.setVisible(false);
            btnUpdate.setVisible(false);
            btnDelete.setVisible(false);
        }
    }

    @FXML
    private void updateGui() {
        listViewRefresh();
        setDisableButtons();
        labelSelectedTask.setText("Selected Task: ");
    }

    @FXML
    protected void listViewRefresh() {
        listT.getItems().clear();
        TaskList items = duke.getTaskList();
        FilterList filterList = duke.getFilterList();
        if (refreshType == Numbers.ZERO.value) {
            if (selectedTab != null) {
                selectedTab.selectFirst();
            }
            for (int i = Numbers.ZERO.value; i < items.size(); i++) {
                listT.getItems().add(items.get(i));
            }
        } else {
            for (int i = Numbers.ZERO.value; i < filterList.size(); i++) {
                listT.getItems().add(filterList.get(i));
            }
            selectedTab.select(filterList.getFilterIndex());
        }
        refreshType = Numbers.ZERO.value;

    }

    @FXML
    private void setDisableButtons() {
        btnDone.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    @FXML
    private void exitProgram() {
        String input = "bye";
        String response;
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
        try {
            Command cmd = duke.getCommand(input);
            duke.saveState(cmd);
            response = Ui.showByeGui();
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            timer.schedule(exitDuke, createTimerDelay());
        } catch (DukeException e) {
            response = Ui.showErrorMsgGui(e.getMessage());
            logr.log(Level.WARNING, response, e);
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
        } catch (Exception e) {
            response = Ui.showErrorMsgGui("     New error, please read console:")
                    +  Ui.showErrorMsgGui("     Duke will continue as per normal.");
            logr.log(Level.WARNING, response, e);
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            e.printStackTrace();
        }
        userInput.clear();
    }

    @FXML
    private void exitProgramWithoutSaving() {
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(Ui.showByeGui(), dukeImage)
        );
        timer.schedule(exitDuke, createTimerDelay());
    }

    /**
     * Exits the program without saving.
     */
    public void exitProgramAbrupt() {
        timer.schedule(exitDuke, createTimerDelay());
    }

    /**
     * Creates a new window to allow the user to add a new task via user friendly interface.
     */
    @FXML
    public void createAddWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/AddWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            fxmlLoader.<AddWindow>getController().setAddWindow(duke, this);
            stage.show();
        } catch (IOException e) {
            logr.log(Level.SEVERE, "Unable to load add window", e);
            e.printStackTrace();
        }
    }

    /**
     * Creates a new window to allow the user to add or update notes of existing task via user friendly interface.
     */
    @FXML
    public void createAddNotesWindow() {
        Task taskObj = listT.getSelectionModel().getSelectedItem();
        TaskList items = duke.getTaskList();
        int itemNumber = items.getIndex(taskObj) + Numbers.ONE.value;
        String notesDesc = items.get(itemNumber - Numbers.ONE.value).getNotes();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/AddNotesWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            fxmlLoader.<AddNotesWindow>getController().setAddNotesWindow(this, itemNumber, notesDesc);
            stage.show();
        } catch (IOException e) {
            logr.log(Level.SEVERE, "Unable to load add notes window", e);
            e.printStackTrace();
        }
    }

    /**
     * Creates a budget window to allow the user to view budget list via user friendly interface.
     */
    @FXML
    public void createBudgetWindow() {
        BudgetList budgetList = duke.getBudgetList();
        float currBudget = budgetList.getBudget();
        String budgetDesc = budgetList.getStringList();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/BudgetWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            fxmlLoader.<BudgetWindow>getController().setBudgetWindow(budgetDesc, currBudget);
            stage.show();
        } catch (IOException e) {
            logr.log(Level.SEVERE, "Unable to load budget window", e);
            e.printStackTrace();
        }
    }

    /**
     * Creates a credits window to allow the user to view credits list via user friendly interface.
     */
    @FXML
    public void createCreditsWindow() {
        String creditsDesc = duke.getCreditsList();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/CreditsWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            fxmlLoader.<CreditsWindow>getController().setCreditsWindow(creditsDesc);
            stage.show();
        } catch (IOException e) {
            logr.log(Level.SEVERE, "Unable to load Credits window", e);
            e.printStackTrace();
        }
    }

    /**
     * Creates a budget window to allow the user to view contacts list via user friendly interface.
     */
    @FXML
    public void createContactsWindow() {
        ContactList contactList = duke.getFullContactList();
        String contactDesc = contactList.getFullContactList();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/ContactsWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            fxmlLoader.<ContactsWindow>getController().setContactsWindow(contactDesc);
            stage.show();
        } catch (IOException e) {
            logr.log(Level.SEVERE, "Unable to load contacts window", e);
            e.printStackTrace();
        }
    }

    /**
     * Creates a new window to allow the user to view commands under help via user friendly interface.
     */
    @FXML
    public void createHelpWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/HelpWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            fxmlLoader.<HelpWindow>getController().setHelpWindow();
            stage.show();
        } catch (IOException e) {
            logr.log(Level.SEVERE, "Unable to load help window", e);
            e.printStackTrace();
        }
    }

}
