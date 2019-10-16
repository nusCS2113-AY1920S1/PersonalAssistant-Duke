package seedu.hustler.data;

import seedu.hustler.Hustler;
import seedu.hustler.game.shop.ShopList;

import java.io.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class ShopStorage {

    public static final String FILEPATH = "data/shop.txt";

    public static ShopList load() throws IOException {
        try {
            Scanner shopTxt = new Scanner(new File(FILEPATH));
            ArrayList<Boolean> boolList = new ArrayList<>();
            while (shopTxt.hasNextLine()) {
                boolList.add(shopTxt.nextBoolean());
            }
            for (int i = 0; i < Hustler.shopList.size(); i ++) {
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

    public static void update() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILEPATH)));
            writer.write(Hustler.shopList.itemsStatus());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
