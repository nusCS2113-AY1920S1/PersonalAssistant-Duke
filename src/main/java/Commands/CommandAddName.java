package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Farmio;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Ui;


public class CommandAddName extends Command{
    private String name;

    public CommandAddName(String userInput) {
        this.name = userInput;
    }

    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        farmio.getFarmer().inputName(name);
        ui.showName(name);
      //System.out.println(farmio.getFarmer().getName());
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }

}
