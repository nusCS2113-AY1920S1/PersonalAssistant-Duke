package duke.ui.card.factory;

import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Plan;
import duke.data.Treatment;
import duke.ui.card.InvestigationCard;
import duke.ui.card.MedicineCard;
import duke.ui.card.PlanCard;
import duke.ui.card.TreatmentCard;

/**
 * A factory class for creating a {@link TreatmentCard}.
 */
public class TreatmentCardFactory {
    /**
     * Creates a {@link TreatmentCard} based on the {@code treatment}'s type.
     *
     * @param treatment {@link Treatment} object.
     * @return A {@link TreatmentCard} object.
     */
    public static TreatmentCard getTreatmentCard(Treatment treatment) {
        if (treatment instanceof Investigation) {
            return new InvestigationCard((Investigation) treatment);
        } else if (treatment instanceof Medicine) {
            return new MedicineCard((Medicine) treatment);
        } else if (treatment instanceof Plan) {
            return new PlanCard((Plan) treatment);
        } else {
            return null;
        }
    }
}
