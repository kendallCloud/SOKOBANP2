/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import sokoban.Clases.Casilla;
import sokoban.Main;
import sokoban.Util.AppContext;
import sokoban.Util.FlowController;
 //import javafx.scene.transform.Translate;

/**
 * FXML Controller class
 * @author Kendall
 */
public class PuzzleController extends Controller {

    @FXML
    private GridPane tablero;
    
    @FXML
    private JFXButton btnPausa;
    
    CasillaController tabl[][] = new CasillaController[8][6];
    Casilla jugador;
    int[][] lv = new int[8][6];
    Casilla[][] cas = new Casilla[8][6];
    @FXML
    private JFXTextField txtnivel;
    @FXML
    private AnchorPane root;

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize() {
        AppContext.getInstance().set("tablero",this);
        tabl = new CasillaController[8][6];     
        SeleccNivel((int)AppContext.getInstance().get("lvl"));
        txtnivel.setText(txtnivel.getText()+AppContext.getInstance().get("lvl"));
        LlenarCasillas();
        
        
        root.setOnKeyPressed(
        new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                
                int x=jugador.getFil(),y = jugador.getCol();
                 System.out.println("|X"+x+"|Y"+y);
        if(event.getCode().equals(KeyCode.UP)){ 
            System.out.println("UP!");
          
            if(1 <= x){
                if(lv[x-1][y] == 5 || lv[x-1][y] == 3){//piso o meta
                    cas[x-1][y] = jugador;
                    cas[x-1][y].pos(x-1,y);
                     lv[x-1][y] = cas[x][y].getTipo();
                    jugador = cas[x-1][y];
                   cas[x][y].setTipo(5);
                //    System.out.println("|X"+jugador.getFil()+"|Y"+jugador.getCol());    
                    tablero.add(CrearPane(cas[x-1][y],(x-1), y), y, x-1);
                     tabl[x][y].Desaparecer();
                }
            }
        }
        
        if(event.getCode().equals(KeyCode.DOWN)){
            System.out.println("DOWN!");
            if(x <= 6){//
                if(lv[x+1][y] == 5 || lv[x+1][y] == 3){//piso o meta
                    cas[x+1][y] = jugador;
                    cas[x+1][y].pos(x+1, y);
                    lv[x+1][y] = cas[x+1][y].getTipo();
                    jugador = cas[x+1][y];
                   cas[x][y].setTipo(5);
                //    System.out.println("|X"+jugador.getFil()+"|Y"+jugador.getCol());    
                    tablero.add(CrearPane(cas[x+1][y],(x+1), y), y, x+1);
                    tabl[x][y].Desaparecer();
                }
            }
        }
        if(event.getCode().equals(KeyCode.RIGHT)){ 
            System.out.println("RIGTH!");
            if(y<=4){//
                if(lv[x][y+1] == 5 || lv[(x)][y+1] == 3){//piso o meta
                    cas[x][y+1] = jugador;
                    cas[x][y+1].pos(x,y+1);
                     lv[x][y+1] = cas[x][y+1].getTipo();
                    jugador = cas[x][y+1];
                   cas[x][y].setTipo(5);
                //    System.out.println("|X"+jugador.getFil()+"|Y"+jugador.getCol());    
                    tablero.add(CrearPane(cas[x][y+1],(x), y+1), y+1, x);
                    tabl[x][y].Desaparecer();
                }
            }
        }
        
        if(event.getCode().equals(KeyCode.LEFT)){  
            System.out.println("LEFT!");
            if(0 < y){// 
                if(lv[x][y-1] == 5 || lv[(x)][y-1] == 3){//piso o meta
                    cas[x][y-1] = jugador;
                    cas[x][y-1].pos(x,y-1);
                    lv[x][y-1] = cas[x][y-1].getTipo();
                    jugador = cas[x][y-1];
                if(lv[x][y-1] == 5)cas[x][y].setTipo(5);
                
                else cas[x][y].setTipo(5);
                //    System.out.println("|X"+jugador.getFil()+"|Y"+jugador.getCol());    
                    tablero.add(CrearPane(cas[x][y-1],x, y-1), y-1, x);
                    tabl[x][y].Desaparecer();
                    
                }
            }
        }   
        event.consume();
    }
        }
            );
       
                }
          

    @Override
    public Node getRoot() {
        return null;
    }
    
     @FXML
    private void Pausar(ActionEvent event) {
        if("Pausar música".equals(btnPausa.getText())){
        FlowController.getInstance().Musica(false);
        btnPausa.setText("Reanudar música");
        }
        else if("Reanudar música".equals(btnPausa.getText())){
            FlowController.getInstance().Musica(true);
            btnPausa.setText("Pausar música");
        }
        
    }

    
  public void CasillaClick(CasillaController clk){
            Casilla c = clk.getObj();
              if(c.getTipo() == 1){
                  
              }
             }
    
    private void LlenarCasillas(){
        Pane p;
        for(int i = 0;i < 8;i++){
            for(int j = 0;j < 6;j++){
                cas[i][j] = new Casilla(lv[i][j],i,j);
                if(lv[i][j]==1) jugador = cas[i][j];
              p = CrearPane(cas[i][j],i,j);
               tablero.add(p,j,i);
            }
        }
    }
    
    private Pane CrearPane(Casilla c,int i,int j){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Casilla.fxml"));
            loader.load();
            CasillaController controller = loader.getController();
            controller.setObj(c);
            tabl[i][j] = controller;
            return loader.getRoot();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }   
    
     private void SeleccNivel(int n){
         for(int i = 0;i < 8;i++){
            for(int j = 0;j < 6;j++){
                lv[i][j]=5;
            }
        }
         switch(n){
             case 1:Nivel1();break;
             case 2:Nivel2();break;
             case 3:Nivel3();break;
             default:Nivel1();break;
         }
         
     }
    
    
  //   1=jugador 2=caja 3=meta 4=pared 5=piso.
    
    public void Nivel3(){
        
        int cont = 0;         

          while(cont<6){
            lv[7][cont] = 4;//pared abajo 
            cont++;
            }
        cont=0;
        while(cont<8){
            lv[cont][0] = 4;//pared izquierda
            lv[cont][5] = 4;//pared derecha
            cont++;
        }
           
        //metas
        lv[1][2] = 2;
        lv[1][3] = 2;
        lv[6][4] = 3;
        
        //cajas
        lv[6][5] = 3;
        lv[2][2] = 2;
        lv[5][4] = 3;
        
        //obstaculos
        lv[5][3] = 4;
        lv[5][2] = 4;
        lv[1][4] = 4;
        
        lv[6][1] =1;//jugador       
           
    
    }
    
      public void Nivel2(){  
        int cont = 0;
        
        lv[0][0] = 1;//jugador
        
        lv[5][1] = 2;//cajas
        lv[5][3] = 2;
        
        lv[1][4] = 3;
        lv[1][5] = 3;
        
        lv[2][0] =4;
        lv[2][4] =4;
        lv[2][5] =4;
        lv[3][0] =4;
        lv[3][1] =4;
        lv[3][4] =4;
        lv[3][5] =4;
        lv[4][0] =4;
        lv[4][1] =4;
        lv[4][3] =4;
        lv[5][5] =4;
         
        
        while(cont<6){
            lv[7][cont] =4;
            cont++;
        } 
        
      }
      
     public void Nivel1(){  
        
        
        lv[7][0] = 1;//jugador
        lv[7][5] = 3;//meta
        
        lv[5][4] = 4;
        lv[5][4] = 4;
        lv[1][2] = 4;
        lv[1][3] = 4;
        lv[2][2] = 4;
        lv[2][3] = 4;
        lv[3][1] = 4;
        lv[4][1] = 4;
        lv[5][1] = 4;
        lv[6][1] = 4;
        lv[6][2] = 4;
        lv[5][2] = 4;
        
        lv[5][5] = 4;
        lv[3][3] = 2;
        
     }

    
}
