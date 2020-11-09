package covid19.core.api.resource;

import covid19.core.api.dominio.TimeMachine;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Lets see some about java.time working with Postgresql
 *
 * Theres a table <TimeMachine> with columns set to differents time and date types.
 *
 * DATE	                            LocalDate
 * TIME [ WITHOUT TIME ZONE ]       LocalTime
 * TIMESTAMP [ WITHOUT TIME ZONE ]  LocalDateTime
 * TIMESTAMP WITH TIME ZONE	        OffsetDateTime
 *
 */
@QuarkusTest
public class DateTimeTest {
    private OffsetDateTime horaDoPost;
    private OffsetDateTime horaPostVistoUTCminus7;
    private OffsetDateTime horaPostVistoUTC;
    
    @Test
    @Order(1)
    @Transactional(Transactional.TxType.REQUIRED)
    public void deveCriarUmRegistroNaTimeMachine(){
        TimeMachine tm = new TimeMachine();
        tm.localDateColumn = LocalDate.now();
        tm.localTimeColumn = LocalTime.now();
        tm.localDateTimeColumn = LocalDateTime.now();
        tm.offsetDateTimeColumn = OffsetDateTime.now();
        
        this.horaDoPost = tm.offsetDateTimeColumn;
        
        TimeMachine.persist(tm);
        
        System.out.println("--------------------------------------------");
        System.out.println(tm);
        System.out.println("--------------------------------------------");
    
    
        System.out.println("--------------------------------------------");
        System.out.println("horaDoPost............: " + horaDoPost);
   
        calcularVerPostUTCminus7();
        System.out.println("horaPostVistoUTCminus7: " + horaPostVistoUTCminus7);
        
        calcularVerPostUTC();
        System.out.println("horaPostVistoUTC......: " + horaPostVistoUTC);
        
        String data = "2020-09-01";
        LocalDateTime dataODT = LocalDate.parse(data).atTime(14, 47, 00);
        OffsetDateTime odt = dataODT.atZone(ZoneOffset.ofHours(-7)).toOffsetDateTime();
        
        System.out.println("data parseada.........: "+odt);
        System.out.println("--------------------------------------------");
    
    }
    
    
    
    private void calcularVerPostUTC() {
        this.horaPostVistoUTC = horaDoPost.withOffsetSameInstant(ZoneOffset.UTC);
    }
    
    
    
    private void calcularVerPostUTCminus7() {
        //--- this is the offset UTC from
        ZoneOffset dif = horaDoPost.getOffset();
        
        //--- this is US/CA LA timezone -7
        this.horaPostVistoUTCminus7 = horaDoPost.withOffsetSameInstant(ZoneOffset.ofHours(-7));
    }
}
