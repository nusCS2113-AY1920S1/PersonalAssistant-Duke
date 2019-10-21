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
    private List<String[]> data;

    public ExportCommand(List<String[]> data) {
        this.data = data;
    }

    @Override
    public boolean execute(Wallet wallet) {
        File loanCsv;
        FileWriter output;
        try {
            File current = new File(ExportCommand.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI().getPath());
            loanCsv = new File(current.getParentFile().getPath(), "exportedLoans.csv");
            output = new FileWriter(loanCsv);
            CSVWriter writer = new CSVWriter(output);
            writer.writeAll(data);
            writer.close();
            System.out.println("Export success! File saved to -> " + loanCsv);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error in writing to csv!");
        }


        return false;
    }

}
