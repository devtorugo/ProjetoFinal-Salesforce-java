package fiap.ddd.sprint4.resources;

import fiap.ddd.sprint4.entities.Regiao;
import fiap.ddd.sprint4.repositories.RegiaoRepository;
import fiap.ddd.sprint4.utils.Log4Logger;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("regioes")
public class RegiaoResource {

    private final RegiaoRepository regiaoRepository = new RegiaoRepository();
    private static final Log4Logger logger = new Log4Logger(RegiaoResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Regiao> getRegioes() {
        try {
            return regiaoRepository.getAll();
        } catch (Exception e) {
            logger.error("Erro ao obter regiões: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response adicionarRegiao(Regiao regiao) {
        try {
            if (regiao.getId() == 0 || regiao.getPaisNome() == null || regiao.getPaisNome().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Os campos da região não estão especificados corretamente.").build();
            }
            regiaoRepository.create(regiao);
            return Response.status(Response.Status.CREATED).entity(regiao).build();
        } catch (Exception e) {
            logger.error("Erro ao adicionar região: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarRegiao(@PathParam("id") int id, Regiao regiaoAtualizada) {
        try {
            if (regiaoRepository.getById(id).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Região não encontrada").build();
            }
            regiaoAtualizada.setId(id);
            regiaoRepository.update(regiaoAtualizada);
            return Response.status(Response.Status.OK).entity(regiaoAtualizada).build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar região: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarRegiao(@PathParam("id") int id) {
        try {
            if (regiaoRepository.getById(id).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Região não encontrada").build();
            }
            regiaoRepository.delete(id);
            return Response.status(Response.Status.OK).entity("Região removida").build();
        } catch (Exception e) {
            logger.error("Erro ao deletar região: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
