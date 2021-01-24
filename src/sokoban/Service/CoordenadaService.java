/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban.Service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import sokoban.Dto.CoordenadaDto;
import sokoban.MODEL.Coordenada;
import sokoban.Util.EntityManagerHelper;
import sokoban.Util.Respuesta;

/**
 *
 * @author Kendall
 */
public class CoordenadaService {
    public EntityManager em;
    public EntityTransaction et;

    public CoordenadaService() {
        em = EntityManagerHelper.getManager();
    }
    
    
     public Respuesta guardarCoords(CoordenadaDto dto, boolean nuevo) {
        try {
            et = em.getTransaction();
            et.begin();
             Coordenada entity;
            if (nuevo) {//nuevo Paciente.
                entity = new Coordenada(dto);
                em.persist(entity);//Guardo en BD
            } else {
                entity = em.find(Coordenada.class, dto.getId());
                if(entity == null){
                    et.rollback();
                    return new Respuesta(false, "No se encontraron las coordenadas a modificar", "NoResultException");
                }
                entity.Actualizar(dto);
                entity = em.merge(entity);
            }
            et.commit();
            return new Respuesta(true, "Se guardo el paciente  exitosamente", "", "Coordenada", new CoordenadaDto(entity));
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(CoordenadaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al registrar las coordenadas", ex);
            return new Respuesta(Boolean.FALSE, "Ocurrio un error al registrar el paciente", ex.getMessage());
        }
    }
    
}
