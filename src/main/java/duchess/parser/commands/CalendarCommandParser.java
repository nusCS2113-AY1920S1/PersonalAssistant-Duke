package duchess.parser.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCalendarCommand;
import duchess.logic.commands.ExportCommand;
import duchess.model.calendar.AcademicYear;
import duchess.parser.Parser;
import duchess.parser.Util;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CalendarCommandParser {
    /**
     * Keywords for calendar interaction.
     */
    private static final String DISPLAY_KEYWORD = "display";
    private static final String EXPORT_KEYWORD = "export";

    /**
     * This class enables the user to interact with the calendar. It parses the
     * user input to determine which calendar interaction to execute and assures that the date
     * input by the user falls within the current academic calendar.
     * follows {@code calendar (export | display) /date <date>}.
     *
     * @param parameters user input mapped to keywords
     * @return either displays calendar or exports calendar
     * @throws DuchessException thrown if there are any missing or invalid parameters
     */
    public static Command parse(Map<String, String> parameters) throws DuchessException {
        try {
            String type = parameters.get("general");
            String date = parameters.get("date");
            LocalDate input = Util.parseDate(date);
            if (!AcademicYear.isWithinAcademicCalendar(input)) {
                throw new DuchessException("Please input a date within the current academic year.");
            }
            List<LocalDate> dateRange = Util.parseToWeekDates(input);
            LocalDate start = dateRange.get(0);
            LocalDate end = dateRange.get(1);
            if (type.equals(DISPLAY_KEYWORD)) {
                return new DisplayCalendarCommand(start, end);
            } else if (type.equals(EXPORT_KEYWORD)) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.updateUI();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                JFrame frame = new JFrame();
                frame.setAlwaysOnTop(true);
                int returnValue = jfc.showDialog(frame, "Select Folder");
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    if (!selectedFile.isDirectory()) {
                        selectedFile = selectedFile.getParentFile();
                    }
                    PrintStream file = new PrintStream(selectedFile + File.separator + "duchess.txt");
                    return new ExportCommand(start, end, file, selectedFile.toString());
                } else {
                    throw new DuchessException("Export command terminated.");
                }
            } else {
                throw new IllegalArgumentException();
            }
        } catch (NullPointerException | IllegalArgumentException | FileNotFoundException e) {
            throw new DuchessException(Parser.CALENDAR_USAGE);
        }
    }
}