//@@lmtaek

package duke.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    String patientDummyInput = "add patient :name :NRIC :room :remark";
    String taskDummyInput = "add task :Walk the dog";

    String assignPatientToDeadlineTask = "assign deadline task :patient name :#2 :02/02/2002 2222";
    String assignPatientToEventTask = "assign event task :#2 :#1 :01/02/2003 1234 to 06/05/2004 2312";

    String deletePatientInputWithID = "delete patient :#123";
    String deletePatientInputWithName = "delete patient :billy joe";
    String deleteTaskInputWithID = "delete task :#10";
    String deleteTaskInputWithName = "delete task :Take medicine";
    String deleteAssignedTaskInputWithID = "delete assigned task :#2 :#5";
    String deleteAssignedTaskInputWithName = "delete assigned task :patient name :task name";

    @Test
    public void parseAddPatientTest() throws DukeException {
        Parser testParser = new Parser(patientDummyInput);
        String[] desiredOutput = {"name", "NRIC", "room", "remark"};
        String[] testOutput = testParser.parseAddPatient();

        assertTrue(desiredOutput.length == testOutput.length);
        for (int i = 0; i < desiredOutput.length; i++) {
            assertTrue(desiredOutput[i].equals(testOutput[i]), "Parsing failed. Expected: "
                    + desiredOutput[i] + " but got: " + testOutput[i]);
        }
    }

    @Test
    public void parseAddTask() throws DukeException {
        Parser testParser = new Parser(taskDummyInput);
        String testOutput = testParser.parseAddTask();

        assertTrue(testOutput.equals("Walk the dog"),
                "Task description did not parse correctly. Expected 'Walk the dog' but got: " + testOutput);
    }

    @Test
    public void parseAssignDeadlineAndEventTasks() throws DukeException {
        Parser testParserDeadline = new Parser(assignPatientToDeadlineTask);
        Parser testParserEvent = new Parser(assignPatientToEventTask);

        final String[] testDeadlineOutput = testParserDeadline.parseAssignDeadlineTask();
        final String[] testEventOutput = testParserEvent.parseAssignEventTask();

        String[] desiredDeadlineOutput = {"patient name", "#2", "02/02/2002 2222"};
        String[] desiredEventOutput = {"#2", "#1", "01/02/2003 1234", "06/05/2004 2312"};

        assertTrue(desiredDeadlineOutput.length == testDeadlineOutput.length);
        for (int i = 0; i < desiredDeadlineOutput.length; i++) {
            assertTrue(desiredDeadlineOutput[i].equals(testDeadlineOutput[i]), "Parsing failed. Expected: "
                    + desiredDeadlineOutput[i] + " but got: " + testDeadlineOutput[i]);
        }

        assertTrue(desiredEventOutput.length == testEventOutput.length);
        for (int i = 0; i < desiredEventOutput.length; i++) {
            assertTrue(desiredEventOutput[i].equals(testEventOutput[i]), "Parsing failed. Expected: "
                    + desiredEventOutput[i] + " but got: " + testEventOutput[i]);
        }
    }

    @Test
    public void parseDeletePatient() throws DukeException {
        Parser testParserID = new Parser(deletePatientInputWithID);
        Parser testParserName = new Parser(deletePatientInputWithName);

        String testOutputID = testParserID.parseDeletePatient();
        String testOutputName = testParserName.parseDeletePatient();

        assertTrue(testOutputID.charAt(0) == '#' && testOutputID.equals("#123"),
                "Delete patient by ID parsing failed. Expected '#123' but got: " + testOutputID);
        assertTrue(testOutputName.equals("billy joe"),
                "Delete patient by name parsing failed. Expected 'billy joe' but got: " + testOutputName);
    }

    @Test
    public void parseDeleteTask() throws DukeException {
        Parser testParserID = new Parser(deleteTaskInputWithID);
        Parser testParserName = new Parser(deleteTaskInputWithName);

        String testOutputID = testParserID.parseDeleteTask();
        String testOutputName = testParserName.parseDeleteTask();

        assertTrue(testOutputID.charAt(0) == '#' && testOutputID.equals("#10"),
                "Delete task by ID parsing failed. Expected '#10' but got: " + testOutputID);
        assertTrue(testOutputName.equals("Take medicine"),
                "Delete task by name parsing failed. Expected 'Take medicine' but got: " + testOutputName);
    }

    @Test
    public void parseDeleteAssignedTask() throws DukeException {
        Parser testParserID = new Parser(deleteAssignedTaskInputWithID);
        Parser testParserName = new Parser(deleteAssignedTaskInputWithName);

        String[] testOutputID = testParserID.parseDeleteAssignedTask();
        String[] testOutputName = testParserName.parseDeleteAssignedTask();

        String[] desiredOutputID = {"#2", "#5"};
        String[] desiredOutputName = {"patient name", "task name"};

        assertTrue(desiredOutputID.length == testOutputID.length);
        for (int i = 0; i < desiredOutputID.length; i++) {
            assertTrue(desiredOutputID[i].equals(testOutputID[i]), "Parsing failed. Expected: "
                    + desiredOutputID[i] + " but got: " + testOutputID[i]);
        }

        assertTrue(desiredOutputName.length == testOutputName.length);
        for (int i = 0; i < desiredOutputName.length; i++) {
            assertTrue(desiredOutputName[i].equals(testOutputName[i]), "Parsing failed. Expected: "
                    + desiredOutputName[i] + " but got: " + testOutputName[i]);
        }

    }

}