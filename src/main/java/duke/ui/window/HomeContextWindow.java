package duke.ui.window;

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import duke.data.Patient;
import duke.exception.DukeFatalException;
import duke.ui.card.PatientCard;
import duke.ui.commons.UiStrings;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;

//@@author gowgos5
/**
 * UI window for the Home context.
 */
public class HomeContextWindow extends ContextWindow {
    private static final String FXML = "HomeContextWindow.fxml";

    @FXML
    private JFXMasonryPane patientListPanel;
    @FXML
    private ScrollPane scrollPane;

    private ArrayList<Patient> patientList;

    /**
     * Constructs the Home UI window.
     *
     * @param patientList List of {@link Patient} objects.
     */
    public HomeContextWindow(ArrayList<Patient> patientList) throws DukeFatalException {
        super(FXML);

        if (patientList == null) {
            throw new DukeFatalException(UiStrings.MESSAGE_ERROR_UNINITIALISED_PATIENT_LIST);
        }

        this.patientList = patientList;

        JFXScrollPane.smoothScrolling(scrollPane);
        updateUi();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUi() throws DukeFatalException {
        fillPatientList();
    }

    /**
     * Fills {@code indexedPatientList} and {@code patientListPanel}.
     */
    private void fillPatientList() throws DukeFatalException {

        patientListPanel.getChildren().clear();
        for (Patient patient : patientList) {
            PatientCard patientCard = patient.toCard();
            patientCard.setIndex(patientList.indexOf(patient) + 1);
            patientListPanel.getChildren().add(patientCard);
        }
    }
}
