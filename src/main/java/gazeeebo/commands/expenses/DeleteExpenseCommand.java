//@@author e0323290

package gazeeebo.commands.expenses;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class deletes the expense from the expense list and expenses map.
 */
public class DeleteExpenseCommand {

    /**
     * This method deletes the expense from the expense list and expenses map.
     *
     * @param ui       the object that deals with printing things to the user.
     * @param expenses the map that maps each expenses to its date
     * @throws IOException catch any error if read file fails
     */
    public DeleteExpenseCommand(final Ui ui,
                                final Map<LocalDate, ArrayList<String>>
                                        expenses)
            throws IOException {
        ArrayList<String> expenseList = new ArrayList<>();

        /* Prints list of expenses*/
        for (LocalDate key : expenses.keySet()) {
            for (int i = 0; i < expenses.get(key).size(); i++) {
                expenseList.add(expenses.get(key).get(i)
                        + " | bought on "
                        + key);
            }
        }

        try {
            String nameToDelete = "";

            if (ui.fullCommand.split(" ").length == 1) {

                System.out.println("What is the index "
                        + "of the item you want to delete?\n");
                for (int j = 0;
                     j < expenseList.size(); j++) {
                    System.out.println((j + 1) + ". " + expenseList.get(j));
                }
                ui.readCommand();
                nameToDelete = ui.fullCommand;
                int index = Integer.parseInt(nameToDelete) - 1;
                System.out.println("Successfully deleted: "
                        + expenseList.get(index));

                String getKeyFromList = expenseList.get(index).split("on ")[1];
                DateTimeFormatter fmt
                        = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate getKey = LocalDate.parse(getKeyFromList, fmt);

                String getValueFromList = expenseList.get(index)
                        .split(" | ")[0].trim();

                for (LocalDate key : expenses.keySet()) {
                    if (key.equals(getKey)) {
                        for (int i = 0;
                             i < expenses.get(key).size(); i++) {
                            if (expenses.get(key).get(i)
                                    .contains(getValueFromList)) {
                                expenses.get(key).remove(i);
                            }
                        }
                    }
                }

            } else if (ui.fullCommand.split(" ").length > 1) {
                for (int i = 1; i < ui.fullCommand.split(" ").length; i++) {
                    nameToDelete = nameToDelete.
                            concat(ui.fullCommand.split(" ")[i] + " ");
                }
                nameToDelete = nameToDelete.trim();
                boolean isInList = false;
                for (int k = 0; k < expenseList.size(); k++) {
                    String item = expenseList.get(k).split(", ")[0];
                    if (item.equals(nameToDelete)) {
                        isInList = true;
                    }
                }
                if (isInList) {
                    for (int i = 0; i < expenseList.size(); i++) {
                        String item = expenseList.get(i).split(", ")[0];
                        if (item.equals(nameToDelete)) {
                            String getKeyFromList
                                    = expenseList.get(i).split("on ")[1];
                            DateTimeFormatter fmt
                                    = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate getKey
                                    = LocalDate.parse(getKeyFromList, fmt);

                            String getValueFromList = expenseList.get(i)
                                    .split(" | ")[0].trim();
                            for (LocalDate key : expenses.keySet()) {
                                if (key.equals(getKey)) {
                                    for (int j = 0;
                                         j < expenses.get(key).size(); j++) {
                                        if (expenses.get(key).get(j)
                                                .contains(getValueFromList)) {
                                            expenses.get(key).remove(j);
                                        }
                                    }
                                }
                            }
                            System.out.println("Successfully deleted: "
                                    + expenseList.get(i));
                        }
                    }
                } else {
                    System.out.println("Item not found!");
                }

            }

        } catch (NumberFormatException e) {
            System.out.print("Wrong input for delete command\n");
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.out.print("Please Input in the correct format\n");
        } catch (IndexOutOfBoundsException e) {
            System.out.print("Index does not exist\n");
        }

    }
}
