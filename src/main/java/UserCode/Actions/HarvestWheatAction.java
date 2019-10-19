package UserCode.Actions;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class HarvestWheatAction extends Action {

    public HarvestWheatAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.HARVEST_WHEAT;
    }

    /*public HarvestWheatAction(JSONObject obj){
        super(obj);
    }*/

    @Override
    public void execute(Ui ui) throws FarmioException, FarmioFatalException {
        if (!farmer.getWheatFarm().hasWheat() || !farmer.getLocation().equals("WheatFarm")) {
            farmio.getFarmer().setfailetask();
            new Simulation("ErrorInExecution", farmio).animate(0);
            if (!farmer.getWheatFarm().hasWheat()) {
                ui.typeWriter("Error! you have attempted to harvest wheat despite not having any seedlings\n");
            } else {
                ui.typeWriter("Error! you have attempted to harvest wheat despite not being at the Wheatfarm\n");
            }
            throw new FarmioException("Task Error!");
        }
        try {
            new Simulation("HarvestWheatSimulation", super.farmio).animate(0, 8);
            farmer.getWheatFarm().harvestWheat();
            new Simulation("HarvestWheatSimulation", super.farmio).delayFrame(9, 1000);
        } catch (Exception e){
            e.getMessage();
        }
    }

//    public JSONObject toJSON() {
//        JSONObject obj = super.toJSON();
//        obj.put("action", "harvest_wheat");
//        return obj;
//    }
}
