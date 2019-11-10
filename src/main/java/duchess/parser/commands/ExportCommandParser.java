package duchess.parser.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.ExportCommand;
import duchess.parser.Parser;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;

public class ExportCommandParser {

    /**
     * Processes the file directory the user wants to save the calendar to and then calls the export command.
     *
     * @param date   input
     * @param isWeek true if user desires week view
     * @return ExportCommand
     * @throws DuchessException thrown if user selects invalid file or terminates process
     */
    public static Command parse(LocalDate date, boolean isWeek) throws DuchessException {
        try {
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
            return new ExportCommand(date, file, selectedFile.toString(), isWeek);
        } catch (FileNotFoundException e) {
            throw new DuchessException(Parser.CALENDAR_USAGE);
        }
    }
}
