//@@author Xdecosee

package wallet.logic.command;

import com.opencsv.CSVWriter;
import wallet.model.Wallet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_ERROR_WRITING_CSV = "Error in writing to csv!";
    public static final String MESSAGE_SUCCESS_WRITING_CSV = "Export success! File saved to -> ";
    private List<String[]> data;
    private String type;

    /**
     * Constructs the EditCommand object with data and type.
     *
     * @param data Processed Data from memory store.
     * @param type type of data.
     */
    public ExportCommand(List<String[]> data, String type) {
        this.data = data;
        this.type = type;
    }

    /**
     * Exports data into home folder.
     *
     * @param wallet The Wallet Object.
     * @return false.
     */
    @Override
    public boolean execute(Wallet wallet) {

        try {
            File current = new File(ExportCommand.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI().getPath());
            File csv = null;
            FileWriter output;
            LocalDateTime localDate = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH.mm.ss");
            String currentDate = localDate.format(formatter);

            if ("expense".equals(type)) {
                csv = new File(current.getParentFile().getPath(), "WalletCLi-expenses-" + currentDate + ".csv");
            } else if ("loan".equals(type)) {
                csv = new File(current.getParentFile().getPath(), "WalletCLi-loans-" + currentDate + ".csv");
            }

            if (csv != null) {
                output = new FileWriter(csv);
                CSVWriter writer = new CSVWriter(output);
                writer.writeAll(data);
                writer.close();
                output.close();
                System.out.println(MESSAGE_SUCCESS_WRITING_CSV + csv);
            } else {
                System.out.println(MESSAGE_ERROR_WRITING_CSV);
            }

        } catch (URISyntaxException e) {
            System.out.println(MESSAGE_ERROR_WRITING_CSV);
        } catch (IOException e) {
            System.out.println(MESSAGE_ERROR_WRITING_CSV);
        }


        return false;
    }

}
