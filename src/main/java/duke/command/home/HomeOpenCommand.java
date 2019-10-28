package duke.command.home;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.card.PatientCard;
import duke.ui.context.Context;

public class HomeOpenCommand extends ArgCommand {
    @Override
    protected ArgSpec getSpec() {
        return HomeOpenSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);

        String bed = getSwitchVal("bed");
        int index = switchToInt("index");
        Patient patient;

        if (bed == null && index == -1) {
            throw new DukeException("You must provide an identifier for a patient!");
        } else if (bed != null && index != -1) {
            throw new DukeException("Please provide only 1 identifier for the patient you are looking for!");
        } else if (bed != null) {
            patient = core.patientMap.getPatient(bed);
        } else {
            // TODO: Law of demeter
            try {
                PatientCard card = (PatientCard) core.ui.getCardList().get(index - 1);
                patient = card.getPatient();
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("No such patient in the list.");
            }
        }

        core.uiContext.setContext(Context.PATIENT, patient);
        core.ui.print("Accessing details of Bed " + patient.getBedNo());
    }
}
