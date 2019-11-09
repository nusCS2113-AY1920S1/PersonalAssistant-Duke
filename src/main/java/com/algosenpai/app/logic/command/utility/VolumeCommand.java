package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.parser.Parser;
import com.algosenpai.app.ui.controller.MusicController;

import java.util.ArrayList;

public class VolumeCommand extends Command {

    /**
     * Initializes command to adjust volume.
     * @param inputs input from user.
     */
    public VolumeCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    /**
     * Returns message status when adjusting volume.
     * @return message status.
     */
    @Override
    public String execute() {
        try {
            if (Parser.isInteger(inputs.get(1))) {
                int soundLevel = Integer.parseInt(inputs.get(1));
                if (soundLevel < 0 || soundLevel > 100) {
                    return "Sound level is from 0 to 100";
                }
                MusicController.setVolume(Integer.parseInt(inputs.get(1)));
                return "Sound level adjusted";
            }
            return "Select a number from 0 to 100 to adjust the sound";
        } catch (IndexOutOfBoundsException e) {
            return "volume <sound level>";
        }
    }
}
