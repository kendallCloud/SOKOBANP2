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
import sokoban.Dto.CoordenadaDto;
import sokoban.Dto.PartidaDto;
import sokoban.MODEL.Coordenada;
import sokoban.Main;
import sokoban.Service.CoordenadaService;
import sokoban.Service.PartidaService;
import sokoban.Util.AppContext;
import sokoban.Util.FlowController;
import sokoban.Util.Mensaje;
import sokoban.Util.Respuesta;
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
    char lst_event;
    Casilla caja1;
    private int nivel,cant_metas;
    int cont_metas = 0;
    int [] meta1 = new int[2];//guardo las coordenadas de la meta
    int [] meta2 = {-1,-1};
    int [] meta3 = {-1,-1};
    int[][] lv = new int[8][6];
    Casilla[][] cas = new Casilla[8][6];
    boolean fail = false;
    boolean moverBox;
    boolean back=true;
     PartidaDto saved = null;
     String name = "";
     boolean nuevo;
    
    @FXML
    private JFXTextField txtnivel;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField txtname;

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize() {
        
        AppContext.getInstance().set("tablero",this);
        tabl = new CasillaController[8][6];
        
    
     if(AppContext.getInstance().get("dto") != null) saved = (PartidaDto) AppContext.getInstance().get("dto");
        
        if(saved != null){
             nivel = saved.getNivel();
             name = saved.getNombre();
             nuevo=false;
             AppContext.getInstance().set("dto",null);
        }else{
           if(AppContext.getInstance().get("dto")==null) nuevo = true;
            nivel = (int)AppContext.getInstance().get("lvl");
            name = (String)AppContext.getInstance().get("nombre");
        }
        AppContext.getInstance().set("nombre",name);
        txtname.setText(name);
        SeleccNivel(nivel);
    
        LlenarCasillas();
                
        root.setOnKeyPressed((KeyEvent event) -> {
            back = true;
            int x=jugador.getFil(),y = jugador.getCol();
        
            if(event.getCode().equals(KeyCode.UP)){
                System.out.println("UP!");
                
                if(1 <= x){
                    if(lv[x-1][y] == 5 || lv[x-1][y] == 3 || lv[x-1][y] == 6){//piso, deathblock o meta
                        MoverJugador(x,y,'U');
                        moverBox = false;
                      
                    }
                    else if(lv[(x-1)][y] == 2){//caja
                        System.out.println("CAJA!");
                        MoverCaja(x-1,y,'U');
                         moverBox = true;
                        if(Gana())GameOver();
                        else if(fail) Mensaje.show(Alert.AlertType.INFORMATION, "F","GAME OVER, NECESITA REINICIO DE NIVEL.");
                    }
                }
            }   if(event.getCode().equals(KeyCode.DOWN)){ 
                System.out.println("DOWN!");
                if(x <= 6){//
                    if(lv[x+1][y] == 5 || lv[x+1][y] == 3|| lv[x+1][y] == 6){//piso o meta
                        MoverJugador(x,y,'D');
                         moverBox = false;
                    }
                    else if(lv[(x+1)][y] == 2){
                        System.out.println("CAJA!");
                        MoverCaja(x+1,y,'D');
                         moverBox = true;
                        if(Gana())GameOver();
                        else if(fail) Mensaje.show(Alert.AlertType.INFORMATION, "F","GAME OVER, NECESITA REINICIO DE NIVEL.");
                    }
                }
            }   if(event.getCode().equals(KeyCode.RIGHT)){ 
                System.out.println("RIGTH!");
                if(y<=4){//
                    if(lv[x][y+1] == 5 || lv[(x)][y+1] == 3 ||lv[x][y+1] == 6){//piso o meta            
                        MoverJugador(x,y,'R');
                        moverBox = false;
                        
                    }
                    else if(lv[(x)][y+1] == 2){
                        System.out.println("CAJA!");      
                        MoverCaja(x,y+1,'R');
                         moverBox = true;
                        if(Gana())GameOver();
                       else if(fail) Mensaje.show(Alert.AlertType.INFORMATION, "F","GAME OVER, NECESITA REINICIO DE NIVEL.");                    }
                }
            }   if(event.getCode().equals(KeyCode.LEFT)){   
                System.out.println("LEFT!");
                if(0 < y){//
                    if(lv[x][y-1] == 5 || lv[(x)][y-1] == 3 ||lv[x][y-1] == 6){//piso o meta
                        MoverJugador(x,y,'L'); 
                        moverBox = false;
                    }
                    else if(lv[(x)][y-1] == 2){//se topa con la caja 
                        System.out.println("CAJA!");
                       MoverCaja(x,y-1,'L');//mando la posición actual y la dirección a la que quiero moverla.
                        moverBox = true;
                        if(Gana())GameOver();
                       else if(fail) Mensaje.show(Alert.AlertType.INFORMATION, "F","GAME OVER, NECESITA REINICIO DE NIVEL.");
                    }
                }
            }   event.consume();
        });
       
                }   
    
      @FXML
    private void Guardar(ActionEvent event){
        PartidaService pquery = new PartidaService();
        CoordenadaService cquery = new CoordenadaService();     
       
        PartidaDto p = new PartidaDto(0,txtname.getText(), nivel);
          Respuesta guard = pquery.guardarPartida(p,nuevo);
      
        char cod,c1 = '-',c2 = '-',c3 = '-';
        String jug,box1,box2,box3;
        int col1 = 0,col2 = 0,col3 = 0;//columnas
        cod = (char) ((char)jugador.getFil()+65);
        int n = 1;
        
        for(int i = 0;i < 8;i++){//obtengo las coordenadas de cada caja para compararlas con las coordenadas de las metas.
            for(int j = 0;j < 6;j++){
                if(lv[i][j]==2){
                    switch (n) {
                        case 1:
                            c1 = (char)((char)i+65);//convierto el número en caracter.
                            col1=j;
                            n++;
                            break;
                        case 2:
                            c2 = (char)((char)i+65);
                            col2=j;
                            n++;
                            break;
                        case 3:
                            c3 = (char)((char)i+65);
                            col3=j;
                            n++;
                            break;
                        default:
                            break;
                    }
                }
            }
         }
        
        jug = ""+cod+""+jugador.getCol();
        box1 = ""+c1+col1;
        box2 = ""+c2+col2;
        box3 = ""+c3+col3;
        
         CoordenadaDto c = new CoordenadaDto(0,jug,box1,box2,box3);
        Respuesta save= cquery.guardarCoords(c,nuevo);
     
        System.out.println(jug);
        System.out.println(box1);
         System.out.println(box2);
          System.out.println(box3);
          
        
          Mensaje.show(Alert.AlertType.WARNING, "Resultado en BD Partida", guard.getMensaje());
          Mensaje.show(Alert.AlertType.WARNING, "Resultado en BD Coordenadas",save.getMensaje());
          System.out.println(guard.getMensajeInterno());
        
    }

    
     @FXML
    private void Reinicio(ActionEvent event) {
        this.initialize();
    }  
    
     @FXML
    private void Retroceder(ActionEvent event) {    
           if(back){RegresarJugador();back=false;}
           if(moverBox) RegresarCaja();moverBox=false;
    }
    
     private void RegresarCaja(){
          int x = caja1.getFil(), y = caja1.getCol();//ubicación de la última caja que moví
          System.out.println("RegresarCaja|"+x+"|"+y+"|"+lst_event);
           
          switch(lst_event){//último evento realizado
          case 'U':
                if(x < 7 && x > 0){
                if(lv[x+1][y] != 2 && 4 != lv[x+1][y]){
                    
                     fail = lv[x+1][y]==6;
                    cas[x+1][y] = caja1;            
                    cas[x+1][y].pos(x+1,y);
                     caja1 = cas[x+1][y];
                     System.out.println(caja1.getCol()+"|"+caja1.getFil());
              
                  tablero.add(CrearPane(cas[x+1][y],x+1,y), y, x+1);//seteo caja.
                     
                  tabl[x][y].Desaparecer();
                 lv[x][y] = 5;
                 lv[x+1][y] = 2;//nueva ubicación de la caja.
                   
                    }
             }
            
          break;
          
          case 'D':
              if(0 < x ){
                if(lv[x-1][y] != 4 && lv[x-1][y] != 2){//4 = pared
                  
                 fail = lv[x-1][y]==6;
                 cas[x-1][y] = caja1;            
                 cas[x-1][y].pos(x-1,y);
                 caja1 = cas[x-1][y];
                 System.out.println(caja1.getCol()+"|"+caja1.getFil());
              
                tablero.add(CrearPane(cas[x-1][y],x-1,y), y, x-1);//seteo caja.
                     
                tabl[x][y].Desaparecer();
                 lv[x][y] = 5;
                 lv[x-1][y] = 2;//nueva ubicación de la caja.
                 //MoverJugador(x,y-1,'R');


//MoverJugador(x+1,y,'U');
                }
             }
               
          break;
            
          case 'L':        
               if(y < 5){  
                if(lv[x][y+1] != 4 && lv[x][y+1] != 2){
                 fail = lv[x][y+1]==6;
                 cas[x][y+1] = caja1;            
                 cas[x][y+1].pos(x,y+1);
                 caja1 = cas[x][y+1];
                 System.out.println(caja1.getCol()+"|"+caja1.getFil());
              
                tablero.add(CrearPane(cas[x][y+1],x, y+1), y+1, x);//seteo caja.
                     
                tabl[x][y].Desaparecer();
                 lv[x][y] = 5;
                 lv[x][y+1] = 2;//nueva ubicación de la caja.
                 //MoverJugador(x,y-1,'R');
                    }
              }
                     
          break;
          
          case 'R':     
                if(0 < y && y < 6){
                if( lv[x][y-1] != 4 && lv[x][y-1] != 2){
                    fail = lv[x][y-1]==6;
                    cas[x][y-1] = caja1;
                    cas[x][y-1].pos(x,y-1);
           
                    caja1 = cas[x][y-1];
                    System.out.println("CAJA|"+caja1.getCol()+"|"+caja1.getFil());
             
                    tablero.add(CrearPane(cas[x][y-1],x, y-1), y-1, x);//seteo caja.
                    
                     tabl[x][y].Desaparecer();
                     lv[x][y] = 5;
                     lv[x][y-1] = 2;//nueva ubicación de la caja.
                      // MoverJugador(x,y+1,'L');                    
                }
             }
          
     
          break;
      }            
         
     }
    
    private void RegresarJugador(){
        
       int x = jugador.getFil(), y = jugador.getCol();
      
      switch(lst_event){
          case 'U':
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
          
          case 'D':
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
            
          case 'L':
              
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
          
          case 'R':
              
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
          
          default:break;
      }            
        
    }
          
     private void MoverJugador(int x,int y,char event){
      
         lst_event = event;  
         
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
      
      
    public void GameOver(){
         Mensaje.show(Alert.AlertType.INFORMATION, "", "NIVEL COMPLETADO.");
         if(nivel < 5){ 
            AppContext.getInstance().set("lvl",(nivel+1));
            saved=null;
            this.initialize();
         }
         else Mensaje.show(Alert.AlertType.INFORMATION, "VICTORY", "¡FELICIDADES!.");
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
            //if(c.getTipo() == 3)  tabl[i][j].setMeta();
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
             
             lv[0][4] =1;
             
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
             lv[4][2] = 6;
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
        lv[7][1] = 6;
        lv[2][4] = 6;
        
           
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
        
        lv[0][1] = 1;//jugador
        
        lv[5][1] = 2;//cajas
        lv[5][3] = 2;
        
        lv[0][0] = 6;
        lv[1][0] = 6;
        lv[5][4] = 6;
        lv[6][5] = 6;
        lv[2][1] = 6;
         lv[3][3] = 6;
        lv[3][1] = 6;
   
     
              
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
        
        lv[3][2]=6;
        lv[4][2]=6;
        lv[4][4]=6;
        lv[5][4]=6;
        lv[2][1]=6;
        lv[0][5]=6;
      
        
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
        lv[3][3] = 2;//caja
        
     }

    
}
