package exception;

public class WordBankNotEnoughForQuizException extends WordUpException {
    public WordBankNotEnoughForQuizException() {
        super("Words not enough. Need at least 4 words to make a quiz! \n" +
                "Please type \"exit_quiz\" to go back to main to main page to add more words");
    }
}
