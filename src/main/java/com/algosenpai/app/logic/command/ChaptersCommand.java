package com.algosenpai.app.logic.command;

import java.util.ArrayList;

public class ChaptersCommand extends Command {

    public ChaptersCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() {
        return "Chapter 1: sorting\nChapter 2: linkedlist\nChapter 3: bitmask";
    }
}
