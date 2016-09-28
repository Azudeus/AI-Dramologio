public class PairActivity {
	private Activity first;
	private Activity second;

	PairActivity(Activity first, Activity second) {
		this.first = first;
		this.second = second;
	}

	public Activity getFirst() {
		return first;
	}

	public Activity getSecond() {
		return second;
	}

}