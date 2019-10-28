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
        if (name.length() <= 15 && name.length() > 0 && name.matches("[a-zA-Z0-9]+")) {
            farmio.getFarmer().inputName(name);
            ui.showName(name);
            farmio.setStage(Farmio.Stage.LEVEL_START);
        }
        else if(name.length() == 0) {
            ui.typeWriter("Provide a name.", false);
            ui.typeWriter("Enter a name:", false);
        }
        else if(name.length() > 15) {
            ui.typeWriter("Your name can have a maximum of 15 characters.", false);
            ui.typeWriter("Enter your name:", false);
        }
        else {
            ui.typeWriter("Special Characters are not allowed", false);
            ui.typeWriter("Enter your name:", false);
        }
    }

}
