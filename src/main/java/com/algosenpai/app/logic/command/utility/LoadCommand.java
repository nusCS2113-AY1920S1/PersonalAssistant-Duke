package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.exceptions.FileParsingException;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;

import java.io.FileNotFoundException;
import java.net.UnknownServiceException;
import java.util.ArrayList;

public class LoadCommand extends Command {

    private UserStats userStats;

    private static final String ERROR_MESSAGE = "Please enter in the format:\n load <filename>.txt";

    /**
     * Create new command.
     */
    public LoadCommand(ArrayList<String> inputs, UserStats userStats) {
        super(inputs);
        this.userStats = userStats;
    }

    @Override
    public String execute() {
        if (inputs.size() < 2 || !isValidFilename(inputs.get(1))) {
            return ERROR_MESSAGE;
        }
        String fileName = inputs.get(1);
        String fileContents;
        try {
            fileContents = Storage.loadData(fileName);
        } catch (FileNotFoundException e) {
            return "File does not exist!";
        }
        UserStats temp;
        try {
            temp = UserStats.parseString(fileContents);
        } catch (FileParsingException e) {
            return "File is not a valid User Data file!";
        }
        userStats.copy(temp);
        return "UserStats has been updated! Type \"stats\" to view the new stats.";
    }

    private boolean isValidFilename(String filename) {
        return filename.endsWith(".txt") && filename.length() > 4;
    }
}
