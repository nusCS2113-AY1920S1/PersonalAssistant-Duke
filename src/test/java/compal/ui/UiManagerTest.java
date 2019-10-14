package compal.ui;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;

public class UiManagerTest {
    private UiManager uiManager;
    private Compal compal;

    @BeforeEach
    public void setUp() {
        compal = new Compal();
        uiManager = new UiManager(compal);
    }
}
