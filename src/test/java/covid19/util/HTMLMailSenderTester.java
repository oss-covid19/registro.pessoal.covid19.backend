package covid19.util;

import covid19.core.util.HTMLEmailSender;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes simples para a classe:
 * Plano de Testes:
 *
 * -deveEnviarHTMLEmail
 *
 */
public class HTMLMailSenderTester {
    @Test
    public void deveEnviarHTMLEmail(){
        Properties props = new Properties();
        props.setProperty(HTMLEmailSender.SMTP_SERVER, "smtp.gmail.com");
        props.setProperty(HTMLEmailSender.USERNAME,"oss.covid19.repo@gmail.com");
        props.setProperty(HTMLEmailSender.PASSWORD, "bourne@1961");
        props.setProperty(HTMLEmailSender.EMAIL_FROM, "oss.covid19.repo@gmail.com");
        props.setProperty(HTMLEmailSender.EMAIL_TO, "milton.vincenttis@gmail.com");
        props.setProperty(HTMLEmailSender.EMAIL_SUBJECT, "This is a Test -- AVB");
        props.setProperty(HTMLEmailSender.EMAIL_TEXT, "");
        
        HTMLEmailSender sender = new HTMLEmailSender(props);
        sender.send();
        
        assert(true);
        
    }
    
    @Test
    public void givenFileName_whenUsingFileUtils_thenFileData() throws IOException {
        File file = new File("/home/miltonvincenttis/workspace/dev/desenvolvimento.produto.covid19/registro.pessoal/backend/app/src/main/java/covid19/core/util/email-sender/html/EsqueceuASenha.html");
        String data = FileUtils.readFileToString(file, "UTF-8");
        
        assertEquals(true, data != null);
    }

}
