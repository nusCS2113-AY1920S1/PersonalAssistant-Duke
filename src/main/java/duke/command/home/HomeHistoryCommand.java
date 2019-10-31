package duke.command.home;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.command.CommandUtils;
import duke.data.Patient;
import duke.exception.DukeException;

public class HomeHistoryCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return HomeHistorySpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);

        String bed = getSwitchVal("bed");
        Patient patient = CommandUtils.findFromHome(core, bed, getArg());
        String patientDetails = getArg() + ", " + patient.getName();
        String message = getSwitchVal("message");
        String rewrite = getSwitchVal("rewrite");
        String newHistory = message;

        if (rewrite != null && (rewrite.toLowerCase().equals("y")
                || rewrite.toLowerCase().equals("yes") || rewrite.toLowerCase().equals("ye"))) {
            patient.setHistory(message);
        } else {
            newHistory = patient.appendHistory(message);
        }
        core.writeJsonFile();
        core.ui.print("History of " + patientDetails + " updated with:\n" + newHistory + "\n");
    }
}
