package sokoban.Util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sokoban.Controller.Controller;
import sokoban.Main;

import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.scene.media.AudioClip;

public class FlowController {
    
    private static FlowController INSTANCE = null;
    private static Stage mainStage;
    private static ResourceBundle idioma;
    private static HashMap<String, FXMLLoader> loaders = new HashMap<>();
    //FMLLoader: interfaz y controlador de la interfaz grafica
    private FlowController() {
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (FlowController.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FlowController();
                }
            }
        }
    }
     public void Musica(boolean On){
         AudioClip mus;
        if(On){
             mus = new AudioClip(this.getClass().getResource("/sokoban/img/OnepunchMan.mp3").toString());
            mus.play();
            mus.setVolume(mus.getVolume()-200);
            
        }else{
             mus = new AudioClip(this.getClass().getResource("/sokoban/img/OnepunchMan.mp3").toString());
            mus.stop();
        }
    }
    
    

    public static FlowController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public void InitializeFlow(Stage stage, ResourceBundle idioma) {
        getInstance();
        this.mainStage = stage;
        this.idioma = idioma;
    }
 
    private FXMLLoader getLoader(String name) {
        FXMLLoader loader = loaders.get(name);
        if (loader == null) {
            synchronized (FlowController.class) {
                if (loader == null) {
                    try {
                        loader = new FXMLLoader(Main.class.getResource("View/" + name + ".fxml"), null);
                        loader.load();
                        loaders.put(name, loader);
                    } catch (Exception ex) {
                        loader = null;
                        java.util.logging.Logger.getLogger(FlowController.class.getName()).log(Level.SEVERE, "Creando loader [" + name + "].", ex);
                    }
                }
            }
        }
        return loader;
    }

    public void goMain() {
        try {
            Musica(true);
            FlowController.mainStage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("View/Principal.fxml"),null)));
          
            FlowController.mainStage.setTitle("SOKOBAN");
            FlowController.mainStage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FlowController.class.getName()).log(Level.SEVERE, "Error inicializando la vista base.", ex);
        }
    }

    public void goView(String viewName) {
        goView(viewName, "Center", null);
       
    }
    
    public void goViewLeft(String viewName) {
        goView(viewName, "Left", null);
    }
    
    public void goView(String viewName, String accion) {
        goView(viewName, "Center", accion);
    }

    public void goView(String viewName, String location, String accion) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.setAccion(accion);
        controller.initialize();
        Stage stage = controller.getStage();
        if (stage == null) {
            stage = this.mainStage;
            controller.setStage(stage);
        }
        switch (location) {
            case "Center":
                ((HBox) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().clear();
                ((HBox) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().add(loader.getRoot());
                break;
            case "Top":
                ((VBox) ((BorderPane) stage.getScene().getRoot()).getTop()).getChildren().clear();
                ((VBox) ((BorderPane) stage.getScene().getRoot()).getTop()).getChildren().add(loader.getRoot());
                break;
            case "Bottom":
                ((VBox) ((BorderPane) stage.getScene().getRoot()).getBottom()).getChildren().clear();
                ((VBox) ((BorderPane) stage.getScene().getRoot()).getBottom()).getChildren().add(loader.getRoot());
                break;
            case "Right":
                ((VBox) ((BorderPane) stage.getScene().getRoot()).getRight()).getChildren().clear();
                ((VBox) ((BorderPane) stage.getScene().getRoot()).getRight()).getChildren().add(loader.getRoot());
                break;
            case "Left":
                ((VBox) ((BorderPane) stage.getScene().getRoot()).getLeft()).getChildren().clear();
                ((VBox) ((BorderPane) stage.getScene().getRoot()).getLeft()).getChildren().add(loader.getRoot());
                break;
                
            default:
                break;
        }
    }

    public void goViewInStage(String viewName, Stage stage) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.setStage(stage);
        stage.getScene().setRoot(loader.getRoot());
    }

    public void goViewInWindow(String viewName) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.initialize();
        Stage stage = new Stage();
        stage.getIcons().add(new Image("sokoban/img/meta.png"));
        stage.setTitle("SOKOBAN");
        stage.setOnHidden((WindowEvent event) -> {
            controller.getStage().getScene().setRoot(new Pane());
            controller.setStage(null);
        });
        controller.setStage(stage);
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public void goViewInWindowModal(String viewName, Stage parentStage, Boolean resizable) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.initialize();
        Stage stage = new Stage();
       // stage.getIcons().add(new Image("/cr/ac/una/Tarea1_Torneos/Icons/soccer_ball_WHITE.png"));
       // stage.setTitle("Tarea Cooperativa");
        stage.setResizable(resizable);
        stage.setOnHidden((WindowEvent event) -> {
            controller.getStage().getScene().setRoot(new Pane());
            controller.setStage(null);
        });
        controller.setStage(stage);
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.centerOnScreen();
        stage.showAndWait();

    }

    public Controller getController(String viewName) {
        return getLoader(viewName).getController();
    }

    public static void setIdioma(ResourceBundle idioma) {
        FlowController.idioma = idioma;
    }
    
    public void initialize() {
        this.loaders.clear();
    }

    public void salir() {
        this.mainStage.close();
    }
    
    
    //////////////////////////////////////////////////////////////////////////
}
