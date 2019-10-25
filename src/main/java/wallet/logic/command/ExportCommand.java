//@@author Xdecosee

package wallet.logic.command;

import com.opencsv.CSVWriter;
import wallet.model.Wallet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_ERROR_WRITING_CSV = "Error in writing to csv!";
    public static final String MESSAGE_SUCCESS_WRITING_CSV = "Export success! File saved to -> ";
    private List<String[]> data;
    private String type;

    public ExportCommand(List<String[]> data, String type) {
        this.data = data;
        this.type = type;
    }

    @Override
    public boolean execute(Wallet wallet) {
        File csv = null;
        FileWriter output;
        try {
            File current = new File(ExportCommand.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI().getPath());
            if ("expenses".equals(type)) {
                csv = new File(current.getParentFile().getPath(), "exportedExpenses.csv");
            } else if ("loans".equals(type)) {
                csv = new File(current.getParentFile().getPath(), "exportedLoans.csv");
            }

            if (csv != null) {
                output = new FileWriter(csv);
                CSVWriter writer = new CSVWriter(output);
                writer.writeAll(data);
                writer.close();
                System.out.println(MESSAGE_SUCCESS_WRITING_CSV + csv);
            } else {
                System.out.println(MESSAGE_ERROR_WRITING_CSV);
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(MESSAGE_ERROR_WRITING_CSV);
        }


        return false;
    }

}
