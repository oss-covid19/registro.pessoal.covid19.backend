package covid19.core.api.dominio;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;

/**
 * Only purpose this table is to exercise the java datetime types to postgres equivalents.
 * DATE	                            LocalDate
 * TIME [ WITHOUT TIME ZONE ]       LocalTime
 * TIMESTAMP [ WITHOUT TIME ZONE ]  LocalDateTime
 * TIMESTAMP WITH TIME ZONE	        OffsetDateTime
 *
 */

@Entity
@Table(name="timemachine")
public class TimeMachine extends PanacheEntity implements Serializable {
    
    @Column(columnDefinition = "DATE")
    public LocalDate localDateColumn;
    
    @Column(columnDefinition = "TIME")
    public LocalTime localTimeColumn;
    
    @Column(columnDefinition = "TIMESTAMP")
    public LocalDateTime localDateTimeColumn;
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    public OffsetDateTime offsetDateTimeColumn;
    
    
    
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                   .append("localDateColumn", localDateColumn)
                   .append("localTimeColumn", localTimeColumn)
                   .append("localDateTimeColumn", localDateTimeColumn)
                   .append("offsetDateTimeColumn", offsetDateTimeColumn)
                   .toString();
    }
}

