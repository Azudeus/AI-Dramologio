import java.util.ArrayList;


public class Hillclimbing extends CSP{
	private int violation;
	private int steps;

	Hillclimbing(ArrayList<Activity> act, ArrayList<Classroom> cls, int steps){
		super(act,cls);
		this.steps = steps;
	}

	public Activity selectStep() {
		int errLength = arrError.size();
		Activity ret = arrError.get(0).getFirst();
		int mostFreq = 0;
		int occurrence;
		for (int i=0 ; i<errLength ; i++) {			
			occurrence = 0;
			for (int j=0 ; j<errLength ; j++) {
				if (arrError.get(i).getFirst() == arrError.get(j).getFirst()) {
					occurrence++;
				} else if (arrError.get(i).getFirst() == arrError.get(j).getSecond()) {
					occurrence++;
				}
			}
			if (occurrence > mostFreq) {
				mostFreq = occurrence;
				ret = arrError.get(i).getFirst();
			}
		}
		for (int i=0 ; i<errLength ; i++) {			
			occurrence = 0;
			for (int j=0 ; j<errLength ; j++) {
				if (arrError.get(i).getSecond() == arrError.get(j).getFirst()) {
					occurrence++;
				} else if (arrError.get(i).getSecond() == arrError.get(j).getSecond()) {
					occurrence++;
				}
			}
			if (occurrence > mostFreq) {
				mostFreq = occurrence;
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
		violation = countViolation();
		Activity saveState;
		String tempRoom;
		int tempDay;
		int tempStart;
		int i=0;

		while ((violation!=0) && (i < steps)) {
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
			i++;
		}
		printAllActivity();
		System.out.println("Jumlah bentrok: " + violation);
	}

    public static void main(String[] args) {
        // TODO code application logic here
        try{
	  		FileReader fileReader = new FileReader(args[0]);
	  		ArrayList<Classroom> classrooms = fileReader.parseArrayClassroom();
	  		ArrayList<Activity> activities = fileReader.parseArrayActivity();
	        new Hillclimbing(activities,classrooms,Integer.parseInt(args[1])).run();
    	} catch (ArrayIndexOutOfBoundsException e){
    		System.out.println("Input not enough, please put filename and how many steps do you want");
    		System.exit(0);
    	}
    }
}