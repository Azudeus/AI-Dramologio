import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by njruntuwene on 10/2/16.
 */
public class FileReader {
    private Scanner sc;

    public FileReader() {
        this("Testcase.txt");
    }

    public FileReader(String inputFile) {
        try{
            sc = new Scanner(new File(inputFile));
        } catch (FileNotFoundException e){
            System.out.println("File not found");
            System.exit(0);
        }
    }

    public ArrayList<Classroom> parseArrayClassroom() {
        ArrayList<Classroom> classrooms = new ArrayList<>();

        String line = null;

        while ((sc.hasNextLine()) && !Objects.equals("Jadwal", (line != null) ? line.trim() : null)) {
            line = sc.nextLine();
            try {
                classrooms.add(Parser.parseClassroom(line));
            } catch (Exception e) {

            }
        }
        return classrooms;
    }

    public ArrayList<Activity> parseArrayActivity() {
        ArrayList<Activity> activities = new ArrayList<>();

        String line = null;

        while ((sc.hasNextLine()) && !Objects.equals("Ruangan", (line != null) ? line.trim() : null)) {
            line = sc.nextLine();
            try {
                activities.add(Parser.parseActivity(line));
            } catch (Exception e) {

            }
        }
        return activities;
    }
}
