package fiap.ddd.sprint4.infrastructure;

import org.glassfish.jersey.server.ResourceConfig;

public class Aplicacao extends ResourceConfig {
    public Aplicacao() {
        // Registra os recursos da sua aplicação
        packages("fiap.ddd.sprint4.resources");

        // Registra o filtro CORS
        register(CorsFilter.class);
    }
}
