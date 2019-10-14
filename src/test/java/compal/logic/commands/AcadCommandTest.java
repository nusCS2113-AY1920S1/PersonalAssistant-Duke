package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AcadCommandTest {
    private static final String CMD_LECT = "lect";
    private static final String CMD_TUT = "tut";
    private static final String CMD_SECT = "sect";
    private static final String CMD_LAB = "lab";
    private static final String SYMBOL_LECT = "LECT";
    private static final String SYMBOL_TUT = "TUT";
    private static final String SYMBOL_SECT = "SECT";
    private static final String SYMBOL_LAB = "LAB";
    private static final String SYMBOL_ACAD = "ACAD";
    private Compal compal;
    private AcadCommand acadCommand;

    @BeforeEach
    public void setUp() {
        compal = new Compal();
        acadCommand = new AcadCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, acadCommand.compal);
    }

    @Test
    public void getStartDateListTest() throws Compal.DukeException {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            list.add("14/10/2019");
        }
        String command = "/date 14/10/2019 14/10/2019 14/10/2019 /time";
        assertEquals(list, acadCommand.getStartDateList(command));
    }

    @Test
    public void getEndDateTest() throws Compal.DukeException {
        String time = "15/10/2019";
        String command = "/edate 15/10/2019";
        assertEquals(time, acadCommand.getEndDate(command));
    }

    @Test
    public void incrementDateTest() {
        String date = "14/10/2019";
        String newDate = "21/10/2019";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        Date newD = null;
        try {
            d = format.parse(date);
            newD = format.parse(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(newD, acadCommand.incrementDateByWeek(d));
    }

    @Test
    public void getSysmbolTest() {
        assertEquals(SYMBOL_LECT, acadCommand.getSymbol(CMD_LECT));
        assertEquals(SYMBOL_LAB, acadCommand.getSymbol(CMD_LAB));
        assertEquals(SYMBOL_SECT, acadCommand.getSymbol(CMD_SECT));
        assertEquals(SYMBOL_TUT, acadCommand.getSymbol(CMD_TUT));
        assertEquals(SYMBOL_ACAD, acadCommand.getSymbol("useless test"));
    }
}
