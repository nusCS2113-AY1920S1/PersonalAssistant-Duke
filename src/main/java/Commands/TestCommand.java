package Commands;

import FarmioExceptions.FarmioException;
import Simulate.PlantSeedSimulation;
import UserInterfaces.Ui;

public class TestCommand extends Command {
    @Override
    public void execute() throws FarmioException {
        Ui ui = new Ui();
        new PlantSeedSimulation(ui).simulate();
    }
}
