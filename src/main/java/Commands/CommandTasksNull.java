package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Ui;

public class CommandTasksNull extends Command {
    private String message;
    public CommandTasksNull(String uiMessage) {
        message = uiMessage;
    }
    public CommandTasksNull() {
        message = "Enter [Start] when you are ready to complete the objective";
    }
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        farmio.getSimulation().animate(farmio.getLevel().getPath(), farmio.getLevel().getNarratives().size());
        ui.show(message);
    }
}