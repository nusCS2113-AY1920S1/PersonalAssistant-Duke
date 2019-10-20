package UserCode.Actions;

import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class BuySeedAction extends Action {

    public BuySeedAction() {
        this.type = ActionType.buySeeds;
    }

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer) throws FarmioFatalException {
        try {
            if (farmer.getMoney() < 101) {
                farmer.setTaskFailed();
                Simulation.animate(ui, storage, farmer, "ErrorInExecution", 0);
                ui.typeWriter("Error! you have attempted to buy seeds despite not having enough money\n");
                throw new FarmioException("Task Error!");
            }
            farmer.getWheatFarm().buySeeds();
            farmer.spentMoney(100);
            Simulation.animate(ui, storage, farmer,"BuySeedSimulation", 0, 4);
        } catch (FarmioException e) {
            e.getMessage();
        }
    }
}
