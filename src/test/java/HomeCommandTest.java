import duke.command.ArgCommand;
import duke.command.home.HomeNewCommand;
import duke.data.Patient;
import duke.exception.DukeException;
import org.junit.jupiter.api.Test;
import templates.CommandTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class HomeCommandTest extends CommandTest {

    /**
     * Tests HomeNewCommand with all switches present.
     */
    @Test
    public void homeNewCommand_allSwitches_correctPatientCreated() {
        ArgCommand newPatientCmd = new HomeNewCommand();
        String[] switchNames = {"bed", "allergies", "height", "weight", "age", "number", "address", "history"};
        String[] switchVals = {"C1", "test allergies", "123", "456", "100", "6582447", "test address", "test history"};
        setupCommand(newPatientCmd, "testCPatient", switchNames, switchVals);
        Patient patient = new Patient("testCPatient", "C1", "test allergies", 123,
                456, 100, 6582447, "test address", "test history");
        try {
            newPatientCmd.execute(core);
            assertTrue(patient.equals(core.patientMap.getPatient("C1")));
        } catch (DukeException excp) {
            fail("Exception thrown when validly creating patient from command!");
        }
    }
}
