package covid19.core.api.dominio;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "registro")
public class Registro extends PanacheEntityBase implements Serializable {
    private static final long serialVersionUId = 1L;
    
    @Id
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    public String id;
    
    @Column
    public Boolean naoPegueiCovid19 = false;
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    public OffsetDateTime naoPegueiCovid19Data = null;
    
    @Column
    public Boolean naoSeiSePegueiCovid19 = false;
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    public OffsetDateTime naoSeiSePegueiCovid19Data = null;
    
    @Column
    public Boolean pegueiCovid19FazMenos14Dias= false;
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    public OffsetDateTime pegueiCovid19FazMenos14DiasData = null;
    
    @Column
    public Boolean pegueiCovid19FazMais14Dias = false;
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    public OffsetDateTime pegueiCovid19FazMais14DiasData = null;
    
    @Column
    public Boolean curadoDeCovid19= false;
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    public OffsetDateTime curadoDeCovid19Data = null;
    
    
}
