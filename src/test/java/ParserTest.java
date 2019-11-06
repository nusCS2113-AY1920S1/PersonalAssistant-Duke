import exceptions.FarmioException;
import farmio.Farmio;
import farmio.Farmio.Stage;
import org.junit.jupiter.api.Test;
import farmio.Parser;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void doTest(){
        try {
            Parser.parse("do", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("do gotomarketa", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("do gotomarket", Farmio.Stage.TASK_ADD);
            assert true;
        } catch (FarmioException e) {
            assert false;
        }
        try {
            Parser.parse("do buyseeds", Farmio.Stage.TASK_ADD);
            assert true;
        } catch (FarmioException e) {
            assert false;
        }
    }

    @Test
    public void ifTest() {
        try {
            Parser.parse("if", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("if hasseeds do plantseeds", Farmio.Stage.TASK_ADD);
            assert true;
        } catch (FarmioException e) {
            assert false;
        }
        try {
            Parser.parse("if hasseed do plantseeds", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("if hasseeds do plantseed", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
    }
}
