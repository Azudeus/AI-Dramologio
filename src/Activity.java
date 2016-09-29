import java.util.ArrayList;
import java.util.Random;

public class Activity {
	private String name;
	private int[] day; //Minggu 0 Sabtu 6
	private int lmtStart; 
	private int lmtFinish;
	private int duration;
	private String room;
	private String tempRoom;
	private int start;


	Activity(String name, ArrayList<Integer> day, int lmtStart, int lmtFinish, int duration, String room) {
		this.name = name;
		this.day = new int[7];
		int length = day.size();
		for (int i = 0; i < 7; i++) {
			this.day[i] = 0;
		}
		for (int i = 0; i < length; i++) {
			this.day[day.get(i)] = 1;
		}
		this.lmtStart = lmtStart;
		this.lmtFinish = lmtFinish;
		this.duration = duration;
		this.room = room;
		this.start = 0;
	}

	public void setRandomStart(int start, int finish) {
		Random rand = new Random();
		int ret = rand.nextInt(finish-start-duration+1) + lmtStart;
		this.start = ret;
	}

	public void setStart(int x) {
		start = x;
	}

	public void setTempRoom(String x) {
		tempRoom = x;
	}

	public String getName() {
		return name;
	}

	public int[] getDay() {
		return day;
	}

	public int getLmtStart() {
		return lmtStart;
	}

	public int getLmtFinish() {
		return lmtFinish;
	}

	public int getDuration() {
		return duration;
	}

	public String getRoom() {
		return room;
	}
	
	public String getTempRoom() {
		return tempRoom;
	}

	public int getStart() {
		return start;
	}


}

