package sokoban.MODEL;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sokoban.MODEL.Partida;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-01-23T15:27:45")
@StaticMetamodel(Coordenada.class)
public class Coordenada_ { 

    public static volatile SingularAttribute<Coordenada, String> jugador;
    public static volatile SingularAttribute<Coordenada, BigDecimal> id;
    public static volatile SingularAttribute<Coordenada, String> caja1;
    public static volatile SingularAttribute<Coordenada, String> caja2;
    public static volatile CollectionAttribute<Coordenada, Partida> partidaCollection;
    public static volatile SingularAttribute<Coordenada, String> caja3;

}