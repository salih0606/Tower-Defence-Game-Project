
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import java.util.List;

public class MissileBullet extends Circle{//yuvarlak bir cisim olacağı için circle classını extendleyerek devam etmek işi kolaylaştırır
	//doğukan
	private AnimationTimer timer;//animasyon süresi için
	private double damage,speed;//mermi hasar ve hızı için
	private Pane gamePane;//scene de görüntülenmesi için
	private List<Enemy> enemy;//enemy classından bilgi almak için
	private double explosionRad;//patladığında hasar vereceği alan
	//en yakın düşmana vuracak
	MissileBullet(double towerX, double towerY,double damage,double speed, double explosionRad,Pane gamePane,List<Enemy> enemy){
		super(towerX,towerY,3);
		this.setFill(Color.BEIGE);
		this.setStroke(Color.BLACK);
		this.damage=damage;
		this.speed=speed;
		this.explosionRad=explosionRad;
		this.enemy=enemy;
		gamePane.getChildren().add(this);
		startMovement();//hareket metodunu çağırmak için
	}
	private void startMovement() {//harekete başlama metodu
	    timer = new AnimationTimer() {
	        private long lastUpdate = 0;

	        @Override
	        public void handle(long now) {
	            if (lastUpdate == 0) {
	                lastUpdate = now;
	                return;
	            }

	            double dt = (now - lastUpdate) / 1e9;
	            lastUpdate = now;
	            move(dt);
	        }
	    };
	    timer.start();
	}
	public void move(double dt){//hareket metotu
		 if (((Enemy) enemy).isDead()) {//düşman öldüyse mermi kendini yok edecek
		        explode();
		        destroy();
		        return;
		    }
		//düşman yaşıyorsa
		    double dx = ((Enemy) enemy).getX() - getCenterX();//düşmanın x iyle towerin x i arasındaki farkı bulacak
		    double dy = ((Enemy) enemy).getY() - getCenterY();
		    double distance = Math.sqrt(dx * dx + dy * dy);

		    if (distance < speed * dt + 2) {//hedef yeteri kadar yakınsa mermi vurmuş sayılacak
		        ((Enemy) enemy).takeDamage(damage);
		        destroy();
		    } else {
		        setCenterX(getCenterX() + (dx / distance) * speed * dt);
		        setCenterY(getCenterY() + (dy / distance) * speed * dt);
		    }
		}
	private void explode() {
		 // Etki alanındaki tüm düşmanlara hasar ver
		for (Enemy e : enemy) {
	        if (e.isDead()) continue;

	        double dx = e.getX() - getCenterX();
	        double dy = e.getY() - getCenterY();
	        double dist = Math.sqrt(dx * dx + dy * dy);

	        if (dist <= explosionRad) {//mesafe patlama yarıçapının içindeyse hasar al
	            e.takeDamage(damage);
	        }
	}
		}
	private void destroy() {
	    timer.stop();
	    gamePane.getChildren().remove(this);
	}
}
