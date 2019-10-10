import duke.Duke;
import duke.exceptions.DukeException;
import duke.exceptions.InputException;
import duke.items.GradedComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GradedComponentTest {

    @Test
    public void gradeCreation_variousGradedComponents_successfulCreationAndWorkingGetters() {
        GradedComponent testGradeOne = new GradedComponent("Essay", 20);
        assertEquals(testGradeOne.getWeight(), 20);
        assertEquals(testGradeOne.storeString(), "0 | Essay | 0 | 20.0 | 0.0");
        assertEquals(testGradeOne.getWeightedScoreAsString(), "----");

        GradedComponent testGradeTwo = new GradedComponent("Report", 26.7);
        assertEquals(testGradeTwo.getWeight(), 26.7);
        assertEquals(testGradeTwo.storeString(), "0 | Report | 0 | 26.7 | 0.0");
        assertEquals(testGradeTwo.getWeightedScoreAsString(), "----");

        GradedComponent testGradeThree = new GradedComponent("Assignment 4", 3.7654321);
        assertEquals(testGradeThree.getWeight(), 3.7654321);
        assertEquals(testGradeThree.storeString(), "0 | Assignment 4 | 0 | 3.7654321 | 0.0");
        assertEquals(testGradeThree.getWeightedScoreAsString(), "----");
    }

    @Test
    public void gradeUpdatePreWeighted_gradedEssayScored_successfulUpdateAndMarkedDone() {
        GradedComponent testGradeOne = new GradedComponent("Essay", 20);
        testGradeOne.updateWeightedScore(15);

        assertEquals(testGradeOne.getWeight(), 20);
        assertEquals(testGradeOne.storeString(), "1 | Essay | 1 | 20.0 | 15.0");
        assertEquals(testGradeOne.getWeightedScoreAsString(), "15");

        GradedComponent testGradeTwo = new GradedComponent("Essay", 0.657);
        testGradeTwo.updateWeightedScore(0.700);

        assertEquals(testGradeTwo.getWeight(), 0.657);
        assertEquals(testGradeTwo.storeString(), "1 | Essay | 1 | 0.657 | 0.7");
        assertEquals(testGradeTwo.getWeightedScoreAsString(), "0.7");
    }

    @Test
    public void gradeUpdateUnweighted_gradedEssayScored_successfulUpdateAndMarkedDone() throws InputException {
        GradedComponent testGradeOne = new GradedComponent("Essay", 20);
        testGradeOne.updateWeightedScore(15, 30);

        assertEquals(testGradeOne.getWeight(), 20);
        assertEquals(testGradeOne.storeString(), "1 | Essay | 1 | 20.0 | 10.0");
        assertEquals(testGradeOne.getWeightedScoreAsString(), "10");

        GradedComponent testGradeTwo = new GradedComponent("Essay", 0.657);
        testGradeTwo.updateWeightedScore(12, 15);

        assertEquals(testGradeTwo.getWeight(), 0.657);
        assertEquals(testGradeTwo.getWeightedScoreAsString(), "0.53");
    }

    @Test
    public void gradeUpdateUnweighted_gradedEssayScoredZeroMaximumScore_ExceptionThrown() throws InputException {
        GradedComponent testGradeOne = new GradedComponent("Essay", 20);
        assertThrows(DukeException.class, () -> testGradeOne.updateWeightedScore(15, 0));

        assertEquals(testGradeOne.getWeight(), 20);
        assertEquals(testGradeOne.storeString(), "0 | Essay | 0 | 20.0 | 0.0");
        assertEquals(testGradeOne.getWeightedScoreAsString(), "----");
    }
}
