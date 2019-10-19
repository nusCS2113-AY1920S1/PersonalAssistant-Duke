package UserCode.Actions;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Simulation;
import FrontEnd.Ui;
import Places.Market;

public class SellWheatAction extends Action {

    public SellWheatAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.SELL_WHEAT;
    }

    @Override
    public void execute(Ui ui) throws FarmioException, FarmioFatalException {
        if (!farmer.getWheatFarm().hasRipened() || !farmer.getLocation().equals("Market")) {
            farmio.getFarmer().setfailetask();
            new Simulation("ErrorInExecution", farmio).animate(0);
            if (!farmer.getWheatFarm().hasWheat()) {
                ui.typeWriter("Error! you have attempted to sell wheat despite not having any wheat\n");
            } else {
                ui.typeWriter("Error! you have attempted to sell wheat despite not being at the market\n");
            }
            throw new FarmioException("Task Error!");
        }
        try {
            new Simulation("SellWheatSimulation", super.farmio).animate(0, 6);
            ui.show("Selling wheat!");
            farmer.changeMoney(farmer.getWheatFarm().sell());
            new Simulation("SellWheatSimulation", farmio).delayFrame(7, 1000);
        } catch (Exception e) {
            e.getMessage();
        }
    }

//    public JSONObject toJSON() {
//        JSONObject obj = super.toJSON();
//        obj.put("action", "selling_wheat");
//        return obj;
//    }
}
