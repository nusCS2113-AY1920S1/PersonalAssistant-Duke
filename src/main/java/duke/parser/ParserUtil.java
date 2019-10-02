package duke.parser;

import duke.commons.Message;
import duke.parser.exceptions.ParseException;

import java.util.ArrayList;
import java.util.List;

public class ParserUtil {
    public static List<Integer> getIndexes(String indexString) throws ParseException {
        List<Integer> indexes = new ArrayList<>();
        if (indexString.contains("~")) {
            indexes = getIndexesInInterval(indexString);
        } else {
            indexes = getIndexesFromString(indexString);
        }
        return indexes;
    }

    private static List<Integer> getIndexesInInterval(String interval) throws ParseException {
        String[] startAndEndIndexes = interval.split("~");
        int start;
        int end;
        try {
            start = Integer.parseInt(startAndEndIndexes[0]) - 1;
            end = Integer.parseInt(startAndEndIndexes[1]) - 1;
        } catch (NumberFormatException e) {
            throw new ParseException(Message.MESSAGE_INVALID_NUMBER_FORMAT);
        }
        if (start > end) {
            throw new ParseException(Message.MESSAGE_INVALID_RANGE);
        }
        List<Integer> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            result.add(i);
        }
        return result;
    }

    private static List<Integer> getIndexesFromString(String string) throws ParseException {
        String[] indexStrings = string.split(",");
        List<Integer> result = new ArrayList<>();
        for (String indexString : indexStrings) {
            try {
                result.add(Integer.parseInt(indexString) - 1);
            } catch (NumberFormatException e) {
                throw new ParseException(Message.MESSAGE_INVALID_NUMBER_FORMAT);
            }
        }
        return result;
    }
}
