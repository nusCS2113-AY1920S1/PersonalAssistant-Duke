package duke.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    String patientDummyInput = "add patient name NRIC room remark";
    String taskDummyInput = "add task Walk the dog";

    @Test
    public void parseAddPatientTest() throws DukeException {
        Parser testParser = new Parser(patientDummyInput);
        String[] testOutput = testParser.parseAdd();

        assertTrue(testOutput[0].equals("name"), "Name field did not parse correctly. Expected: 'name', but got: " + testOutput[0]);
        assertTrue(testOutput[1].equals("NRIC"), "NRIC field did not parse correctly. Expected: 'NRIC', but got: " + testOutput[1]);
        assertTrue(testOutput[2].equals("room"), "Room field did not parse correctly. Expected: 'room', but got: " + testOutput[2]);
        assertTrue(testOutput[3].equals("remark"), "Remark field did not parse correctly. Expected: 'remark', but got: " + testOutput[3]);

    }

    @Test
    public void parseAddTask() throws DukeException {
        Parser testParser = new Parser(taskDummyInput);
        String[] testOutput = testParser.parseAdd();

        assertTrue(testOutput[0].equals("Walk the dog"), "Task description did not parse correctly. Expected 'Walk the dog' but got: " + testOutput[0]);
    }
}
