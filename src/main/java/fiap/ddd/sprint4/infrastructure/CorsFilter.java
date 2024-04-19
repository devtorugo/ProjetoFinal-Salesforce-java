package fiap.ddd.sprint4.infrastructure;


import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();

        // Adiciona cabeçalhos CORS
        headers.add("Access-Control-Allow-Origin", "*"); // Permitir solicitações de qualquer origem
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT"); // Métodos HTTP permitidos
        headers.add("Access-Control-Allow-Headers", "Content-Type"); // Cabeçalhos permitidos
    }
}
