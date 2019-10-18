package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Farmio;
import Farmio.Level;
import Exceptions.FarmioException;
import FrontEnd.Simulation;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class CommandGameNew extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        farmio.setFarmer(new Farmer());
        farmio.setLevel(new Level(farmio.getStorage().getLevel(1), farmio.getFarmer()));
        new Simulation("GameNew", farmio.getUi()).animate(0);
        farmio.setStage(Farmio.Stage.TASK_ADD);
    }
}
