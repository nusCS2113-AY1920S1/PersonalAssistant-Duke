package optix.commons.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShowTest {
    private Show show;

    @BeforeEach
    void init() {
        show = new Show("Test Show", 20);
    }

    @Test
    void testHasSameName() {
        assertTrue(show.hasSameName("test show"));
        assertFalse(show.hasSameName("testshow"));
    }
}