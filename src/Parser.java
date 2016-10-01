/**
 * Created by njruntuwene on 9/22/16.
 */

import java.util.regex.*;

public class Parser {
    public static Activity parseActivity(String line) throws Exception{
        String name;
        String room;
        int startHour;
        int endHour;
        int duration;
        boolean[] days;
        Pattern pattern = Pattern.compile("(.*?)(\\s*[;]\\s*)(.*?)(\\s*[;]\\s*)(.*?)(\\s*[;]\\s*)(.*?)(\\s*[;]\\s*)(.*?)(\\s*[;]\\s*)(.*)");

        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            name = matcher.group(1);
            room = matcher.group(3);
            startHour = parseHour(matcher.group(5));
            endHour = parseHour(matcher.group(7));
            duration = Integer.parseInt(matcher.group(9));
            days = parseDays(matcher.group(11));
            return new Activity(name,days,startHour,endHour,duration,room);
        }
        throw new Exception("Pattern not found");
    }

    public static Classroom parseClassroom(String line) throws Exception {
        String name;
        int openHour;
        int closeHour;
        boolean[] days;

        Pattern pattern = Pattern.compile("(.*?)(\\s*[;]\\s*)(.*?)(\\s*[;]\\s*)(.*?)(\\s*[;]\\s*)(.*)");

        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            name = matcher.group(1);
            openHour = parseHour(matcher.group(3));
            closeHour = parseHour(matcher.group(5));
            days = parseDays(matcher.group(7));
            return new Classroom(name,openHour,closeHour,days);
        }
        throw new Exception("Pattern not found");
    }

    public static boolean[] parseDays(String line) {
        boolean[] days = new boolean[7];
        Pattern pattern = Pattern.compile("(\\d+)(,*)");

        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            days[Integer.parseInt(matcher.group(1)) % 7] = true;
        }
        return days;
    }

    public static int parseHour(String line) throws Exception {
        Pattern pattern = Pattern.compile("(0*)(\\d*)(.*)");

        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(2));
        }
        throw new Exception("Pattern not found");
    }
}
