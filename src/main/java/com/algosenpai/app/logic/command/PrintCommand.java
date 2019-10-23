package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.stats.ChapterStat;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.utility.PdfDocumentWriterUtility;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class PrintCommand extends Command {

    private ArrayList<QuestionModel> printList;
    private UserStats userStats;

    private ArrayList<String> paragraphs;

    private PdfDocumentWriterUtility pdfWriter = new PdfDocumentWriterUtility();

    /**
     * Create new command.
     * @param inputs input from user.
     */
    private PrintCommand(ArrayList<String> inputs) {
        super(inputs);
        this.paragraphs = new ArrayList<>();
    }



    /**
     * Creates print command.
     * @param inputs user inputs.
     * @param printList list of questions to write to pdf.
     */
    public PrintCommand(ArrayList<String> inputs, ArrayList<QuestionModel> printList) {
        this(inputs);
        this.printList = printList;
        this.prepareQuestions();
    }

    /**
     * Creates print command.
     * @param inputs user inputs.
     * @param userStats user stats to write to pdf.
     */
    public PrintCommand(ArrayList<String> inputs, UserStats userStats) {
        this(inputs);
        this.userStats = userStats;
        this.prepareUserStates();
    }

    /**
     * Prepares question to write to pdf.
     */
    private void prepareQuestions() {
        int questionCount = 1;
        for (QuestionModel question: printList) {
            paragraphs.add("Q" + questionCount++ + ")");
            paragraphs.add(question.getQuestion());
            paragraphs.add(question.getUserAnswer());
            paragraphs.add(question.getAnswer());
            paragraphs.add(question.checkAnswer() ? "Correct" : "Wrong");
        }
    }

    /**
     * Prepares user stats to write to pdf.
     */
    private void prepareUserStates() {
        paragraphs.add(userStats.toString());
    }

    @Override
    public String execute() {
        try {
            pdfWriter.saveToPdf(paragraphs, inputs.get(1));
            return "Successfully write to pdf";
        } catch (DocumentException | FileNotFoundException e) {
            return "Error writing to file";
        }
    }
}
