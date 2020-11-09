package covid19.core.api.dominio;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Testar:
 * -Gravar somente Autenticacao
 * -Gravar Registro associado a uma Autenticacao existente
 * -Recuperar Autenticacao e Registro associado
 *
 */
@QuarkusTest
public class RelacionalTest {
    
    /**
     * Tem que apenas gravar Autorizacao com null para Registro
     */
    @Transactional(Transactional.TxType.REQUIRED)
    @Test
    public void testaGravarSomenteAutenticacao() {
        Autorizacao autorizacao = new Autorizacao();
        Autorizacao.persist(autorizacao);
    }
    
    
    /**
     * Tem que associar Registro na Autorizacao já existente, gravar o Registro
     */
    @Transactional(Transactional.TxType.REQUIRED)
    @Test
    public void testaGravarRegistroAssociadoUmamaAutenticacaoExistente() {
        //--- usuario autenticado
        Autorizacao autorizacao = new Autorizacao();
        autorizacao.email = "usuario.autenticado";
        autorizacao.password = "senha autenticada";
        Autorizacao.persist(autorizacao);
        
        assertNull(autorizacao.registro);
        assertNotNull(autorizacao.id);
        

        Registro registro = new Registro();
        autorizacao.registro = registro;
        
        registro.naoPegueiCovid19 = true;
        registro.curadoDeCovid19 = true;
        registro.naoSeiSePegueiCovid19 = true;
        registro.pegueiCovid19FazMais14Dias = true;
        registro.pegueiCovid19FazMenos14Dias = true;
        registro.curadoDeCovid19Data = OffsetDateTime.now();
        registro.naoPegueiCovid19Data = OffsetDateTime.now();
        registro.naoSeiSePegueiCovid19Data = OffsetDateTime.now();
        registro.pegueiCovid19FazMais14DiasData = OffsetDateTime.now();
        registro.pegueiCovid19FazMenos14DiasData = OffsetDateTime.now();
        
        Registro.persist(registro);
        assertEquals(autorizacao.registro.id, registro.id);
    }
    
    
    @Transactional(Transactional.TxType.REQUIRED)
    @Test
    public void testaRecuperarAutenticacaoComRegistroAssociado() {
        String userId = "7604764b-c1a2-47f7-ac4e-c572a6f91928";
        String registroId = "99fa309b-e362-440c-8812-af9df067dad3";
        
        Autorizacao autorizacao = Autorizacao.findById(userId);
        assertEquals(autorizacao.id, userId);
        assertEquals(autorizacao.registro.id, registroId);
        
    }
    
}