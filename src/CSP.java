import java.util.Objects;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


public class CSP {
	protected ArrayList<Activity> arrAct;
	protected ArrayList<Classroom> arrClass;
	protected ArrayList<PairActivity> arrError;
	private Scanner sc;

	public static final int NOT_FOUND = -999;

	CSP() {
		arrAct = new ArrayList<Activity>();
		arrClass = new ArrayList<Classroom>();
		arrError = new ArrayList<PairActivity>();
		fileReader();
		parser();

		//Test XX01-----------------------------------------------
		System.out.println(arrAct.size());
		for (int i=0 ; i<arrAct.size();i++) {
			System.out.println(arrAct.get(i).getName());
		}
		System.out.println(arrClass.size());
		for(int i=0 ; i<arrClass.size(); i++) {
			System.out.println(arrClass.get(i).getName());
		}
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
			sc.nextLine();
			line = sc.nextLine();
		}
		try {
			while ((sc.hasNextLine()) && !Objects.equals("Ruangan", (line != null) ? line.trim() : null)) {
				arrAct.add(Parser.parseActivity(line));
				line = sc.nextLine();
			}

			while (sc.hasNextLine()) {
				line = sc.nextLine();
				arrClass.add(Parser.parseClassroom(line));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}


	public Classroom findClassroom(String name) {
		boolean found = false;
		int i=0;
		int clsLength = arrClass.size();
		while ((!found) && (i<clsLength)) {
			if (Objects.equals(arrClass.get(i).getName(), name)){
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

	private ArrayList<Integer> checkDay(Activity act, Classroom cls) {
		boolean[] dayValid = new boolean[7];
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (int i=0; i<7 ; i++) {
			dayValid[i] = act.getDay()[i] & cls.getDay()[i];
			if (dayValid[i]) {
				ret.add(i);
			}
		}
		return ret;
	}

	private Integer randomDay(Activity act, Classroom cls) {
		Random rand = new Random();
		ArrayList<Integer> listDay = checkDay(act,cls);
		//TEST XX05----------------------------------------------------
		System.out.println(act.getName());
		System.out.println(act.getDay()[3]);
		System.out.println(cls.getName());
		System.out.println(listDay.size());
		System.out.println("Succesfully checkday");
		if (listDay.size() != 0) {
			System.out.println("Succesfully entering found day cases");
			int dayLength = listDay.size();
			int indexDay = rand.nextInt(dayLength);	
			return listDay.get(indexDay);
		} else {
			System.out.println("Succesfully entering NOT found day cases");
			return NOT_FOUND;
		}
	}

	public void setRandomActivity(Activity act) {
		Classroom cls;
		int tempDay;
		int lmtStart=999;
		int lmtFinish=-999;

		if (Objects.equals(act.getRoom(), new String("-"))) {
			//Test XX03-----------------------------------------------------
			System.out.println("Entering case room -");
			do{
				cls = randomClass();
				tempDay = randomDay(act,cls);
				if(tempDay != NOT_FOUND){
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
				}
			}while(lmtFinish <= (lmtStart+act.getDuration()));
		} else {
			System.out.println("Entering case room listed");
			cls = findClassroom(act.getRoom());
			//TEST XX04---------------------------------------------------------
			System.out.println("Succesfully finding classroom");
			tempDay = randomDay(act,cls);
			System.out.println("Succesfully randoming day");
			System.out.println(tempDay);
			if(tempDay!=NOT_FOUND){
				System.out.println("Succesfully entered comparing time");
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
			}
			System.out.println(lmtStart);
			System.out.println(lmtFinish);
			System.out.println(act.getDuration());
			// EXCEPTION
			if (lmtFinish < (lmtStart + act.getDuration())) {
				System.out.println("Activity Can't fit to the class, Program Exiting");
				System.exit(0);
			}
		}
		act.setTempDay(tempDay);
		act.setTempRoom(cls.getName());
		act.setRandomStart(lmtStart,lmtFinish);
	}

	public void setRandomAllActivity() {
		//Test XX02---------------------------------------------------------
		System.out.println("Entering set random all activity");
		int actLength = arrAct.size();
		for (int i=0 ; i<actLength ; i++) {
			setRandomActivity(arrAct.get(i));
			System.out.println("Succesfully randomed 1 activity");
		}
	}

	public void checkViolation() {
		arrError.clear();
		int actLength = arrAct.size();
		int actClass = arrClass.size();
		for (int i=0; i<actLength ; i++) {
			for(int j=i+1 ; j<actLength ; j++) {
				if ((arrAct.get(i).getTempRoom() == arrAct.get(j).getTempRoom()) && (arrAct.get(i)).getTempDay() == arrAct.get(j).getTempDay()) {
					if ((arrAct.get(i).getStart() > arrAct.get(j).getStart()) && ((arrAct.get(j).getStart()+arrAct.get(j).getDuration()) > arrAct.get(i).getStart())) {
						PairActivity err = new PairActivity(arrAct.get(i),arrAct.get(j));
						arrError.add(err);
					} else if ((arrAct.get(j).getStart() > arrAct.get(i).getStart()) && ((arrAct.get(i).getStart()+arrAct.get(i).getDuration()) > arrAct.get(j).getStart())) {
						PairActivity err = new PairActivity(arrAct.get(i),arrAct.get(j));
						arrError.add(err);
					} 
				}
			}
		}
	}

	public int countViolation() {
		return arrError.size();
	}

	public void printAllActivity() {
		int length = arrAct.size();
		for (int i=0; i<length; i++) {
			System.out.print("name" + arrAct.get(i).getName() + "-");
			System.out.print("start" + arrAct.get(i).getStart() + "-");
			System.out.print("duration" + arrAct.get(i).getDuration() + "-");
			System.out.print("day" + arrAct.get(i).getTempDay() + "-");
			System.out.println("room" + arrAct.get(i).getTempRoom());
		}
	}
}