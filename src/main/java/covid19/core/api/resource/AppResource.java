package covid19.core.api.resource;

import covid19.core.api.dominio.Autorizacao;
import covid19.core.api.dominio.Registro;
import covid19.core.api.service.AppService;
import covid19.core.util.HTMLEmailSender;
import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Properties;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Transactional(Transactional.TxType.REQUIRED)
@Path("/covid19")
public class AppResource {
    @ConfigProperty(name = "app.server")
    String appServer;
    
    @Inject
    AppService service;
    
    /**
     * Entrar: autorizacao: email + password
     * <p>
     * curl -X GET  http://localhost:8080/covid19/autorizacao -H "Content-type:application/json" -d '{"email":"milton.vincenttis","password":"senha"}'
     *
     * @param autorizacaoSolicitante
     * @return
     */
    @GET
    @Path("/autorizacao")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response getAutorizacao(Autorizacao autorizacaoSolicitante) {
        final Autorizacao autorizacaoUsuarioRegistrado = service.findAutorizacaoByEmail(autorizacaoSolicitante.email);
        
        final boolean isUserRegistrado = autorizacaoUsuarioRegistrado != null;
        
        if (isUserRegistrado && isSenhaCorreta(autorizacaoSolicitante, autorizacaoUsuarioRegistrado)) {
            //--- 200: json: Autorizacao + Registro
            /*
            {
              "email": "architect.tonena@gmail.com",
              "id": "7604764b-c1a2-47f7-ac4e-c572a6f91928",
              "password": "senha",
              "recadastrar": false,
              "registro": {
                "curadoDeCovid19": true,
                "curadoDeCovid19Data": "2020-09-01T14:51:15.98305-03:00",
                "id": "99fa309b-e362-440c-8812-af9df067dad3",
                "naoPegueiCovid19": true,
                "naoPegueiCovid19Data": "2020-09-01T14:51:15.98305-03:00",
                "naoSeiSePegueiCovid19": false,
                "naoSeiSePegueiCovid19Data": "2020-09-01T14:51:15.98305-03:00",
                "pegueiCovid19FazMais14Dias": false,
                "pegueiCovid19FazMais14DiasData": "2020-09-01T14:51:15.98305-03:00",
                "pegueiCovid19FazMenos14Dias": true,
                "pegueiCovid19FazMenos14DiasData": "2020-09-01T14:51:15.98305-03:00"
              }
            }
            */
            return Response.ok(autorizacaoUsuarioRegistrado).build();
        } else {
            //--- 404: Not Found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    
    //--- EsqueceuASenha:
    
    
    
    /*
     * -Backend:
     * Usuário envia seu email registrado:
     * App envia uma senha temporária para seu email
     * Usuário é informado da senha temporária.
     *
     * -Frontend:
     * Detectar quando usuário carregar o App, se é pra recadastrar senha
     * Redirecionar tela para o Usuário trocar a senha
     *
     */
    @GET
    @Path("/esqueceu-senha")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response getEsqueceuSenha(Autorizacao autorizacaoSolicitante) {
        final Autorizacao autorizacaoUsuarioRegistrado = service.findAutorizacaoByEmail(autorizacaoSolicitante.email);
        final boolean isUserRegistrado = autorizacaoUsuarioRegistrado != null;
        
        System.out.println("***********" + System.getProperty("app.server"));
        
        if (isUserRegistrado) {
            //--- enviar email com senha 4-6 posições
            //--- TODO: Processar email: gerar o URL para registrar como RECADASTRAR
            enviarEmailParaUsuarioRegistrado(autorizacaoUsuarioRegistrado.id);
            
            //--- 200: json: Autorizacao + Registro
            return Response.ok(autorizacaoUsuarioRegistrado).build();
        } else {
            //--- 404: Not Found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    
    
    @GET
    @Path("/esqueceu-senha/{autorizacaoId}/recadastrar")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response getEsqueceuSenhaRecadastrar(@PathParam("autorizacaoId") String autorizacaoId) throws URISyntaxException {
        final Autorizacao autorizacaoUsuarioRegistrado = Autorizacao.findById(autorizacaoId);
        final boolean isUserRegistrado = autorizacaoUsuarioRegistrado != null;
        
        System.out.println("***********" + System.getProperty("app.server"));
        
        if (isUserRegistrado) {
            //--- grava flag precisa recadastrar senha
            autorizacaoUsuarioRegistrado.recadastrar = true;
            
            //return Response.ok().build() ;
            return Response.temporaryRedirect(new URI(appServer + "/EsqueceuASenhaNotificado.html")).build();
            
        } else {
            //--- 404: Not Found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    
    
    /**
     * Prepara e evia email com instruçõs para o email do usuário registrado.
     * Ao solicitar o recadastramento de senha, o App (mobile) na carga inicial irá para a tela: Inscricao (customizada para troca de senha)
     *
     * @param id
     */
    private void enviarEmailParaUsuarioRegistrado(final String id) {
        String hrefRecadastrar = MessageFormat.format(appServer + "/covid19/esqueceu-senha/{0}/recadastrar", id);
        Properties props = new Properties();
        props.setProperty(HTMLEmailSender.SMTP_SERVER, "smtp.gmail.com");
        props.setProperty(HTMLEmailSender.USERNAME, "oss.covid19.repo@gmail.com");
        props.setProperty(HTMLEmailSender.PASSWORD, "bourne@1961");
        props.setProperty(HTMLEmailSender.EMAIL_FROM, "oss.covid19.repo@gmail.com");
        props.setProperty(HTMLEmailSender.EMAIL_TO, "architect.tonena@gmail.com");
        props.setProperty(HTMLEmailSender.EMAIL_SUBJECT, "Registro Pessoal: Covid-19  > Esqueci a Senha");
        props.setProperty(HTMLEmailSender.EMAIL_TEXT, loadAndChangeTextFromHTMLEmailFile(hrefRecadastrar));
        
        HTMLEmailSender sender = new HTMLEmailSender(props);
        sender.send();
    }
    
    
    
    public void main(String[] args) {
        enviarEmailParaUsuarioRegistrado("7604764b-c1a2-47f7-ac4e-c572a6f91928");
    }
    
    
    
    private String loadAndChangeTextFromHTMLEmailFile(String hrefRecadastrar) {
        File file = new File("/home/miltonvincenttis/workspace/dev/desenvolvimento.produto.covid19/registro.pessoal/backend/app/src/main/java/covid19/core/util/email-sender/html/EsqueceuASenha.html");
        String data;
        try {
            data = FileUtils.readFileToString(file, "UTF-8");
            data = data.replace("{0}", hrefRecadastrar);
        } catch (IOException e) {
            e.printStackTrace();
            return "<a href='Deu Pau'>Deu merda</a>";
            
        }
        return data;
    }
    
    //--- Inscricao: No momemto de Inscricao bem sucedida: gravar no dispositivo do usuário (autorizacaoId): 7604764b-c1a2-47f7-ac4e-c572a6f91928
    
    
    
    @POST
    @Path("/registro")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response postRegistro(Registro registro) {
        //--- TODO: validar por null
        service.postRegistro(registro);
        return Response.status(Response.Status.CREATED).build();
    }
    
    
    
    @GET
    @Path("/registro/{id}")
    @Produces(APPLICATION_JSON)
    public Response getRegistro(@PathParam("id") String id) {
        //--- TODO: validar por null
        Registro registro = service.findByIdRegistro(id);
        return Response.ok(registro).build();
    }
    
    
    
    /**
     * Se tem o registro, retornar 204
     * Se não tem o registro, então NÃO CRIAR e retornar 404
     *
     * @param registro
     * @return
     */
    @PUT //--- alteracao
    @Path("/registro/{id}")
    public Response putRegistro(Registro registro) {
        boolean existeRegistro;
        
        //--- TODO: validar por null \ validar existencia do registro sendo atualizado
        
        Registro registroAtual = service.findByIdRegistro(registro.id);
        
        if ((existeRegistro = registroAtual != null)) {
            service.putRegistro(registro);
        }
        
        return existeRegistro ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
    
    
    
    @DELETE //--- remocao
    @Path("/registro/{id}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response deleteRegistro(Registro registro) {
        //--- TODO: validar por null
        
        boolean removido = service.deleteRegistro(registro);
        return removido ? Response.ok().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
    
    
    
    /**
     * URL de HEALTH CHECK
     *
     * @return
     */
    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok("pong").build();
    }
    
    
    //--- private methodss
    
    
    
    /**
     * Verifica a corretitude da senha
     * <p>
     * Correta somente se igual.
     *
     * @param autorizacaoSolicitante
     * @param autorizacaoUsuarioRegistrado
     * @return boolean
     */
    private boolean isSenhaCorreta(final Autorizacao autorizacaoSolicitante, final Autorizacao autorizacaoUsuarioRegistrado) {
        return (autorizacaoUsuarioRegistrado.password.equals(autorizacaoSolicitante.password));
    }
    
}
