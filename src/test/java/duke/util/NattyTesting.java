package duke.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.ParseLocation;
import com.joestelmach.natty.Parser;


public class NattyTesting {

    @Test
    public void nattyTrials() {
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse("the day before next thursday");
        for (DateGroup group : groups) {
            List dates = group.getDates();
            int line = group.getLine();
            int column = group.getPosition();
            String matchingValue = group.getText();
            String syntaxTree = group.getSyntaxTree().toStringTree();
            Map<String, List<ParseLocation>> parseMap = group.getParseLocations();
            boolean isRecurring = group.isRecurring();
            Date recursUntil = group.getRecursUntil();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = format.parse("2009-12-31");
                assertEquals(recursUntil, recursUntil);
            } catch (ParseException e) {
                System.out.println(e.toString());
            }
        }
    }


}
