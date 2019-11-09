import executor.Executor;
import executor.command.Command;
import executor.command.CommandBye;
import executor.command.CommandHelp;
import executor.command.CommandType;
import org.junit.jupiter.api.Test;
import storage.StorageManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandHelpTest {

    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();
        String outputStr = null;
        Command c = Executor.createCommand(CommandType.BYE,"null");
        outputStr = c.getDescription();
        String out = "BYE" + " - " + outputStr + "\n\n";

        CommandHelp c1 = new CommandHelp("help bye");
        c1.execute(storageManager);
        String ans1 = c1.getInfoCapsule().getOutputStr();
        assertEquals(out,ans1);

        CommandHelp c2 = new CommandHelp("helpcwdcwv");
        c2.execute(storageManager);
        String ans2 = c2.getInfoCapsule().getOutputStr();
        assertEquals("Command invalid. Enter 'help' to see all the available commands.\n",ans2);

        CommandHelp c3 = new CommandHelp("help 234243");
        c3.execute(storageManager);
        String ans3 = c3.getInfoCapsule().getOutputStr();
        assertEquals("Command invalid. Enter 'help' to see all the available commands.\n",ans3);
    }
}
