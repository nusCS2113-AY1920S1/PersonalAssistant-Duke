import duke.command.impression.ImpressionNewCommand;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ImpressionCommandTest extends CommandTest {

    @Test
    public void ImpressionNewCommand_validCommand_correctDataCreated() {
        //TODO test other commands
        ImpressionNewCommand cmd = new ImpressionNewCommand();
        Map<String, String> medVals = Map.ofEntries(
                Map.entry("name", "test")
        );
        cmd.setSwitchValsMap(medVals);
    }
}
