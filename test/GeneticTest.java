import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by njruntuwene on 10/2/16.
 */
public class GeneticTest {
    @Test
    public void testRun() throws Exception {
        int step = 100;
        int popSize = 10;
        FileReader fr = new FileReader("Testcase.txt");
        ArrayList<Classroom> classrooms = fr.parseArrayClassroom();
        ArrayList<Activity> activities = fr.parseArrayActivity();

        Genetic genetic = new Genetic(popSize,step,activities,classrooms);
        CSP answer = genetic.run();

        answer.checkViolation();
        assertEquals(answer.countViolation(),0);
        answer.printAllActivity();
    }
}