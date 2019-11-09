//@@author e0323290

package gazeeebo.commands.expenses;

import gazeeebo.UI.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Map;

/**
 * This method finds expenses bought on the same date.
 */
public class FindExpenseCommand {

    /**
     * This method finds expenses bought on the same date.
     *
     * @param ui       the object that deals with printing things to the user.
     * @param expenses the map that maps each expenses to its date
     */
    public FindExpenseCommand(final Ui ui,
                              final Map<LocalDate,
                                      ArrayList<String>> expenses) {
        try {
        String date = ui.fullCommand.split(" ")[1];
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfPurchase;

            dateOfPurchase = LocalDate.parse(date, fmt);
            boolean isExist = false;
            for (LocalDate key : expenses.keySet()) {
                if (dateOfPurchase.equals(key)) {
                    for (int i = 0; i < expenses.get(key).size(); i++) {
                        System.out.println((i + 1) + "."
                                + expenses.get(key).get(i));
                    }
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                System.out.println(date + " is not found in the list.");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.print("Please input in the correct format\n");
        } catch (
                DateTimeParseException e) {
            System.out.println("Wrong date format");
        }
    }
}



