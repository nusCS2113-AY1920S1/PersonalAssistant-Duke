package planner.logic.modules.cca;

import org.junit.jupiter.api.Test;
import planner.logic.exceptions.legacy.ModInvalidTimeException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import static org.junit.jupiter.api.Assertions.assertTrue;


class CcaTest {

    @Test
    void testToString() throws ModInvalidTimeException {
        Cca cca = new Cca("soccer","1600", "1800", "Monday");
        String expectedOutput = "[C] soccer | 16:00 - 18:00 on MONDAY";
        assertTrue(expectedOutput.equals(cca.toString()));
    }

    @Test
    void testType() throws ModInvalidTimeException {
        Cca cca = new Cca("soccer","1600", "1800", "Monday");
        assertTrue(cca.type().equals("cca"));
    }
}