package tests;

import duke.command.Command;
import duke.command.ObjCommand;
import duke.command.ObjSpec;
import duke.command.SearchSpec;
import duke.command.home.HomeFindSpec;
import duke.command.home.HomeOpenSpec;
import duke.command.impression.ImpressionFindSpec;
import duke.command.impression.ImpressionOpenSpec;
import duke.command.patient.PatientFindSpec;
import duke.command.patient.PatientOpenSpec;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Patient;
import duke.data.Plan;
import duke.data.Result;
import duke.data.SearchResults;
import duke.exception.DukeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import templates.CommandTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test for open and find commands in Home, Patient and Impression contexts in order to test the ObjSpecs and
 * ObjCommand system.
 */
public class ObjCommandTest extends CommandTest {

    private Patient patient1;
    private Patient patient2;
    private Patient patient3;
    private Impression impression1;
    private Impression impression2;
    private Impression impression3;
    private Investigation invx;
    private Result result;
    private Plan plan;
    private ObjCommand cmd;

    /**
     * This sets up complex data for the patient before the tests are run.
     */
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
            impression1 = new Impression("John's syndrome", "", patient1);
            impression2 = new Impression("Joe's complex", "", patient1);
            impression3 = new Impression("Jack's fever", "Related to John's syndrome", patient1);
            patient1.addNewImpression(impression1);
            patient1.addNewImpression(impression2);
            patient1.addNewImpression(impression3);
            invx = new Investigation("Joker 2019", impression1, 0, "0",
                    "");
            result = new Result("Mojo jojo", impression1, 1, "");
            plan = new Plan("memes", impression1, 0, "1", "It's just a joke bro! It's a"
                    + " SOCIAL EXPERIMENT");
            impression1.addNewTreatment(invx);
            impression1.addNewEvidence(result);
            impression1.addNewTreatment(plan);
        } catch (DukeException excp) {
            fail("Exception thrown while setting up data for open command test: " + excp.getMessage());
        }
    }

    /**
     * Cleans up once tests are complete by removing patients.
     */
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
            cmd.execute(core); // opens search results
            List<DukeObject> searchList = getSearchResultList();
            assertTrue(searchList.contains(patient1) && searchList.contains(patient2)
                    && searchList.size() == 2);
            cmd.execute(core, patient1);
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
            cmd.execute(core); // opens search results
            List<DukeObject> resultList = getSearchResultList();
            assertTrue(resultList.containsAll(Arrays.asList(patient1, patient2))
                    && resultList.size() == 2);
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
            core.uiContext.open(patient1);
            cmd.execute(core); // opens search results
            List<DukeObject> searchList = getSearchResultList();
            // matches invx (follow-up) and result (critical) but not plan (follow-up but non-matching name)
            assertTrue(searchList.containsAll(Arrays.asList(impression1, impression2, invx, result))
                    && searchList.size() == 4);
            cmd.execute(core, impression1);
            assertEquals(core.uiContext.getObject(), impression1);
        } catch (DukeException excp) {
            fail("Exception thrown while executing patient open command: " + excp.getMessage());
        }
    }

    @Test
    public void impressionOpenCommand_irrelevantMatches_matchesIgnored() {
        setupCommand(ImpressionOpenSpec.getSpec());
        try {
            core.uiContext.open(impression1);
            cmd.execute(core); // opens search results
            List<DukeObject> searchList = getSearchResultList();
            assertTrue(searchList.containsAll(Arrays.asList(invx, result))
                    && searchList.size() == 2);
            cmd.execute(core, invx);
            assertEquals(core.uiContext.getObject(), invx);
        } catch (DukeException excp) {
            fail("Exception thrown while executing impression open command: " + excp.getMessage());
        }
    }

    /**
     * Tests basic search functionality, ensuring it identifies patients whose description match the search,
     * and child objects whose names match the search.
     */
    @Test
    public void homeFindCommand_childMatches_matchesFound() {
        setupCommand(HomeFindSpec.getSpec());
        try {
            cmd.execute(core); // opens search results
            List<DukeObject> searchList = getSearchResultList();
            assertTrue(searchList.containsAll(Arrays.asList(patient1, patient2, patient3, impression1,
                    impression2, impression3, invx, result, plan))
                    && searchList.size() == 9);
            cmd.execute(core, plan);
            assertEquals(core.uiContext.getObject(), plan);
        } catch (DukeException excp) {
            fail("Exception thrown while executing home find command: " + excp.getMessage());
        }
    }

    @Test
    public void patientFindCommand_childMatches_matchesFound() {
        setupCommand(PatientFindSpec.getSpec());
        try {
            core.uiContext.open(patient1);
            cmd.execute(core); // opens search results
            List<DukeObject> searchList = getSearchResultList();
            assertTrue(searchList.containsAll(Arrays.asList(impression1, impression2, impression3,
                    invx, result, plan))
                    && searchList.size() == 6);
            cmd.execute(core, result);
            assertEquals(core.uiContext.getObject(), result);
        } catch (DukeException excp) {
            fail("Exception thrown while executing patient find command: " + excp.getMessage());
        }
    }

    @Test
    public void impressionFindCommand_multipleMatches_matchesFound() {
        setupCommand(ImpressionFindSpec.getSpec());
        try {
            core.uiContext.open(impression1);
            cmd.execute(core); // opens search results
            List<DukeObject> searchList = getSearchResultList();
            assertTrue(searchList.containsAll(Arrays.asList(invx, result, plan))
                    && searchList.size() == 3);
            cmd.execute(core, result);
            assertEquals(core.uiContext.getObject(), result);
        } catch (DukeException excp) {
            fail("Exception thrown while executing impression find command: " + excp.getMessage());
        }
    }

    private void setupCommand(ObjSpec spec) {
        String[] switchNames = {};
        String[] switchVals = {};
        try {
            cmd = new ObjCommand(spec, "jo", switchNames, switchVals);
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
}
