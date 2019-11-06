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
import java.util.Map;

public class CalendarCommandParser {
    /**
     * Keywords for calendar interaction.
     */
    private static final String DISPLAY_KEYWORD = "display";
    private static final String EXPORT_KEYWORD = "export";
    private static final String WEEK_KEYWORD = "week";
    private static final String DAY_KEYWORD = "day";

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
            String view = parameters.get("view");
            boolean isValidView = view.equals(WEEK_KEYWORD) || view.equals(DAY_KEYWORD);
            boolean isValidCommand = type.equals(DISPLAY_KEYWORD) || type.equals(EXPORT_KEYWORD);
            LocalDate input = Util.parseDate(date);
            if (!AcademicYear.isWithinAcademicCalendar(input)) {
                throw new DuchessException("Please input a date within the current academic year.");
            } else if (!isValidCommand) {
                throw new DuchessException(Parser.CALENDAR_USAGE);
            } else if (!isValidView) {
                throw new DuchessException("Please input either a day or week view.");
            }

            boolean isWeek = view.equals(WEEK_KEYWORD);

            if (type.equals(DISPLAY_KEYWORD)) {
                return new DisplayCalendarCommand(input, isWeek);
            } else {
                assert (type.equals(EXPORT_KEYWORD));
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.updateUI();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                JFrame frame = new JFrame();
                frame.setAlwaysOnTop(true);
                int returnValue = jfc.showDialog(frame, "Select Folder");
                PrintStream file;
                File selectedFile;
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = jfc.getSelectedFile();
                    if (!selectedFile.isDirectory()) {
                        selectedFile = selectedFile.getParentFile();
                    }
                    file = new PrintStream(selectedFile + File.separator + "duchess.txt");
                } else {
                    throw new DuchessException("Export command terminated.");
                }
                return new ExportCommand(input, file, selectedFile.toString(), isWeek);
            }
        } catch (NullPointerException | IllegalArgumentException | FileNotFoundException e) {
            throw new DuchessException(Parser.CALENDAR_USAGE);
        }
    }
}