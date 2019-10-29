import duke.command.impression.ImpressionNewCommand;
import duke.data.Impression;
import duke.data.Medicine;
import duke.data.Patient;
import duke.exception.DukeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    public void ImpressionNewCommand_fullCommand_correctDataCreated() {
        //TODO test other DukeData
        ImpressionNewCommand cmd = new ImpressionNewCommand();
        Map<String, String> medVals = Map.ofEntries(
                Map.entry("name", "test"),
                Map.entry("priority", "2"),
                Map.entry("status", "1"),
                Map.entry("dose", "test dose"),
                Map.entry("startDate", "today"),
                Map.entry("duration", "next two weeks")
        );
        cmd.setSwitchValsMap(medVals);
        Medicine testMed = new Medicine("test", impression, 2, 1, "test dose",
                "today", "next two weeks");

        try {
            cmd.execute(core);
            assertEquals(testMed, impression.getTreatment("test"));
        } catch (DukeException excp) {
            fail("Exception thrown while executing valid command!");
        }
    }
}
