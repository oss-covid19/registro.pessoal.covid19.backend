package covid19.core.configuracao;


import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

import javax.ws.rs.core.Application;

/**
 * OpenAPI specs.
 */
@OpenAPIDefinition(
    info = @Info(
        title="oss-covid19 API",
        version = "0.0.1",
        contact = @Contact(
            name = "Registro Pessoal - covid-19",
            url = "https://github.com/oss-covid19",
            email = "oss.covid19.repo@gmail.com"),
        license = @License(
            name = "MIT License",
            url = "https://github.com/oss-covid19/registro.pessoal.covid19.backend/blob/master/LICENSE"))
    
    
)
public class COVID19AppAPI extends Application {
}
