/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban.MODEL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import sokoban.Dto.PartidaDto;

/**
 * @author Kendall
 */
@Entity
@Table(name = "PARTIDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partida.findAll", query = "SELECT p FROM Partida p")
    , @NamedQuery(name = "Partida.findById", query = "SELECT p FROM Partida p WHERE p.id = :id")
    , @NamedQuery(name = "Partida.findByNombre", query = "SELECT p FROM Partida p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Partida.findByNivel", query = "SELECT p FROM Partida p WHERE p.nivel = :nivel")})
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "NIVEL")
    private Long nivel;
    @JoinColumn(name = "CORDS", referencedColumnName = "ID")
    @ManyToOne
    private Coordenada cords;

    public Partida() {
    }

    public Partida(Long id) {
        this.id = id;
    }

    public Partida(Long id, String nombre, Long nivel) {
        this.id = id;
        this.nombre = nombre;
        this.nivel = nivel;
    }

    public Partida(PartidaDto dto) {
         this.id = dto.getId();
        this.nombre = dto.getNombre();
        this.nivel = Long.valueOf(dto.getNivel());
    }
      public void Actualizar(PartidaDto dto) {
            this.id = Long.valueOf(dto.getId());
            this.nombre = dto.getNombre();
            this.nivel = Long.valueOf(dto.getNivel());
        }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }

    public Coordenada getCords() {
        return cords;
    }

    public void setCords(Coordenada cords) {
        this.cords = cords;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partida)) {
            return false;
        }
        Partida other = (Partida) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sokoban.MODEL.Partida[ id=" + id + " ]";
    }
    
}
