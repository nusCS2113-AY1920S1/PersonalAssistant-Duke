package duke.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;


//@@author talesrune
/**
 * Controller for AddNotesWindow. Provides the layout for the other controls.
 */
public class AddNotesWindow extends AnchorPane {

    private MainWindow mainWindow;
    private int itemNumber;

    @FXML
    private TextField tfNotesDesc;
    @FXML
    private Button btnAddNotes;


    /**
     * Setting up Add Notes Window Interface.
     *
     *
     * @param mainWindow The main window that runs DUKE Manager.
     * @param itemNumber The number order of the task.
     * @param notesDesc The existing notes of the task.
     */
    @FXML
    public void setAddNotesWindow(MainWindow mainWindow, int itemNumber, String notesDesc) {
        this.mainWindow = mainWindow;
        this.itemNumber = itemNumber;
        tfNotesDesc.setText(notesDesc);
    }

    @FXML
    private void onMouseClick_AddNotes() {
        mainWindow.handleUserEvent("notes " + itemNumber + " /add " + tfNotesDesc.getText());
        mainWindow.listViewRefresh();
        Stage stage = (Stage) btnAddNotes.getScene().getWindow();
        stage.close();
    }

}