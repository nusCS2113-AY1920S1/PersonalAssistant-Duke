package com.algosenpai.app.logic.command.utility.print;

import com.algosenpai.app.stats.UserStats;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PrintUserCommand extends PrintCommand {

    /**
     * User stats.
     */
    private UserStats userStats;

    /**
     * Paragraphs to write to pdf.
     */
    private ArrayList<String> paragraphs;

    /**
     * Initializes command to print user status.
     * @param inputs user inputs.
     * @param userStats user stats to write to pdf.
     */
    PrintUserCommand(ArrayList<String> inputs, UserStats userStats) {
        super(inputs);
        this.userStats = userStats;
        this.paragraphs = new ArrayList<>();
        this.prepareUserStates();
    }

    /**
     * Prepares user stats to write to pdf.
     */
    private void prepareUserStates() {
        paragraphs.add(userStats.toString());
    }

    /**
     * Writes user stats to pdf and returns status message.
     * @return status message.
     */
    @Override
    public String execute() {
        if (!isPdfFileExtension(inputs.get(2))) {
            return "Wrong file extension";
        }
        try {
            pdfWriter.saveToPdf(paragraphs, inputs.get(2));
            return "Successfully write to pdf";
        } catch (DocumentException | FileNotFoundException e) {
            return "Error writing to file";
        }
    }
}
