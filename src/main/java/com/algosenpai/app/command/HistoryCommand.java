package com.algosenpai.app.command;

import com.algosenpai.app.exceptions.DukeExceptions;

public class HistoryCommand extends Command {

    private int num;

    public HistoryCommand(int num) {
        this.num = num;
    }

    @Override
    public void execute() throws DukeExceptions {

    }
}
