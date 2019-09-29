package duke.util;

import duke.exceptions.DukeInvalidTimeException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.tree.Tree;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.ParseLocation;
import com.joestelmach.natty.Parser;


public class NattyTesting {

    private NattyWrapper natty = new NattyWrapper();

    @Test
    public void nattyDateTest() {
        try {
            Date first = natty.runParser("today");
            Date second = Calendar.getInstance().getTime();
            assertTrue(first.before(second));
        } catch (DukeInvalidTimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void nattyLocalDateTimeTest() {
        try {
            LocalDateTime before = natty.dateToLocalDateTime("today");
            LocalDateTime after = LocalDateTime.now();
            assertTrue(before.isBefore(after));
        } catch (DukeInvalidTimeException e) {
            System.out.println(e.getMessage());
        }
    }


}
