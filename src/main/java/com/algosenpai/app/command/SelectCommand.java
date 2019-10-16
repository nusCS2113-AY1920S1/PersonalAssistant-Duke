package com.algosenpai.app.command;

import com.algosenpai.app.exceptions.DukeExceptions;

public class SelectCommand extends Command {

    private int num;

    public SelectCommand(int num) {
        this.num = num;
    }

    @Override
    public void execute() throws DukeExceptions{

    }

}
