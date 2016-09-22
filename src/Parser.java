/**
 * Created by njruntuwene on 9/22/16.
 */

import java.util.regex.*;

public class Parser {
    static Matcher parse(String line) {
        Pattern pattern = Pattern.compile("((\\S*)(\\s*[;]\\s*)(.*?)(\\s*[;]\\s*)(\\S*))");

        Matcher matcher = pattern.matcher(line);
        return matcher;
    }
}
