package gazeeebo.commands.gpacalculator;

import java.util.Map;

public class ListGPACommand {
    static final int HORT_LINE_SEPARATOR = 30;
    public ListGPACommand(final Map<String, GPACommand> calculator, final String LINE_BREAK, double cap) {
        System.out.println("Module code | MC | GPA\n" + LINE_BREAK);
        for(String key:calculator.keySet()) {
            System.out.println(key + "      | " + calculator.get(key).moduleCredit + "  | " + calculator.get(key).score);
            System.out.println(LINE_BREAK);
        }
        System.out.println("GPA: " + cap);
    }
}
