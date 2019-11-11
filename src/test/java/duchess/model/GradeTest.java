package duchess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GradeTest {
    private final Grade completeGrade = new Grade("midterm", 15, 30, 20);

    private final Grade incompleteGrade = new Grade("finals", 30);

    @Test
    public void complete_constructor() {
        assertEquals("midterm 15.0/30.0 20.0%", completeGrade.toString());
        assertEquals(15.0, completeGrade.getMarks());
        assertEquals(30.0, completeGrade.getMaxMarks());
        assertEquals(20.0, completeGrade.getWeightage());
        assertEquals(10.0, completeGrade.getModulePercentage());
    }

    @Test
    public void incomplete_constructor() {
        assertEquals("finals 30.0%", incompleteGrade.toString());
        assertEquals(30.0, incompleteGrade.getWeightage());
    }

    @Test
    public void getIsComplete_completeGrade_returnsTrue() {
        assertTrue(completeGrade.getIsComplete());
    }

    @Test
    public void getIsComplete_incompleteGrade_returnsFalse() {
        assertFalse(incompleteGrade.getIsComplete());
    }
}
