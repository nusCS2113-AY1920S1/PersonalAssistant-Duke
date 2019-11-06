package dolla.command;

import dolla.DollaData;
import dolla.Time;
import dolla.task.Record;
import dolla.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

//@@author yetong1895
public class ExecuteShortcutCommand extends Command {

    private int index;

    public ExecuteShortcutCommand(String index) {
        this.index = Integer.parseInt(index);
    }

    @Override
    public void execute(DollaData dollaData) throws Exception {
        ArrayList<Record> entryList = dollaData.getRecordList(MODE_ENTRY);
        ArrayList<Record> shortcutList = dollaData.getRecordList(MODE_SHORTCUT);
        try {
            Record shortcut = entryList.get(index);
            Ui.printDateRequest();
            Scanner input = new Scanner(System.in);
            while (true) {
                try {
                    String inputDate = input.nextLine();
                    LocalDate newDate = Time.readDate(inputDate);
                    Command c = new AddEntryCommand(shortcut.getType(), shortcut.getAmount(),
                                                    shortcut.getDescription(), newDate, "");
                    c.execute(dollaData);
                    break;
                } catch (DateTimeParseException e) {
                    Ui.printDateFormatError();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            Ui.printNumberOfRecords(shortcutList.size());
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
