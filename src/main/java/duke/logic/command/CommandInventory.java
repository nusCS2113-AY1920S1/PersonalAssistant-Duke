package duke.logic.command;

import duke.exception.DukeException;
import duke.model.list.inventorylist.InventoryList;
import duke.storage.InventoryStorage;

import java.text.ParseException;
import java.util.ArrayList;

public abstract class CommandInventory {
    protected String userInput;

    public abstract ArrayList<String> execute(InventoryList inventoryList, InventoryStorage inventoryStorage) throws DukeException, ParseException;

    public abstract boolean isExit();
}
