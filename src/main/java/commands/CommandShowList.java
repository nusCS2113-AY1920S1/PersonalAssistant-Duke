package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.Menu;
import frontend.Ui;

public class CommandShowList extends Command {
    private String filePath;
    public CommandShowList(String listPath) {
        filePath = listPath;
    }

    /**
     * Shows a list from a file to the user.
     * @param farmio the game where the level is extracted to show only the relevant actions.
     * @throws FarmioFatalException if Simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        double level = farmio.getFarmer().getLevel();

        if(filePath.equals("ActionList")) {
            if(level == 1.1) {
                farmio.getSimulation().simulate(filePath, 11,false);
            }
            if(level == 1.2) {
                farmio.getSimulation().simulate(filePath, 12,false);
            }
            if(level == 1.3) {
                farmio.getSimulation().simulate(filePath, 13,false);
            }
            if(level == 1.4) {
                farmio.getSimulation().simulate(filePath, 14,false);
            }
            if(level == 1.5) {
                farmio.getSimulation().simulate(filePath, 15,false);
            }
            if(level == 1.6) {
                farmio.getSimulation().simulate(filePath, 15,false);
            }
        }

        if(filePath.equals("ConditionList")) {
            if(level == 1.1) {
                farmio.getSimulation().simulate(filePath, 11,false);
            }
            if(level == 1.2) {
                farmio.getSimulation().simulate(filePath, 11,false);
            }
            if(level == 1.3) {
                farmio.getSimulation().simulate(filePath, 11,false);
            }
            if(level == 1.4) {
                farmio.getSimulation().simulate(filePath, 14,false);
            }
            if(level == 1.5) {
                farmio.getSimulation().simulate(filePath, 16,false);
            }
            if(level == 1.6) {
                farmio.getSimulation().simulate(filePath, 16,false);
            }
        }

        if(filePath.equals("MarketList")) {
            if(level < 1.5) {
                farmio.getSimulation().simulate(filePath, 4,false);
            } else if(level < 2) {
                farmio.getSimulation().simulate(filePath, 1, false);
            } else if(level < 3) {
                farmio.getSimulation().simulate(filePath, 2,false);
            } else if(level < 4) {
                farmio.getSimulation().simulate(filePath, 3,false);
            }
        }

        ui.show("Press [Enter] to go back to game");
    }

}