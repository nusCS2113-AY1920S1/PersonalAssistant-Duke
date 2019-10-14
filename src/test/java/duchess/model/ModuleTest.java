package duchess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModuleTest {
    private final Module module = new Module(
            "CS1231",
            "Discrete Mathematics"
    );

    @Test
    public void constructor() {
        assertEquals(module.toString(), "CS1231 Discrete Mathematics");
    }

    @Test
    public void getName_getsName() {
        assertEquals(module.getName(), "Discrete Mathematics");
    }

    @Test
    public void getCode_getsCode() {
        assertEquals(module.getCode(), "CS1231");
    }

    @Test
    public void isOfCode_sameCode_returnsTrue() {
        assertTrue(module.isOfCode("cs1231"));
    }

    @Test
    public void isOfCode_differentCode_returnsFalse() {
        assertFalse(module.isOfCode("CS2040"));
    }

    @Test
    public void equals_sameCode_returnsTrue() {
        Module other = new Module("CS1231", "Does not matter");
        assertTrue(module.equals(other));
    }

    @Test
    public void equals_differentCode_returnsFalse() {
        Module other = new Module("CS1232", "Discrete Mathematics");
        assertFalse(module.equals(other));
    }
}
