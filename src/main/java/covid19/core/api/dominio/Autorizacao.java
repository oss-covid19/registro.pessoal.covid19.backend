package covid19.core.api.dominio;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "autorizacao")
public class Autorizacao  extends PanacheEntityBase implements Serializable {
    @Id
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    public String id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "registro_id", referencedColumnName = "id")
    public Registro registro;
    
    @Column
    public String email = null;
    
    @Column
    public String password = null;
    
    @Column
    public Boolean recadastrar = null;
    
    public static Autorizacao findByEmail(String email){
        return find("email",email ).firstResult();
    }
    
}
