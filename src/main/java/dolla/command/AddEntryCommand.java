package dolla.command;

import dolla.command.action.Undo;
import dolla.model.DollaData;
import dolla.Time;
import dolla.command.action.Redo;
import dolla.model.EntryList;
import dolla.model.Record;
import dolla.ui.Ui;
import dolla.model.Entry;

import java.time.LocalDate;

/**
 * AddEntryCommand is used to create a new Entry entity.
 */
public class AddEntryCommand extends Command {

    private String type;
    private double amount;
    private String description;
    private LocalDate date;
    private static final String mode = MODE_ENTRY;

    /**
     * Creates an instance of AddEntryCommand.
     * @param type Income or Expense.
     * @param amount Amount of money that is earned/spent.
     * @param description Details pertaining to the entry.
     * @param date Date of income/expense.
     */
    public AddEntryCommand(String type, double amount, String description, LocalDate date) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    @Override
    public void execute(DollaData dollaData) {
        Entry newEntry = new Entry(type, amount, description, date);
        EntryList entryList = (EntryList) dollaData.getRecordListObj(mode);
        Undo.addToStateList(mode,entryList.get());
        Redo.clearRedoState(mode);
        dollaData.addToRecordList(mode, newEntry);
        Ui.echoAddRecord(newEntry);
    }

    @Override
    public String getCommandInfo() {
        String command = "AddEntryCommand";
        //return (command + "{ type: " + type + ", amount: " + amount + ", description: "
        //        + description + ", date: " + Time.dateToString(date) + ", prevPosition: "
        //        + prevPosition) + " }";
        return (command + "{ " + type + ", " + amount + ", "
                + description + ", " + Time.dateToString(date)) + "}";
    }
}
