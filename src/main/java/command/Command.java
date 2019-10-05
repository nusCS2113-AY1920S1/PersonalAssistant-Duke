package command;

import Dictionary.Word;
import Dictionary.WordBank;
import storage.Storage;
import ui.Ui;

public abstract class Command {

    protected Word word;

    public void execute(Ui ui, WordBank wordBank, Storage storage) {
        //ask ui to print something
        //ask storage to store the thing into tasks
    }

    public boolean isExit() {
        return false;
    }
}
