public class Activity {
	private String nama;
	private int[] hari; //Minggu 0 Sabtu 6
	private int mulai; 
	private int selesai;
	private int durasi;
	private int ruang;

	Activity(String nama, int[] hari, int mulai, int selesai, int durasi, int ruang) {
		this.nama = nama;
		this.hari = new int[6];
		for (int i=0;i<7;i++) {
			this.hari[i] = hari[i];
		}
		this.mulai = mulai;
		this.selesai = selesai;
		this.durasi = durasi;
		this.ruang = ruang;
	}
}

