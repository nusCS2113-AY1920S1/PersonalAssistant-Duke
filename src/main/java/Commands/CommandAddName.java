package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Exceptions.FarmioException;
import FrontEnd.Ui;


public class CommandAddName extends Command{
    private String name;

    public CommandAddName(String userInput) {
        this.name = userInput.toUpperCase();
    }

    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        farmio.getFarmer().inputName(name);
        ui.registerName(name);
        ui.typeWriter("Welcome Farmer " + name + ", please press [ENTER] to begin the tutorial or enter [skip] to skip the story", false);
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }

}
