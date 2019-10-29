package duke.command.patient;

import duke.command.ArgLevel;
import duke.command.ArgSpec;

public class PatientHistorySpec extends ArgSpec {

    private static final PatientHistorySpec spec = new PatientHistorySpec();

    public static PatientHistorySpec getSpec() {
        return spec;
    }

    private PatientHistorySpec() {
        emptyArgMsg = "You did not tell me anything about what to add for his/her medical history!";
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches();
    }
}
