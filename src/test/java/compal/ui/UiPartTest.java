package compal.ui;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;

public class UiPartTest {
    private UiPart uiPart;
    private Compal compal;

    @BeforeEach
    public void setUp() {
        compal = new Compal();
        uiPart = new UiPart(compal, compal.tasklist.arrlist);
    }
}
