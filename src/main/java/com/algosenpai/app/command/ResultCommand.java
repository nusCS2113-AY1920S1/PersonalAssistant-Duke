package com.algosenpai.app.command;

import com.algosenpai.app.exceptions.DukeExceptions;

public class ResultCommand extends Command {

    private int chap;

    public ResultCommand(int chap) {
        this.chap = chap;
    }

    @Override
    public void execute() throws DukeExceptions {

    }
}
