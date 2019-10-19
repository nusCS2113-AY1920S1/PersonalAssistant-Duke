package UserCode.Actions;

import Farmio.Farmio;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class BuySeedAction extends Action {

    public BuySeedAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.buySeeds;
    }

    @Override
    public void execute(Ui ui) {
        Storage storage = farmio.getStorage();
        try {
            if (farmer.getMoney() < 101) {
                farmio.getFarmer().setfailetask();
                Simulation.animate(ui, storage, farmio.getFarmer(), "ErrorInExecution", 0);
                ui.typeWriter("Error! you have attempted to buy seeds despite not having enough money\n");
                throw new FarmioException("Task Error!");
            }
            farmer.getWheatFarm().buySeeds();
            farmer.changeMoney(-100);
            Simulation.animate(ui, storage, farmio.getFarmer(),"BuySeedSimulation", 0, 4);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
