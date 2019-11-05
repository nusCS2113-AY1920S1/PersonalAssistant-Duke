package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import exceptions.FarmioException;
import frontend.Ui;


public class CommandAddName extends Command {
    private String name;

    public CommandAddName(String userInput) {
        this.name = userInput.toUpperCase();
    }

    /**
     * Adds the name that the user inputs and saves it as an in-game name.
     * @param farmio The game where its stage is set to NAME_ADD.
     * @throws FarmioFatalException if simulation file is missing.
     * @throws FarmioException if name is invalid.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        farmio.getSimulation().simulate();
        if (name.equals("MENU")) {
            ui.typeWriter("Keywords cannot be used as a character name.", false);
            ui.typeWriter("Enter your name:", false);
        } else if (name.length() <= 15 && name.length() > 0 && (name.matches("[a-zA-Z0-9]+") || name.contains("_"))) {
            farmio.getFarmer().inputName(name);
            ui.typeWriter("Welcome Farmer "
                    + name
                    + ", please press [ENTER] to begin the tutorial"
                    + " or enter [skip] to skip the story", false);
            farmio.setStage(Farmio.Stage.LEVEL_START);
        } else if (name.length() == 0) {
            ui.typeWriter("Provide a name.", false);
            ui.typeWriter("Enter a name:", false);
        } else if (name.length() > 15) {
            ui.typeWriter("Your name can have a maximum of 15 characters.", false);
            ui.typeWriter("Enter your name:", false);
        } else {
            ui.typeWriter("Special Characters are not allowed", false);
            ui.typeWriter("Enter your name:", false);
        }
    }
}