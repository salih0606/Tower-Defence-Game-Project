//Ahmet Salih Demir
import javafx.scene.shape.*;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
public class Cell {
	private boolean isPath; //Hücremizin path olup olmadığını belirlemek için kullanılacak değiken.
	private int row; // Hücremizin gridpane üzerindeki satır konumu.
	private int col; // Hücremisisn gridpane üzerindeki sütun konumu.
	private Rectangle view; //Her hücreyi temsil eden dikdörtgen görseli.
	private Tower tower;  // Hğcrede kule bulunma durumunu kontrol etmek için bir kule objesi.
	
	public Cell(int row, int col, boolean isPath, boolean hasTower) {
		this.col = col;
		this.row = row;
		this.isPath = isPath;
		
		//Hücrelerin görünümünü ve path olup olmama durumlarına göre renklerini ayarladığımız bölüm burası.
		//Aslıncda bu kısımda genel amaç path olmayan Cell'lerimizi renklendirmek. 
		Image image1 = new Image("grass4.png");
		Image image2 = new Image("grass5.png");
		view = new Rectangle(40,40);
		view.setStroke(Color.TRANSPARENT);
		view.setArcHeight(8);
		view.setArcWidth(8);
		int a = (int)(Math.random() * 2);
		boolean b = false;
		if(a == 1)
			b=true;
		view.setFill(b  ? new ImagePattern(image1) : new ImagePattern(image2));
	}
	
	//Bu methodu map içerisinde path durmunu ayarlamak ve path hücrelerini renklendirmek için kullanıyoruz.
	public void setPath(boolean isPath) {
		this.isPath = isPath;
		Image image3 = new Image("path.png");
		view.setFill(new ImagePattern(image3));
	}
	
	//Herhangi bir hücrenin görselini döndüren methodumuz.
	public Rectangle getView() {
		return view;
	}
	//Hücrenin path olup olmadığını döndüren methodumuz.
	public boolean isPath() {
		return isPath;
	}
	//Hücre üzerinde kule olup olmadığını döndüren method.
	public boolean hasTower() {
		return this.tower != null;
	}
	//Satır sayısını döndür.
	public int getRow() {
		return row;
	}
	//Sütın sayısnını döndür.
	public int getCol() {
		return col;
	}
	//Hücre üzerindeki kule nesnesini döndüren methodumuz.
	public Tower getTower() { //YENİ
		return tower;
	}
	//Hücre içerisine kule yerleştirmek için kullandığımız method.
	//Ayrıca methodun çalışıp çalışmadığını kontrol etmek için içerisine hücreye konulan toweri yazdıran bir satırımız da var.
	public void setTower(Tower tower) { //YENİ
		 System.out.println("setTower çağrıldı: " + tower);
		this.tower=tower;
		
	}
}
