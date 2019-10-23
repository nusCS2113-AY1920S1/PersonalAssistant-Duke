package UserCode.Actions;

import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Simulation;
import FrontEnd.Ui;
import Places.Market;

public class BuySeedAction extends Action {

    public BuySeedAction() {
        super(ActionType.buySeeds);
    }

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer, Simulation simulation) throws FarmioException, FarmioFatalException {
        if (farmer.getMoney() < Market.PRICE_OF_SEED || !farmer.getLocation().equals("Market")) {
            farmer.setTaskFailed();
            simulation.animate("ErrorInExecution", 0, false);
            if (!farmer.getLocation().equals("Market")) {
                ui.typeWriter("Error! you have attempted to buy seeds despite not being at the market/\n");
            } else {
                ui.typeWriter("Error! you have attempted to buy seeds despite not having enough money/\n");
            }
            throw new FarmioException("Task Error!");
        }
        simulation.animate("BuySeedSimulation", 0, 4);
        farmer.getWheatFarm().buySeeds();
        farmer.spendMoney(Market.PRICE_OF_SEED);
        simulation.animate(1000, "BuySeedSimulation", 5);
    }
}
