package command;

import Dictionary.WordBank;
import storage.Storage;
import ui.Ui;

public class BadCommand extends Command {

    @Override
    public void execute(Ui ui, WordBank wordBank, Storage storage) {
        //ask ui to print something
        //ask tasks to store the thing in arraylist
        //ask storage to write to file
        isExit();
    }

}
