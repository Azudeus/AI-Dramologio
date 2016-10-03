import java.util.Objects;
import java.util.Random;
import java.util.ArrayList;

public class CSP {
	protected ArrayList<Activity> arrAct;
	protected ArrayList<Classroom> arrClass;
	protected ArrayList<PairActivity> arrError;
	private boolean[] boolClass;

	public static final int NOT_FOUND = -999;

	public CSP() {
		arrAct = new ArrayList<Activity>();
		arrClass = new ArrayList<Classroom>();
		arrError = new ArrayList<PairActivity>();
	}

	public CSP(ArrayList<Activity> activities, ArrayList<Classroom> classrooms) {
		arrAct = new ArrayList<>();
		for (Activity activity : activities) {
			arrAct.add(new Activity(activity));
		}
		arrClass = classrooms;
		arrError = new ArrayList<>();
	}

	public ArrayList<Activity> getArrayActivity() {
		return arrAct;
	}

	public ArrayList<Classroom> getArrayClassroom() {
		return arrClass;
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
		if (listDay.size() != 0) {
			int dayLength = listDay.size();
			int indexDay = rand.nextInt(dayLength);	
			return listDay.get(indexDay);
		} else {
			return NOT_FOUND;
		}
	}

	public void setRandomActivity(Activity act) {
		Classroom cls;
		int tempDay;
		int lmtStart=999;
		int lmtFinish=-999;

		if (Objects.equals(act.getRoom(), "-")) {
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
			cls = findClassroom(act.getRoom());
			tempDay = randomDay(act,cls);
			if(tempDay!=NOT_FOUND){
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
		for (Activity activity : arrAct) {
			setRandomActivity(activity);
		}
		checkViolation();
	}

	public void checkViolation() {
		arrError.clear();
		int actLength = arrAct.size();
		for (int i=0; i<actLength ; i++) {
			for(int j=i+1 ; j<actLength ; j++) {
				if (Objects.equals(arrAct.get(i).getTempRoom(), arrAct.get(j).getTempRoom()) && (arrAct.get(i).getTempDay() == arrAct.get(j).getTempDay())) {
					if ((arrAct.get(i).getStart() > arrAct.get(j).getStart()) && ((arrAct.get(j).getStart()+arrAct.get(j).getDuration()) > arrAct.get(i).getStart())) {
						PairActivity err = new PairActivity(arrAct.get(i),arrAct.get(j));
						arrError.add(err);
					} else if ((arrAct.get(j).getStart() > arrAct.get(i).getStart()) && ((arrAct.get(i).getStart()+arrAct.get(i).getDuration()) > arrAct.get(j).getStart())) {
						PairActivity err = new PairActivity(arrAct.get(i),arrAct.get(j));
						arrError.add(err);
					} else if (arrAct.get(i).getStart() == arrAct.get(j).getStart()) {
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
		for (Activity activity : arrAct) {
			System.out.println(activity.toString());
		}
	}

	public double percentage(){
		int classLength = arrClass.size();
		double day = 0;
		double sum = 0;
		double total = 0;
		//HITUNG TOTAL JAM YANG AVAILABLE
		for (int i = 0; i < classLength; i++){
			for (int j = 0; j < 7; j++){
				if (arrClass.get(i).getDay()[j]){
					day++;
				}
			}
			total = total + (day * (arrClass.get(i).getClosedTime() - arrClass.get(i).getOpenTime()));
			day = 0;
		}
		//HITUNG TOTAL JAM YANG DIGUNAKAN TANPA BENTROK
		int actLength = arrAct.size();
		for (int i = 0; i < actLength; i++){
			sum = sum + arrAct.get(i).getDuration();
		}
		//HITUNG TOTAL JAM JIKA BENTROK
		int violation = countViolation();
		for (int i = 0; i < violation; i++){
			//JIKA MULAINYA BERSAMAAN
			if (arrError.get(i).getFirst().getStart() == arrError.get(i).getSecond().getStart()){
				if (arrError.get(i).getFirst().getDuration() > arrError.get(i).getSecond().getDuration()){
					sum = sum - arrError.get(i).getSecond().getDuration();
				}
				else{
					sum = sum - arrError.get(i).getFirst().getDuration();
				}
			}
			//JIKA AKTIVITAS 2 MULAI DULUAN
			else if (arrError.get(i).getFirst().getStart() > arrError.get(i).getSecond().getStart()){
				if (arrError.get(i).getFirst().getStart()+arrError.get(i).getFirst().getDuration() <= arrError.get(i).getSecond().getStart()+arrError.get(i).getSecond().getDuration()){
					sum = sum - arrError.get(i).getFirst().getDuration();
				}
				else{
					sum = sum - (arrError.get(i).getSecond().getStart()+arrError.get(i).getSecond().getDuration() - arrError.get(i).getFirst().getStart());	
				}
			}
			//JIKA AKTIVITAS 1 MULAI DULUAN
			else{
				if (arrError.get(i).getFirst().getStart()+arrError.get(i).getFirst().getDuration() >= arrError.get(i).getSecond().getStart()+arrError.get(i).getSecond().getDuration()){
					sum = sum - arrError.get(i).getSecond().getDuration();
				}
				else{
					sum = sum - (arrError.get(i).getFirst().getStart()+arrError.get(i).getFirst().getDuration() - arrError.get(i).getSecond().getStart());	
				}
			}
		}
		return (sum/total * 100);
	}
}