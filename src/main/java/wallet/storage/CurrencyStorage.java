//@@author matthewng1996

package wallet.storage;

import wallet.model.currency.Currency;

import java.io.*;
import java.util.ArrayList;

public class CurrencyStorage {

    public static final String DEFAULT_STORAGE_FILEPATH_CURRENCY = "/currency.txt";

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
