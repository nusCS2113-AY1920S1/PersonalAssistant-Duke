//@@author namiwa

package planner.logic.modules.module;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import planner.logic.exceptions.legacy.ModException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ModuleTaskTest {
    private String modCode = "";
    private ModuleInfoDetailed moduleInfoDetailed = new ModuleInfoDetailed();
    private ModuleTask moduleTask = new ModuleTask(modCode, moduleInfoDetailed);

    @Test
    void testNonNull() {
        assertNotNull(moduleTask);
    }

    @Test
    void testGetModCode() {
        assertEquals(modCode, moduleTask.getModuleCode());
    }

    @Test
    void testModuleCredit() {
        assertThrows(NumberFormatException.class, moduleTask::getModuleCredit,
                modCode);
    }

    @Test
    void testModuleInfoDetailed() {
        assertEquals(moduleInfoDetailed, moduleTask.getModuleInfoDetailed());
    }

    @Test
    void testPrerequisites() {
        assertEquals(modCode, moduleTask.getPrerequisites());
    }

    @Test
    @Order(1)
    void testGrade() {
        assertEquals(modCode, moduleTask.getGrade());
    }

    @Test
    @Order(2)
    void testModuleCreditAfterSetting() {
        try {
            moduleTask.setGrade("A+");
            assertEquals("A+", moduleTask.getGrade());
            assertEquals(0, moduleTask.getGradeAsNumbers());
        } catch (ModException me) {
            me.printStackTrace();
        }
    }

    @Test
    void testToString() {
        assertThrows(NumberFormatException.class, () -> moduleTask.toString(), modCode);
    }

}
