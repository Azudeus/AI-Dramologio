import java.util.ArrayList;
import java.util.Random;

public class Activity {
	private String name;
	private boolean[] day; //Minggu 0 Sabtu 6
	private int lmtStart; 
	private int lmtFinish;
	private int duration;
	private String room;
	private String tempRoom;
	private int start;
	private int tempDay;

	public Activity(String name, boolean[] day, int lmtStart, int lmtFinish, int duration, String room) {
		this.name = name;
		this.day = new boolean[7];
		for (int i = 0; i < 7; i++) {
			this.day[i] = day[i];
		}
		this.lmtStart = lmtStart;
		this.lmtFinish = lmtFinish;
		this.duration = duration;
		this.room = room;
		this.start = 0;
		this.tempDay = 0;
		this.tempRoom = "";
	}

	public Activity(Activity activity) {
		this.name = activity.name;
		this.day = new boolean[7];
		for (int i = 0; i < 7; i++) {
			this.day[i] = activity.day[i];
		}
		this.lmtStart = activity.lmtStart;
		this.lmtFinish = activity.lmtFinish;
		this.duration = activity.duration;
		this.room = activity.room;
		this.start = activity.start;
		this.tempDay = activity.tempDay;
		this.tempRoom = activity.tempRoom;
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

	public void setTempDay(int x) {
		tempDay = x;
	}

	public String getName() {
		return name;
	}

	public boolean[] getDay() {
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

	public int getTempDay() {
		return tempDay;
	}

	public String toString() {
		return  this.getName() + " " +
				this.getTempDay() + " " +
				this.getStart() + " " +
				this.getDuration() + " " +
				this.getTempRoom();
	}
}

