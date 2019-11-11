package com.algosenpai.app.logic.command.utility.print;

import com.algosenpai.app.logic.chapters.LectureGenerator;
import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.utility.LogCenter;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class PrintArchiveCommand extends PrintCommand {

    /**
     * List of archived questions and answers.
     */
    private ArrayList<QuestionModel> archiveList;

    /**
     * Paragraphs to write to pdf.
     */
    private ArrayList<String> paragraphs;

    private static final Logger logger = LogCenter.getLogger(PrintArchiveCommand.class);

    /**
     * Initializes command to print archive.
     * @param inputs user inputs.
     * @param archiveList list of questions to write to pdf.
     */
    PrintArchiveCommand(ArrayList<String> inputs, ArrayList<QuestionModel> archiveList) {
        super(inputs);
        this.archiveList = archiveList;
        this.paragraphs = new ArrayList<>();
        this.prepareQuiz();
    }

    /**
     * Prepares question to write to pdf.
     */
    private void prepareQuiz() {
        int questionCount = 1;
        for (QuestionModel question: archiveList) {
            paragraphs.add("Q" + questionCount++ + ")");
            paragraphs.add(question.getQuestion());
            paragraphs.add(question.getUserAnswer());
            paragraphs.add(question.getAnswer());
            paragraphs.add(question.checkAnswer() ? "Correct" : "Wrong");
        }
    }

    /**
     * Writes archived questions and answers to pdf and returns status message.
     * @return status message
     */
    @Override
    public String execute() {
        if (!isPdfFileExtension(inputs.get(2))) {
            return "Wrong file extension";
        }
        if (isEmpty(paragraphs)) {
            return "Nothing in archive";
        }
        try {
            pdfWriter.saveToPdf(paragraphs, inputs.get(2));
            logger.info("Archived questions have successfully been written to the pdf file.");
            return "The questions you have archived have been successfully export to the pdf file!"
                    + " Happy reviewing! \\(ᵔᵕᵔ)/";
        } catch (DocumentException | FileNotFoundException e) {
            logger.severe("Oh no! Your questions were not able to be exported to the pdf file (ᵟຶ︵ ᵟຶ)");
            return "Error writing archive to file";
        }
    }
}
