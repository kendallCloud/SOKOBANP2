/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban.Clases;

/**
 *
 * @author Kendall
 */
public class Casilla {
    boolean mov;//si la casilla permite movimiento o no.
    boolean transitable;//si se puede o no caminar sobre la casilla.
    int tipo;// 1=jugador 2=caja 3=meta 4=pared.
    int fil;
    int col;
    String img;
    

    public Casilla(boolean mov, int tipo, int fil, int col, String img) {
        this.mov = mov;
        this.tipo = tipo;
        this.fil = fil;
        this.col = col;
        this.img = img;
    }
    
     public Casilla(int tipo, int fil, int col) {
        this.mov = mov(tipo);
        this.tipo = tipo;
        this.fil = fil;
        this.col = col;
        this.img = AsignarImg(tipo);
        this.transitable = CaminarSobre(tipo);
    }

    public Casilla() {
        this.mov = false;
        this.tipo = -1;
        this.fil = -1;
        this.col = -1;
        this.img = "";
        this.transitable = false;
        
    }
     public void pos(int x ,int y){
        this.fil = x;
        this.col = y;
     }
     
     
     
     private String AsignarImg(int id){
        String url = "/sokoban/img/", png =".png";
        
        switch(id){
            case 1:return url+"jugador"+png;
            case 2:return url+"caja"+png;
            case 3:return url+"meta"+png;
            case 4:return url+"block"+png;
            case 5:return url+"piso"+png;
            case 6:return url+"piso"+png;
            default:return "";
        }      
    }
    private boolean mov(int id){return id == 1||id == 2;} //sólo cajas y jugador pueden moverse.
    private boolean CaminarSobre(int id){return id == 3 || id == 1;}//Sólo se puede caminar sobre el piso o sobre la meta.
    
    

    public boolean isMov() {
        return mov;
    }

    public void setMov(boolean mov) {
        this.mov = mov;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getFil() {
        return fil;
    }

    public void setFil(int fil) {
        this.fil = fil;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

   
    
    
    
    
}
