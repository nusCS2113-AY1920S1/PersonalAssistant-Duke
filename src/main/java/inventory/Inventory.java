package inventory;
import java.util.ArrayList;

import exception.DukeException;
import storage.Constants;


/* purpose of inventory
* er
* just need a checklist of things and its quantity
* use same default fof every room
* items not present just put qty 0
*
* must link to ROOM
*
 */


public class Inventory extends ArrayList<Item>{
    //create an arraylist of ITEMS
    private String name;
    private int quantity;
    ArrayList<Item> Inventory;

    /**
     * Create Inventory from text file.
     * @param loader strings from text file containing Item info
     * @throws DukeException if file format incorrect
     */
    public Inventory (ArrayList<String> loader){
        for (String line : loader) {
            String[] splitStr = line.split("\\|", 2);
            int parsed = parseInt(splitStr[Constants.ITEMQTY]);
            this.add(new Item(splitStr[Constants.ITEMNAME], parseInt((splitStr[Constants.ITEMQTY])));
    }

}
