import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LaserBullet{//doğukan

	private double damage,speed;//mermi hasar ve hızı için
	private Pane gamePane;//scene de görüntülenmesi için
	private Enemy enemy;////enemy classından bilgi almak için
	private Line laser;//lazerin şekli için
	private LevelManager levelManager;
	//en yakın düşmana vuracak
	LaserBullet(double towerX, double towerY,double damage,double speed, Pane gamePane,Enemy enemy){//constructor game main ve tower classlarında çağırmak için
		
		this.damage=damage;
		this.speed=speed;
		this.enemy=enemy;
		laser = new Line(towerX,towerY,enemy.getX(),enemy.getY());//lazerin başlama ve bitiş yeri için
		laser.setStroke(Color.DARKRED);//dışının rengi
		laser.setStrokeWidth(2);//dışının kalınlığı
		gamePane.getChildren().add(laser);//pane ekleme
		hitTarget();//düşmana vurma
	}
	
	private void hitTarget() {
	    if ( !enemy.isDead())//düşman ölü değilse
	        enemy.takeDamage(damage);//düşman hasar alacak
	        if(enemy.isDead()) {//düşman ölüyse
	        	levelManager.enemyKilled();//para gelecek
	        }
	    

	    // 100 ms sonra lazer çizgisini kaldır
	    new Thread(() -> {
	        try {
	            Thread.sleep(100);
	        } catch (InterruptedException ignored) { }

	        javafx.application.Platform.runLater(() -> {
	            gamePane.getChildren().remove(laser);
	        });
	    }).start();
	}
}
