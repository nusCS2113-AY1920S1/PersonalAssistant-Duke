package planner.logic.modules.cca;

import org.junit.jupiter.api.Test;
import planner.logic.exceptions.legacy.ModInvalidTimeException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CcaTest {

    @Test
    void testToString() throws ModInvalidTimeException {
        Cca cca = new Cca("soccer","1600", "1800", "Monday");
        String expectedOutput = "[C] soccer | 16:00 - 18:00 on MONDAY";
        assertEquals(expectedOutput, cca.toString());
    }

    @Test
    void testType() throws ModInvalidTimeException {
        Cca cca = new Cca("soccer","1600", "1800", "Monday");
        assertEquals("cca", cca.type());
    }
}