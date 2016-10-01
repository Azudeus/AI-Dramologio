import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;


public class Hillclimbing extends CSP{
	private int violation;

	Hillclimbing() {

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
		int steps = 0;
		Activity saveState;
		String tempRoom;
		int tempDay;
		int tempStart;
		while ((violation!=0) && (steps < 3000)) {
			saveState = selectStep();
			tempRoom = saveState.getTempRoom();
			tempDay = saveState.getTempDay();
			tempStart = saveState.getStart();
			setRandomActivity(saveState);
			checkViolation();
			if (countViolation() <= violation) {
				violation = countViolation();
			} else {
				setSaveState(saveState,tempRoom,tempDay,tempStart);
			}
			steps++;
			//TEST XX06---------------------------------
		}
		printAllActivity();
	}

    public static void main(String[] args) {
        // TODO code application logic here
        new Hillclimbing().run();
    }


}