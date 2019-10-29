package com.algosenpai.app.logic.command;

import com.algosenpai.app.utility.PdfDocumentWriterUtility;
import java.util.ArrayList;

public class PrintCommand extends Command {

    /**
     * Pdf writer.
     */
    PdfDocumentWriterUtility pdfWriter = new PdfDocumentWriterUtility();

    /**
     * Initializes command to show users how to use print functionality.
     * @param inputs input from user.
     */
    public PrintCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    /**
     * Returns message to show users how to use print functionality.
     * @return message to show print functionality.
     */
    @Override
    public String execute() {
        return "Please use the following format\n"
                + "print <archive | quiz |  user> <filename>.pdf";
    }

    boolean isPdfFileExtension(String filename) {
        return filename.toLowerCase().endsWith(".pdf");
    }

    boolean isEmpty(ArrayList<String> paragraphs) {
        return paragraphs.size() == 0;
    }

}
