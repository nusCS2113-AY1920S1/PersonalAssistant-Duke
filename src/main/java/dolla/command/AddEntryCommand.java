package dolla.command;

import dolla.DollaData;
import dolla.Ui;
import dolla.action.undo;
import dolla.task.Entry;

import java.time.LocalDateTime;

/**
 * AddEntryCommand is used to create a new Entry entity.
 */
public class AddEntryCommand extends Command {

    private String type;
    private double amount;
    private String description;
    private LocalDateTime date;
    private int prevPosition;

    /**
     * Creates an instance of AddEntryCommand.
     * @param type Income or Expense.
     * @param amount Amount of money that is earned/spent.
     * @param description Details pertaining to the entry.
     * @param date Date of income/expense.
     */
    public AddEntryCommand(String type, double amount, String description, LocalDateTime date, int prevPosition) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.prevPosition = prevPosition;
    }

    @Override
    public void execute(DollaData dollaData) {
        Entry newEntry = new Entry(type, amount, description, date);

        if(prevPosition != -1) { //an undo input
            dollaData.addToPrevPosition("entry", newEntry, prevPosition);
        } else { //normal input
            dollaData.addToLogList("entry", newEntry);
        }
        index = dollaData.getLogList("entry").size();
        undo.removeCommand("entry",index);
        Ui.echoAddEntry(newEntry);
    }
}
