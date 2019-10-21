package duke.storage;

import duke.core.DukeException;
import duke.patient.Patient;
import duke.relation.EventPatientTask;
import duke.relation.PatientTask;
import duke.relation.StandardPatientTask;
import duke.task.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * It is a centralized class to manage all the storages related to data w/r.
 * It provides API for saving during command execution.
 */
public class StorageManager {

    private static final String COMMAND_COUNTER_FILENAME = "counter.csv";
    private static final String PATIENT_FILENAME = "patients.csv";
    private static final String ASSIGNED_TASK_FILENAME = "patientsTasks.csv";
    private static final String STANDARD_TASK_FILENAME = "standardTasks.csv";

    private static final String[] COMMAND_COUNTER_HEADERS = {"Command Name", "Frequency"};
    private static final String[] ASSIGNED_TASK_HEADERS = {"PID", "TID", "DONE", "RECURRENCE",
        "DEADLINE", "STARTTIME", "ENDTIME", "TASKTYPE", "uuid"};
    private static final String[] PATIENT_HEADERS = {"Id", "Name", "NRIC", "Room", "Remark"};
    private static final String[] STANDARD_TASK_HEADERS = {"Id", "Description"};

    private CsvStorage commandCounterStorage;
    private CsvStorage patientStorage;
    private CsvStorage assignedTaskStorage;
    private CsvStorage standardTaskStorage;

    /**
     * Initialize all storages to perform save/load of all data.
     *
     * @param filePath relative path of where all local data store
     */
    public StorageManager(String filePath) {
        this.commandCounterStorage = new CsvStorage(filePath + "/" + COMMAND_COUNTER_FILENAME);
        this.patientStorage = new CsvStorage(filePath + "/" + PATIENT_FILENAME);
        this.assignedTaskStorage = new CsvStorage(filePath + "/" + ASSIGNED_TASK_FILENAME);
        this.standardTaskStorage = new CsvStorage(filePath + "/" + STANDARD_TASK_FILENAME);
    }

    /**
     * Save patient data in the format of("Id", "Name", "NRIC", "Room", "Remark") to local csv files.
     *
     * @param patients a list containing patient's info
     */
    public void savePatients(ArrayList<Patient> patients) throws DukeException {
        // Initialize capacity of 3000 rows of patient's information
        ArrayList<ArrayList<String>> infoList = new ArrayList<>(3000);
        try {
            for (Patient patient : patients) {
                ArrayList<String> row = new ArrayList<String>();
                row.add(String.valueOf(patient.getID())); // Append value of column ID in a row
                row.add(patient.getName()); // Append value of column Name in a row
                row.add(patient.getNric()); // Append value of column Nric in a row
                row.add(patient.getRoom()); // Append value of column Room in a row
                row.add(patient.getRemark()); // Append value of column Remark in a row
                infoList.add(row);
            }
            patientStorage.write(infoList, PATIENT_HEADERS);
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * Save task data in the format of ("Id", "Description") to local csv files.
     *
     * @param tasks a list containing task's info
     */
    public void saveTasks(ArrayList<Task> tasks) throws DukeException {
        // Initialize capacity of 30 rows of standard task's info
        ArrayList<ArrayList<String>> infoList = new ArrayList<>(3000);
        try {
            for (Task task : tasks) {
                ArrayList<String> row = new ArrayList<String>();
                row.add(String.valueOf(task.getID())); // Append value of column ID in a row
                row.add(task.getDescription()); // Append value of column description in a row
                infoList.add(row);
            }
            standardTaskStorage.write(infoList, STANDARD_TASK_HEADERS);
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * .
     *
     * @param assignedTasks .
     * @throws DukeException .
     */
    public void saveAssignedTasks(ArrayList<PatientTask> assignedTasks) throws DukeException {
        // Initialize capacity of 200 rows of patient-specified task's info
        ArrayList<ArrayList<String>> infoList = new ArrayList<>(200);
        try {
            for (PatientTask assignedTask : assignedTasks) {
                String pid = String.valueOf(assignedTask.getPatientId());
                String tid = String.valueOf(assignedTask.getTaskID());
                String uniqueId = String.valueOf(assignedTask.getUid());
                String isDone = String.valueOf(assignedTask.isDone());
                String isRecurr = String.valueOf(assignedTask.isRecurrsive());
                String deadline = null;
                String startTime = null;
                String endTime = null;
                String type = assignedTask.getTaskType();

                if (assignedTask instanceof StandardPatientTask) {
                    deadline = ((StandardPatientTask) assignedTask).getDeadlineRaw();
                } else if (assignedTask instanceof EventPatientTask) {
                    startTime = ((EventPatientTask) assignedTask).getStartTimeRaw();
                    endTime = ((EventPatientTask) assignedTask).getEndTimeRaw();
                }
                ArrayList<String> row = new ArrayList<String>(Arrays.asList(pid, tid, isDone, isRecurr,
                    deadline, startTime, endTime, type, uniqueId));
                infoList.add(row);
            }
            assignedTaskStorage.write(infoList, ASSIGNED_TASK_HEADERS);
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * Save command frequency to local csv files.
     *
     * @param counts A map containing commands as keys and frequent count as values
     */
    public void saveCounters(Map<String, Integer> counts) throws DukeException {
        // Initialize capacity of 20 commands type.
        ArrayList<ArrayList<String>> infoList = new ArrayList<>(20);
        try {
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                ArrayList<String> row = new ArrayList<String>();
                row.add(entry.getKey()); // Append value of column "Command Name" in a row
                row.add(entry.getValue().toString()); // Append value of column 'Frequency' in a row
                infoList.add(row); // Append row to the list of rows
            }
            commandCounterStorage.write(infoList, COMMAND_COUNTER_HEADERS);
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * .
     *
     * @return .
     * @throws DukeException .
     */
    public ArrayList<Patient> loadPatients() throws DukeException {
        // Load a list of Map<header, values> from local data file
        ArrayList<Map<String, String>> patientsMap = patientStorage.read();
        ArrayList<Patient> patientList = new ArrayList<Patient>();
        try {
            for (Map<String, String> patientInfo : patientsMap) {
                int id = Integer.parseInt(patientInfo.get(PATIENT_HEADERS[0]));
                String name = patientInfo.get(PATIENT_HEADERS[1]);
                String nric = patientInfo.get(PATIENT_HEADERS[2]);
                String room = patientInfo.get(PATIENT_HEADERS[3]);
                String remark = patientInfo.get(PATIENT_HEADERS[4]);
                patientList.add(new Patient(id, name, nric, room, remark));
            }
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
        return patientList;
    }

    /**
     * .
     *
     * @return .
     * @throws DukeException .
     */
    public ArrayList<Task> loadTasks() throws DukeException {
        // Load a list of Map<header, values> from local data file
        ArrayList<Map<String, String>> tasksMap = standardTaskStorage.read();
        ArrayList<Task> taskList = new ArrayList<Task>();
        try {
            for (Map<String, String> taskInfo : tasksMap) {
                int id = Integer.parseInt(taskInfo.get(STANDARD_TASK_HEADERS[0]));
                String description = taskInfo.get(STANDARD_TASK_HEADERS[1]);
                taskList.add(new Task(id, description));
            }
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
        return taskList;
    }

    /**
     * .
     *
     * @return .
     * @throws DukeException .
     */
    public ArrayList<PatientTask> loadAssignedTasks() throws DukeException {
        // Load a list of Map<header, values> from local data file
        ArrayList<Map<String, String>> assignedTaskMap = assignedTaskStorage.read();
        ArrayList<PatientTask> assignedTaskList = new ArrayList<PatientTask>();
        try {
            for (Map<String, String> assignedTaskInfo : assignedTaskMap) {
                int pid = Integer.parseInt(assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[0]));
                int tid = Integer.parseInt(assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[1]));
                boolean isDone = Boolean.parseBoolean(assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[2]));
                boolean isRecursive = Boolean.parseBoolean(assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[3]));
                String deadline = assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[4]);
                String startTime = assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[5]);
                String endTime = assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[6]);
                String taskType = assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[7]);
                int uniqueId = Integer.parseInt(assignedTaskInfo.get(ASSIGNED_TASK_HEADERS[8]));
                if (taskType.equals("S")) {
                    assignedTaskList.add(new StandardPatientTask(pid, tid, isDone, isRecursive, deadline, taskType));
                } else if (taskType.equals("E")) {
                    assignedTaskList.add(new EventPatientTask(pid, tid, isDone, isRecursive,
                        startTime, endTime, taskType));
                }
            }
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
        return assignedTaskList;
    }

    /**
     * .
     *
     * @return .
     * @throws DukeException .
     */
    public Map<String, Integer> loadCommandFrequency() throws DukeException {
        // Load a list of Map<header, values> from local data file
        ArrayList<Map<String, String>> counterMap = commandCounterStorage.read();
        Map<String, Integer> integratedCounterMap = new HashMap<>();
        try {
            for (Map<String, String> rowInfo : counterMap) {
                String commandName = rowInfo.get(COMMAND_COUNTER_HEADERS[0]);
                int frequency = Integer.parseInt(rowInfo.get(COMMAND_COUNTER_HEADERS[1]));
                integratedCounterMap.put(commandName, frequency);
            }
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
        return integratedCounterMap;
    }
}
