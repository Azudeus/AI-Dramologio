import java.util.ArrayList;
import java.util.Random;

public class Activity {
	private String name;
	private int[] day; //Minggu 0 Sabtu 6
	private int lmtStart; 
	private int lmtFinish;
	private int duration;
	private String room;
	private int start;


	Activity(String name, ArrayList<Integer> day, int lmtStart, int lmtFinish, int duration, String room) {
		this.name = name;
		this.day = new int[6];
		int length = day.size();
		for (int i=0;i<7;i++) {
			this.day[i]=0;
		}
		for (int i=0;i<length;i++) {
			this.day[day.get(i)] =1;
		}
		this.lmtStart = lmtStart;
		this.lmtFinish = lmtFinish;
		this.duration = duration;
		this.room = room;
		this.start = 0;
	}

	public setRandomStart() {
		Random rand = new Random();
		int ret = rand.nextInt(lmtFinish-lmtStart+1-duration) + lmtStart;
		this.start = ret;
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

	public int getStart() {
		return start;
	}

	public int setStart(int x) {
		start = x;
	}
}

