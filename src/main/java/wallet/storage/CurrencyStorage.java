//@@author matthewng1996

package wallet.storage;

import wallet.model.currency.Currency;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class deals with loading of Currency from currency.txt
 */
public class CurrencyStorage {

    public static final String DEFAULT_STORAGE_FILEPATH_CURRENCY = "/currency.txt";

    /**
     * This method loads the currency.txt list into currentList for use in currency conversion.
     * @return returns a list of currencies
     */
    public ArrayList<Currency> loadFile() {
        ArrayList<Currency> currencyList = new ArrayList<>();
        try {
            InputStream is = getClass().getResourceAsStream(DEFAULT_STORAGE_FILEPATH_CURRENCY);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String str;
            while ((str = br.readLine()) != null) {
                String[] data = str.split(",");
                if (!data[0].isEmpty() && !data[1].isEmpty() && !data[2].isEmpty()) {
                    if (data[2].trim().equals("false")) {
                        Currency currency = new Currency(data[0].trim().toLowerCase(),
                                Double.parseDouble(data[1]),
                                false);
                        currencyList.add(currency);
                    } else {
                        Currency currency = new Currency(data[0].trim().toLowerCase(),
                                Double.parseDouble(data[1]),
                                true);
                        currencyList.add(currency);
                    }
                }
            }
            br.close();
            isr.close();
            is.close();
        } catch (FileNotFoundException e) {
            System.out.println("No saved currencies found.");
        } catch (IOException e) {
            System.out.println("End of file.");
        }

        return currencyList;
    }
}
