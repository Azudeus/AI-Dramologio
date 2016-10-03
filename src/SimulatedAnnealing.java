import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.math.*;

public class SimulatedAnnealing extends CSP{
	private int violation;
	private int steps;
	private double temperature;
	private double r;

	SimulatedAnnealing(ArrayList<Activity> act, ArrayList<Classroom> cls, int steps) {
		super(act,cls);
		this.steps = steps;
		temperature = 100;
		r = 0.97;
	}

	public Activity selectStep() {
		int errLength = arrError.size();
		Activity ret = arrError.get(0).getFirst();
		int mostFreq = 0;
		int occurence;
		for (int i=0 ; i<errLength ; i++) {			
			occurence = 0;
			for (int j=0 ; j<errLength ; j++) {
				if (arrError.get(i).getFirst() == arrError.get(j).getFirst()) {
					occurence++;
				} else if (arrError.get(i).getFirst() == arrError.get(j).getSecond()) {
					occurence++;
				}
			}
			if (occurence > mostFreq) {
				mostFreq = occurence;
				ret = arrError.get(i).getFirst();
			}
		}
		for (int i=0 ; i<errLength ; i++) {			
			occurence = 0;
			for (int j=0 ; j<errLength ; j++) {
				if (arrError.get(i).getSecond() == arrError.get(j).getFirst()) {
					occurence++;
				} else if (arrError.get(i).getSecond() == arrError.get(j).getSecond()) {
					occurence++;
				}
			}
			if (occurence > mostFreq) {
				mostFreq = occurence;
				ret = arrError.get(i).getSecond();
			}
		}
		return ret;
	}

	public void setSaveState(Activity act,String tr,int td, int ts) {
		act.setTempRoom(tr);
		act.setTempDay(td);
		act.setStart(ts);
	}

	public void run() {
		setRandomAllActivity();
		checkViolation();
		violation = countViolation();
		Activity saveState;
		String tempRoom;
		int tempDay;
		int tempStart;
		double delta;
		double exp;
		double probability;
		double comp;
		int i = 0;
		//System.out.println("violation = " + violation);
		//System.out.println("steps = " + steps);
		while ((violation!=0) && (i < steps)) {
			//System.out.println("masuk while");
			saveState = selectStep();
			tempRoom = saveState.getTempRoom();
			tempDay = saveState.getTempDay();
			tempStart = saveState.getStart();
			setRandomActivity(saveState);
			checkViolation();
			//System.out.println(violation);
			//System.out.println(countViolation());
			if (countViolation() <= violation) {
				//System.out.println("masuk");
				violation = countViolation();
			} else {
				//System.out.println("masuk2");
				delta = violation - countViolation();
				exp = delta / temperature;
				probability = Math.exp(exp);
				//System.out.println(probability);
				Random rand = new Random();
				comp = rand.nextFloat();
				//System.out.println(comp);
				if (probability >= comp){
					violation = countViolation();
				}
				else{
					setSaveState(saveState,tempRoom,tempDay,tempStart);
				}
			}
			temperature = temperature * r;
			i++;
			//System.out.println ("Violation" + countViolation());
		}
		printAllActivity();
		System.out.println("Jumlah bentrok: " + violation);
	
	}

    public static void main(String[] args) {
       try{
	  		FileReader fileReader = new FileReader(args[0]);
	  		ArrayList<Classroom> classrooms = fileReader.parseArrayClassroom();
	  		ArrayList<Activity> activities = fileReader.parseArrayActivity();
	        new SimulatedAnnealing(activities,classrooms,Integer.parseInt(args[1])).run();
    	} catch (ArrayIndexOutOfBoundsException e){
    		System.out.println("Input not enough, please put filename and how many steps do you want");
    		System.exit(0);
    	}
    }
}