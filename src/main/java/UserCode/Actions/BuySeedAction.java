package UserCode.Actions;

import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Simulation;
import FrontEnd.Ui;
import Places.Market;
import javafx.util.Pair;

import java.util.ArrayList;

public class BuySeedAction extends Action {

    public BuySeedAction() {
        super(ActionType.buySeeds);
    }

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer, Simulation simulation) throws FarmioException, FarmioFatalException {
        ArrayList<Pair<Boolean, String>> criteriaFeedbackList = new ArrayList<>();
        criteriaFeedbackList.add(new Pair<>(farmer.getGold() < Market.PRICE_OF_SEED, "Error! you have attempted to buy seeds despite not having enough money"));
        criteriaFeedbackList.add(new Pair<>(!farmer.getLocation().equals("Market"), "Error! you have attempted to buy seeds despite not being at the market"));
        checkActionCriteria(ui, farmer, simulation, criteriaFeedbackList);
        simulation.simulate("BuySeedSimulation", 0, 4);
        farmer.getWheatFarm().buySeeds();
        farmer.spendGold(Market.PRICE_OF_SEED);
        simulation.simulate(1000, "BuySeedSimulation", 5);
    }
}
