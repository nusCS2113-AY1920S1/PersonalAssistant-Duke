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
        this.type = ActionType.buySeeds;
    }

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer) throws FarmioException, FarmioFatalException {
        if (farmer.getMoney() < Market.PRICE_OF_SEED || !farmer.getLocation().equals("Market")) {
            farmer.setTaskFailed();
            Simulation.animate(ui, storage, farmer, "ErrorInExecution", 0);
            if (!farmer.getLocation().equals("Market")) {
                ui.typeWriter("Error! you have attempted to buy seeds despite not being at the market\n");
            } else {
                ui.typeWriter("Error! you have attempted to buy seeds despite not having enough money\n");
            }
            throw new FarmioException("Task Error!");
        }
        Simulation.animate(ui, storage, farmer,"BuySeedSimulation", 0, 4);
        farmer.getWheatFarm().buySeeds();
        farmer.spendMoney(Market.PRICE_OF_SEED);
        Simulation.animate(ui, storage, farmer, 1000, "BuySeedSimulation", 5);
    }
}
