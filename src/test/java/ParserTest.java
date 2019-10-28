import exceptions.FarmioException;
import farmio.Farmio;
import org.junit.jupiter.api.Test;
import farmio.Parser;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void doTest(){
        try {
            Parser.parse("do", farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("do gotomarketa", farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("do gotomarket", farmio.Stage.TASK_ADD);
            assert true;
        } catch (FarmioException e) {
            assert false;
        }
        try {
            Parser.parse("do buyseeds", farmio.Stage.TASK_ADD);
            assert true;
        } catch (FarmioException e) {
            assert false;
        }
    }

    @Test
    public void ifTest() {
        try {
            Parser.parse("if", farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("if hasseeds do plantseeds", farmio.Stage.TASK_ADD);
            assert true;
        } catch (FarmioException e) {
            assert false;
        }
        try {
            Parser.parse("if hasseed do plantseeds", farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("if hasseeds do plantseed", farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
    }
}
