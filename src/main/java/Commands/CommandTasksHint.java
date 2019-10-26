package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Ui;

public class CommandTasksHint extends Command {
    private String message;
    public CommandTasksHint(String uiMessage) {
        message = uiMessage;
    }
    public CommandTasksHint() {
        message = "Enter [Start] when you are ready to complete the objective";
    }
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        farmio.getSimulation().animate(farmio.getLevel().getPath(), farmio.getLevel().getNarratives().size() - 1);
        ui.typeWriter(farmio.getLevel().getHint() +"/");
        ui.typeWriter(message + "/");
    }
}