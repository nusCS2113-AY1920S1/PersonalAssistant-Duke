package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;

import java.util.ArrayList;

public class SaveCommand extends Command {

    private UserStats userStats;

    private static final String ERROR_MESSAGE = "Please enter in the format:\n save <filename>.txt";

    /**
     * Create new command.
     * @param inputs input from user.
     */
    public SaveCommand(ArrayList<String> inputs, UserStats userStats) {
        super(inputs);
        this.userStats = userStats;
    }

    @Override
    public String execute() {
        // If user does not specify a filename, the data is saved to the default filename "UserData.txt"
        String fileName = "UserData.txt";

        if (inputs.size() > 1) {
            if (isValidFilename(inputs.get(1))) {
                fileName = inputs.get(1);
            } else {
                return ERROR_MESSAGE;
            }
        }

        Storage.saveData(fileName,userStats.toString());
        return "Your data is saved to " + fileName + "!";
    }

    private boolean isValidFilename(String filename) {
        return filename.endsWith(".txt") && filename.length() > 4;
    }
}
