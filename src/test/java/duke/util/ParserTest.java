//@@author lmtaek

package duke.util;

import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    String patientDummyInput = "add patient :name :NRIC :room :remark";
    String taskDummyInput = "add task :Walk the dog";

    String assignPatientToDeadlineTask = "assign deadline task :patient name :#2 :02/02/2002 2222";
    String assignPatientToPeriodTask = "assign period task :#2 :#1 :01/02/2003 1234 :06/05/2004 2312";

    String deletePatientInputWithID = "delete patient :#123";
    String deletePatientInputWithName = "delete patient :billy joe";
    String deleteTaskInputWithID = "delete task :#10";
    String deleteTaskInputWithName = "delete task :Take medicine";
    String deleteAssignedTaskInputWithUniqueID = "delete assigned task :#3";

    String updatePatientInput = "update patient :name :field :new data";
    String updateTaskInput = "update task :task name :new description";

    String findPatientInputWithName = "find patient :name";
    String findPatientInputWithID = "find patient :#200";
    String findAssignedTasksInput = "find patient tasks :jane doe";

    @Test
    public void parseAddPatientTest() throws DukeException {
        Parser testParser = new Parser(patientDummyInput);
        String[] desiredOutput = {"name", "NRIC", "room", "remark"};
        String[] testOutput = testParser.parseUserInput();

        assertTrue(desiredOutput.length == testOutput.length);
        for (int i = 0; i < desiredOutput.length; i++) {
            assertTrue(desiredOutput[i].equals(testOutput[i]), "Parsing failed. Expected: "
                    + desiredOutput[i] + " but got: " + testOutput[i]);
        }
    }

    @Test
    public void parseAddTask() throws DukeException {
        Parser testParser = new Parser(taskDummyInput);
        String testOutput = testParser.parseUserInput()[0];

        assertTrue(testOutput.equals("Walk the dog"),
                "Task description did not parse correctly. Expected 'Walk the dog' but got: " + testOutput);
    }

    @Test
    public void parseAssignDeadlineAndPeriodTasks() throws DukeException {
        Parser testParserDeadline = new Parser(assignPatientToDeadlineTask);
        Parser testParserPeriod = new Parser(assignPatientToPeriodTask);

        final String[] testDeadlineOutput = testParserDeadline.parseUserInput();
        final String[] testPeriodOutput = testParserPeriod.parseUserInput();

        String[] desiredDeadlineOutput = {"patient name", "#2", "02/02/2002 2222"};
        String[] desiredPeriodOutput = {"#2", "#1", "01/02/2003 1234", "06/05/2004 2312"};

        assertTrue(desiredDeadlineOutput.length == testDeadlineOutput.length);
        for (int i = 0; i < desiredDeadlineOutput.length; i++) {
            assertTrue(desiredDeadlineOutput[i].equals(testDeadlineOutput[i]), "Parsing failed. Expected: "
                    + desiredDeadlineOutput[i] + " but got: " + testDeadlineOutput[i]);
        }

        assertTrue(desiredPeriodOutput.length == testPeriodOutput.length);
        for (int i = 0; i < desiredPeriodOutput.length; i++) {
            assertTrue(desiredPeriodOutput[i].equals(testPeriodOutput[i]), "Parsing failed. Expected: "
                    + desiredPeriodOutput[i] + " but got: " + testPeriodOutput[i]);
        }
    }

    @Test
    public void parseDeletePatient() throws DukeException {
        Parser testParserID = new Parser(deletePatientInputWithID);
        Parser testParserName = new Parser(deletePatientInputWithName);

        String testOutputID = testParserID.parseUserInput()[0];
        String testOutputName = testParserName.parseUserInput()[0];

        assertTrue(testOutputID.charAt(0) == '#' && testOutputID.equals("#123"),
                "Delete patient by ID parsing failed. Expected '#123' but got: " + testOutputID);
        assertTrue(testOutputName.equals("billy joe"),
                "Delete patient by name parsing failed. Expected 'billy joe' but got: " + testOutputName);
    }

    @Test
    public void parseDeleteTask() throws DukeException {
        Parser testParserID = new Parser(deleteTaskInputWithID);
        Parser testParserName = new Parser(deleteTaskInputWithName);

        String testOutputID = testParserID.parseUserInput()[0];
        String testOutputName = testParserName.parseUserInput()[0];

        assertTrue(testOutputID.charAt(0) == '#' && testOutputID.equals("#10"),
                "Delete task by ID parsing failed. Expected '#10' but got: " + testOutputID);
        assertTrue(testOutputName.equals("Take medicine"),
                "Delete task by name parsing failed. Expected 'Take medicine' but got: " + testOutputName);
    }

    @Test
    public void parseDeleteAssignedTask() throws DukeException {
        Parser testParserUniqueID = new Parser(deleteAssignedTaskInputWithUniqueID);
        String[] testOutputUniqueID = testParserUniqueID.parseUserInput();
        String[] desiredOutputUniqueID = {"#3"};
        assertTrue(desiredOutputUniqueID[0].equals(testOutputUniqueID[0]), "Parsing failed. Expected: "
                + desiredOutputUniqueID[0] + " but got: " + testOutputUniqueID[0]);

    }

    @Test
    public void parseUpdatePatientTest() throws DukeException {
        Parser testParser = new Parser(updatePatientInput);
        String[] testOutput = testParser.parseUserInput();
        String[] desiredOutput = {"name", "field", "new data"};

        assertTrue(desiredOutput.length == testOutput.length);
        for (int i = 0; i < desiredOutput.length; i++) {
            assertTrue(desiredOutput[i].equals(testOutput[i]), "Parsing failed. Expected: "
                    + desiredOutput[i] + " but got: " + testOutput[i]);
        }

    }

    @Test
    public void parseUpdateTaskTest() throws DukeException {

        Parser testParser = new Parser(updateTaskInput);
        String[] testOutput = testParser.parseUserInput();
        String[] desiredOutput = {"task name", "new description"};

        assertTrue(desiredOutput.length == testOutput.length);
        for (int i = 0; i < desiredOutput.length; i++) {
            assertTrue(desiredOutput[i].equals(testOutput[i]), "Parsing failed. Expected: "
                    + desiredOutput[i] + " but got: " + testOutput[i]);
        }

    }

    @Test
    public void parseFindPatientTest() throws DukeException {
        Parser testParserID = new Parser(findPatientInputWithID);
        String testOutputID = testParserID.parseUserInput()[0];
        String desiredOutputID = "#200";

        assertTrue(desiredOutputID.equals(testOutputID), "Parsing failed. Expected: "
                + desiredOutputID + " but got: " + testOutputID);

        Parser testParserName = new Parser(findPatientInputWithName);
        String testOutputName = testParserName.parseUserInput()[0];
        String desiredOutputName = "name";

        assertTrue(desiredOutputName.equals(testOutputName), "Parsing failed. Expected: "
                + desiredOutputName + " but got: " + testOutputName);

    }

    @Test
    public void parseFindAssignedTasksTest() throws DukeException {
        Parser testParser = new Parser(findAssignedTasksInput);
        String testOutput = testParser.parseUserInput()[0];
        String desiredOutput = "jane doe";

        assertTrue(desiredOutput.equals(testOutput), "Parsing failed. Expected: "
                + desiredOutput + " but got: " + testOutput);
    }

}
