package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.card.ImpressionCard;
import duke.ui.context.Context;

public class PatientOpenCommand extends ArgCommand {
    @Override
    protected ArgSpec getSpec() {
        return PatientOpenSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);

        Patient patient = (Patient) core.uiContext.getObject();
        String impressionId = getSwitchVal("impression");
        int index = switchToInt("index");

        if (impressionId == null && index == -1) {
            throw new DukeException("You must provide an identifier!");
        } else if (impressionId != null && index != -1) {
            throw new DukeException("Please provide only 1 identifier for the patient you are looking for!");
        } else if (impressionId != null) {
            core.uiContext.setContext(Context.IMPRESSION, patient.getImpression(impressionId));
        } else {
            // TODO: Law of demeter
            try {
                ImpressionCard card = (ImpressionCard) core.ui.getCardList().get(index - 1);
                core.uiContext.setContext(Context.IMPRESSION, card.getImpression());
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("I cannot find this impression!");
            }
        }

        core.ui.print("Opening impression...");
    }
}
