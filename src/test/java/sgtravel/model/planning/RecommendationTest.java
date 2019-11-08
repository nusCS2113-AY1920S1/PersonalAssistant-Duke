package sgtravel.model.planning;

import org.junit.jupiter.api.Test;
import sgtravel.ModelStub;
import sgtravel.commons.exceptions.FileLoadFailException;
import sgtravel.commons.exceptions.RecommendationFailException;
import sgtravel.model.Model;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RecommendationTest {

    @Test
    void testMakeItinerary() throws FileLoadFailException {
        Model model = new ModelStub();
        Recommendation recommendation = model.getRecommendations();
        String[] itineraryDetails = {"itinerary ", "23/04/28", "24/05/28"};
        assertThrows(RecommendationFailException.class, () -> { recommendation.makeItinerary(itineraryDetails); });
    }
}
