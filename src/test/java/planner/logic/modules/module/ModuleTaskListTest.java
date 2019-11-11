//@@author namiwa

package planner.logic.modules.module;

import java.util.List;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModuleTaskListTest {
    private String modCode = "";
    private ModuleInfoDetailed moduleInfoDetailed = new ModuleInfoDetailed();
    private ModuleTask moduleTask = new ModuleTask(modCode, moduleInfoDetailed);

    private ModuleTasksList moduleTasksList = new ModuleTasksList();

    @Test
    void testNonNull() {
        assertNotNull(moduleTasksList);
        assertNotNull(moduleTasksList.getTasks());
        assertNotNull(moduleTasksList.getSetModuleTask());
    }

    @Test
    @Order(1)
    void testAdd() {
        assertTrue(moduleTasksList.getTasks().add(moduleTask));
    }

    @Test
    @Order(2)
    void testDelete() {
        moduleTasksList.getTasks().add(moduleTask);
        moduleTasksList.delete(0);
        assertEquals(0, moduleTasksList.getSize());
    }

    @Test
    void testClearAll() {
        moduleTasksList.clearAll();
        assertEquals(0, moduleTasksList.getSize());
    }

    @Test
    void testFind() {
        moduleTasksList.getTasks().add(moduleTask);
        List<ModuleTask> testList = moduleTasksList.find(modCode);
        assertNotNull(testList);
        assertEquals(1, testList.size());
    }
}
