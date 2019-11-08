
package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.logic.command.Command;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ResultCommand extends Command {

    private AtomicInteger results;

    /**
     * Create new command.
     * @param inputs input from user.
     */
    private ResultCommand(ArrayList<String> inputs) {
        super(inputs);
    }


    public ResultCommand(ArrayList<String> inputs, AtomicInteger results) {
        this(inputs);
        this.results = results;
    }

    @Override
    public String execute() {
        if (results.get() == -1) {
            return "You have not attempted any quiz yet.";
        }
        return "You had " + results + "/10 questions correct for the last attempt.";
    }
}
