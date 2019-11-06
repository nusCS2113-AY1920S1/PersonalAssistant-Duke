import exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.Level;
import farmio.Storage;
import frontend.UiDummy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FrontEndTest {
    Farmio farmio;

    public FrontEndTest() {
        farmio = new Farmio();
        farmio.setUi(new UiDummy());
    }
    @Test
    void singleFullScreenSimulationTest() throws FarmioFatalException {
        UiDummy.uiTestString = "";
        farmio.getSimulation().simulate();
        assertEquals("sleepclearshow", UiDummy.uiTestString);
    }
    @Test
    void multiFullScreenSimulationTest() throws FarmioFatalException {
        UiDummy.uiTestString = "";
        farmio.getSimulation().simulate("Test", 0, 3, true);
        assertEquals("sleepclearshow".repeat(9), UiDummy.uiTestString);
    }
    @Test
    void singleSimulationTest() throws FarmioFatalException {
        UiDummy.uiTestString = "";
        farmio.setLevel(new Level(farmio.getStorage().getLevel(1.1), "tester"));
        farmio.getSimulation().simulate("Test",0);
        assertEquals("sleepclearshow", UiDummy.uiTestString);
    }
    @Test
    void multiFrameSimulationTest() throws FarmioFatalException {
        UiDummy.uiTestString = "";
        farmio.setLevel(new Level(farmio.getStorage().getLevel(1.1), "tester"));
        farmio.getSimulation().simulate("Test",0);
        assertEquals("sleepclearshow", UiDummy.uiTestString);
    }
}
