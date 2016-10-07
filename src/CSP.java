import java.util.Objects;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class CSP {
	protected ArrayList<Activity> arrAct;
	protected ArrayList<Classroom> arrClass;
	protected ArrayList<PairActivity> arrError;
	protected ArrayList<Activity> unplaceables;
	protected int[][][] mat = new int[100][100][100];

	public static final int NOT_FOUND = -999;

	public CSP() {
		arrAct = new ArrayList<Activity>();
		arrClass = new ArrayList<Classroom>();
		arrError = new ArrayList<PairActivity>();
		unplaceables = new ArrayList<Activity>();
		for (int i = 0; i < 100; i++){
			for (int j = 0; j < 100; j++){
				for (int k = 0; k < 100; k++){
					mat[i][j][k] = 0;
				}
			}
		}
	}

	public CSP(ArrayList<Activity> activities, ArrayList<Classroom> classrooms) {
		arrAct = new ArrayList<>();
		for (Activity activity : activities) {
			arrAct.add(new Activity(activity));
		}
		arrClass = classrooms;
		arrError = new ArrayList<>();
		unplaceables = new ArrayList<Activity>();
	}

	public CSP(ArrayList<Activity> activities, ArrayList<Classroom> classrooms, ArrayList<Activity> unplaceables) {
		arrAct = new ArrayList<>();
		for (Activity activity : activities) {
			arrAct.add(new Activity(activity));
		}
		arrClass = classrooms;
		arrError = new ArrayList<>();
		this.unplaceables = new ArrayList<Activity>();
		for (Activity activity : unplaceables) {
			this.unplaceables.add(new Activity(activity));
		}
	}

	public CSP(CSP oldCSP) {
		arrAct = new ArrayList<>();
		for (Activity activity : oldCSP.arrAct) {
			arrAct.add(new Activity(activity));
		}
		arrClass = oldCSP.arrClass;
		arrError = new ArrayList<>();
		unplaceables = new ArrayList<>();
		for (Activity activity : oldCSP.unplaceables) {
			unplaceables.add(new Activity(activity));
		}
	}

	public ArrayList<Activity> getArrayActivity() {
		return arrAct;
	}

	public ArrayList<Classroom> getArrayClassroom() {
		return arrClass;
	}

	public ArrayList<Activity> getUnplaceables() {
		return unplaceables;
	}

	public Classroom findClassroom(Activity act) {
		boolean found = false;
		int i=0;
		int clsLength = arrClass.size();
		while ((!found) && (i<clsLength)) {
			if ((Objects.equals(arrClass.get(i).getName(), act.getRoom())) && isDayAvailable(act,arrClass.get(i)) && isTimeAvailable(act,arrClass.get(i))){
				found = true;
			} else {
				i++;
			}
		}
		if (!found) {
			unplaceables.add(act);
			act.setFlag(true);
			return null;
		}
		return arrClass.get(i);
	}

	public boolean isDayAvailable(Activity act,Classroom cls) {
		ArrayList<Integer> days = checkDay(act,cls);
		boolean ret;
		if (days.size()>0) {
			ret = true;
		} else {
			ret = false;
		}
		return ret;
	}

	public boolean isTimeAvailable(Activity act,Classroom cls) {
		int lmtStart=999;
		int lmtFinish=-999;

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

		if (lmtFinish < (lmtStart+act.getDuration())) {
			return false;
		} else {
			return true;
		}
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

	private ArrayList<Integer> shuffleDay(Activity act, Classroom cls) {
		ArrayList<Integer> listDay = checkDay(act,cls);
		Collections.shuffle(listDay);
		return listDay;
	}

	public void setRandomActivity(Activity act) {
		if (act.getFlag()) return;

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
			}while(lmtFinish < (lmtStart+act.getDuration()));
		act.setTempDay(tempDay);
		act.setTempRoom(cls.getName());
		act.setRandomStart(lmtStart,lmtFinish);

		} else {
			cls = findClassroom(act);
			if (cls != null){
				int i = 0;
				ArrayList<Integer> days = shuffleDay(act,cls);
				do {
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
					i++;			
				} while ((i<days.size()) && (lmtFinish < lmtStart + act.getDuration()));
				tempDay = days.get(i-1);

				act.setTempDay(tempDay);
				act.setTempRoom(cls.getName());
				act.setRandomStart(lmtStart,lmtFinish);
			}
		}
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
			if(!activity.getFlag()){
				System.out.println(activity.toString());
			}
		}
	}

	public void printAllUnplaceable() {
		for (Activity activity : unplaceables) {
			System.out.print(activity.getName() + ";");
			System.out.print(activity.getRoom() + ";");
			System.out.print(activity.getLmtStart() + ";");
			System.out.print(activity.getLmtFinish() + ";");
			System.out.println(activity.getDuration());
		}		
	}

	public int findIndexClass(int i){
		int classLength = arrClass.size();
		int hasil = 99;
		for (int j = 0; j < classLength; j++){
			if (arrAct.get(i).getTempRoom() == arrClass.get(j).getName()){
				hasil = j;
			}
		}
		return hasil;
	}

	public double percentage(){
		int actLength = arrAct.size();
		double sum = 0;
		int idxClass;
		int idxDay;
		int idxStart;
		int idxStop;
		for (int i = 0; i < actLength; i++){
			idxClass = findIndexClass(i);
			idxDay = arrAct.get(i).getTempDay();
			idxStart = arrAct.get(i).getStart();
			idxStop = idxStart + arrAct.get(i).getDuration() - 1;
			for (int j = idxStart; j < idxStop; j++){
				mat[idxClass][idxDay][j] = 1;
			}
		}

		for (int i = 0; i < 100; i++){
			for (int j = 0; j < 100; j++){
				for (int k = 0; k < 100; k++){
					sum = sum + mat[i][j][k];
				}
			}
		}


		int errLength = arrError.size();

		int classLength = arrClass.size();
		double day = 0;
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

		return (sum/total * 100);
	}

/*	 public static void main(String[] args) {
	  		FileReader fileReader = new FileReader(args[0]);
	  		ArrayList<Classroom> classrooms = fileReader.parseArrayClassroom();
	  		ArrayList<Activity> activities = fileReader.parseArrayActivity();
	        CSP a = new CSP(activities,classrooms);
	        Activity a1 = a.getArrayActivity().get(0);
	        Classroom c12 = a.getArrayClassroom().get(12);

	        ArrayList<Integer> x = a.checkDay(a1,c12);
	        System.out.println(a1.getName());
	        for (int i=0;i<7;i++){
		        System.out.println(a1.getDay()[i]);
		    }
	        System.out.println(c12.getName());
	        System.out.println(c12.getOpenTime());
	        System.out.println(c12.getClosedTime());
	        for (int i=0;i<7;i++){
		        System.out.println(c12.getDay()[i]);
		    }
	        for (Integer asd:x) {
	        	System.out.println(asd);
	        }
	 }
*/
}
