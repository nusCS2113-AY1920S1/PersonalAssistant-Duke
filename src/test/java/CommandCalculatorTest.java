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

        CommandAdd add2 = new CommandAdd("134fg 1");
        add2.execute(storageManager);
        String result2 = add2.getInfoCapsule().getOutputStr();
        assertEquals("Invalid input please enter in this format: add <num1> / <num2>", result2);

        CommandSub sub = new CommandSub("1/3");
        sub.execute(storageManager);
        String output = sub.getInfoCapsule().getOutputStr();
        assertEquals("1 - 3 = -2.0\n", output);

        CommandSub sub2 = new CommandSub("1");
        sub2.execute(storageManager);
        String output2 = sub2.getInfoCapsule().getOutputStr();
        assertEquals("Enter forward slash and second number. Format: sub <num1> / <num2>", output2);

        CommandDiv div = new CommandDiv("4/2");
        div.execute(storageManager);
        String result1 = div.getInfoCapsule().getOutputStr();
        assertEquals("4 / 2 = 2.0\n", result1);

        CommandDiv div2 = new CommandDiv("2/0");
        div2.execute(storageManager);
        String result3 = div2.getInfoCapsule().getOutputStr();
        assertEquals("2 / 0 = Infinity\n", result3);

        CommandDiv div3 = new CommandDiv("0.0/-1");
        div3.execute(storageManager);
        String result4 = div3.getInfoCapsule().getOutputStr();
        assertEquals("The result is zero", result4);

        CommandMul mul = new CommandMul("3/2.0");
        mul.execute(storageManager);
        String output3 = mul.getInfoCapsule().getOutputStr();
        assertEquals("3 * 2.0 = 6.0\n", output3);

        CommandMul mul2 = new CommandMul("3/ abc");
        mul2.execute(storageManager);
        String output4 = mul2.getInfoCapsule().getOutputStr();
        assertEquals("Invalid input please enter the second number. Format: mul <num1> / <num2>", output4);
    }

}
