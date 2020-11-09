package covid19.core.api.service;


import covid19.core.api.dominio.Autorizacao;
import covid19.core.api.dominio.Registro;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AppService {
    
    /**
     * @param registro
     */
    public void postRegistro(final Registro registro) {
        Registro.persist(registro);
        
        //--- TODO: transacionar com a estatistica
        
    }
    
    
    
    /**
     *
     * @param id
     * @return
     */
    public Registro findByIdRegistro(final String id) {
        return Registro.findById(id);
    }
    
    
    
    /**
     * @param registro
     */
    public void putRegistro(final Registro registro) {
        Registro registroAtual = Registro.findById(registro.id);
        
        
    }
    
    /**
     * @param registro
     * @return
     */
    public boolean deleteRegistro(final Registro registro) {
        return Registro.deleteById(registro.id);
    }
    
    
    
    /**
     * @param email
     * @return
     */
    public Autorizacao findAutorizacaoByEmail(final String email) {
        return Autorizacao.findByEmail(email);
    }
    
}
