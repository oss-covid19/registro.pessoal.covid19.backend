package covid19.core.configuracao;


import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * http://patorjk.com/software/taag/#p=display&f=Banner3&t=COVID-19
 */

@ApplicationScoped
class COVID19ApplicationLifeCycle {
    static final Logger LOGGER = Logger.getLogger(COVID19ApplicationLifeCycle.class);
    
    @PersistenceContext
    EntityManager entityManager;
    
    
    
    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("");
        LOGGER.info(" ######   #######  ##     ## #### ########             ##    #######  ");
        LOGGER.info("##    ## ##     ## ##     ##  ##  ##     ##          ####   ##     ## ");
        LOGGER.info("##       ##     ## ##     ##  ##  ##     ##            ##   ##     ## ");
        LOGGER.info("##       ##     ## ##     ##  ##  ##     ## #######    ##    ######## ");
        LOGGER.info("##       ##     ##  ##   ##   ##  ##     ##            ##          ## ");
        LOGGER.info("##    ## ##     ##   ## ##    ##  ##     ##            ##   ##     ## ");
        LOGGER.info(" ######   #######     ###    #### ########           ######  #######  ");
        LOGGER.info("");

    }
    
    
    
    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("COVID-19 service está parando...");
    }
}