package UserCode.Actions;

import Farmio.Farmer;
import Farmio.Ui;

public class buyWheatAction extends Action {
    int moneyChange = -100;

    public buyWheatAction(Farmer farmer) {
        super(farmer);
    }

    @Override
    public void execute(Ui ui) {
        farmer.getWheatFarm().buySeeds();
        //I'll write the simulation stuff after the simulation class is fixed
        ui.show("Buying seeds!");
    }
}
