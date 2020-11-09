package covid19.core.api.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import covid19.core.api.dominio.Registro;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.is;

        /**
 * Plano de Teste:
 *
 * -Entrar:
 *      -deveAutorizarEmailESenhaCorretos
 *          :testa com email registrado + senha correta
 *
 *      -deveNaoAutorizarEmailNaoEncontrado
 *          :testa com email não registrado
 *
 *      -deveNaoAutorizarEmailEncontradoSenhaIncorreta
 *          :testa com email registrado + senha incorreta

 *
 * -EsqueceuASenha:
 *      -deveEncontrarEmail
 *          :testa com email registrado
 *      -devNaoEncontrarEmail
 *          :testa com email não registrado
 *
 *
 * -Inscricao:
 * -Trocar a Senha:
 */
@QuarkusTest
public class AppResourceTest {
    private final Gson gson = new GsonBuilder().serializeNulls().create();
    
    /**
     * Entrar: deveAutorizar: email + password corretos:
     */
    @Test
    public void deveAutorizarEmailESenhaCorretos(){
        given()
            .body("{\"email\":\"architect.tonena@gmail.com\", \"password\":\"senha\"}")
            .contentType("application/json")
        .when().get("/covid19/autorizacao")
        .then()
           .statusCode(OK.getStatusCode());
    
    }
    
    //-------------------------------------------------------------------------
    
    /**
     * Entrar: deveNaoAutorizar: email incorreto + password correta
     */
    @Test
    public void deveNaoAutorizarEmailNaoEncontrado(){
        given()
            .body("{\"email\":\"architectxtonena@gmail.com\", \"password\":\"senha\"}")
            .contentType("application/json")
            .when().get("/covid19/autorizacao")
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
        
    }
    
    /**
     * Entrar: deveNaoAutorizar: email correto + password incorreta:
     */
    @Test
    public void deveNaoAutorizarEmailEncontradoSenhaIncorreta(){
        given()
            .body("{\"email\":\"architect.tonena@gmail.com\", \"password\":\"senha errada\"}")
            .contentType("application/json")
            .when().get("/covid19/autorizacao")
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
        
    }
    
    //-------------------------------------------------------------------------
    
    
    /**
     * -EsqueceuASenha: deveEncontrarEmail: Testa com email registrado
     */
    @Test
    public void deveEncontrarEmail(){
        given()
            .body("{\"email\":\"architect.tonena@gmail.com\", \"password\":\"senha\"}")
            .contentType("application/json")
            .when().get("/covid19/esqueceu-senha")
            .then()
            .statusCode(OK.getStatusCode());
    }
    
    
    
    /**
     * -EsqueceuASenhaRecadastrar: devEnviarEmailUsuarioRegistradoPorId:
     */
    @Test
    public void devEncontrarUsuarioRegistradoPorId(){
        given()
            .when().get("/covid19/esqueceu-senha/7604764b-c1a2-47f7-ac4e-c572a6f91928/recadastrar")
            .then()
            .statusCode(OK.getStatusCode());
    }
    
    
    
    @Test
    public void deveCriarRegistroComPost(){
        String registroJson = gson.toJson(createRegistro(null));
        
        given()
            .body(registroJson)
            .contentType("application/json")
        .when().post("/covid19/registro")
        .then()
            .statusCode(CREATED.getStatusCode());
    }
    
    @Test
    public void dadoIDdeveRecuperarRegistroViaGet(){
        //--- este ID é carregado no import.sql
        String rID = "99fa309b-e362-440c-8812-af9df067dad3";
        
        given()
            .when().get("/covid19/registro/"+rID)
            .then()
                .statusCode(OK.getStatusCode());
    }
    
    //-------------------------------------------------------------------------

    @Test
    public void testDeveRetornaPong(){
        given()
            .when().get("/covid19/ping")
            .then()
                .statusCode(200)
                .body(is("pong"));
    }
    
    //-------------------------------------------------------------------------
    
    private Registro createRegistro(String id){
        Registro registro = new Registro();
        registro.id = id;
        registro.naoPegueiCovid19 = true;
        return registro;
    }
}
