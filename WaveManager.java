//Ahmet Salih Demir
import java.util.*;
import java.io.*;
public class WaveManager {
	private List<WaveData> waves; //Dalga bilgilerini tutan genel listemiz.
	public WaveManager(String fileName) {
		waves = new ArrayList<>();//Listemizi oluşturuyoruz.
		
		//Burada da bir okuyucu oluşturarak aynı level dosyalarında dalga bilgilerimiz alıyoruz.
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			
			//Dosyada wave data satırına kadar ilerliyoruz.
			while((line = reader.readLine()) != null) {
				if(line.equals("WAVE_DATA:")) 
					break;
				
			}
			//wave data satırından sonra gelen satırlar dalga bilgilerimizi içeriyor
			while((line = reader.readLine()) != null) {
				//Burada da satır içerisindeki bilgileri virgüller ile ayırıyoruz ve hepsini belirli değerlere aşitliyoruz.
				String[] parts = line.split(", ");
				//Dalgadaki düşman sayısı
				int enemyCount = Integer.parseInt(parts[0]);
				//Düşmanların spawnları arsındaki süre
				double betweenTime = Double.parseDouble(parts[1]);
				//Bir bölümdeki iki wave arası süre.
				int delayBetweenWave = Integer.parseInt(parts[2]);
				
				//dalgaların tutulduğu listemize elde ettiğimiz değerlerle oluşturulacak dalgalar ekliyoruz.
				waves.add(new WaveData(enemyCount, betweenTime, delayBetweenWave));
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
		//istenilen dalgayı döndüren method.
		public WaveData getWave(int index) {
		    return waves.get(index);
		}
        //toplam dalga sayısını söndüren method.
		public int getTotalWaves() {
		    return waves.size();
		}
		//belirtilen x / y konumlarında can ve hızı belirleyen genel düşman tipini spawnlayan method.
		public Enemy spawnEnemy(double x, double y) {
			return new Enemy(100, 1, x, y);
		}
} 

//Tek bir dalganın bilgilerini saklayan sınıfımız.
class WaveData {
	public int enemyCount;
	public double betweenTime;
	public int delayBetweenWave;
	
	//Bu constuctor içerisinde dalga bilgilerini ayarlıyoruz aslında.
	public WaveData(int enemyCount, double betweenTime, int delayBetweenTime) {
		this.enemyCount = enemyCount;
		this.betweenTime = betweenTime;
		this.delayBetweenWave = delayBetweenTime;
	}
}