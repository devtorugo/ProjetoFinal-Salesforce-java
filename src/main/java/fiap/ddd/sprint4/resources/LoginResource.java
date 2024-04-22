package fiap.ddd.sprint4.resources;

import fiap.ddd.sprint4.entities.Login;
import fiap.ddd.sprint4.repositories.LoginRepository;
import fiap.ddd.sprint4.utils.Log4Logger;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("logins")
public class LoginResource {

    private final LoginRepository loginRepository = new LoginRepository();
    private static final Log4Logger logger = new Log4Logger(LoginResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Login> getLogins() {
        try {
            return loginRepository.getAll();
        } catch (Exception e) {
            logger.error("Erro ao obter logins: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response adicionarLogin(Login login) {
        try {
            // Aqui você pode adicionar validações adicionais, se necessário
            loginRepository.create(login);
            return Response.status(Response.Status.CREATED).entity(login).build();
        } catch (Exception e) {
            logger.error("Erro ao adicionar login: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarLogin(@PathParam("id") int id, Login loginAtualizado) {
        try {
            if (loginRepository.getById(id).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Login não encontrado").build();
            }
            loginAtualizado.setId(id);
            loginRepository.update(loginAtualizado);
            return Response.status(Response.Status.OK).entity(loginAtualizado).build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar login: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarLogin(@PathParam("id") int id) {
        try {
            if (loginRepository.getById(id).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Login não encontrado").build();
            }
            loginRepository.delete(id);
            return Response.status(Response.Status.OK).entity("Login removido").build();
        } catch (Exception e) {
            logger.error("Erro ao deletar login: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
