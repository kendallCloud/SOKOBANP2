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

    public PartidaDto(int id, String nombre, int nivel) {
        this.id = Long.valueOf(id);
        this.nombre = nombre;
        this.nivel = nivel;
    }
    
     public PartidaDto(Partida p){
        this.id = p.getId();
        this.nombre = p.getNombre();
        this.nivel = p.getNivel().intValue();    
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
