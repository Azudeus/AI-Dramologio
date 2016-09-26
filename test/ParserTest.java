import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by njruntuwene on 9/22/16.
 */
public class ParserTest {
    private String line;

    @Test
    public void testParseActivity() throws Exception {
        line = "IF2110;7602;07.00;12.00;4;1,2,3,4,5";
        Activity activity = Parser.parseActivity(line);
    }

    @Test
    public void testParseClassroom() throws Exception {
        line = "Labdas2;10.00;14.00;2,4";
        Classroom classroom = Parser.parseClassroom(line);
    }

    @Test
    public void testParseDays() throws Exception {
        line = "1,4,5";
        int[] days = Parser.parseDays(line);
    }

    @Test
    public void testParseHour() throws Exception {
        line = "03.00";
        int hour = Parser.parseHour(line);
        assertEquals(hour,3);
    }
}