package com.algosenpai.app.logic.command.critical;

import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class ResetCommand extends Command {

    private UserStats userStats;
    // User has to enter reset once, then enter a confirmation second time.
    // If resetMode is false, it means the user is asking for the first time,
    // So we should display a confirm(yes/no) message.
    // If it is true, the user has already confirmed, and we should go ahead with resetting the data.
    private AtomicBoolean resetMode;


    /**
     * Create new command.
     */
    public ResetCommand(ArrayList<String> inputs, UserStats userStats, AtomicBoolean resetMode) {
        super(inputs);
        this.userStats = userStats;
        this.resetMode = resetMode;
    }


    @Override
    public String execute() {

        if (!resetMode.get()) {
            resetMode.set(true);
            return "Are you sure you want to reset your progress? (y/n)";
        } else if (inputs.get(0).equals("y")) {
            resetMode.set(false);
            userStats.resetAll();
            Storage.saveData("UserData.txt",userStats.toString());
            return "You progress has been reset!";
        } else {
            resetMode.set(false);
            return "Reset operation cancelled!";
        }

    }
}
