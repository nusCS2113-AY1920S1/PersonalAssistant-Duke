package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.Menu;
import frontend.Ui;

public class CommandMenuInGame extends Command {
    private String input;

    public CommandMenuInGame(String userInput) {
        this.input = userInput;
    }


    /**
     * Shows the menu.
     * @param farmio the game which stage is set as MENU.
     * @throws FarmioFatalException if simulation file is missing.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        double level = farmio.getFarmer().getLevel();
        Ui ui = farmio.getUi();

        if(input.equals("actions")) {
            if(level == 1.1) {
                farmio.getSimulation().simulate("ActionList", 11,true);
            }
            if(level == 1.2) {
                farmio.getSimulation().simulate("ActionList", 12,true);
            }
            if(level == 1.3) {
                farmio.getSimulation().simulate("ActionList", 13,true);
            }
            if(level == 1.4) {
                farmio.getSimulation().simulate("ActionList", 14,true);
            }
            if(level == 1.5) {
                farmio.getSimulation().simulate("ActionList", 15,true);
            }
            if(level == 1.6) {
                farmio.getSimulation().simulate("ActionList", 15,true);
            }
        }
        if(input.equals("conditions")) {
            if(level == 1.1) {
                farmio.getSimulation().simulate("ConditionList", 11,true);
            }
            if(level == 1.2) {
                farmio.getSimulation().simulate("ConditionList", 11,true);
            }
            if(level == 1.3) {
                farmio.getSimulation().simulate("ConditionList", 11,true);
            }
            if(level == 1.4) {
                farmio.getSimulation().simulate("ConditionList", 14,true);
            }
            if(level == 1.5) {
                farmio.getSimulation().simulate("ConditionList", 16,true);
            }
            if(level == 1.6) {
                farmio.getSimulation().simulate("ConditionList", 16,true);
            }
        }

        if(input.equals("market")) {
            if(level < 1.5) {
                farmio.getSimulation().simulate("MarketList", 4,true);
            } else if(level < 2) {
                farmio.getSimulation().simulate("MarketList", 1, true);
            } else if(level < 3) {
                farmio.getSimulation().simulate("MarketList", 2,true);
            } else if(level < 4) {
                farmio.getSimulation().simulate("MarketList", 3,true);
            }
        }
        if(input.equals("")) {
            Menu.show(farmio, true);
        }
        farmio.setStage(Farmio.Stage.MENU);
    }
}