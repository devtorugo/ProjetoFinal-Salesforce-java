package fiap.ddd.sprint4.resources;

import fiap.ddd.sprint4.entities.Termo;
import fiap.ddd.sprint4.repositories.TermoRepository;
import fiap.ddd.sprint4.utils.Log4Logger;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;



@Path("termos")
public class TermoResource {

    private final TermoRepository termoRepository = new TermoRepository();
    private static final Log4Logger logger = new Log4Logger(TermoResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Termo> getTermos() {
        try {
            return termoRepository.getAll();
        } catch (Exception e) {
            logger.error("Erro ao obter termos: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response adicionarTermo(Termo termo) {
        try {

            if (termo.getId() == 0 || !termo.isAceitarTermo()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Os campos do termo não estão especificados corretamente.").build();
            }

            termoRepository.create(termo);

            return Response.status(Response.Status.CREATED).entity(termo).build();
        } catch (Exception e) {
            logger.error("Erro ao adicionar termo: " + e.getMessage());
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarTermo(@PathParam("id") int id, Termo termoAtualizado) {
        try {
            if (termoRepository.getById(id).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Termo não encontrado").build();
            }
            termoAtualizado.setId(id);
            termoRepository.update(termoAtualizado);
            return Response.status(Response.Status.OK).entity(termoAtualizado).build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar termo: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarTermo(@PathParam("id") int id) {
        try {
            if (termoRepository.getById(id).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Termo não encontrado").build();
            }
            termoRepository.delete(id);
            return Response.status(Response.Status.OK).entity("Termo removido").build();
        } catch (Exception e) {
            logger.error("Erro ao deletar termo: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
