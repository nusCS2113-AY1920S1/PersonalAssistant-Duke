package com.algosenpai.app.logic.command.utility.print;

import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.utility.PdfDocumentWriterUtility;
import java.util.ArrayList;

public class PrintCommand extends Command {

    private ArrayList<QuestionModel> quizList;
    private ArrayList<QuestionModel> archiveList;
    private UserStats userStats;

    /**
     * Pdf writer.
     */
    PdfDocumentWriterUtility pdfWriter = new PdfDocumentWriterUtility();

    /**
     * Initializes command to decide what print action to take.
     * @param inputs input from user.
     */
    PrintCommand(ArrayList<String> inputs) {
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
