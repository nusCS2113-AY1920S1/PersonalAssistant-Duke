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

public class PlantSeedAction extends Action {

    public PlantSeedAction() {
        super(ActionType.plantSeeds);
    }

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer, Simulation simulation) throws FarmioFatalException, FarmioException {
        ArrayList<Pair<Boolean, String>> criteriaFeedbackList = new ArrayList<>();
        criteriaFeedbackList.add(new Pair<>(!farmer.getWheatFarm().hasSeeds(), "Error! you have attempted to plant seeds despite not having any seeds"));
        criteriaFeedbackList.add(new Pair<>(!farmer.getLocation().equals("WheatFarm"), "Error! you have attempted to plant seeds despite not being at the Wheatfarm"));
        checkActionCriteria(ui, farmer, simulation, criteriaFeedbackList);
        simulation.simulate("PlantSeedSimulation", 0, 10);
        farmer.getWheatFarm().plantSeeds();
        simulation.simulate(1000, "PlantSeedSimulation", 11);
    }
}