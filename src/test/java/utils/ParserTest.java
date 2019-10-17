package utils;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {
    @Test
    public void dateTest() throws DukeException {
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        String testDate = "25/10/2019 1030";
        try {
            Date tempz = ft.parse(testDate);
            assertEquals(tempz, Parser.parseDate(testDate));
        } catch (ParseException e) {
            throw new DukeException("Invalid date format, the correct format is: dd/MM/yyyy");
        }
    }

    @Test
    public void similarityTest() {
        // equal test
        for (int i = 0; i < 10; i++) {
            String randomString = "";
            while (true) {
                int randomInt = (int) (Math.random() * 96) + 30;
                char randomChar = (char) randomInt;
                randomString += randomChar;
                if (randomInt < 40) {
                    break;
                }
            }
            System.out.println("randomString is: " + randomString);
            assertEquals(1, Parser.getSimilarity(randomString, randomString));
        }

        // mismatch test under same length
        for (int i = 0; i < 10; i++) {
            String randomString1 = "";
            String randomString2 = "";
            while (true) {
                int randomInt1 = (int) (Math.random() * 96) + 30;
                int randomInt2 = (int) (Math.random() * 96) + 30;
                char randomChar1 = (char) randomInt1;
                char randomChar2 = (char) randomInt2;
                if (Math.random() > 0.3 && (!randomString1.equals(randomString2))) {
                    randomString2 += randomChar1;
                } else {
                    randomString2 += randomChar2;
                }
                randomString1 += randomChar1;
                if (randomInt1 < 40) {
                    break;
                }
            }
            double similarity = Parser.getSimilarity(randomString1, randomString2);
            System.out.println("randomStrings are: " + randomString1 + ", " + randomString2);
            System.out.println("The similarity is: " + similarity);
            assertTrue(similarity < 1 && similarity >= 0);
        }
    }
}
