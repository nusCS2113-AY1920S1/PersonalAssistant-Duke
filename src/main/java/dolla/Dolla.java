package dolla;

import dolla.command.Command;
import dolla.model.DollaData;
import dolla.exception.DollaException;
import dolla.parser.MainParser;
import dolla.parser.ParserStringList;
import dolla.storage.StorageRead;

import java.util.Scanner;

/**
 * Dolla is a chat-bot styled expense manager.
 *
 * @author  Aik Peng
 * @version 1.0
 * @since   2019-07-26
 */
public class Dolla implements ModeStringList, ParserStringList {

    private DollaData dollaData = new DollaData();
    private boolean isExit = false;

    private Dolla() {
        StorageRead.load();
    }

    private void verifyIsExit(String string) {
        if (string.equals(COMMAND_BYE)) {
            isExit = true;
        }
    }

    private void run() throws DollaException {
        Reminder reminder = new Reminder(MODE_DEBT);
        reminder.showReminder(dollaData);
        Scanner input = new Scanner(System.in); // TODO: Add to Ui or MainParser instead?
        while (!isExit) {
            String inputLine = input.nextLine();
            verifyIsExit(inputLine);
            String mode = dollaData.getMode();
            Command c = MainParser.handleInput(mode, inputLine);
            c.execute(dollaData);
        }
    }

    public static void main(String[] args) throws DollaException {
        new Dolla().run();
    }
}