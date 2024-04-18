package fiap.ddd.sprint4.resources;

import fiap.ddd.sprint4.entities.TesteGratis;
import fiap.ddd.sprint4.repositories.TesteGratisRepository;
import fiap.ddd.sprint4.utils.Log4Logger;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("testes-gratis")
public class TesteGratisResource {

    private final TesteGratisRepository testeGratisRepository = new TesteGratisRepository();
    private static final Log4Logger logger = new Log4Logger(TesteGratisResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TesteGratis> getTestesGratis() {
        try {
            return testeGratisRepository.getAll();
        } catch (Exception e) {
            logger.error("Erro ao obter testes grátis: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response adicionarTesteGratis(TesteGratis testeGratis) {
        try {
            // Aqui você pode adicionar validações adicionais, se necessário
            testeGratisRepository.create(testeGratis);
            return Response.status(Response.Status.CREATED).entity(testeGratis).build();
        } catch (Exception e) {
            logger.error("Erro ao adicionar teste grátis: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarTesteGratis(@PathParam("id") int id, TesteGratis testeGratisAtualizado) {
        try {
            if (testeGratisRepository.getById(id).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Teste grátis não encontrado").build();
            }
            testeGratisAtualizado.setId(id);
            testeGratisRepository.update(testeGratisAtualizado);
            return Response.status(Response.Status.OK).entity(testeGratisAtualizado).build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar teste grátis: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarTesteGratis(@PathParam("id") int id) {
        try {
            if (testeGratisRepository.getById(id).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Teste grátis não encontrado").build();
            }
            testeGratisRepository.delete(id);
            return Response.status(Response.Status.OK).entity("Teste grátis removido").build();
        } catch (Exception e) {
            logger.error("Erro ao deletar teste grátis: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
