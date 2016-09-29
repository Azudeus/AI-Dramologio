import java.util.ArrayList;

public class Classroom {
	private String name;
	private int openTime;
	private int closedTime;
	private boolean[] day;

	Classroom(String name, int openTime, int closedTime, boolean[] day) {
		this.name = name;
		this.openTime = openTime;
		this.closedTime = closedTime;
		this.day = new boolean[7];
		for (int i=0; i < 7 ; i++) {
			this.day[i] = day[i];
		}
	}

	public String getName() {
		return name;
	}

	public int getOpenTime() {
		return openTime;
	}

	public int getClosedTime() {
		return closedTime;
	}

	public boolean[] getDay() {
		return day;
	}
}