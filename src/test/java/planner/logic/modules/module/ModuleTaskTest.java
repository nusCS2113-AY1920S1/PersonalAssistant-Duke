//@@author namiwa

package planner.logic.modules.module;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ModuleTaskTest {
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


}
