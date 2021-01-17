 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sokoban.Util.AppContext;
import sokoban.Util.FlowController;
import sokoban.Util.Mensaje;

/**
 * FXML Controller class
 *
 * @author Kendall
 */
public class PrincipalController extends Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView img;
    @FXML
    private JFXComboBox<String> cmbox;
    @FXML
    private JFXTextField txtNombre;
    
    String niveles[] = {"1","2","3"};

    /**
     * Initializes the controller class.
     */
   
    @Override
    public void initialize() {
        ObservableList<String> nivel = FXCollections.observableArrayList(niveles);
        cmbox.setItems(nivel);
        transicion();
    }
    public void transicion(){
        FadeTransition ft = new FadeTransition(Duration.millis(1250),img);
        ft.setFromValue(1.0);
        ft.setToValue(0.3);
        ft.setCycleCount(300);
        ft.setAutoReverse(true);
        ft.play();
    }
    public boolean validar(){
        String sms = "";
        if(txtNombre.getText().isEmpty()){
            sms += "Nombre del jugador\n";
            Mensaje.show(Alert.AlertType.ERROR, "Los siguientes datos son necesarios:", sms);
            return false; 
        }
        return true;
    }
            
    @Override
    public Node getRoot() { return root;}

    @FXML
    private void acercade(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("AcercaDe");
    }

    @FXML
    private void Inicio(ActionEvent event) {      
      int n = 1;  
     if(validar()){
       if(cmbox.getSelectionModel().getSelectedItem() != null) n = (int)Integer.parseInt(cmbox.getSelectionModel().getSelectedItem());
       AppContext.getInstance().set("lvl",n);  
        FlowController.getInstance().salir();
        FlowController.getInstance().goViewInWindow("Puzzle");
        }
    }
}