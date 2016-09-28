import java.util.ArrayList;

public class Classroom {
	private String name;
	private int openTime;
	private int closedTime;
	private ArrayList<Integer> day;

	Classroom(String name, int openTime, int closedTime, ArrayList<Integer> day) {
		this.name = name;
		this.openTime = openTime;
		this.closedTime = closedTime;
		this.day = day;
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

	public ArrayList<Integer> getDay() {
		return day;
	}
}