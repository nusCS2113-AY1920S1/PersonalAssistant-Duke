package UserCode.Actions;

import Farmio.Farmio;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import FrontEnd.Ui;
import Farmio.Farmer;

public class buyWheatAction extends Action {
    int moneyChange = -100;

    public buyWheatAction(Farmio farmio) {
        super(farmio);
    }

    @Override
    public void execute(Ui ui) {
        farmer.getWheatFarm().buySeeds();
        //I'll write the simulation stuff after the simulation class is fixed
        ui.show("Buying seeds!");
    }
}
