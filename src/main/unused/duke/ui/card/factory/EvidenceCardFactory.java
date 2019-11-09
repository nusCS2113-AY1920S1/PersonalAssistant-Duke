package duke.ui.card.factory;

import duke.data.Evidence;
import duke.data.Observation;
import duke.data.Result;
import duke.ui.card.EvidenceCard;
import duke.ui.card.ObservationCard;
import duke.ui.card.ResultCard;

/**
 * A factory class for creating an {@link EvidenceCard}.
 */
public class EvidenceCardFactory {
    /**
     * Creates an {@link EvidenceCard} based on the {@code evidence}'s type.
     *
     * @param evidence {@link Evidence} object.
     * @return A {@link EvidenceCard} object.
     */
    public static EvidenceCard getEvidenceCard(Evidence evidence) {
        if (evidence instanceof Observation) {
            return new ObservationCard((Observation) evidence);
        } else if (evidence instanceof Result) {
            return new ResultCard((Result) evidence);
        } else {
            return null;
        }
    }
}
