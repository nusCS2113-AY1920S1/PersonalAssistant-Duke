package dolla.command;

import dolla.DollaData;
import dolla.ModeStringList;
import dolla.Time;
import dolla.task.EntryList;
import dolla.ui.EntryUi;
import dolla.ui.Ui;
import dolla.action.Redo;
import dolla.action.Undo;
import dolla.task.Entry;

import java.time.LocalDate;

/**
 * AddEntryCommand is used to create a new Entry entity.
 */
public class AddEntryCommand extends Command {

    private String type;
    private double amount;
    private String description;
    private LocalDate date;
    private int prevPosition;
    private static final String mode = ModeStringList.MODE_ENTRY;

    /**
     * Creates an instance of AddEntryCommand.
     * @param type Income or Expense.
     * @param amount Amount of money that is earned/spent.
     * @param description Details pertaining to the entry.
     * @param date Date of income/expense.
     * @param prevPosition previous position of a deleted input that is passed from an undo command;
     *                     -1 if the input is not from undo command.
     */
    public AddEntryCommand(String type, double amount, String description, LocalDate date, int prevPosition) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.prevPosition = prevPosition;
    }

    @Override
    public void execute(DollaData dollaData) {
        Entry newEntry = new Entry(type, amount, description, date);
        EntryList entryList = (EntryList) dollaData.getRecordList(mode);
        int duplicateEntryIndex = entryList.findExistingRecordIndex(dollaData, newEntry, mode);

        if (recordDoesNotExist(duplicateEntryIndex)) {
            if (prevPosition == -1) {
                dollaData.addToRecordList(mode, newEntry);
                index = dollaData.getRecordList(mode).size();
                Undo.removeCommand(mode, index);
                Redo.clearRedo(mode);
            } else if (prevPosition == -2) {
                dollaData.addToRecordList(mode, newEntry);
                index = dollaData.getRecordList(mode).size();
                Undo.removeCommand(mode, index);
                prevPosition = -1; //reset to -1
            } else {
                dollaData.addToPrevPosition(mode, newEntry, prevPosition);
                Redo.removeCommand(mode, prevPosition);
                prevPosition = -1; //reset to -1
            }
            Ui.echoAddRecord(newEntry);
        } else {
            Entry existingEntry = (Entry) entryList.getFromList(duplicateEntryIndex);
            EntryUi.existingEntryPrinter(existingEntry);
        }
    }

    @Override
    public String getCommandInfo() {
        String command = "AddEntryCommand";
        //return (command + "{ type: " + type + ", amount: " + amount + ", description: "
        //        + description + ", date: " + Time.dateToString(date) + ", prevPosition: "
        //        + prevPosition) + " }";
        return (command + "{ " + type + ", " + amount + ", "
                + description + ", " + Time.dateToString(date) + ", "
                + prevPosition) + " }";
    }
}
