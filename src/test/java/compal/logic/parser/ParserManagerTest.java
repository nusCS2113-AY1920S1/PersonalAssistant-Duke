package compal.logic.parser;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserManagerTest {
    private Compal compal;
    private ParserManager pm;

    @BeforeEach
    public void setup() {
        compal = new Compal();
        pm = new ParserManager(compal, compal.tasklist);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, pm.compal);
    }

    @Test
    public void setStatusTest() {
        String status = "test";
        pm.setStatus(status);
        assertEquals(status, pm.status);
        assertEquals(0,pm.stage);
    }
}
