/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import sokoban.Dto.PartidaDto;
import sokoban.MODEL.Partida;
import sokoban.Util.EntityManagerHelper;
import sokoban.Util.Respuesta;

/**
 *
 * @author Kendall
 */
public class PartidaService {
     public EntityManager em;
    public EntityTransaction et;

    public PartidaService() {
        em = EntityManagerHelper.getManager();
    }
    
    
      public Respuesta getByNombre(String name){
        try {
            Query query = em.createNamedQuery( "Partida.findByNombre",Partida.class);
            
            Query setParameter = query.setParameter("nombre",name);
            return new Respuesta(Boolean.TRUE, "", "", "Partida", convertirLista(query.getResultList()));
        } catch (Exception ex) {
            Logger.getLogger(PartidaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al obtener la partida", ex);
            return new Respuesta(Boolean.FALSE, "Ocurrio un error al obtener la partida", ex.getMessage());
        }
    }
       
      public Respuesta guardarPartida(PartidaDto dto){
        try {
            et = em.getTransaction();
            et.begin();
            Partida entity = new Partida();
           //nuevo Paciente.
                entity = new Partida(dto);
                em.persist(entity);//Guardo en BD
             
                entity = em.find(Partida.class,dto.getId());
                if(entity == null){
                    et.rollback();
                    return new Respuesta(false, "No se encontró la partida a modificar", "NoResultException");
                }
                entity.Actualizar(dto);
                entity = em.merge(entity);
            
            et.commit();
            return new Respuesta(true, "Se guardó el paciente  exitosamente", "", "Partida", new PartidaDto(entity));
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(Partida.class.getName()).log(Level.SEVERE, "Ocurrio un error al registrar la partida", ex);
            return new Respuesta(Boolean.FALSE, "Ocurrio un error al registrar la partida", ex.getMessage());
        }
    }
      
      private List<PartidaDto> convertirLista(List<Partida> lista){
        List<PartidaDto> listaDto = new ArrayList<>();
        lista.forEach(partida -> {
            listaDto.add(new PartidaDto(partida));
        });
        return listaDto;
    }
    
}
