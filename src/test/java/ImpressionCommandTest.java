import duke.command.ArgCommand;
import duke.command.impression.ImpressionNewCommand;
import duke.data.Impression;
import duke.data.Medicine;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.context.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ImpressionCommandTest extends CommandTest {

    private Impression impression;
    private Patient patient;

    @BeforeEach
    public void setupPatientAndImpression() {
        patient = new Patient("name", "bedNo", "allergies", 0, 0,
                0, 0, "", "");
        impression = new Impression("name", "description", patient);
        patient.addNewImpression(impression);
        core.patientMap.addPatient(patient);
        core.uiContext.setContext(Context.IMPRESSION, impression);
    }

    @Test
    public void ImpressionNewCommand_fullCommand_correctDataCreated() {
        //TODO test other DukeData
        ArgCommand newMedCmd = new ImpressionNewCommand();
        String[] switchNames = {"medicine", "name", "priority", "status", "dose", "startDate", "duration"};
        String[] switchVals = {null, "test", "2", "1", "test dose", "today", "next two weeks"};
        setupCommand(newMedCmd, null, switchNames, switchVals);
        Medicine testMed = new Medicine("test", impression, 2, 1, "test dose",
                "today", "next two weeks");

        try {
            newMedCmd.execute(core);
            assertEquals(testMed, impression.getTreatment("test"));
        } catch (DukeException excp) {
            fail("Exception thrown while executing valid command!");
        }
    }
}
