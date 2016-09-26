import java.util.ArrayList;

public class Activity {
	private String nama;
	private int[] hari; //Minggu 0 Sabtu 6
	private int mulai; 
	private int selesai;
	private int durasi;
	private String ruang;

	Activity(String nama, ArrayList<Integer> hari, int mulai, int selesai, int durasi, String ruang) {
		this.nama = nama;
		this.hari = new int[6];
		int length = hari.size();
		for (int i=0;i<7;i++) {
			this.hari[i]=0;
		}
		for (int i=0;i<length;i++) {
			this.hari[hari.get(i)] =1;
		}
		this.mulai = mulai;
		this.selesai = selesai;
		this.durasi = durasi;
		this.ruang = ruang;
	}
}

