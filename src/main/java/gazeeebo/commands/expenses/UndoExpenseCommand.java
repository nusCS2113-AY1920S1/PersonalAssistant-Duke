//@@author jessteoxizhi

package gazeeebo.commands.expenses;

import gazeeebo.storage.Storage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

/**
 * Undo previous expenses commands (e.g. add, delete)
 */
public class UndoExpenseCommand {
    /**
     * Undo the previous expenses command.
     *
     * @param expenses    the map that maps each expenses to its date
     * @param oldExpenses keep the previous expenses command
     * @param storage     the object that deals with storing data
     * @return return the expenses map
     * @throws IOException Catch error if the read file fails
     */
    public static Map<LocalDate, ArrayList<String>> undoExpenses(
            Map<LocalDate, ArrayList<String>> expenses,
            final Stack<Map<LocalDate,
                    ArrayList<String>>> oldExpenses,
            final Storage storage) throws IOException {

        if (!oldExpenses.empty()) {
            expenses = oldExpenses.peek();
            String toStore = "";
            for (LocalDate key : expenses.keySet()) {
                if (expenses.get(key).size() > 1) {
                    for (int i = 0; i < expenses.get(key).size(); i++) {
                        toStore = toStore.concat(key + "|"
                                + expenses.get(key).get(i)
                                + "\n");
                    }
                } else if (expenses.get(key).size() == 1) {

                    toStore = toStore.concat(key + "|"
                            + expenses.get(key).get(0)
                            + "\n");
                }
            }
            storage.writeToExpensesFile(toStore);
            oldExpenses.pop();
            System.out.println("You have undone the previous command.");
        } else {
            System.out.println("The previous command cannot be undone");
        }
        return expenses;
    }
}
