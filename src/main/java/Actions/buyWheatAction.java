package Actions;

import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import Farmio.Ui;

public class buyWheatAction extends Action {
    int moneyChange = -100;

    public buyWheatAction(WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm) {
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
    }

    @Override
    public int execute(Ui ui) {
        wheatFarm.buySeeds();
        //I'll write the simulation stuff after the simulation class is fixed
        ui.show("Buying seeds!");
        return moneyChange;
    }
}
