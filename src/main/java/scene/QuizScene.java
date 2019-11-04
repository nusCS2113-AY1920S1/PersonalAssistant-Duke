package scene;

import command.QuizCommand;
import exception.ChangeSceneException;
import exception.InvalidAnswerException;
import exception.WordBankNotEnoughForQuizException;
import exception.CommandInvalidException;
import exception.WordUpException;
import dictionary.Bank;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;

public class QuizScene extends NewScene {
    private QuizCommand quizCommand;
    boolean startQuiz = false;
    private Integer countQuiz;
    private Integer wrongQuiz;
    private ArrayList<String> quizArray;

    /**
     * Instantiates a QuizScene.
     * @param storage object required to create quiz scene
     */
    public QuizScene(Ui ui, Bank bank, Storage storage, Stage window) {
        super(ui, bank, storage, ui.quizGreet(), window);
        setupHandleInput();
        this.countQuiz = 0;
        this.wrongQuiz = 0;
        this.quizArray = new ArrayList<>();
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public String getResponse(String userInput) throws
            ChangeSceneException,
            InvalidAnswerException,
            WordBankNotEnoughForQuizException,
            CommandInvalidException {
        if (userInput.equals("exit_quiz")) {
            throw new ChangeSceneException();
        } else if (!startQuiz && userInput.equals("start")) {
            this.generateQuiz();
            startQuiz = true;
            return ui.quizDisplay(quizCommand.question, quizCommand.options, quizCommand.optionSequence);
        } else {
            if (!startQuiz) {
                throw new CommandInvalidException(userInput);
            } else {
                String s;
                try {
                    int i = Integer.parseInt(userInput);
                    if (i < 1 || i > 4) {
                        throw new InvalidAnswerException();
                    }
                    if (userInput.equals(Integer.toString((4 - quizCommand.optionSequence) % 4 + 1))) {
                        s = ui.quizResponse(true, quizCommand.answer);
                    } else {
                        s = ui.quizResponse(false, quizCommand.answer);
                        quizArray.add(quizCommand.question + ": " + quizCommand.answer);
                        wrongQuiz += 1;
                    }

                    if (countQuiz < 5) {
                        this.generateQuiz();
                        return s + "\n"
                                + ui.quizDisplay(quizCommand.question, quizCommand.options, quizCommand.optionSequence);
                    } else {
                        startQuiz = false;
                        return s + "\n"
                                + ui.quizIncorrect(wrongQuiz, countQuiz, quizArray);
                    }


                } catch (NumberFormatException e) {

                    throw new InvalidAnswerException();
                }
            }

        }
    }

    private void generateQuiz() throws WordBankNotEnoughForQuizException {
        quizCommand = new QuizCommand();
        this.countQuiz += 1;
        //quizCommand.generateQuiz(wordBank);
        quizCommand.generateQuiz(bank.getWordBankObject());
    }

    @Override
    protected void handleUserInput() throws ChangeSceneException {
        Label userText = new Label(userInput.getText());
        Label dukeText;
        try {
            dukeText = new Label(getResponse(userInput.getText()));
        } catch (InvalidAnswerException | WordBankNotEnoughForQuizException | CommandInvalidException e) {
            dukeText = new Label(e.showError());
        }
        dialogContainer.getChildren().addAll(
                Box.getUserDialog(userText, new ImageView(user)),
                Box.getDukeDialog(dukeText, new ImageView(duke))
        );
        userInput.clear();
    }

    @Override
    public void resolveException(WordUpException e) {
        if (e instanceof ChangeSceneException) {
            window.setScene(new MainScene(ui, bank, storage, window).getScene());
        }
    }
}