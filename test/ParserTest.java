import org.junit.Test;

import java.util.ArrayList;

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
        ArrayList<Integer> days = Parser.parseDays(line);
        assertEquals(days.get(0),new Integer(1));
        assertEquals(days.get(1),new Integer(4));
        assertEquals(days.get(2),new Integer(5));
    }

    @Test
    public void testParseHour() throws Exception {
        line = "03.00";
        int hour = Parser.parseHour(line);
        assertEquals(hour,3);
    }
}