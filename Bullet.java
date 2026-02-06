import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Bullet extends Circle{//yuvarlak bir cisim olacağı için circle classını extendleyerek devam etmek işi kolaylaştırır
	//doğukan

	private AnimationTimer timer;//animasyon süresi için
	private double damage,speed;//mermi hasar ve hızı için
	private Pane gamePane;//scene de görüntülenmesi için
	private Enemy enemy;//enemy classından bilgi almak için
	//en yakın düşmana vuracak
	Bullet(double towerX, double towerY,double damage,double speed, Pane gamePane, Enemy enemy){//constructor game main ve tower classlarında çağırmak için
		super(towerX,towerY,3);//merminin yuvarlak özelliklerini belirtmek için
		this.setFill(Color.BLACK);//merminin iç rengi
		this.setStroke(Color.BLACK);//merminin kenar rengi
		this.damage=damage;//değişkenleri atamak için
		this.speed=speed;
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
	
	public void move(double dt) {//hareket metotu
		 if (enemy.isDead()) {//düşman öldüyse mermi kendini yok edecek
		        destroy();
		        return;
		    }
		 	//düşman yaşıyorsa
		    double dx = enemy.getX() - getCenterX();//düşmanın x iyle towerin x i arasındaki farkı bulacak
		    double dy = enemy.getY() - getCenterY();
		    double distance = Math.sqrt(dx * dx + dy * dy);

		    if (distance < speed * dt + 2) {//hedef yeteri kadar yakınsa mermi vurmuş sayılacak
		        enemy.takeDamage(damage);
		        destroy();
		    } else {
		        setCenterX(getCenterX() + (dx / distance) * speed * dt);
		        setCenterY(getCenterY() + (dy / distance) * speed * dt);
		    }
		}
	private void destroy() {//yok etme metodu
	    timer.stop();
	    gamePane.getChildren().remove(this);//mermiyi görüntüden silecek
	}
		
	}