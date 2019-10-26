package duke;

import duke.DialogBox;
import duke.Duke;
import duke.patient.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


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
    private Button sendButton;
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, Integer> patientIdCol;
    @FXML
    private TableColumn<Patient, String> patientNameCol;
    @FXML
    private TableColumn<Patient, String> patientRoomCol;
    @FXML
    private TableColumn<Patient, String> patientRemarkCol;
    @FXML
    private TableColumn<Patient, String> patientNricCol;

    private Duke duke = new Duke("./data");;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/robot.png"));

    private ObservableList<Patient> patientData;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        initializeTableViews();
    }

    public void initializeTableViews(){
        patientData= FXCollections.observableArrayList(duke.getPatientManager().getPatientList());
        patientTable.setItems(patientData);
        patientIdCol.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("id"));
        patientNameCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("name"));
        patientNricCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("nric"));
        patientRoomCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("room"));
        patientRemarkCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("remark"));
    }

    public void updateTableViews(){

    }


    public void setDuke(Duke duke) {
        this.duke = duke;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        duke.readUserInputFromGUI(input);
        duke.run();
        String responses = duke.getDukeResponses();
        System.out.println(responses);
        dialogContainer.getChildren().addAll(
            DialogBox.getUserDialog(input, userImage),
            DialogBox.getDukeDialog(responses, dukeImage)
        );
        userInput.clear();
        duke.clearDukeResponses();
    }
}