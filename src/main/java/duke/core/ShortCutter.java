package duke.core;

import duke.command.*;
import duke.statistic.Counter;

import java.util.*;

public class ShortCutter {
    Counter counter;
    Ui ui;

    public ShortCutter(Counter counter, Ui ui) {
        this.counter = counter;
        this.ui = ui;
    }

    private Map<String, Integer> sortedCommandTable;
    private Map<Integer, String> topUsedCommandTable;

    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator = (k1, k2) -> {
            int compare = map.get(k2).compareTo(map.get(k1));
            if (compare == 0)
                return 1;
            else
                return compare;
        };
        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    public Command runShortCut() throws DukeException {
        sortedCommandTable = sortByValues(counter.getCommandTable());
        topUsedCommandTable = MapSorter(sortedCommandTable);
        String commandName;
        String choiceIndex = ui.readCommand();
        switch (choiceIndex) {
        case "1":
            commandName = topUsedCommandTable.get(1);
            if (commandName.equals("AddPatientCommand")) {
                String name;
                String nric;
                String room;
                String remark;
                System.out.println("Name?");
                name = ui.readCommand();
                System.out.println("NRIC?");
                nric = ui.readCommand();
                System.out.println("Room?");
                room = ui.readCommand();
                System.out.println("Remark?");
                remark = ui.readCommand();

                String[] patientInfo = new String[]{name, nric, room, remark};
                return new AddPatientCommand(patientInfo);
            } else if (commandName.equals("AddStandardTaskCommand")) {
                System.out.println("Task Name?");
                String taskName = ui.readCommand();
                return new AddStandardTaskCommand(taskName);
            } else if (commandName.equals("DeletePatientCommand")) {
                System.out.println("Patient ID Number ?");
                String patientId = ui.readCommand();
                return new DeletePatientCommand(patientId);

            } else if (commandName.equals("DeleteTaskCommand")) {
                System.out.println("Task ID?");
                String taskId = "#" + ui.readCommand();
                return new DeleteTaskCommand(taskId);

            } else if (commandName.equals("FindPatientCommand")) {
                System.out.println("Patient ID?");
                String patientId = "#" + ui.readCommand();
                return new FindPatientCommand(patientId);

            } else if (commandName.equals("FindPatientTaskCommand")) {
                System.out.println("Task ID?");
                String taskId = "#" + ui.readCommand();
                return new FindPatientTaskCommand(taskId); // check for command

            } else if (commandName.equals("ListPatientsCommand")) {
                return new ListPatientsCommand();

            } else if (commandName.equals("ListTasksCommand")) {
                return new ListTasksCommand();

            } else if (commandName.equals("UpdatePatientCommand")) {
                String patientId;
                String infoType;
                String changedValue;
                System.out.println("Patient ID ?");
                patientId = "#" + ui.readCommand();
                System.out.println("what do you want to change?");
                infoType = ui.readCommand();
                System.out.println("Change to ?");
                changedValue = ui.readCommand();

                String userInput = patientId + infoType + changedValue;
                return new UpdatePatientCommand(userInput);
            }

//            } else if (commandName.equals("UpdateTaskCommand")) {
//
//
//
//            } else if (commandName.equals("AssignTaskToPatientCommand")) {
//
//
//            }

        case "2":
            commandName = topUsedCommandTable.get(2);
            if (commandName.equals("AddPatientCommand")) {
                String name;
                String nric;
                String room;
                String remark;
                System.out.println("Name?");
                name = ui.readCommand();
                System.out.println("NRIC?");
                nric = ui.readCommand();
                System.out.println("Room?");
                room = ui.readCommand();
                System.out.println("Remark?");
                remark = ui.readCommand();

                String[] patientInfo = new String[]{name, nric, room, remark};
                return new AddPatientCommand(patientInfo);
            } else if (commandName.equals("AddStandardTaskCommand")) {
                System.out.println("Task Name?");
                String taskName = ui.readCommand();
                return new AddStandardTaskCommand(taskName);
            } else if (commandName.equals("DeletePatientCommand")) {
                System.out.println("Patient ID Number ?");
                String patientId = ui.readCommand();
                return new DeletePatientCommand(patientId);

            } else if (commandName.equals("DeleteTaskCommand")) {
                System.out.println("Task ID?");
                String taskId = "#" + ui.readCommand();
                return new DeleteTaskCommand(taskId);

            } else if (commandName.equals("FindPatientCommand")) {
                System.out.println("Patient ID?");
                String patientId = "#" + ui.readCommand();
                return new FindPatientCommand(patientId);

            } else if (commandName.equals("FindPatientTaskCommand")) {
                System.out.println("Task ID?");
                String taskId = "#" + ui.readCommand();
                return new FindPatientTaskCommand(taskId); // check for command

            } else if (commandName.equals("ListPatientsCommand")) {
                return new ListPatientsCommand(); // check for command

            } else if (commandName.equals("ListTasksCommand")) {
                return new ListTasksCommand(); // check for command

            } else if (commandName.equals("UpdatePatientCommand")) {
                String patientId;
                String infoType;
                String changedValue;
                System.out.println("Patient ID ?");
                patientId = "#" + ui.readCommand();
                System.out.println("what do you want to change?");
                infoType = ui.readCommand();
                System.out.println("Change to ?");
                changedValue = ui.readCommand();

                String userInput = patientId + infoType + changedValue;
                return new UpdatePatientCommand(userInput);
            }
        case "3": {
            commandName = topUsedCommandTable.get(3);
            if (commandName.equals("AddPatientCommand")) {
                String name;
                String nric;
                String room;
                String remark;
                System.out.println("Name?");
                name = ui.readCommand();
                System.out.println("NRIC?");
                nric = ui.readCommand();
                System.out.println("Room?");
                room = ui.readCommand();
                System.out.println("Remark?");
                remark = ui.readCommand();

                String[] patientInfo = new String[]{name, nric, room, remark};
                return new AddPatientCommand(patientInfo);
            } else if (commandName.equals("AddStandardTaskCommand")) {
                System.out.println("Task Name?");
                String taskName = ui.readCommand();
                return new AddStandardTaskCommand(taskName);
            } else if (commandName.equals("DeletePatientCommand")) {
                System.out.println("Patient ID Number ?");
                String patientId = ui.readCommand();
                return new DeletePatientCommand(patientId);

            } else if (commandName.equals("DeleteTaskCommand")) {
                System.out.println("Task ID?");
                String taskId = "#" + ui.readCommand();
                return new DeleteTaskCommand(taskId);

            } else if (commandName.equals("FindPatientCommand")) {
                System.out.println("Patient ID?");
                String patientId = "#" + ui.readCommand();
                return new FindPatientCommand(patientId);

            } else if (commandName.equals("FindPatientTaskCommand")) {
                System.out.println("Task ID?");
                String taskId = "#" + ui.readCommand();
                return new FindPatientTaskCommand(taskId); // check for command

            } else if (commandName.equals("ListPatientsCommand")) {
                return new ListPatientsCommand(); // check for command

            } else if (commandName.equals("ListTasksCommand")) {
                return new ListTasksCommand(); // check for command

            } else if (commandName.equals("UpdatePatientCommand")) {
                String patientId;
                String infoType;
                String changedValue;
                System.out.println("Patient ID ?");
                patientId = "#" + ui.readCommand();
                System.out.println("what do you want to change?");
                infoType = ui.readCommand();
                System.out.println("Change to ?");
                changedValue = ui.readCommand();

                String userInput = patientId + infoType + changedValue;
                return new UpdatePatientCommand(userInput);
            }

        }

        default:
            throw new DukeException("ERROR");
        }

    }

    public Map<Integer, String> MapSorter(Map<String, Integer> sortedCommandTable) {
        Map<Integer, String> topCommandTable = new HashMap<>();
        ArrayList<String> keys = new ArrayList<>(sortedCommandTable.keySet());
        if (sortedCommandTable.size() < 3) {
            for (int i = 0; i < sortedCommandTable.size(); i++) {
                int index = i + 1;
                topCommandTable.put(index, keys.get(i));
                System.out.println("[" + index + "] " + keys.get(i));
            }

        } else {
            for (int i = 0; i < 3; i++) {
                int index = i + 1;
                topCommandTable.put(index, keys.get(i));
                System.out.println("[" + index + "] " + keys.get(i));
            }
        }
        System.out.println("Please choose one of these commands");
        return topCommandTable;
    }


}
