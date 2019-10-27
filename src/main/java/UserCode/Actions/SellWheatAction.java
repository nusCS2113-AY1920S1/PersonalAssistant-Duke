package UserCode.Actions;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Storage;
import FrontEnd.Simulation;
import FrontEnd.Ui;
import Places.Market;
import javafx.util.Pair;

import java.util.ArrayList;

public class SellWheatAction extends Action {

    public SellWheatAction() {
        super(ActionType.sellGrain);
    }

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer, Simulation simulation) throws FarmioFatalException, FarmioException {
        ArrayList<Pair<Boolean, String>> criteriaFeedbackList = new ArrayList<>();
        criteriaFeedbackList.add(new Pair<>(!farmer.getWheatFarm().hasGrain(), "Error! you have attempted to sell grain despite not having any grain"));
        criteriaFeedbackList.add(new Pair<>(!farmer.getLocation().equals("Market"), "Error! you have attempted to sell grain despite not being at the market"));
        checkActionCriteria(ui, farmer, simulation, criteriaFeedbackList);
        simulation.simulate("SellWheatSimulation", 0, 6);
        ui.typeWriter("Selling grain!", false);
        ui.sleep(1000);
        farmer.earnMoney(farmer.getWheatFarm().sell());
        simulation.simulate(1000, "SellWheatSimulation", 7);
    }
}