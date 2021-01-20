/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban.Controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Kendall
 */
public class AcercaDeController extends Controller {

    @FXML
    private ImageView box1;
    @FXML
    private ImageView box2;

    @Override
    public void initialize() {fade();}

    @Override
    public Node getRoot() {
       return null;
    }

    /**
     * Initializes the controller class.
     */
    void fade(){   
        
        FadeTransition ft = new FadeTransition(Duration.millis(3000),box1);
         ft.setFromValue(1.0);
         ft.setToValue(0);
         ft.setNode(box1);
         ft.setCycleCount(4);

        ft.play();
       /// f.play();
    }
    
}
