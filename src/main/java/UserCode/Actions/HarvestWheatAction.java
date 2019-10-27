package UserCode.Actions;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Storage;
import FrontEnd.Simulation;
import FrontEnd.Ui;
import javafx.util.Pair;

import java.util.ArrayList;

public class HarvestWheatAction extends Action {

    public HarvestWheatAction() {
        super(ActionType.harvestWheat);
    }

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer, Simulation simulation) throws FarmioFatalException, FarmioException {
        ArrayList<Pair<Boolean, String>> criteriaFeedbackList = new ArrayList<>();
        criteriaFeedbackList.add(new Pair<>(!farmer.getWheatFarm().hasWheat(), "Error! you have attempted to harvest wheat despite not having any wheat"));
        criteriaFeedbackList.add(new Pair<>(!farmer.getLocation().equals("WheatFarm"), "Error! you have attempted to harvest wheat despite not being at the Wheatfarm"));
        checkActionCriteria(ui, farmer, simulation, criteriaFeedbackList);
        simulation.simulate("HarvestWheatSimulation", 0, 8);
        farmer.getWheatFarm().harvestWheat();
        simulation.simulate(1000,"HarvestWheatSimulation", 9);
    }
}