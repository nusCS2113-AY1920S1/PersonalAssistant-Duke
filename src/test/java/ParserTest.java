import java.util.Stack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

    @Test
    void testCommandBuilder_prependInformationToUserInput_fullUserInputAttained() {
        Stack<String> inputInformation = new Stack<>();
        inputInformation.push("modules");
        inputInformation.push("CG1111");
        inputInformation.push("files");
        StringBuilder fullUserInput = new StringBuilder();
        fullUserInput.append("1");
        Stack<String> cloneLog = new Stack<>();
        cloneLog.addAll(inputInformation);
        while (!cloneLog.empty()) {
            String individualInput = cloneLog.peek();
            fullUserInput.insert(0, " ");
            fullUserInput.insert(0, individualInput);
            cloneLog.pop();
        }
        fullUserInput.insert(0, " ");
        fullUserInput.insert(0, "delete");
        assertEquals("delete modules CG1111 files 1", fullUserInput.toString());
    }
}