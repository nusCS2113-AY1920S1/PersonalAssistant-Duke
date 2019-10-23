package seedu.hustler.data;

import seedu.hustler.Hustler;
import seedu.hustler.game.shop.ShopList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The shop storage manager that handles saving and editing the shop.txt file.
 */
public class ShopStorage {

    /**
     * The filepath of the shop.txt.
     */
    public static final String FILEPATH = "data/shop.txt";

    /**
     * Attemps to load the shop.txt file and initializes the boolean values of
     * each item in the shop list.
     * @return the shop list with the updated values.
     * @throws IOException throws an error if there is no shop.txt file.
     *     Creates a new instance and initializes the boolean values.
     */
    public static ShopList load() throws IOException {
        try {
            Scanner shopTxt = new Scanner(new File(FILEPATH));
            ArrayList<Boolean> boolList = new ArrayList<>();
            while (shopTxt.hasNextLine()) {
                boolList.add(shopTxt.nextBoolean());
            }
            for (int i = 0; i < Hustler.shopList.size(); i++) {
                Hustler.shopList.updateStatus(i, boolList.get(i));
            }
            return Hustler.shopList;
        } catch (FileNotFoundException e) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILEPATH)));
            for (int i = 0; i < 6; i++) {
                writer.write("false" + (i != 5 ? "\n" : ""));
            }
            writer.close();
            return Hustler.shopList;
        }
    }

    /**
     * Updates the shop.txt file whenever user purchases an item to reflect
     * correctly in the shop list.
     * @return the shop list with the updated values.
     */
    public static ShopList update() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILEPATH)));
            writer.write(Hustler.shopList.itemsStatus());
            writer.close();
            return Hustler.shopList;
        } catch (IOException e) {
            e.printStackTrace();
            return Hustler.shopList;
        }
    }
}
