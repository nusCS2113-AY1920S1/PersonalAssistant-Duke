package duke.MementoPattern;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import duke.command.Command;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.relation.PatientTask;
import duke.relation.PatientTaskList;
import duke.statistic.Counter;
import duke.task.Task;
import duke.task.TaskManager;

import java.util.ArrayList;

public class Memento
{
    private TaskManager memTaskList;
    private PatientManager memPatientManager;
    private PatientTaskList memPatientTaskList;

    public Memento(TaskManager taskManager, PatientTaskList patientTaskList, PatientManager patientManager) {
        this.memTaskList = taskManager;
        this.memPatientManager = patientManager;
        this.memPatientTaskList = patientTaskList;
    }

    public TaskManager getTaskState() {
        return this.memTaskList;
    }

    public PatientManager getPatientState() {
        return this.memPatientManager;
    }

    public PatientTaskList getPatientTaskState() {
        return this.memPatientTaskList;
    }
}