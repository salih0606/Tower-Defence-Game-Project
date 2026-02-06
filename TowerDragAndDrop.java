//BURAK BERK DEMİRBAŞ
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TowerDragAndDrop {
    private Map map;
    private Pane towerLayer;
    private Game game;
	private List<Enemy> enemies ;
	private Pane gamePane;
	private ImageView draggingImage;
	private Circle rangeCircle;
	private Point2D offset;

    public TowerDragAndDrop(Map map, Pane towerLayer, Game game, List<Enemy> enemies, Pane gamePane) {
    	//drag and drop işlemi yapılacak objeyi başlatıyoruz. Bu işlemi Game classında yapıyorum
    	this.map = map;
        this.towerLayer = towerLayer;
        this.game = game;
        this.enemies = enemies;
        this.gamePane = gamePane;
    }

    public void drag(ImageView icon) {//belirli bir ikon için drag and drop işlemini başlatıyorum ( Aslında tek method ile alltaki methodların eventHandlerlarını başlatıyorum)
    	//icon.setOnMousePressed(this::handleMousePressed); yazımı ile icon.setOnMousePressed(e -> handleMousePressed(e)); eş değermiş 
    	icon.setOnMousePressed(e -> handleMousePressed(e));
    	icon.setOnMouseDragged(e -> handleMouseDragged(e));
    	icon.setOnMouseReleased(e -> handleMouseReleased(e));
    }
    
    private void handleMousePressed(MouseEvent e) {
        ImageView icon = (ImageView) e.getSource();

        draggingImage = new ImageView(icon.getImage());//geçici bir draggingImage oluşturuyorum. İkon ile aynı image aynı boyut 
        draggingImage.setFitWidth(icon.getFitWidth());
        draggingImage.setFitHeight(icon.getFitHeight());
        draggingImage.setOpacity(1);
        towerLayer.getChildren().add(draggingImage);

        rangeCircle = new Circle(110);//Range(circle) oluşturuyorum 
        rangeCircle.setFill(Color.rgb(0, 255, 0, 0.2));
        rangeCircle.setStroke(Color.GREEN);
        rangeCircle.setMouseTransparent(true);
        towerLayer.getChildren().add(rangeCircle);

        offset = new Point2D(e.getX(), e.getY());//ilk tıkladığımız yeri kaydederiz nereden tuttuysak oradan sürükleyebilmek için
        e.consume();
    }
    
    private void handleMouseDragged(MouseEvent dragEvent) {//sürükleme işlemi sırasında ikonu ve rangei o anki mouse konumuna göregüncellemek için ve rangei tam olarak ikonun ortasına hizalamak için bu  methodu kullanıyorum
        Point2D scenePoint = new Point2D(dragEvent.getSceneX(), dragEvent.getSceneY());
        Point2D localPoint = towerLayer.sceneToLocal(scenePoint);//üstte aldığımız scene üzerndeki konumu towerLayerın kendi koordinat sistemine göre dönüştürür 

        draggingImage.setLayoutX(localPoint.getX() - offset.getX());//üstte aldığımız bilgilere göre imageı yerleştirme işlemi
        draggingImage.setLayoutY(localPoint.getY() - offset.getY());
        
        //imageın tam merkezine yerleştirmek için bu işlemleri yapıyorum
        rangeCircle.setCenterX(draggingImage.getLayoutX() + draggingImage.getFitWidth() / 2);
        rangeCircle.setCenterY(draggingImage.getLayoutY() + draggingImage.getFitHeight() / 2);

        dragEvent.consume();
    }
    
    private void handleMouseReleased(MouseEvent releaseEvent) {
        Point2D scenePoint = new Point2D(releaseEvent.getSceneX(), releaseEvent.getSceneY());//fare konumunu alıyorum
        
        //Mape göre hangi koordinata geliyor bunu anlayıp cellsizea bölüyorum ki hangi satıra, sütuna bırakma işlemi yapılmış bunu anlayalım
        int cellSize = 40;
        int col = (int) ((scenePoint.getX() - map.calculateTopLeftX()) / cellSize);
        int row = (int) ((scenePoint.getY() - map.calculateTopLeftY()) / cellSize);
        Cell cell = null;

        try {
            cell = map.getCell(row, col);//o satır ve sütundaki celli alır 

            if (!cell.isPath() && !cell.hasTower()) {//path değil ve kule içermiyorsa if in içine girer isPath ve hasTowerı Cell classından alır
                String type = getTowerType(draggingImage.getImage());//Bu satırda getTowerType methodunun içine girer ve sürüklenen
                //fotoğrafın url sinin hangi kule typeı olduğunu anlamaya yarar
                double range = 70;
                double cost = 100;
                double cooldown = 1.0;
                double cd = 1.0;
                
              //cellin en ortasının koordinatını hesaplıyorum daha sonra placeIconatCenter methodunda kullanıcam
                double centerX = col * cellSize + cellSize / 2;
                double centerY = row * cellSize + cellSize / 2;


                Tower tower = TowerFactory.createTowerByType(type, centerX, centerY, range, cost, cooldown, cd, enemies,game,gamePane);
                //üstteki satır ile towerFactory classından o typeda bir kule objecti oluştururuz
                System.out.println("oluşturulan kule: " + tower);//hataları denemek için koyuldu 

                if (tower != null) {//kule yerleşti ise fotoğrafını o noktada mape ekle cell classındaki setTower methodunun içine object ata ki bir sonraki seferde hasTower kullanırken true dönsün
                    towerLayer.getChildren().add(tower.getView());
                    placeIconAtCenter(tower.getView(), centerX, centerY);
                    cell.setTower(tower);
                    game.addTower(tower);
                }
            }

        } catch (ArrayIndexOutOfBoundsException a) {//Burada normalde bu hatayı verdiği zaman catchleyip kulenin satılma işlemini yapmak lazım yani parayı arttıracağız
            System.out.println("Kule harita dışına bırakıldı.");
        } finally {//işlem bittikten sonra rangei görünmez yap 
            rangeCircle.setOpacity(0);
            towerLayer.getChildren().remove(rangeCircle);
            towerLayer.getChildren().remove(draggingImage);//? kaldırmak mermi sıkma işlemini etkiler mi yoksa ayrıca bir range oluşturuluyor mu bak !

            if (cell != null) {
            	//hataları denemek için koyuldu 
                System.out.println("scene: " + scenePoint);
                System.out.println("mapTopLeft: " + map.calculateTopLeftX() + ", " + map.calculateTopLeftY());
                System.out.println("row: " + row + ", col: " + col);
                System.out.println("isPath: " + cell.isPath() + ", hasTower: " + cell.hasTower());
                System.out.println("cell instance: " + cell);
                System.out.println("map.getCell again: " + map.getCell(row, col));
            }
        }
        
        releaseEvent.consume();
    }
    
    private String getTowerType(Image image) {//sürüklenen imageı parametre olarak alır ve imageın urlsini eşleyerek hangi kule türü olduğunu tespit eder ve type olarak kaydeder
        String url = image.getUrl();
        if (url.contains("cannon.png")) return "CANNON";
        if (url.contains("infernoTower.png")) return "LASER";
        if (url.contains("tripleCannon.png")) return "TRIPLE";
        if (url.contains("havan.png")) return "MISSILE";
        return "UNKNOWN";
      //handleMouseReleased methodunun içinde hesapladığım cellin centerX ve centerY değerlerini kullanarak mapin sol üst köşesinden centerX e kadar ilerleyip yerleştirdiğimiz (drop işlemini yaptığımız) cellin neresine koyarsak koyalım ikonu merkeze yerleştirmek
    }

    private void placeIconAtCenter(Node icon, double centerX, double centerY) {
        icon.setLayoutX(map.calculateTopLeftX() + centerX - ((ImageView)icon).getFitWidth() / 2);
        icon.setLayoutY(map.calculateTopLeftY() + centerY - ((ImageView)icon).getFitHeight() / 2);
    }
}
    









