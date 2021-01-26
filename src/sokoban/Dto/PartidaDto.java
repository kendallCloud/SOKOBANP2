/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban.Dto;

import sokoban.MODEL.Partida;

/**
 *
 * @author Kendall
 */
public class PartidaDto {
    private Long id;
    private String nombre;
    private int nivel;
    private CoordenadaDto coords;

    public PartidaDto(int id, String nombre, int nivel) {
        this.id = Long.valueOf(id);
        this.nombre = nombre;
        this.nivel = nivel;
    }
    
     public PartidaDto(int id, String nombre, int nivel,CoordenadaDto crds) {
        this.id = Long.valueOf(id);
        this.nombre = nombre;
        this.nivel = nivel;
        coords = crds;
    }  
    
     public PartidaDto(Partida p){
        this.id = p.getId();
        this.nombre = p.getNombre();
        this.nivel = p.getNivel().intValue();    
     }

    public CoordenadaDto getCoords() {
        return coords;
    }

    public void setCoords(CoordenadaDto coords) {
        this.coords = coords;
    }
     
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }
    
    
    
}
