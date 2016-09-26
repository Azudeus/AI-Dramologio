import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;


public class Hillclimbing {
	private ArrayList<Activity> arrAct;
	private ArrayList<Classroom> arrClass;
	private Scanner sc;

	Hillclimbing() {
		arrAct = new ArrayList<Activity>();
		arrClass = new ArrayList<Classroom>();
		fileReader();
	}

	private void fileReader() {
		try{
            sc = new Scanner(new File("test/input.txt"));
        } catch (FileNotFoundException e){
            System.out.println("File tidak ada");
            System.exit(0);
        } 
	}

	private ArrayList<Activity> parser() {
		String line = null;
		if (sc.hasNextLine()) {
			line = sc.nextLine();
		}
		while((sc.hasNextLine()) && !(line.trim() == "Jadwal")) {
			line = sc.nextLine();
			arrAct.add(Parser.parseActivity(line));
		}

		while(sc.hasNextLine()) {
			line = sc.nextLine();
			arrClass.add(Parser.parseClassroom(line));
		}
	}


}