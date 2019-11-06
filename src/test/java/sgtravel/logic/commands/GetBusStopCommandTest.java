package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetBusStopCommandTest {

    @Test
    void getBusStopTest() throws DukeException {
        ModelStub model = new ModelStub();
        String expected = "This is the information for this Bus Stop:\n" + "Clementi Int\n"
                + "99\n" + "14\n" + "96A\n" + "96B\n" + "147e\n" + "7B\n" + "282\n" + "173\n" + "284\n" + "196\n"
                + "285\n" + "175\n" + "165\n" + "166\n" + "156\n" + "147\n" + "7\n" + "96\n";


        GetBusStopCommand command = new GetBusStopCommand("17009");
        assertEquals(expected, command.execute(model).getMessage());

        //non-existant bus stop
        GetBusStopCommand command2 = new GetBusStopCommand("0");
        assertThrows(DukeException.class, () -> {
            command2.execute(model);
        });

        //negative number bus stop
        GetBusStopCommand command3 = new GetBusStopCommand("-1");
        assertThrows(DukeException.class, () -> {
            command3.execute(model);
        });

        GetBusStopCommand command4 = new GetBusStopCommand("-2");
        assertThrows(DukeException.class, () -> {
            command4.execute(model);
        });

        //test for string
        GetBusStopCommand command5 = new GetBusStopCommand("test");
        assertThrows(DukeException.class, () -> {
            command5.execute(model);
        });
    }
}
