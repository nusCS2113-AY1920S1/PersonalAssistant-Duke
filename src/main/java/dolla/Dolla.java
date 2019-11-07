package dolla;

import dolla.command.Command;
import dolla.exception.DollaException;
import dolla.parser.MainParser;
import dolla.parser.ParserStringList;
import dolla.storage.StorageRead;

import java.util.Scanner;

/**
 * <h1>duke.Dolla</h1>
 * duke.Dolla is a chat-bot styled expense manager.
 *
 * @author  Aik Peng
 * @version 1.0
 * @since   2019-07-26
 */
public class Dolla implements ModeStringList, ParserStringList {

    private DollaData dollaData = new DollaData();

    private Dolla() {
        StorageRead.load();
    }

    private void run() throws DollaException {
        boolean isExit = false;
        Reminder reminder = new Reminder(MODE_DEBT);
        reminder.showReminder(dollaData);
        Scanner input = new Scanner(System.in); // TODO: Add to Ui or MainParser instead?
        while (!isExit) {
            if (input.hasNextLine()) {
                String inputLine = input.nextLine();
                if (inputLine.equalsIgnoreCase(COMMAND_BYE)) {
                    isExit = true;
                    MainParser.exit();
                } else {
                    Command c = MainParser.handleInput(dollaData.getMode(), inputLine);
                    c.execute(dollaData);
                }
            }
        }
    }

    public static void main(String[] args) throws DollaException { // Exception needs to be handled?
        new Dolla().run();
    }
}