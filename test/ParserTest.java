/**
 * Created by Nathan on 9/22/16.
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class ParserTest {
    private String line;

    @Test
    public void testParseActivity() throws Exception {
        line = "IF2110;7602;07.00;12.00;4;1,2,3,4,5";
        Activity activity = Parser.parseActivity(line);
        assertEquals(activity.getName(),"IF2110");
        assertEquals(activity.getRoom(),"7602");
        assertEquals(activity.getLmtStart(),7);
        assertEquals(activity.getLmtFinish(),12);
        assertEquals(activity.getDuration(),4);
    }

    @Test
    public void testParseClassroom() throws Exception {
        line = "Labdas2;10.00;14.00;2,4";
        Classroom classroom = Parser.parseClassroom(line);
        assertEquals(classroom.getName(),"Labdas2");
        assertEquals(classroom.getOpenTime(),10);
        assertEquals(classroom.getClosedTime(),14);
    }

    @Test
    public void testParseDays() throws Exception {
        line = "1,4,5";
        boolean[] days = Parser.parseDays(line);
        assertTrue(days[1]);
        assertFalse(days[2]);
        assertFalse(days[3]);
        assertTrue(days[4]);
        assertTrue(days[5]);
    }

    @Test
    public void testParseHour() throws Exception {
        line = "03.00";
        int hour = Parser.parseHour(line);
        assertEquals(hour,3);
    }
}