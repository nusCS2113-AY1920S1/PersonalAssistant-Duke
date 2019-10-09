package com.algosenpai.app.command;

import com.algosenpai.app.exceptions.DukeExceptions;

public abstract class Command {

    /**
     * Create new command.
     */
    Command() {

    }

    /**
     * Execute command action.
     */
    public abstract void execute() throws DukeExceptions;

}
