import java.util.ArrayList;

public class Classroom {
	private String nama;
	static int[][] waktu; // value 0 or 1

	Classroom(String nama, int buka, int tutup, ArrayList<Integer> hari) {
		this.nama = nama;
		waktu = new int[6][24];

		for (int i=0 ; i < 6 ; i++) {
			if (isInDate(buka, hari)) {
				for (int j=0 ; j < 24 ; j++) {
					if ((j >= buka) && (j <= tutup)) {
						waktu[i][j] = 1;
					} else {
						waktu[i][j] = 0;
					}
				} 
			} else {
				for (int j=0 ; j < 24 ; j++) {
					waktu[i][j] = 0;
				}
			}
		}
	}

	private boolean isInDate(int date, ArrayList<Integer> arr) {
		boolean ret = false;
		int length = arr.size();
		int i = 0;
		while ((i<length) && (!ret)) {
			if (date == arr.get(i)) {
				ret = true;
			} else {
				i++;
			}
		}
		return ret;
	}
}