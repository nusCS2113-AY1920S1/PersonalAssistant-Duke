//@@author namiwa

package planner.logic.modules.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ModuleInfoDetailedTest {

    private final ModuleInfoDetailed modDetailed = new ModuleInfoDetailed();
    final String expected = "";

    @Test
    public void testModuleCode() {
        assertEquals(expected, modDetailed.getModuleCode());
    }

    @Test
    public void testTitle() {
        assertEquals(expected, modDetailed.getTitle());
    }

    @Test
    public void testDescription() {
        assertEquals(expected, modDetailed.getDescription());
    }

    @Test
    public void testModuleLevel() {
        assertEquals(expected, modDetailed.getModuleLevel());
    }

    @Test
    public void testDepartment() {
        assertEquals(expected, modDetailed.getDepartment());
    }

    @Test
    public void testFaculty() {
        assertEquals(expected, modDetailed.getFaculty());
    }

    @Test
    public void testPreclusion() {
        assertEquals(expected, modDetailed.getPreclusion());
    }

    @Test
    public void testPrerequisites() {
        assertEquals(expected, modDetailed.getPrerequisites());
    }

    @Test
    public void testAttributes() {
        assertNotNull(modDetailed.getAttributes());
    }

    @Test
    public void testGrade() {
        assertEquals(expected, modDetailed.getGrade());
    }

    @Test
    public void testSemesterData() {
        assertNotNull(modDetailed.getSemesterData());
    }

    @Test
    public void testString() {
        modDetailed.setModuleCredit("B");
        String expectedString = "Module Code:, MC:, SU:, grade:";
        assertThrows(NumberFormatException.class,
                modDetailed::toString,
                expectedString);
    }
}

