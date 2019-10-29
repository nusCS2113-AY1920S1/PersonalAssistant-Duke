package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.Menu;
import frontend.Ui;

public class CommandMenuInGame extends Command {
    private String input;
    private double level;
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
        level = farmio.getFarmer().getLevel();
        Ui ui = farmio.getUi();

        if(input.equals("action")) {
            if(level == 1.1) {
                farmio.getSimulation().simulate("ActionList", 1,true);
            }
            if(level == 1.2) {
                farmio.getSimulation().simulate("ActionList", 1,true);
            }
            if(level == 1.3) {
                farmio.getSimulation().simulate("ActionList", 1,true);
            }
            if(level == 1.4) {
                farmio.getSimulation().simulate("ActionList", 1,true);
            }
            if(level == 1.5) {
                farmio.getSimulation().simulate("ActionList", 1,true);
            }
            if(level == 1.6) {
                farmio.getSimulation().simulate("ActionList", 1,true);
            }
        }
        if(input.equals("condition")) {
            if(level == 1.1) {
                farmio.getSimulation().simulate("ConditionList", 1,true);
            }
            if(level == 1.2) {
                farmio.getSimulation().simulate("ConditionList", 1,true);
            }
            if(level == 1.3) {
                farmio.getSimulation().simulate("ConditionList", 1,true);
            }
            if(level == 1.4) {
                farmio.getSimulation().simulate("ConditionList", 1,true);
            }
            if(level == 1.5) {
                farmio.getSimulation().simulate("ConditionList", 1,true);
            }
            if(level == 1.6) {
                farmio.getSimulation().simulate("ConditionList", 1,true);
            }
        }

        if(input.equals("market")) {
            if(level < 2) {
                farmio.getSimulation().simulate("MarketList", 1,true);
            }
            if(level < 3) {
                farmio.getSimulation().simulate("MarketList", 2,true);
            }
            if(level < 4) {
                farmio.getSimulation().simulate("MarketList", 3,true);
            }
        }
        if(input.equals("")) {
            Menu.show(farmio, true);
        }
        farmio.setStage(Farmio.Stage.MENU);
    }
}