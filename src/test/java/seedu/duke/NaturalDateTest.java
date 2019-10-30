package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.powermock.api.mockito.PowerMockito.doReturn;


import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

import org.junit.jupiter.api.Test;
import seedu.duke.task.command.TaskAddCommand;
import seedu.duke.task.command.TaskParseNaturalDateHelper;
import seedu.duke.task.entity.Task;

import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TaskParseNaturalDateHelper.class)
public class NaturalDateTest{

    @Test
    public void getDateTest() throws Exception {
        LocalDateTime dateTime = TaskParseNaturalDateHelper.convertNaturalDate("Mon", "1220");
        assertEquals(dateTime, TaskParseNaturalDateHelper.getDate("Mon 1220"));
        assertEquals(dateTime, TaskParseNaturalDateHelper.getDate("mon 1220"));
        assertEquals(dateTime, TaskParseNaturalDateHelper.getDate("Mon     1220"));
        dateTime = TaskParseNaturalDateHelper.convertNaturalDate("Thu", null);
        assertEquals(dateTime, TaskParseNaturalDateHelper.getDate("Thu"));
        assertEquals(dateTime, TaskParseNaturalDateHelper.getDate("Thu   "));
        assertEquals(dateTime, TaskParseNaturalDateHelper.getDate("thu"));
        LocalDateTime time = Task.parseDate("21/10/2019 1220");
        assertEquals(time, TaskParseNaturalDateHelper.getDate("21/10/2019 1220"));
    }
}
