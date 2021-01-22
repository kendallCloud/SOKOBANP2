/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban.Controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sokoban.Clases.Casilla;
import sokoban.Util.AppContext;

/**
 * FXML Controller class
 *
 * @author Kendall
 */
public class CasillaController extends Controller {

    /**
     * Initializes the controller class.
     */
    Casilla obj;
    @FXML
    private ImageView img;
    @FXML
    private Pane pane;
    
    private final PuzzleController cnt = (PuzzleController) AppContext.getInstance().get("tablero");
      

    public Casilla getObj() {
        return obj;
    }

    public void setObj(Casilla obj) {
        this.obj = obj;
        img.setImage(new Image(obj.getImg()));
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public void Desaparecer(){
         FadeTransition ft = new FadeTransition(Duration.millis(500),img);
         ft.setFromValue(1.0);
         ft.setToValue(0);
         ft.setNode(img);
         ft.setCycleCount(1);
         ft.play();
    }
     public void Aparecer(){
            FadeTransition ft = new FadeTransition(Duration.millis(500),img);
         ft.setFromValue(0);
         ft.setToValue(1.0);
         ft.setNode(img);
         ft.setCycleCount(4);
         ft.play();
    }
    
    
    @Override
    public void initialize() {

    }

    @Override
    public Node getRoot() {
       return null;
    }

    
    
}
