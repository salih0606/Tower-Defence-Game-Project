import java.util.List;
import javafx.scene.layout.Pane;

public class SingleShotTower extends Tower{//Doğukan Bayrı
	private Pane gamePane;//kuleyi scene de göstermek için
	private List<Enemy> enemies;//düşmandan gerekli bilgileri almak için
	SingleShotTower(String imageName , double towerX, double towerY, double range , double cost,  double cooldown,double cd,List<Enemy> enemies,Pane gamePane, Game game) {//cd ve cooldown aynı sayı olmalı
		super(imageName,towerX,towerY,range ,cost,cooldown,cd,null); //tower abstract classının constructorı																				
		this.gamePane=gamePane;//constructorda yazılmayanlar
		this.enemies=enemies;
		this.game = game;
		
	}
	@Override
	public void shoot(Enemy enemies) {//kuleden merminin çıkıp düşmanı vurması için metot
		Bullet bullet = new Bullet(getX(), getY(),34,3, gamePane,enemies);
		
		System.out.println("Bullet created at: " + getX() + ", " + getY());
	}
@Override
	public Enemy findTarget() {//düşmanı bulmak için metot
	    for (Enemy e : enemies) {//enemy arraylistinden tek tek tüm düşmanları analiz etme
	        if (!e.isDead()) {
	            double dx = e.getX() - (getX());
	            double dy = e.getY() - (getY());
	            double dist = Math.sqrt(dx * dx + dy * dy);//aradaki mesafeyi hesaplama

	            if (dist <= range) {
	            	
	                return e;
	            }
	        }
	    }System.out.println("Target found: " + enemies);
	    return null;
	    

	}
	public void update(double dt) {//düşmanı vurduktan sonra bekleme
	    cooldown -= dt;

	    Enemy enemy = findTarget();

	    if (enemy != null && cooldown <= 0) {
	        shoot(enemy);
	    cooldown=cd;
	    }
	}
	public double getX() {// getter lar ve setterlar
		return towerX;
	}
	public double getY() {
		return towerY;
	}
	public void setX(double x) {
		towerX=x;
	}
	public void setY(double y) {
		towerY=y;
	}	
}