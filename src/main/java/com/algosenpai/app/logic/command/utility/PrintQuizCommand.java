package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.logic.models.QuestionModel;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PrintQuizCommand extends PrintCommand {

    /**
     * List of questions and answers.
     */
    private ArrayList<QuestionModel> quizList;

    /**
     * Paragraphs to write to pdf.
     */
    private ArrayList<String> paragraphs;

    /**
     * Initializes command to print quiz.
     * @param inputs user inputs.
     * @param quizList list of questions to write to pdf.
     */
    public PrintQuizCommand(ArrayList<String> inputs, ArrayList<QuestionModel> quizList) {
        super(inputs);
        this.quizList = quizList;
        this.paragraphs = new ArrayList<>();
        this.prepareQuiz();
    }

    /**
     * Prepares question to write to pdf.
     */
    private void prepareQuiz() {
        int questionCount = 1;
        for (QuestionModel question: quizList) {
            paragraphs.add("Q" + questionCount++ + ")");
            paragraphs.add(question.getQuestion());
            paragraphs.add(question.getUserAnswer());
            paragraphs.add(question.getAnswer());
            paragraphs.add(question.checkAnswer() ? "Correct" : "Wrong");
        }
    }

    /**
     * Writes quiz to pdf and returns status message.
     * @return status message.
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
            return "Successfully write quiz to pdf";
        } catch (DocumentException | FileNotFoundException e) {
            return "Error writing quiz to file";
        }
    }
}
