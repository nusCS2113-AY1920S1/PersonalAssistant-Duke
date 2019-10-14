package compal.ui;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;

public class DailyCalUiTest {
    private DailyCalUI dailyCalUI;
    private Compal compal;

    @BeforeEach
    public void setUp() {
        compal = new Compal();
        dailyCalUI = new DailyCalUI(compal);
    }
}
