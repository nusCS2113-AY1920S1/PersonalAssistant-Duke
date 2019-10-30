// @@author namiwa

package planner.logic.modules.module;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ExamInfoTest {
    private final ExamInfo examInfo = new ExamInfo();

    @Test
    public void testSemester() {
        int expected = 0;
        assertEquals(expected, examInfo.getSemester());
    }

    @Test
    public void testExamData() {
        final String expected = "";
        assertEquals(expected, examInfo.getExamDate());
    }

    @Test
    public void testExamDuration() {
        int expected = 0;
        assertEquals(expected, examInfo.getExamDuration());
    }

    @Test
    public void testString() {
        final String expected = "{Semester:0,,0}";
    }
}
