package javacake.topics;

import javacake.DukeException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

public class TopicProgress {

    public enum TopicDifficulty {

    }

    private String compileResult(BufferedReader br, String bufferResult) throws IOException{
        if ((br.readLine()).equals("0")) {
            return "0";
        }
        else {
            return "1";
        }
    }

    public String viewSubTopic1Result() throws DukeException {
        String bufferResult = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("content/Result/ListIndex1/javabasics/EasyQuiz.txt"));
            bufferResult += compileResult(br, bufferResult);
            System.out.println(bufferResult);
            br = new BufferedReader(new FileReader("content/Result/ListIndex1/javabasics/MediumQuiz.txt"));
            bufferResult += compileResult(br, bufferResult);
            System.out.println(bufferResult);
            br = new BufferedReader(new FileReader("content/Result/ListIndex1/javabasics/HardQuiz.txt"));
            bufferResult += compileResult(br, bufferResult);
            System.out.println(bufferResult);
            return bufferResult;
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }

    }
    public static void main(String[] args) throws DukeException {


    }

}
