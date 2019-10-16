package com.algosenpai.app.command;

import com.algosenpai.app.exceptions.DukeExceptions;

public class ClearCommand extends Command {
    private int num;

    public ClearCommand(int num) {
        this.num = num;
    }

    @Override
    public void execute() throws DukeExceptions {

    }
}
