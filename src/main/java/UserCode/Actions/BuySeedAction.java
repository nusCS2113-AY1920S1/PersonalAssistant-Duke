package UserCode.Actions;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Simulation;
import FrontEnd.Ui;
import Places.Market;

public class BuySeedAction extends Action {

    public BuySeedAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.BUY_SEEDS;
    }

    @Override
    public void execute(Ui ui) throws FarmioException, FarmioFatalException {
        Simulation ErrorSimulation = new Simulation("ErrorInExecution", farmio);
        Simulation BuySeedSimulation = new Simulation("BuySeedSimulation", farmio);
        if (farmer.getMoney() < Market.PRICE_OF_SEED || !farmer.getLocation().equals("Market")) {
            farmio.getFarmer().setfailetask();
            ErrorSimulation.animate(0);
            if (!farmer.getLocation().equals("Market")) {
                ui.typeWriter("Error! you have attempted to buy seeds despite not being at the market\n");
            } else {
                ui.typeWriter("Error! you have attempted to buy seeds despite not having enough money\n");
            }
            throw new FarmioException("Task Error!");
        }
        try {
            BuySeedSimulation.animate(0, 4);
            farmer.getWheatFarm().buySeeds();
            farmer.changeMoney(-Market.PRICE_OF_SEED);
            BuySeedSimulation.delayFrame(5, 1000);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
