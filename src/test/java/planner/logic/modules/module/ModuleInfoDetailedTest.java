//@@author namiwa

package planner.logic.modules.module;

import static org.junit.jupiter.api.Assertions.assertEquals;

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


}
