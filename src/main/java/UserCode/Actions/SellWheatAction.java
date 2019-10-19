package UserCode.Actions;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class SellWheatAction extends Action {

    public SellWheatAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.sellWheat;
    }

    @Override
    public void execute(Ui ui) {

        try {
            farmer.getWheatFarm().buySeeds(); //TODO create wheatFarm.sellWheat()
            Simulation.animate(ui, farmio.getStorage(), farmio.getFarmer(), "SellWheat", 0, 7);
            ui.show("Selling wheat!");
        } catch (FarmioFatalException e) {
            e.printStackTrace();
        }

    }

//    public JSONObject toJSON() {
//        JSONObject obj = super.toJSON();
//        obj.put("action", "selling_wheat");
//        return obj;
//    }
}
