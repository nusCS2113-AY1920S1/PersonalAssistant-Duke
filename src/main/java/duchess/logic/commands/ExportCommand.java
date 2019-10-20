package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.calendar.CalendarManager;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class ExportCommand extends Command {
    private File file;
    private LocalDate start;
    private LocalDate end;

    /**
     * Constructor for class. Instantiates start and end dates of calendar.
     *
     * @param dates array containing start and end dates
     * @throws DuchessException thrown when user closes file browser without choosing a file
     */
    public ExportCommand(List<LocalDate> dates) throws DuchessException {
        this.file = processFile();
        this.start = dates.get(0);
        this.end = dates.get(1);
    }

    private File processFile() throws DuchessException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.updateUI();
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //int returnValue = jfc.showOpenDialog(new JFrame());
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        int returnValue = jfc.showDialog(frame, "Select Folder");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            if (!selectedFile.isDirectory()) {
                selectedFile = selectedFile.getParentFile();
            }
            return new File(selectedFile + File.separator + "duchess.txt");
        }
        throw new DuchessException("Export command terminated.");
    }

    private void export(SortedMap<LocalTime, String[]> query, Ui ui) {
        try {
            PrintStream txtFW = new PrintStream(file);
            txtFW.println(ui.plainSeparator);
            txtFW.println(ui.processHeader(CalendarManager.getDateInformation(start)));
            txtFW.println(ui.blockSeparator);
            txtFW.println(ui.calendarHeader);
            txtFW.println(ui.blockSeparator);
            for (Map.Entry<LocalTime, String[]> entry : query.entrySet()) {
                String time = entry.getKey().toString().replaceAll(":", "");
                String[] strArr = entry.getValue();
                txtFW.println(ui.processRow(time, strArr));
                txtFW.println(ui.blockSeparator);
            }
            txtFW.flush();
            txtFW.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        SortedMap<LocalTime, String[]> query = CalendarManager.flatCalendar(store.getDuchessCalendar(), start, end);
        export(query, ui);
        ui.showFinishedExport(file.toString());
    }
}
