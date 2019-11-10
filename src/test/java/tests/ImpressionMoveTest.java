package tests;

import duke.command.ObjCommand;
import duke.command.impression.ImpressionMoveSpec;
import duke.data.Impression;
import duke.data.Observation;
import duke.data.Patient;
import duke.exception.DukeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import templates.CommandTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class ImpressionMoveTest extends CommandTest {

    private Impression impression;
    private Patient patient;
    private ObjCommand moveCmd;

    /**
     * Sets up the core of this object to have a patient and impression, opens the impression, and stores those
     * references for comparison.
     */
    @BeforeEach
    public void setupPatientAndImpression() {
        patient = new Patient("name", "bedNo", "allergies", 0, 0,
                0, 0, "", "");
        impression = new Impression("name", "description", patient);
        try {
            patient.addNewImpression(impression);
            core.patientData.addPatient(patient);
        } catch (DukeException excp) {
            fail("Failed to setup patient and impression for testing!");
        }
        core.uiContext.open(impression);
    }

    @Test
    public void impressionMoveCommand_impressionAmbiguous_moveData() {
        String[] switchNames = {"impression", "evidence"};
        String[] switchVals = {"ooga", "name"};
        Impression otherImpression = new Impression("booga", "description2", patient);
        Impression newImpression = new Impression("ooga", "description2", patient);
        Observation obsv = null;

        try {
            patient.addNewImpression(otherImpression);
            obsv = new Observation("name", impression, 0, "summary", true);
            setupCmdAndData(switchNames, switchVals, newImpression, obsv);
        } catch (DukeException excp) {
            fail("Exception thrown while setting up data: " + excp.getMessage());
        }

        try {
            moveCmd.execute(core, newImpression);
            assertNotNull(newImpression.getEvidence("name"));
            switchVals[0] = "name"; // move back to original impression
            moveCmd = new ObjCommand(ImpressionMoveSpec.getSpec(), null, switchNames, switchVals);
            moveCmd.execute(core); // unambiguous
            assertNotNull(impression.getEvidence("name"));
        } catch (DukeException excp) {
            fail("Exception thrown while moving data: " + excp.getMessage());
        }
    }

    @Test
    public void impressionMoveCommand_unambiguous_moveData() {
        String[] switchNames = {"impression", "evidence"};
        String[] switchVals = {"name2", "name"};
        Observation obsv = null;

        Impression newImpression = new Impression("name2", "description2", patient);

        try {
            obsv = new Observation("name", impression, 0, "summary", true);
            setupCmdAndData(switchNames, switchVals, newImpression, obsv);
        } catch (DukeException excp) {
            fail("Exception thrown while setting up: " + excp.getMessage());
        }

        try {
            moveCmd.execute(core); // unambiguous
        } catch (DukeException excp) {
            fail("Exception thrown while moving data: " + excp.getMessage());
        }
        assertNotNull(newImpression.getEvidence("name"));
    }

    @Test
    public void impressionMoveCommand_dataAmbiguous_moveData() {
        String[] switchNames = {"impression", "evidence"};
        String[] switchVals = {"name2", "name"};
        Observation obsv = null;
        Impression newImpression = new Impression("name2", "description2", patient);

        try {
            obsv = new Observation("name", impression, 0, "summary", true);
            impression.addNewEvidence(new Observation("name2", impression, 0,
                    "summary", true));
            setupCmdAndData(switchNames, switchVals, newImpression, obsv);
        } catch (DukeException excp) {
            fail("Exception when creating observation: " + excp.getMessage());
        }

        try {
            moveCmd.execute(core, newImpression);
            core.uiContext.open(impression);
            moveCmd.execute(core, obsv);
        } catch (DukeException excp) {
            fail("Exception thrown while moving data: " + excp.getMessage());
        }
        assertNotNull(newImpression.getEvidence("name"));
    }

    private void setupCmdAndData(String[] switchNames, String[] switchVals, Impression newImpression, Observation obsv) {
        try {
            moveCmd = new ObjCommand(ImpressionMoveSpec.getSpec(), null, switchNames, switchVals);
            impression.addNewEvidence(obsv);
            patient.addNewImpression(newImpression);
        } catch (DukeException excp) {
            fail("Exception thrown while setting up Command, Impression and Observation for move command test: "
                    + excp.getMessage());
        }
    }
}
