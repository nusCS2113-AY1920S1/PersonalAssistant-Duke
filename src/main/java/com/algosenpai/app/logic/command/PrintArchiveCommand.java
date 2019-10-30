package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.models.QuestionModel;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PrintArchiveCommand extends PrintCommand {

    /**
     * List of questions and answers.
     */
    private ArrayList<QuestionModel> archiveList;

    /**
     * Paragraphs to write to pdf.
     */
    private ArrayList<String> paragraphs;

    /**
     * Initializes command to print archive.
     * @param inputs user inputs.
     * @param archiveList list of questions to write to pdf.
     */
    public PrintArchiveCommand(ArrayList<String> inputs, ArrayList<QuestionModel> archiveList) {
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
     * Writes quiz to pdf and returns status message.
     * @return status message
     */
    @Override
    public String execute() {
        if (!isPdfFileExtension(inputs.get(2))) {
            return "Wrong file extension";
        }
        if (isEmpty(paragraphs)) {
            return "Nothing in quiz";
        }
        try {
            pdfWriter.saveToPdf(paragraphs, inputs.get(2));
            return "Successfully write archive to pdf";
        } catch (DocumentException | FileNotFoundException e) {
            return "Error writing archive to file";
        }
    }
}
