import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Level;
import Farmio.Storage;
import FrontEnd.Simulation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationTest {
    @Test
    void test() {
        try {
            Farmio farmio = new Farmio();
            Level level = new Level(farmio.getStorage().getLevel(farmio.getFarmer().getLevel()));
            farmio.setLevel(level);
            Simulation simulationTest = farmio.getSimulation();
            simulationTest.simulate("Test", 0, true);
            simulationTest.simulate("Test", 0, false);
            simulationTest.simulate("Test", 0);
            simulationTest.simulate("Test", 0, 3,true);
            simulationTest.simulate("Test", 0, 3,false);
            simulationTest.simulate("Test", 0, 3);
            simulationTest.simulate(100, "Test", 3);
            simulationTest.simulate();
            assert true;
        } catch (FarmioFatalException e) {
            assert false;
        }
    }
}
