package compal.logic.parser;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserManagerTest {
    private Compal d;
    private ParserManager pm;

    @BeforeEach
    public void setup() {
        d = new Compal();
        pm = new ParserManager(d, d.tasklist);
    }

    @Test
    public void iniTest() {
        assertEquals(d, pm.compal);
    }

    @Test
    public void SetStatusTest() {
        String status = "test";
        pm.setStatus(status);
        assertEquals(status, pm.status);
        assertEquals(0,pm.stage);
    }
}
