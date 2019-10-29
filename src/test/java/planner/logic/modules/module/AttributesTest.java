package planner.logic.modules.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class AttributesTest {
    private final Attributes attributes = new Attributes();

    @Test
    public void testNull() {
        assertNotNull(attributes);
    }

    @Test
    public void testIsSu() {
        assertFalse(attributes.isSu());
    }

    @Test
    public void testisFyp() {
        assertFalse(attributes.isFyp());
    }



}
