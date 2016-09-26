/**
 * Created by njruntuwene on 9/22/16.
 */

import java.util.regex.*;

public class Parser {
    public static Activity parseActivity(String line) {
        String name;
        String room;
        int startHour;
        int endHour;
        int duration;
        int[] days;
        Pattern pattern = Pattern.compile("(.*?)(\\s*[;]\\s*)(.*?)(\\s*[;]\\s*)(.*?)(\\s*[;]\\s*)(.*?)(\\s*[;]\\s*)(.*?)(\\s*[;]\\s*)(.*?)");

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
    }

    public static Classroom parseClassroom(String line) {
        String name;
        int openHour;
        int closeHour;
        int[] days;

        Pattern pattern = Pattern.compile("(.*?)(\\s*[;]\\s*)(.*?)(\\s*[;]\\s*)(.*?)(\\s*[;]\\s*)(.*?)");

        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            name = matcher.group(1);
            openHour = parseHour(matcher.group(3));
            closeHour = parseHour(matcher.group(5));
            days = parseDays(matcher.group(7));
            return new Classroom(name,openHour,closeHour);
        }
        return null;
    }

    public static int[] parseDays(String line) {
        int[] days;
        Pattern pattern = Pattern.compile("(\\d*)(,*)");

        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {

        }
        return days;
    }

    public static int parseHour(String line) {
        Pattern pattern = Pattern.compile("(0*)(\\d*)(.*)");

        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(2));
        }
        return -1;
    }
}
