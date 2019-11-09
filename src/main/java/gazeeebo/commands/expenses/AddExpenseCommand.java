//@@author e0323290

package gazeeebo.commands.expenses;

import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class adds the expense from the expense list and expenses.
 */

public class AddExpenseCommand {
    /**
     * This method adds the expense from the expense list and expenses.
     *
     * @param ui       the object that deals with printing things to the user.
     * @param expenses the map that maps each expenses to its date
     * @throws ArrayIndexOutOfBoundsException catches error if
     *                                        add command format is wrong
     * @throws DateTimeParseException         catches error if
     *                                        date format is wrong
     */
    public AddExpenseCommand(final Ui ui,
                             final Map<LocalDate, ArrayList<String>> expenses)
            throws ArrayIndexOutOfBoundsException, DateTimeParseException {
        final int addCharacterCount = 3;

        try {
            String toAdd = ui.fullCommand;
            String item = toAdd.substring(addCharacterCount)
                    .split(",")[0].trim();
            String price = toAdd.substring(addCharacterCount)
                    .split(",")[1].trim();
            String itemAndPrice = item + ", "
                    + price;
            String date = toAdd.substring(addCharacterCount)
                    .split(",")[2].trim();
            System.out.println(price.substring(0, 1));

            if (!(price.charAt(0) == '$')) {
                throw new DukeException("Please key in the "
                        + "correct format for money: $__");
            }

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateOfPurchase = LocalDate.parse(date, fmt);
            //Storing expenses value as an array to the date keys.
            //If the item were bought on the same date as a previous item,
            //add the item in the array under the same key.
            ArrayList<String> itemAndPriceList = new ArrayList<>();
            boolean isEqual = false;
            for (LocalDate key : expenses.keySet()) {
                if (dateOfPurchase.equals(key)) { //if date equal
                    expenses.get(key).add(itemAndPrice);
                    isEqual = true;
                }
            }
            if (!isEqual) {
                itemAndPriceList.add(itemAndPrice);
                expenses.put(dateOfPurchase,
                        itemAndPriceList);
            }
            System.out.println("Successfully added: "
                    + "\n" + itemAndPrice
                    + ", bought on " + dateOfPurchase);

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.print("Please input in the correct format\n");
        } catch (DateTimeParseException e) {
            System.out.println("Wrong date format");
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
}
