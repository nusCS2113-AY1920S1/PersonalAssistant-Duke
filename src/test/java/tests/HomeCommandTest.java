package tests;

import templates.CommandTest;

public class HomeCommandTest extends CommandTest {
    /**
     * Tests HomeNewCommand with all switches present.
    @Test
    public void homeNewCommand_allSwitches_correctPatientCreated() {
        String[] switchNames = {"name", "bed", "allergies", "height", "wejght", "age", "number", "address", "history"};
        String[] switchVals = {"testCPatient", "C1", "test allergies", "123", "456", "100", "6582447", "test address",
            "test history"};
        ArgCommand newPatientCmd = new HomeNewCommand(null, switchNames, switchVals);
        Patient patient = new Patient("testCPatient", "C1", "test allergies", 123,
                456, 100, 6582447, "test address", "test history");
        try {
            newPatientCmd.execute(core);
            assertTrue(patient.equals(core.patientMap.getPatient("C1")));
        } catch (DukeException excp) {
            fail("Exception thrown when validly creating patient from command: " + excp.getMessage());
        }
    }
    */

    /**
     * Tests HomeReport Command with the summary switch present.
    @Test
    public void homeReportCommandTest() {
        String[] switchNames = {"bed", "summary"};
        String[] switchVals = {"testC1", "This as a test report that is used to test the report command from the"
                + " home context"};
        core.patientMap.addPatient(new Patient("testCPatient", "testC1", "test allergies",
                123, 456, 100, 6582447, "test address", "test history"));
        ArgCommand newReportCmd = new HomeReportCommand("", switchNames, switchVals);
        try {
            newReportCmd.execute(core);
            String expected = "PATIENT REPORT\n\nThis report shows all the data that was stored about a patient at "
                    + "the time the report was created.\n\nReport Summary: This as a test report that is used to test "
                    + "the report command from the home context\n\nPatient Data;\n\tName of patient: "
                    + "testCPatient\n\tBed Number: testC1\n\tHeight: 123\n\tWeight: 456\n\tAllergies: test "
                    + "allergies\n\tAge: 100\n\tNumber: 6582447\n\tAddress: test address\n\tHistory: "
                    + "test history\n\n\n";
            String actual = Files.readString(Paths.get("data/Reports/testCPatient-testC1.txt"),
                    StandardCharsets.US_ASCII);
            assertEquals(expected, actual);
        } catch (DukeException | IOException excp) {
            fail("Exception thrown when validly creating report from command in home context: " + excp.getMessage());
        }
    }
    */
}
