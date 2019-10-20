package UserCode.Actions;

import Farmio.Farmer;
import FrontEnd.Ui;
import Farmio.Storage;

public class BuyWheatAction extends Action {
    int moneyChange = -100;

    public BuyWheatAction() {
//        type = ActionType.BUY_WHEAT;
    }

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer) {
        farmer.getWheatFarm().buySeeds();
        farmer.setMoney(100);
        //I'll write the simulation stuff after the simulation class is fixed
        ui.show("Buying seeds!");
    }
}
