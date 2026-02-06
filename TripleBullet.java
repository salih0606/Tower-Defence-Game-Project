import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class TripleBullet extends Circle{//doğukan
	
	private double towerX,towerY,damage,speed;
	private Pane gamePane;
	private Enemy enemy;
	//en yakın düşmana vuracak
	TripleBullet(double towerX, double towerY,double damage,double speed,Pane gamePane,Enemy enemy){
		if(enemy.isDead())return;
		new Bullet(towerX,towerY,damage,speed,gamePane,enemy);//üç tane bullet metotdu
		new Bullet(towerX,towerY,damage,speed,gamePane,enemy);
		new Bullet(towerX,towerY,damage,speed,gamePane,enemy);
	}

}
 