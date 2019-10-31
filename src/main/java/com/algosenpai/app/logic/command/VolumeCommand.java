package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.parser.Parser;
import com.algosenpai.app.ui.controller.MusicController;

import java.util.ArrayList;

public class VolumeCommand extends Command {

    /**
     * Initializes command to exit program.
     * @param inputs input from user.
     */
    public VolumeCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() {
        try {
            if (Parser.isInteger(inputs.get(1))) {
                MusicController.setVolume(Double.parseDouble(inputs.get(1)));
                return "Sound level adjusted";
            }
            return "Sound level needs to be a float";
        } catch (IndexOutOfBoundsException e) {
            return "volume <sound level>";
        }
    }
}
