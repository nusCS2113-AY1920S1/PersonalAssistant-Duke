import exceptions.FarmioFatalException;
import farmio.Farmer;
import farmio.Farmio;
import farmio.Level;
import farmio.Storage;
import frontend.Ui;
import frontend.UiDummy;
import org.junit.jupiter.api.Test;
import places.Farm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FrontEndTest {
    Farmio farmio;

    public FrontEndTest() throws FarmioFatalException{
        farmio = new Farmio();
        farmio.setUi(new UiDummy());
        farmio.setLevel(new Level(farmio.getStorage().getLevel(1.1), "tester"));
    }
    @Test
    void singleFullScreenSimulationTest() throws FarmioFatalException {
        farmio.getSimulation().simulate();
        assertEquals("sleepclearshow", UiDummy.uiTestString);
    }
    @Test
    void multiFullScreenSimulationTest() throws FarmioFatalException {
        UiDummy.uiTestString = "";
        farmio.getSimulation().simulate("Test", 0, 3, true);
        assertEquals("sleepclearshow".repeat(4), UiDummy.uiTestString);
    }
    @Test
    void singleSimulationTest() throws FarmioFatalException {
        UiDummy.uiTestString = "";
        farmio.getSimulation().simulate("Test",0);
        assertEquals("sleepclearshow", UiDummy.uiTestString);
    }
    @Test
    void multiFrameSimulationTest() throws FarmioFatalException {
        UiDummy.uiTestString = "";
        farmio.getSimulation().simulate("Test", 0, 3, true);
        assertEquals("sleepclearshow".repeat(4), UiDummy.uiTestString);
    }
    @Test
    void multiFrameReverseSimulationTest() throws FarmioFatalException {
        UiDummy.uiTestString = "";
        farmio.getSimulation().simulate("Test", 3, 0, true);
        assertEquals("sleepclearshow".repeat(4), UiDummy.uiTestString);
    }
    @Test
    void NarrativeSimulationTest() throws FarmioFatalException {
        double levelId = 1.1;
        while (levelId != 0) {
            UiDummy.uiTestString = "";
            farmio.setLevel(new Level(farmio.getStorage().getLevel(levelId), "tester"));
            Level level = farmio.getLevel();
            Ui ui = farmio.getUi();
            ui.showNarrative(level, farmio.getSimulation());
            assert ("inputsleepclearshowtypewriter".repeat(level.getNarratives().size())
                    + "levelBegin").equals(UiDummy.uiTestString);
            levelId = farmio.getFarmer().nextLevel();
        }
    }
    @Test
    void warningTest() {
        UiDummy.uiTestString = "";
        farmio.getUi().showWarning("");
        assertEquals("warning", UiDummy.uiTestString);
    }
    @Test
    void errorTest() {
        UiDummy.uiTestString = "";
        farmio.getUi().showError("");
        assertEquals("error", UiDummy.uiTestString);
    }
    @Test
    void infoTest() {
        UiDummy.uiTestString = "";
        farmio.getUi().showInfo("");
        assertEquals("info", UiDummy.uiTestString);
    }

    @Test
    void hintTest() {
        UiDummy.uiTestString = "";
        farmio.getUi().showHint("");
        assertEquals("hint", UiDummy.uiTestString);
    }

    @Test
    void exitTest() {
        UiDummy.uiTestString = "";
        farmio.getUi().showExit();
        assertEquals("exit", UiDummy.uiTestString);
    }
}
