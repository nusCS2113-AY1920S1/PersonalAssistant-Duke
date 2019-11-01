package duke.models.counter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import duke.commands.Command;
import duke.commands.CommandManager;
import duke.exceptions.DukeException;
import duke.util.ShortCutter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShortCutterTest<map> {

    private Map<String, Integer> commandTable = new HashMap<>();
    private Map<String, Integer> sortedCommandTable = new HashMap<>();
    String[] commandName = {"AddPatientCommand", "AddTaskCommand", "DeletePatientCommand",
            "DeleteTaskCommand", "FindPatientCommand", "ListPatientsCommand", "ListTasksCommand",
            "UpdatePatientCommand", "UpdateTaskCommand"};

    @Test
    public void sortByValuesTest() {
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

}
