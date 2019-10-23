package command;


import exception.DukeException;
import inventory.Inventory;
import inventory.Item;
import storage.Storage;
import ui.Ui;
import user.Login;
import user.User;

import java.io.IOException;
import java.text.ParseException;

public class AddInventoryCommand extends Command{
    private String[] splitC;
    private int quantity;
    private String name;


    /**
     * Creates new inventory item
     * format is: addinventory NAME /qty QUANTITY
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException if format not followed
     * @throws IOException when entry is incorrect
     */
    public  AddInventoryCommand(String input, String[] splitStr) throws DukeException, IOException {
        if (splitStr.length <= 4) //length of splitStr array
            throw new DukeException("☹ OOPS!!! Please create your inventory with the following format: name, quantity");
        if (!input.contains(" /qty ")) {
            throw new DukeException("Please add the quantity of your item");
        }

        String temp = input.substring(13);
        splitC = temp.split(" /qty ", 2);
        if (splitC.length < 2) {
            throw new DukeException("☹ OOPS!!! Please create your inventory with the following format: NAME /qty QUANTITY");
        }
        this.name = splitC[0];
        this.quantity = Integer.parseInt(splitC[1]);
    }

    @Override
    public void execute(Inventory name, Ui ui, Storage inventoryStorage)
            throws DukeException, IOException, ParseException {
        Inventory inventory = new Inventory(name, quantity);
        name.add(inventory);
        inventoryStorage.saveToFile(name);
        ui.addToOutput("Got it, I've added this to inventory.\n"
                + inventory.toString() + "\n" + "Now you have " + name.size() + " item(s) in the inventory.");
}
