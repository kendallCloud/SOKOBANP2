/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban.Dto;

import sokoban.MODEL.Coordenada;

/**
 *
 * @author Kendall
 */
public class CoordenadaDto {
    private Long id; 
    private String jugador;
    private String caja1;   
    private String caja2;
    private String caja3;

    public CoordenadaDto(int id, String jugador, String caja1, String caja2, String caja3) {
        this.id = Long.valueOf(id);
        this.jugador = jugador;
        this.caja1 = caja1;
        this.caja2 = caja2;
        this.caja3 = caja3;
    }

    public CoordenadaDto(Coordenada entity) {
       this.id = entity.getId();
       this.jugador = entity.getJugador();
       this.caja1 = entity.getCaja1();
       this.caja2 = entity.getCaja2();
       this.caja3 = entity.getCaja3();
    }

    public int getId() {
        return id.intValue();
    }

    public String getJugador() {
        return jugador;
    }

    public String getCaja1() {
        return caja1;
    }

    public String getCaja2() {
        return caja2;
    }

    public String getCaja3() {
        return caja3;
    }
    
    
}
