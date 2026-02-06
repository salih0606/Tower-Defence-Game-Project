import java.util.List;
import javafx.scene.layout.Pane;

public class MissileLauncherTower extends Tower {
	//Doğukan
	private Pane gamePane;//kuleyi scene de göstermek için
	private List<Enemy> enemies;//düşmandan gerekli bilgileri almak için
	MissileLauncherTower(String imageName, double towerX, double towerY, double range , double cost, double cooldown,double cd,List<Enemy> enemies,Pane gamePane) {
		super(imageName,towerX,towerY,range ,cost,cooldown,cd,null);//tower abstract classının constructorı
		this.gamePane=gamePane;//constructorda yazılmayanlar
		this.enemies=enemies;
	}
	@Override
	public void shoot(List<Enemy> enemies) {//kuleden merminin çıkıp düşmanı vurması için metot
		MissileBullet bullet = new MissileBullet(getX(),getY(),20,300,20,gamePane,enemies);
		
	}

	
	public Enemy findTarget() {//düşmanı bulmak için metot
	    for (Enemy e : enemies) {//enemy arraylistinden tek tek tüm düşmanları analiz etme
	        if (!e.isDead()) {
	            double dx = e.getX() - (getX() + 20);
	            double dy = e.getY() - (getY() + 20);
	            double dist = Math.sqrt(dx * dx + dy * dy);//aradaki mesafeyi hesaplama

	            if (dist <= range) {
	                return e;
	            }
	        }
	    }
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

	@Override
	public double getX() {// getter lar ve setterlar
		// TODO Auto-generated method stub
		return towerX;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return towerY;
	}

	@Override
	public void setX(double x) {
		// TODO Auto-generated method stub
		towerX=x;
	}

	@Override
	public void setY(double y) {
		// TODO Auto-generated method stub
		towerY=y;
	}
	@Override
	public void shoot(Enemy enemy) {
		// TODO Auto-generated method stub
		
	}

}