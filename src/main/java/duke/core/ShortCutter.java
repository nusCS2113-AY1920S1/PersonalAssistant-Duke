package duke.core;

import duke.command.AddPatientCommand;
import duke.command.AddStandardTaskCommand;
import duke.command.Command;
import duke.command.DeletePatientCommand;
import duke.command.DeleteTaskCommand;
import duke.command.FindPatientCommand;
import duke.command.ListPatientsCommand;
import duke.command.ListTasksCommand;
import duke.command.UpdatePatientCommand;
import duke.command.UpdateTaskCommand;
import duke.statistic.Counter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

//@@author qjie7

/**
 * This is a ShortCutter class to provide short cut implementation
 * DukeCommand will be used to activate the short cut mode.
 * This is not a simple UI implementation but also considered user's
 * daily work behavior to provide  personalised short cut solution.
 *
 * @author QIAN JIE
 * @version 1.3
 */

public class ShortCutter {
    private Counter counter;
    private Ui ui;

    public ShortCutter() {
    }

    /**
     * A constructor for ShortCutter class.
     *
     * @param counter receive a counter object
     * @param ui      receive a ui object
     */
    public ShortCutter(Counter counter, Ui ui) {
        this.counter = counter;
        this.ui = ui;
    }

    private Map<String, Integer> sortedCommandTable;
    private Map<Integer, String> topUsedCommandTable;


    /**
     * This function is used to allow map to be sorted by its values.
     * @param map a command frequency table in Map structure with key
     *            as the command type and value as the command frequency.
     * @return a Map structure that is sorted according to param's value
     *         which is the used command frequency.
     */

    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator = (k1, k2) -> {
            int compare = map.get(k2).compareTo(map.get(k1));
            if (compare == 0) {

                return 1;
            } else {
                return compare;
            }


        };
        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    /**
     * This function is used to generate a topUsedCommandTable based on
     * the data provided from sortedCommandTable.
     * @param sortedCommandTable a sorted command frequency table from high to low in Map structure with key
     *                           as the command type and value as the command frequency.
     * @return a Map structure with key being the index number for user to choose
     *         and value be the command type.
     */

    public Map<Integer, String> mapSorter(Map<String, Integer> sortedCommandTable) throws DukeException {
        Map<Integer, String> topCommandTable = new HashMap<>();
        ArrayList<String> keys = new ArrayList<>(sortedCommandTable.keySet());
        System.out.println("< Most Frequently Used Commands >");
        if (sortedCommandTable.size() < 5) {
            for (int i = 0; i < sortedCommandTable.size(); i++) {
                int index = i + 1;
                topCommandTable.put(index, keys.get(i));
                System.out.println("[" + index + "] " + commandNameConverter(keys.get(i)));
            }

        } else {
            for (int i = 0; i < 5; i++) {
                int index = i + 1;
                topCommandTable.put(index, keys.get(i));
                System.out.println("[" + index + "] " + commandNameConverter(keys.get(i)));
            }
        }
        System.out.println("Please choose one of these commands");
        return topCommandTable;
    }

    /**
     * This function is used to run the short cut UI logic by getting
     * necessary information from user to provide the target output.
     *
     * @return command the command that is to be executed.
     * @throws DukeException throw a dukeException with error message for debugging.
     * @author QIAN JIE
     */
    public Command runShortCut() throws DukeException {
        sortedCommandTable = sortByValues(counter.getCommandTable());
        topUsedCommandTable = mapSorter(sortedCommandTable);
        String commandName;
        Command command;
        String choiceIndex = ui.readCommand();
        switch (choiceIndex) {
        case "1":
            commandName = topUsedCommandTable.get(1);
            command = shortCutChecker(commandName);
            return command;
        case "2":
            commandName = topUsedCommandTable.get(2);
            command = shortCutChecker(commandName);
            return command;
        case "3":
            commandName = topUsedCommandTable.get(3);
            command = shortCutChecker(commandName);
            return command;
        case "4":
            commandName = topUsedCommandTable.get(4);
            command = shortCutChecker(commandName);
            return command;
        case "5":
            commandName = topUsedCommandTable.get(5);
            command = shortCutChecker(commandName);
            return command;
        default:
            throw new DukeException("Please enter a valid index number shown here");
        }

    }

    /**
     * This function is used to provide check/filter on possible
     * type of command that is chosen by the user. This check is
     * necessary due the the constantly changing behavior on the
     * ranking of the command usages.
     *
     * @return command the command that is to be executed.
     * @throws DukeException throw a dukeException with error message for debugging.
     */
    public Command shortCutChecker(String commandName) throws DukeException {
        if (commandName.equals("AddPatientCommand")) {
            String patientName = ui.getPatientInfo("name");
            String nric = ui.getPatientInfo("nric");
            String room = ui.getPatientInfo("room");
            String remark = ui.getPatientInfo("remark");
            String[] patientInfo = new String[]{patientName, nric, room, remark};
            return new AddPatientCommand(patientInfo);
        } else if (commandName.equals("AddStandardTaskCommand")) {
            String taskName = ui.getTaskInfo("name");
            return new AddStandardTaskCommand(taskName);
        } else if (commandName.equals("DeletePatientCommand")) {
            String patientId = ui.getPatientInfo("id");
            return new DeletePatientCommand(patientId);

        } else if (commandName.equals("DeleteTaskCommand")) {
            String taskId = ui.getTaskInfo("id");
            return new DeleteTaskCommand(taskId);

        } else if (commandName.equals("FindPatientCommand")) {
            String patientId = ui.getPatientInfo("id");
            return new FindPatientCommand(patientId);

        } else if (commandName.equals("ListPatientsCommand")) {
            return new ListPatientsCommand();

        } else if (commandName.equals("ListTasksCommand")) {
            return new ListTasksCommand();

        } else if (commandName.equals("UpdatePatientCommand")) {
            String patientId = ui.getPatientInfo("id");
            String infoType = ui.getPatientInfo("change");
            String changeValue = ui.getPatientInfo(("changeValue"));
            String[] patientInfo = new String[]{patientId, infoType, changeValue};
            return new UpdatePatientCommand(patientInfo);
        } else if (commandName.equals("UpdateTaskCommand")) {
            String taskId = ui.getTaskInfo("id");
            String change = ui.getTaskInfo("change");
            String changeValue = ui.getTaskInfo("changeValue");
            String[] patientInfo = new String[]{taskId, change, changeValue};
            return new UpdateTaskCommand(patientInfo);
        } else {
            //Assign patient task will be add...
            //FindPatientTask will be added....
            throw new DukeException("No matching command!");
        }
    }


    /**
     * This function is used to convert the provided command class name
     * into a string that is much user friendly and easy to read by the user.
     * @param commandClassName the command class name chosen by the user
     * @return a String that is easy to read by the user.
     * @throws DukeException throw a dukeException with error message for debugging.
     */
    public String commandNameConverter(String commandClassName) throws DukeException {
        String convertedName;
        if (commandClassName.equals("AddPatientCommand")) {
            convertedName = "Add Patient";
            return convertedName;

        } else if (commandClassName.equals("AddStandardTaskCommand")) {
            convertedName = "Add a Standard Task";
            return convertedName;

        } else if (commandClassName.equals("DeletePatientCommand")) {
            convertedName = "Delete a Patient";
            return convertedName;


        } else if (commandClassName.equals("DeleteTaskCommand")) {
            convertedName = "Delete a Task";
            return convertedName;


        } else if (commandClassName.equals("FindPatientCommand")) {
            convertedName = "Find a Patient";
            return convertedName;


        } else if (commandClassName.equals("FindPatientTaskCommand")) {
            convertedName = "Find a patient with task ";
            return convertedName;

        } else if (commandClassName.equals("ListPatientsCommand")) {
            convertedName = "Show all the patient";
            return convertedName;

        } else if (commandClassName.equals("ListTasksCommand")) {
            convertedName = "Show all the tasks";
            return convertedName;


        } else if (commandClassName.equals("UpdatePatientCommand")) {
            convertedName = "Update Patient information";
            return convertedName;

        } else if (commandClassName.equals("UpdateTaskCommand")) {
            convertedName = "Update Task information";
            return convertedName;
        } else if (commandClassName.equals("AssignTaskToPatientCommand")) {
            convertedName = "Assign a task to a patient";
            return convertedName;
        } else {
            throw new DukeException("No matching command!");
        }
    }
}
