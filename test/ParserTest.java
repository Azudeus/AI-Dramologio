import org.junit.Before;
import org.junit.Test;
import java.util.regex.*;
import static org.junit.Assert.*;

/**
 * Created by njruntuwene on 9/22/16.
 */
public class ParserTest {
    private String line;
    @Before
    public void setUp() throws Exception {
        line = "IF1234 ; 1, 2 , 3 ;123";
    }

    @Test
    public void test_parse() throws Exception {
        Matcher matcher = Parser.parse(line);
        System.out.println(matcher.find());
        System.out.println(matcher.group(0));
        System.out.println(matcher.group(1));
        System.out.println(matcher.group(2));
        System.out.println(matcher.group(3));
        System.out.println(matcher.group(4));
        System.out.println(matcher.group(5));
        System.out.println(matcher.group(6));
        assertEquals(1,1);
    }
}