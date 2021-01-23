package sokoban.MODEL;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sokoban.MODEL.Coordenada;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-01-23T15:27:45")
@StaticMetamodel(Partida.class)
public class Partida_ { 

    public static volatile SingularAttribute<Partida, Coordenada> cords;
    public static volatile SingularAttribute<Partida, BigDecimal> id;
    public static volatile SingularAttribute<Partida, String> nombre;
    public static volatile SingularAttribute<Partida, BigInteger> nivel;

}