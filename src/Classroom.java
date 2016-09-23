public class Classroom {
	private int nama;
	static int[][] waktu; // value 0 or 1

	Classroom(int nama, int buka, int tutup) {
		this.nama = 0;
		waktu = new int[6][24];

		for (int i=0 ; i < 6 ; i++) {
			for (int j=0 ; j < 24 ; j++) {
				if ((j >= buka) && (j <= tutup)) {
					waktu[i][j] = 1;
				} else {
					waktu[i][j] = 0;
				}
			} 	
		}
	}
}