package UserCode.Actions;

import Farmio.Farmio;
import FrontEnd.Simulate;
import FrontEnd.Ui;

public class SellWheatAction extends Action {

    public SellWheatAction(Farmio farmio) {
        super(farmio);
    }

    @Override
    public void execute(Ui ui) {
        farmer.getWheatFarm().buySeeds(); //TODO create wheatFarm.sellWheat()
        new Simulate(ui, "SellWheat", 10).simulate(super.farmio);
        ui.show("Selling wheat!");
    }

//    public JSONObject toJSON() {
//        JSONObject obj = super.toJSON();
//        obj.put("action", "selling_wheat");
//        return obj;
//    }
}
