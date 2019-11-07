import executor.command.CommandAdd;
import executor.command.CommandDiv;
import executor.command.CommandMul;
import executor.command.CommandSub;
import org.junit.jupiter.api.Test;
import storage.StorageManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandCalculatorTest {

    @Test
     void execute() {
        StorageManager storageManager = new StorageManager();
        CommandAdd add = new CommandAdd("1/2");
        add.execute(storageManager);
        String result = add.getInfoCapsule().getOutputStr();
        assertEquals("1 + 2 = 3.0\n", result);

        CommandSub sub = new CommandSub("1/3");
        sub.execute(storageManager);
        String output = sub.getInfoCapsule().getOutputStr();
        assertEquals("1 - 3 = -2.0\n", output);

        CommandDiv div = new CommandDiv("4/2");
        div.execute(storageManager);
        String result1 = div.getInfoCapsule().getOutputStr();
        assertEquals("4 / 2 = 2.0\n", result1);

        CommandDiv div2 = new CommandDiv("2/0");
        div2.execute(storageManager);
        String result3 = div2.getInfoCapsule().getOutputStr();
        assertEquals("2 / 0 = Infinity\n", result3);

        CommandMul mul = new CommandMul("3/2.0");
        mul.execute(storageManager);
        String output2 = mul.getInfoCapsule().getOutputStr();
        assertEquals("3 * 2.0 = 6.0\n", output2);

    }

}
