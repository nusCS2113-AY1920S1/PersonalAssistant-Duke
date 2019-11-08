package dolla.action;

import dolla.command.AddEntryCommand;
import dolla.command.Command;
import dolla.exception.DollaException;
import dolla.model.DollaData;
import dolla.parser.MainParser;

import java.time.LocalDate;

public class IntegratedActionTest implements ActionTestStringList {
    private DollaData dollaData = new DollaData();

    private void dataInput() throws DollaException {
        Command c = MainParser.handleInput(MODE_ENTRY, TEST_INPUT1);
        c.execute(dollaData);

        c = MainParser.handleInput(MODE_ENTRY, TEST_INPUT2);
        c.execute(dollaData);

        c = MainParser.handleInput(MODE_ENTRY, TEST_INPUT3);
        c.execute(dollaData);

        c = MainParser.handleInput(MODE_ENTRY, TEST_INPUT4);
        c.execute(dollaData);
    }
}
