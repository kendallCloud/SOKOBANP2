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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import sokoban.Clases.Casilla;
import sokoban.Main;
import sokoban.Util.AppContext;
import sokoban.Util.FlowController;
import sokoban.Util.Mensaje;
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
    Casilla caja1;
    private int nivel,cant_metas;
    int cont_metas = 0;
    int [] meta1 = new int[2];//guardo las coordenadas de la meta
    int [] meta2 = {-1,-1};
    int [] meta3 = {-1,-1};
    int[][] lv = new int[8][6];
    Casilla[][] cas = new Casilla[8][6];
    boolean fail = false;
    
    @FXML
    private JFXTextField txtnivel;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField txtmetas;

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize() {
        
        AppContext.getInstance().set("tablero",this);
        tabl = new CasillaController[8][6];
        nivel = (int)AppContext.getInstance().get("lvl");
        SeleccNivel(nivel);
    
        LlenarCasillas();
                
        root.setOnKeyPressed((KeyEvent event) -> {
            int x=jugador.getFil(),y = jugador.getCol();
          //  System.out.println("Jugador|X"+x+"|Y"+y);
            if(event.getCode().equals(KeyCode.UP)){
                System.out.println("UP!");
                
                if(1 <= x){
                    if(lv[x-1][y] == 5 || lv[x-1][y] == 3 || lv[x-1][y] == 6){//piso o meta
                        MoverJugador(x,y,'U');
                      
                    }
                    else if(lv[(x-1)][y] == 2){//caja
                        System.out.println("CAJA!");
                        MoverCaja(x-1,y,'U');
                        if(Gana())GameOver();
                        else if(fail) Mensaje.show(Alert.AlertType.INFORMATION, "F","GAME OVER, NIVEL FALLIDO.");
                    }
                }
            }   if(event.getCode().equals(KeyCode.DOWN)){ 
                System.out.println("DOWN!");
                if(x <= 6){//
                    if(lv[x+1][y] == 5 || lv[x+1][y] == 3|| lv[x+1][y] == 6){//piso o meta
                        MoverJugador(x,y,'D');                    
                    }
                    else if(lv[(x+1)][y] == 2){
                        System.out.println("CAJA!");
                        MoverCaja(x+1,y,'D');
                        if(Gana())GameOver();
                        else if(fail) Mensaje.show(Alert.AlertType.INFORMATION, "F","GAME OVER, NIVEL FALLIDO.");
                    }
                }
            }   if(event.getCode().equals(KeyCode.RIGHT)){ 
                System.out.println("RIGTH!");
                if(y<=4){//
                    if(lv[x][y+1] == 5 || lv[(x)][y+1] == 3 ||lv[x][y+1] == 6){//piso o meta            
                        MoverJugador(x,y,'R');
                        
                    }
                    else if(lv[(x)][y+1] == 2){
                        System.out.println("CAJA!");      
                        MoverCaja(x,y+1,'R');
                        if(Gana())GameOver();
                        else if(fail) Mensaje.show(Alert.AlertType.INFORMATION, "F","GAME OVER, NIVEL FALLIDO.");
                    }
                }
            }   if(event.getCode().equals(KeyCode.LEFT)){   
                System.out.println("LEFT!");
                if(0 < y){//
                    if(lv[x][y-1] == 5 || lv[(x)][y-1] == 3 ||lv[x][y-1] == 6){//piso o meta
                        MoverJugador(x,y,'L');     
                    }
                    else if(lv[(x)][y-1] == 2){//se topa con la caja 
                        System.out.println("CAJA!");
                       MoverCaja(x,y-1,'L');//mando la posición actual y la dirección a la que quiero moverla.
                        if(Gana())GameOver();
                        else if(fail) Mensaje.show(Alert.AlertType.INFORMATION, "F","GAME OVER, NIVEL FALLIDO.");
                    }
                }
            }   event.consume();
        });
       
                }
    
    
    
      @FXML
    private void Guardar(ActionEvent event) {
        char cod,c1 = '-',c2 = '-',c3 = '-';
        cod = (char) ((char)jugador.getFil()+65);
        int n = 1;
        
        for(int i = 0;i < 8;i++){//obtengo las coordenadas de cada caja para compararlas con las coordenadas de las metas.
            for(int j = 0;j < 6;j++){
                if(lv[i][j]==2){
                    switch (n) {
                        case 1:
                            c1 = (char)((char)i+65);//convierto el número en caracter.
                            n++;
                            break;
                        case 2:
                            c2 = (char)((char)i+65);//convierto el número en caracter.
                            n++;
                            break;
                        case 3:
                            c3 = (char)((char)i+65);//convierto el número en caracter.
                            n++;
                            break;
                        default:
                            break;
                    }
                }
            }
         }
        
     
        System.out.println("player"+cod+"|"+jugador.getCol());
        System.out.println("caja1"+c1+"|");
         System.out.println(c2+"|");
          System.out.println(c3+"|");
        
    }

    
    
     @FXML
    private void Reinicio(ActionEvent event) {
        Mensaje.show(Alert.AlertType.CONFIRMATION, "FAVOR CONFIRMAR", "¿Seguro de reiniciar el nivel?");      
        this.initialize();
    }    
          
     private void MoverJugador(int x,int y,char event){
         switch(event){
            case 'U':
                if(0 < x){
                    cas[x-1][y] = jugador;
                    cas[x-1][y].pos(x-1,y);                 
                    jugador = cas[x-1][y];
                   cas[x][y].setTipo(5);
                    tablero.add(CrearPane(cas[x-1][y],(x-1), y), y, x-1);               
                     tabl[x][y].Desaparecer();
                      lv[x][y] = 5;
                }

            break;          
                
            case 'D':
                if(x<7){
                   cas[x+1][y] = jugador;
                    cas[x+1][y].pos(x+1, y);
                    jugador = cas[x+1][y];
                   cas[x][y].setTipo(5);
                    tablero.add(CrearPane(cas[x+1][y],(x+1), y), y, x+1);
                    tabl[x][y].Desaparecer();
                     lv[x][y] = 5;
                }
                 
            break;                
                
            case 'R':  
               if(y<6){
                 cas[x][y+1] = jugador;
                 cas[x][y+1].pos(x,y+1);
                   
                 jugador = cas[x][y+1];
                 cas[x][y].setTipo(5);
                  
                 tablero.add(CrearPane(cas[x][y+1],(x), y+1), y+1, x);
                 tabl[x][y].Desaparecer();
                 lv[x][y] = 5;
                  lv[x][y+1]=2;

         }
                
            break;
            case 'L': 
             if(0<y){
                    cas[x][y-1] = jugador;
                    cas[x][y-1].pos(x,y-1);
                    jugador = cas[x][y-1];
                if(lv[x][y-1] == 5)cas[x][y].setTipo(5);        
                else cas[x][y].setTipo(5);
                    tablero.add(CrearPane(cas[x][y-1],x, y-1), y-1, x);
                     tabl[x][y].Desaparecer();
                     lv[x][y] = 5;
                      lv[x][y-1]=2;
                }
            break;
         }
     }         
     
    private void MoverCaja(int x,int y,char event){
      
        switch(event){
            case 'U':
             if(0 < x ){
                if(lv[x-1][y] != 4 && lv[x-1][y] != 2){//4 = pared
                      fail = lv[x-1][y]==6;
                    cas[x-1][y] = caja1;
                    cas[x-1][y].pos(x-1,y);                 
                    caja1 = cas[x-1][y];
                    System.out.println(caja1.getCol()+"|"+caja1.getFil());
                   

                    tablero.add(CrearPane(cas[x-1][y],x, y),y, x-1);//seteo caja.
                  
                     tabl[x+1][y].Desaparecer();
                     lv[x][y]=5;
                     lv[x-1][y]=2;
                       MoverJugador(x+1,y,'U');
                }
             }
                          break; 
                //             
            case 'D':
             if(x < 7 && x > 0){
                if(lv[x+1][y] != 2 && 4 != lv[x+1][y]){
                    fail = lv[x+1][y]==6;
                //mover caja
                   cas[x+1][y] = caja1;
                    cas[x+1][y].pos(x+1,y);                 
                    caja1 = cas[x+1][y];
                    System.out.println(caja1.getCol()+"|"+caja1.getFil());
                    tablero.add(CrearPane(cas[x+1][y],x, y),y, x+1);//seteo caja.
                
                     tabl[x-1][y].Desaparecer();
                     lv[x+1][y]=2;
                     lv[x][y] = 5;
                     MoverJugador(x-1,y,'D'); 
                    }
             }
                     break; 
                
                
            case 'R':         
                 //mover caja
              if(y < 5){  
                if(lv[x][y+1] != 4 && lv[x][y+1] != 2){
                 fail = lv[x][y+1]==6;
                 cas[x][y+1] = caja1;            
                    cas[x][y+1].pos(x,y+1);                 
                    caja1 = cas[x][y+1];
                    System.out.println(caja1.getCol()+"|"+caja1.getFil());
              
                    tablero.add(CrearPane(cas[x][y+1],x, y+1), y+1, x);//seteo caja.
                     
                     tabl[x][y-1].Desaparecer();
                     lv[x][y] = 5;
                     lv[x][y+1] = 2;//nueva ubicación de la caja.
                     MoverJugador(x,y-1,'R');
                    }
              }
                     break;             
            case 'L':  
             if(0 < y ){
                if( lv[x][y-1] != 4 && lv[x][y-1] != 2){
                    fail = lv[x][y-1]==6;
                 cas[x][y-1] = caja1;
                    cas[x][y-1].pos(x,y-1);                 
                    caja1 = cas[x][y-1];
                    System.out.println("CAJA|"+caja1.getCol()+"|"+caja1.getFil());
             
                    tablero.add(CrearPane(cas[x][y-1],x, y-1), y-1, x);//seteo caja.
                    
                     tabl[x][y+1].Desaparecer();
                         lv[x][y] = 5;
                     lv[x][y-1] = 2;//nueva ubicación de la caja.
                       MoverJugador(x,y+1,'L');                    
                }
             }
                     break;               
        }      
           
       }
      private boolean Gana(){
        
        int[][] xy = new  int[3][2];
        int n = 0,n1 = 0;
        boolean check1 = false,check2 = false,check3 = false;
        
        for(int i = 0;i < 8;i++){//obtengo las coordenadas de cada caja para compararlas con las coordenadas de las metas.
            for(int j = 0;j < 6;j++){
                if(lv[i][j]==2){
                    xy[n][0]=i;
                    xy[n][1]=j;
                    if(n < 2) n++;
                   else n=0;
                }
                else{
                   if(n1 < 2) n1++;
                   else n1=0;
                }
            }
         }
        
        for(int f=0;f < 3;f++){
            if(xy[f][0] == meta1[0] && xy[f][1] == meta1[1])check1 =true;
        }
        
          for(int f=0;f < 3;f++){
            if(xy[f][0] == meta2[0] && xy[f][1] == meta2[1])check2 =true;
            }
          
            for(int f=0;f < 3;f++){
            if(xy[f][0] == meta3[0] && xy[f][1] == meta3[1])check3 =true;
            }
        

           switch(cant_metas){
               case 1: return check1;
               case 2: return check1&&check2;
               case 3: return check1&&check2&&check3;
               default: return false;
           }
    }
      
      
    public boolean Pierde(){
        
        
        
        return false;
    }
      
    public void GameOver(){
         Mensaje.show(Alert.AlertType.INFORMATION, "", "NIVEL COMPLETADO.");
         if(nivel < 5){ 
            AppContext.getInstance().set("lvl",(nivel+1));
            this.initialize();
         }
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

    private void LlenarCasillas(){
        Pane p;
        for(int i = 0;i < 8;i++){
            for(int j = 0;j < 6;j++){
                cas[i][j] = new Casilla(lv[i][j],i,j);
                if(lv[i][j]==1) jugador = cas[i][j];
                if(lv[i][j]==2) caja1 = cas[i][j];
                    
                
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
             case 2:Nivel2();break;
             case 3:Nivel3();break;
             case 4:Nivel4();break;
             case 5:Nivel5();break;
             default:Nivel1();break;
         }
         
     }
      
  //   1=jugador 2=caja 3=meta 4=pared 5=piso.
     
          public void Nivel5(){
                txtnivel.setText("NIVEL 5");
             
               
            int cont = 0;     
                   
            while(cont<6){
                 lv[0][cont] = 6;
                 lv[7][cont] = 6;
                  cont++;
                }           
                cont=0;
            while(cont<8){
                lv[cont][5] = 6;
             if(cont%2 == 0) lv[cont][3] = 4;
             if(cont%2 == 0) lv[cont][1] = 4;
                 cont++;
            }
            lv[1][2]=1;
            lv[7][0]=6;
            
            cont = 2;          
                while(cont<8){
             if(cont%2 == 0) lv[cont][4] = 2;
                 cont++;
            }
                
             cont = 0;          
                while(cont<6){
             if(cont%2 == 0) lv[cont][0] = 3;
                 cont++;
                }            
             
             meta1[0]=0;meta1[1]=0;
             meta2[0]=2;meta2[1]=0;
             meta3[0]=4;meta3[1]=0;
             cant_metas = 3;
              
            
          }
     
         public void Nivel4(){
             txtnivel.setText("NIVEL 4");
             
               for(int i = 0;i < 6;i++){
                 lv[i][5]=6;
                }        
             
             lv[0][5] =1;
             
             lv[2][1] =4;
             lv[3][2] =4;
             lv[4][3] =4;
             lv[3][5] =4;
             lv[4][5] =4;
             
             lv[4][1]=2;
             lv[5][1]=2;
             lv[6][1]=2;   
             
             lv[0][0]=3;
             lv[2][2]=3;
             lv[4][4]=3;
             
             meta1[0]=0;meta1[1]=0;
             meta2[0]=2;meta2[1]=2;
             meta3[0]=4;meta3[1]=4;
             
             lv[3][3]=6;
             lv[3][1]=6;
             // lv[3][3]=6;
             
             for(int i = 0;i < 6;i++){
                 lv[7][i]=6;
             }
             
            cant_metas = 3;
         
         }
    
    public void Nivel3(){
       
         txtnivel.setText("NIVEL 3");
        int cont = 0;         

          while(cont<6){
            lv[7][cont] = 4;//pared abajo 
             lv[0][cont] = 6;
            cont++;
            }
        cont=0;
        while(cont<8){
            lv[cont][0] = 4;//pared izquierda
            lv[cont][1] = 6;
            lv[cont][5] = 4;//pared derecha
             
            cont++;
        }
        
        lv[0][4] = 6;
        lv[7][4] = 6;
           
        //cajas
        lv[1][2] = 2;
        lv[1][3] = 2;
        lv[2][2] = 2;
       
        
        //metas
        lv[6][5] = 3;
        lv[6][4] = 3;
        lv[5][4] = 3;
        
        meta1[0]=6;meta1[1]=5;
        meta2[0]=6;meta2[1]=4;
        meta3[0]=5;meta3[1]=4;   
        cant_metas = 3;
        
        //obstaculos
        lv[5][3] = 4;
        lv[5][2] = 4;
        lv[1][4] = 4;
        
        lv[6][1] =1;//jugador                
    
    }
    
      public void Nivel2(){  
           txtnivel.setText("NIVEL 2");
        int cont = 0;
        
         
        while(cont<8){
            lv[cont][0] = 6;//pared izquierda
            lv[cont][5] = 6;//pared derecha            
            cont++;
        }
        cont=0;  
        
        lv[0][0] = 1;//jugador
        
        lv[5][1] = 2;//cajas
        lv[5][3] = 2;
        
        lv[5][4] = 6;
        lv[6][5] = 6;
        lv[2][1] = 6;
         lv[3][3] = 6;
        
        
        
        lv[1][4] = 3;
        meta1[0] = 1;meta1[1] = 4;
        
        lv[0][5] = 3;
        meta2[0] = 0;meta2[1] = 5;
        
         cant_metas = 2;
        
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
        txtnivel.setText("NIVEL 1");
        
          for(int i = 0;i < 8;i++){lv[i][0]=6; lv[i][5]=6;}
        
        lv[7][0] = 1;//jugador
        lv[7][5] = 3;//meta
        
        lv[3][2] = 6;
        lv[4][2] = 6;
        lv[2][1] = 6;
        lv[0][5] = 6;
        
        meta1[0]=7;
        meta1[1]=5;
        cant_metas =1;
        
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
