package duke.ui.card.factory;

import duke.data.DukeObject;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.Treatment;
import duke.ui.card.ImpressionCard;
import duke.ui.card.PatientCard;
import duke.ui.card.UiCard;

/**
 * A factory class for creating a {@link UiCard}.
 */
public class CardFactory {
    /**
     * Creates a {@link UiCard} based on the {@code object}'s type.
     *
     * @param object {@link DukeObject} object.
     * @return A {@link UiCard} object.
     */
    public static UiCard getCard(DukeObject object) {
        if (object instanceof Patient) {
            return new PatientCard((Patient) object);
        } else if (object instanceof Impression) {
            return new ImpressionCard((Impression) object, false);
        } else if (object instanceof Evidence) {
            return EvidenceCardFactory.getEvidenceCard((Evidence) object);
        } else if (object instanceof Treatment) {
            return TreatmentCardFactory.getTreatmentCard((Treatment) object);
        } else {
            return null;
        }
    }
}
