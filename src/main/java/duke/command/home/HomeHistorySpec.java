package duke.command.home;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.CommandUtils;
import duke.command.Switch;
import duke.data.Patient;
import duke.exception.DukeException;

public class HomeHistorySpec extends ArgSpec {
    private static final HomeHistorySpec spec = new HomeHistorySpec();

    public static HomeHistorySpec getSpec() {
        return spec;
    }

    private HomeHistorySpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("bed", String.class, true, ArgLevel.REQUIRED, "b"),
                new Switch("message", String.class, false, ArgLevel.REQUIRED, "m"),
                new Switch("rewrite", String.class, true, ArgLevel.REQUIRED, "r")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        String bed = cmd.getSwitchVal("bed");
        Patient patient = CommandUtils.findFromHome(core, bed, cmd.getArg());
        String patientDetails = cmd.getArg() + ", " + patient.getName();
        String message = cmd.getSwitchVal("message");
        String rewrite = cmd.getSwitchVal("rewrite");
        String newHistory = message;

        if (rewrite != null && (rewrite.toLowerCase().equals("y")
                || rewrite.toLowerCase().equals("yes") || rewrite.toLowerCase().equals("ye"))) {
            patient.setHistory(message);
        } else {
            newHistory = patient.appendHistory(message);
        }
        core.writeJsonFile();
        core.updateUi("History of " + patientDetails + " updated with:\n" + newHistory + "\n");
    }
}
