import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class CSP {
	private ArrayList<Activity> arrAct;
	private ArrayList<Classroom> arrClass;
	private Scanner sc;

	CSP() {
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

	private void parser() {
		String line = null;
		if (sc.hasNextLine()) {
			line = sc.nextLine();
		}
		while ((sc.hasNextLine()) && !(line.trim() == "Jadwal")) {
			line = sc.nextLine();
			arrAct.add(Parser.parseActivity(line));
		}

		while (sc.hasNextLine()) {
			line = sc.nextLine();
			arrClass.add(Parser.parseClassroom(line));
		}
	}


	public Classroom findClassroom(String name) {
		boolean found = false;
		int i=0;
		int clsLength = arrClass.size();
		while ((!found) && (i<clsLength)) {
			if (arrClass.get(i).getName() == name) {
				found = true;
			} else {
				i++;
			}
		}
		return arrClass.get(i);

	}


	public Classroom randomClass() {
		int clsLength = arrClass.size();
		Random rand = new Random();
		int indexClass = rand.nextInt(clsLength);
		return arrClass.get(indexClass);
	}

	public void setRandomActivity(Activity act) {
		Classroom cls;
		int lmtStart=999;
		int lmtFinish=-999;

		if (act.getRoom() == "-") {
			do{
				cls = randomClass();
				if (cls.getOpenTime() >= act.getLmtStart()) {
					lmtStart = cls.getOpenTime();
				} else {
					lmtStart = act.getLmtStart();
				}

				if (cls.getClosedTime() < act.getLmtFinish()) {
					lmtFinish = cls.getClosedTime();
				} else {
					lmtFinish = act.getLmtFinish();
				}
			}while(lmtFinish <= (lmtStart+act.getDuration()));
		} else {
			cls = findClassroom(act.getRoom());
			if (cls.getOpenTime() >= act.getLmtStart()) {
				lmtStart = cls.getOpenTime();
			} else {
				lmtStart = act.getLmtStart();
			}

			if (cls.getClosedTime() < act.getLmtFinish()) {
				lmtFinish = cls.getClosedTime();
			} else {
				lmtFinish = act.getLmtFinish();
			}
			// EXCEPTION
			if (lmtFinish <= (lmtStart + act.getDuration())) {
				System.out.println("Activity Can't fit to the class, Program Exiting");
				System.exit(0);
			}
		}
		act.setTempRoom(cls.getName());
		act.setRandomStart(lmtStart,lmtFinish);
	}

	public void setRandomAllActivity() {
		int actLength = arrAct.size();
		for (int i=0 ; i<actLength ; i++) {
			setRandomActivity(arrAct.get(i));
		}
	}

	public int checkViolation() {
		int actLength = arrAct.size();
		int actClass = arrClass.size();
		int ret = 0;
		for (int i=0; i<actLength ; i++) {
			for(int j=i+1 ; j<actLength ; j++) {
				if (arrAct.get(i).getTempRoom() == arrAct.get(j).getTempRoom()) {
					if ((arrAct.get(i).getStart() > arrAct.get(j).getStart()) && ((arrAct.get(j).getStart()+arrAct.get(j).getDuration()) > arrAct.get(i).getStart())) {
						ret++;
					} else if ((arrAct.get(j).getStart() > arrAct.get(i).getStart()) && ((arrAct.get(i).getStart()+arrAct.get(i).getDuration()) > arrAct.get(j).getStart())) {
						ret++;
					} 
				}
			}
		}
		return ret;
	}
}