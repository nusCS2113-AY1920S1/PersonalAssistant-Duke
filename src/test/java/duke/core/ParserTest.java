package duke.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    String patientDummyInput = "add patient name NRIC room remark";
    String taskDummyInput = "add task Walk the dog";

    String assignPatientIDToTaskS = "assign by id: S 1 2 02/02/2002 2222";
    String assignPatientIDToTaskE = "assign by id: E 2 1 02/02/2002 2222 to 03/02/2002 1234";

    String deletePatientInput = "delete patient #123";

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

    @Test
    public void parseAssignPatientByID() throws DukeException {
        Parser testParserStandard = new Parser(assignPatientIDToTaskS);
        Parser testParserEvent = new Parser(assignPatientIDToTaskE);

        String[] testOutputStandard = testParserStandard.parseAssign();
        String[] testOutputEvent = testParserEvent.parseAssign();

        assertTrue(testOutputStandard[0].equals("S"), "Task type parsed incorrectly. Expected 'S' but got: " + testOutputStandard[0]);
        assertTrue(testOutputStandard[1].equals("1") && testOutputStandard[2].equals("2"), "IDs parsed incorrectly.\n"
        + "Expected patient ID of 1, and got: " + testOutputStandard[1] + ".\n Expected task ID of 2 and got: " + testOutputStandard[2]);
        assertTrue(testOutputStandard[3].equals("02/02/2002 2222"));

        assertTrue(testOutputEvent[0].equals("E"), "Task type parsed incorrectly. Expected 'E' but got: " + testOutputEvent[0]);
        assertTrue(testOutputEvent[1].equals("2") && testOutputEvent[2].equals("1"), "IDs parsed incorrectly.\n"
                + "Expected patient ID of 2, and got: " + testOutputEvent[1] + ".\n Expected task ID of 1 and got: " + testOutputEvent[2]);
        assertTrue(testOutputEvent[3].equals("02/02/2002 2222"), "Start date parsed incorrectly. Expected '02/02/2002 2222' but got: " + testOutputEvent[3]);
        assertTrue(testOutputEvent[4].equals("03/02/2002 1234"), "End date parsed incorrectly. Expected '03/02/2002 1234' but got: " + testOutputEvent[4]);
    }

    @Test
    public void parseDeletePatient() {

    }


}
