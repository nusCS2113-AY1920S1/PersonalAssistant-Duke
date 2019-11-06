package usercode.actions;

import exceptions.FarmioFatalException;
import farmio.Farmer;
import farmio.Storage;
import exceptions.FarmioException;
import frontend.Simulation;
import frontend.Ui;
import places.Market;
import javafx.util.Pair;

import java.util.ArrayList;

public class BuySeedAction extends Action {

    public BuySeedAction() {
        super(ActionType.buySeeds);
    }

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer, Simulation simulation)
            throws FarmioException, FarmioFatalException {
        ArrayList<Pair<Boolean, String>> criteriaFeedbackList = new ArrayList<>();
        criteriaFeedbackList.add(new Pair<>(farmer.getGold() < Market.PRICE_OF_SEED,
                "Error! you have attempted to buy seeds despite not having enough money"));
        criteriaFeedbackList.add(new Pair<>(!farmer.getLocation().equals("Market"),
                "Error! you have attempted to buy seeds despite not being at the market"));
        checkActionCriteria(ui, farmer, simulation, criteriaFeedbackList);
        simulation.simulate("BuySeedSimulation", 0, 9);
        farmer.getWheatFarm().buySeeds();
        farmer.spendGold(Market.PRICE_OF_SEED);
        simulation.simulate();
        ui.sleep(700);
    }
}
