package duke.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FixedTest {

    @Test
    void testToString() {
        Fixed fixed = new Fixed("Homework", 5, 30);
        assertEquals(fixed.toString(), "[F][✘] Homework (needs 5 hours 30 mins )");
    }
}
