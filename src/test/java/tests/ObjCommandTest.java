package tests;

import duke.command.Command;
import duke.command.ObjCommand;
import duke.command.ObjSpec;
import duke.command.SearchSpec;
import duke.command.home.HomeOpenSpec;
import duke.command.impression.ImpressionOpenSpec;
import duke.command.patient.PatientOpenSpec;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Patient;
import duke.data.Result;
import duke.data.SearchResults;
import duke.exception.DukeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import templates.CommandTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test for open commands in Home, Patient and Impression contexts in order to test the ObjSpecs and ObjCommand system.
 */
public class ObjCommandTest extends CommandTest {

    private Patient patient1;
    private Patient patient2;
    private Patient patient3;
    private Impression impression1;
    private Impression impression2;
    private Investigation invx;
    private Result result;
    private ObjCommand openCmd;

    @BeforeEach
    public void setupData() {
        try {
            patient1 = new Patient("John", "C210", "", 185,
                    82, 22, 61234567, "", "");
            patient2 = new Patient("Joe", "A408", "", 169,
                    65, 23, 63264128, "", "");
            patient3 = new Patient("Jack", "A123", "Bad jokes", 169,
                    65, 23, 63264128, "", "");
            core.patientData.addPatient(patient1);
            core.patientData.addPatient(patient2);
            core.patientData.addPatient(patient3);
            impression1 = new Impression("Idiopathic arthritis", "No identifiable cause for joint pain", patient1);
            impression2 = new Impression("Plantar fascitis", "Sudden onset heel muscle tightness", patient1);
            patient1.addNewImpression(impression1);
            patient1.addNewImpression(impression2);
            invx = new Investigation("Load bearing x-ray", impression1, 0, "0",
                    "X-ray to examine the extent of damage to patient's joints");
            result = new Result("CT scan", impression1, 0, "X-ray wasn't enough");
            impression1.addNewTreatment(invx);
            impression1.addNewEvidence(result);
        } catch (DukeException excp) {
            fail("Exception thrown while setting up data for open command test: " + excp.getMessage());
        }
    }

    @AfterEach
    public void cleanupData() {
        try {
            core.patientData.deletePatient(patient1.getBedNo());
            core.patientData.deletePatient(patient2.getBedNo());
        } catch (DukeException excp) {
            fail("Exception thrown when cleaning up patients: " + excp.getMessage());
        }
    }

    /**
     * Tests basic search by name functionality, ensuring it ignores a patient whose description matches the search,
     * and child objects whose names match the search.
     */
    @Test
    public void homeOpenCommand_irrelevantMatches_matchesIgnored() {
        setupCommand(HomeOpenSpec.getSpec());
        try {
            openCmd.execute(core); // opens search results
            assertTrue(correctPatientSearchResults(getSearchResultList()));
            openCmd.execute(core, patient1);
            assertEquals(core.uiContext.getObject(), patient1);
        } catch (DukeException excp) {
            fail("Exception thrown while executing home open command: " + excp.getMessage());
        }
    }

    /**
     *  Verifies that SearchSpec works by performing the simple case test through SearchSpec.
     */
    @Test
    public void homeOpenCommand_viaSearchSpec_patientOpened() {
        setupCommand(HomeOpenSpec.getSpec());
        try {
            openCmd.execute(core); // opens search results
            List<DukeObject> resultList = getSearchResultList();
            assertTrue(correctPatientSearchResults(resultList));
            // construct a search command corresponding to the index of patient 1
            String idxStr = Integer.toString(resultList.indexOf(patient1) + 1);
            Command searchCmd = new Command(SearchSpec.getSpec(idxStr));
            searchCmd.execute(core);
            assertEquals(core.uiContext.getObject(), patient1);
        } catch (DukeException excp) {
            fail("Exception thrown while executing home open command: " + excp.getMessage());
        }
    }

    @Test
    public void patientOpenCommand_irrelevantMatches_matchesIgnored() {
        setupCommand(PatientOpenSpec.getSpec());
        try {
            openCmd.execute(core); // opens search results
            assertTrue(correctPatientSearchResults(getSearchResultList()));
            openCmd.execute(core, patient1);
            assertEquals(core.uiContext.getObject(), patient1);
        } catch (DukeException excp) {
            fail("Exception thrown while executing home open command: " + excp.getMessage());
        }
    }

    @Test
    public void impressionOpenCommand_irrelevantMatches_matchesIgnored() {
        setupCommand(ImpressionOpenSpec.getSpec());
        try {
            openCmd.execute(core); // opens search results
            assertTrue(correctPatientSearchResults(getSearchResultList()));
            openCmd.execute(core, patient1);
            assertEquals(core.uiContext.getObject(), patient1);
        } catch (DukeException excp) {
            fail("Exception thrown while executing home open command: " + excp.getMessage());
        }
    }



    private void setupCommand(ObjSpec spec) {
        String[] switchNames = {};
        String[] switchVals = {};
        try {
            openCmd = new ObjCommand(spec, "jo", switchNames, switchVals);
        } catch (DukeException excp) {
            fail("Exception thrown while trying to create open command: " + excp.getMessage());
        }
    }

    private List<DukeObject> getSearchResultList() {
        SearchResults results = null;
        DukeObject currObj = core.uiContext.getObject();
        try {
            results = (SearchResults) currObj;
        } catch (ClassCastException excp) {
            fail("Command did not open search context, opened " + currObj + " instead!");
        }
        return results.getSearchList();
    }

    private boolean correctPatientSearchResults(List<DukeObject> searchList) {
        return searchList.contains(patient1) && searchList.contains(patient2)
                && searchList.size() == 2;
    }
}
