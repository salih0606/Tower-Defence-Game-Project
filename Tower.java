import java.util.List;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
public abstract class Tower  {
	//Doğukan
protected double range,cost,towerX,towerY,cooldown,cd ;//düşmnanı tespit edeceiğ yarıçap,değeri,kulenin x ve y si ,mermiler arası bekleme süresi , bekleme süresinin finalı
protected ImageView view;
protected Game game;
protected Pane gamePane;
protected List<Enemy> enemies;//düşman classından bilgi almak için
public Tower(String imageName ,double towerX, double towerY, double range , double cost, double cooldown,double cd, List<Enemy> enemies){//yazması daha kolay olsun diye abstract class
	this.towerX = towerX;
	this.towerY=towerY;
	this.range=range;
	this.cost=cost;
	this.cooldown=cooldown;
	this.cd=cd;
	this.enemies=enemies;
	 try {
         String path = getClass().getResource("/" + imageName).toExternalForm();//resimleri alıp görüntülemeyi deneme
         this.view = new ImageView(new Image(path));
     } catch (Exception e) {
         System.err.println("Görsel yüklenemedi: " + imageName);
         this.view = new ImageView(); // Boş bırak ama çökmesin
     }
     this.view.setFitWidth(40);  // Kendi değerine göre ayarla
     this.view.setFitHeight(40); // Kendi değerine göre ayarla
     this.view.setLayoutX(towerX - 20); // X konumlandırması
     this.view.setLayoutY(towerY - 20); // Y konumlandırması
	
}
public abstract Enemy findTarget() ;
public abstract void shoot(Enemy enemies);
public abstract double getX();
public abstract double getY();
public abstract void setX(double x);
public abstract void setY(double y);
public void shoot(List<Enemy> enemy) {
	// TODO Auto-generated method stub
	
}
public Node getView() {
	// TODO Auto-generated method stub
	return view;
}
public abstract void update(double deltaTime);





}
