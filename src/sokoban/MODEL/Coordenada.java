/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban.MODEL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kenda
 */
@Entity
@Table(name = "COORDENADA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coordenada.findAll", query = "SELECT c FROM Coordenada c")
    , @NamedQuery(name = "Coordenada.findById", query = "SELECT c FROM Coordenada c WHERE c.id = :id")
    , @NamedQuery(name = "Coordenada.findByJugador", query = "SELECT c FROM Coordenada c WHERE c.jugador = :jugador")
    , @NamedQuery(name = "Coordenada.findByCaja1", query = "SELECT c FROM Coordenada c WHERE c.caja1 = :caja1")
    , @NamedQuery(name = "Coordenada.findByCaja2", query = "SELECT c FROM Coordenada c WHERE c.caja2 = :caja2")
    , @NamedQuery(name = "Coordenada.findByCaja3", query = "SELECT c FROM Coordenada c WHERE c.caja3 = :caja3")})
public class Coordenada implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "JUGADOR")
    private String jugador;
    @Basic(optional = false)
    @Column(name = "CAJA1")
    private String caja1;
    @Column(name = "CAJA2")
    private String caja2;
    @Column(name = "CAJA3")
    private String caja3;
    @OneToMany(mappedBy = "cords")
    private Collection<Partida> partidaCollection;

    public Coordenada() {
    }

    public Coordenada(BigDecimal id) {
        this.id = id;
    }

    public Coordenada(BigDecimal id, String jugador, String caja1) {
        this.id = id;
        this.jugador = jugador;
        this.caja1 = caja1;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public String getCaja1() {
        return caja1;
    }

    public void setCaja1(String caja1) {
        this.caja1 = caja1;
    }

    public String getCaja2() {
        return caja2;
    }

    public void setCaja2(String caja2) {
        this.caja2 = caja2;
    }

    public String getCaja3() {
        return caja3;
    }

    public void setCaja3(String caja3) {
        this.caja3 = caja3;
    }

    @XmlTransient
    public Collection<Partida> getPartidaCollection() {
        return partidaCollection;
    }

    public void setPartidaCollection(Collection<Partida> partidaCollection) {
        this.partidaCollection = partidaCollection;
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
        if (!(object instanceof Coordenada)) {
            return false;
        }
        Coordenada other = (Coordenada) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sokoban.MODEL.Coordenada[ id=" + id + " ]";
    }
    
}
