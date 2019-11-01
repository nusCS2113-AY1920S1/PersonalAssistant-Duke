package duke.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShortCutterTest {

    private Map<String, Integer> commandTable = new HashMap<>();
    private Map<String, Integer> sortedCommandTable = new HashMap<>();

    String cmd1 = "AddPatientCommand";
    String cmd2 = "DeletePatientCommand";
    String cmd3 = "DeleteTaskCommand";
    String cmd4 = "FindPatientCommand";
    String cmd5 = "ListPatientsCommand";
    String cmd6 = "UpdatePatientCommand";
    String cmd7 = "UpdateTaskCommand";

    @Test
    public void sortByValues_commandTable_sortedCommandTableByValue() {
        commandTable.put("AddPatientCommand", 1);
        commandTable.put("AddTaskCommand", 2);
        commandTable.put("AssignDeadlineTaskCommand", 3);
        commandTable.put("AssignPeriodTaskCommand", 4);
        commandTable.put("ListPatientsCommand", 5);
        commandTable.put("ListTasksCommand", 6);
        commandTable.put("DeleteAssignedTaskCommand", 7);
        commandTable.put("DeletePatientCommand", 8);
        commandTable.put("DeleteTaskCommand", 9);
        commandTable.put("FindPatientCommand", 10);
        commandTable.put("FindTaskCommand", 11);
        commandTable.put("UpdatePatientCommand", 12);
        commandTable.put("UpdateTaskCommand", 13);

        sortedCommandTable = ShortCutter.sortByValues(commandTable);
        ArrayList<Integer> values = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedCommandTable.entrySet()) {
            int value = entry.getValue();
            values.add(value);
        }

        assertEquals(values.get(0), 13);
        assertEquals(values.get(1), 12);
        assertEquals(values.get(2), 11);
        assertEquals(values.get(3), 10);
        assertEquals(values.get(4), 9);
        assertEquals(values.get(5), 8);
        assertEquals(values.get(6), 7);
        assertEquals(values.get(7), 6);
        assertEquals(values.get(8), 5);
        assertEquals(values.get(9), 4);
        assertEquals(values.get(10), 3);
        assertEquals(values.get(11), 2);
        assertEquals(values.get(12), 1);
    }

    @Test
    public void commandNameConverter_CommandType_RespectiveConvertedCmdName() throws DukeException {
        final String s1 = ShortCutter.commandNameConverter(cmd1);
        final String s2 = ShortCutter.commandNameConverter(cmd2);
        final String s3 = ShortCutter.commandNameConverter(cmd3);
        final String s4 = ShortCutter.commandNameConverter(cmd4);
        final String s5 = ShortCutter.commandNameConverter(cmd5);
        final String s6 = ShortCutter.commandNameConverter(cmd6);
        final String s7 = ShortCutter.commandNameConverter(cmd7);

        assertEquals(s1, "Add Patient");
        assertEquals(s2, "Delete a Patient");
        assertEquals(s3, "Delete a Task");
        assertEquals(s4, "Find a Patient");
        assertEquals(s5, "Show all the patient");
        assertEquals(s6, "Update Patient information");
        assertEquals(s7, "Update Task information");
    }


}
