package fiap.ddd.sprint4.resources;

import fiap.ddd.sprint4.entities.InformacoesDoServico;
import fiap.ddd.sprint4.repositories.InformacoesDoServicoRepository;
import fiap.ddd.sprint4.utils.Log4Logger;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("informacoes-do-servico")
public class InformacoesDoServicoResource {

    private final InformacoesDoServicoRepository informacoesDoServicoRepository = new InformacoesDoServicoRepository();
    private static final Log4Logger logger = new Log4Logger(InformacoesDoServicoResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<InformacoesDoServico> getInformacoesDoServico() {
        try {
            return informacoesDoServicoRepository.getAll();
        } catch (Exception e) {
            logger.error("Erro ao obter informações do serviço: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInformacoesDoServicoById(@PathParam("id") int id) {
        try {
            var informacoesDoServicoOptional = informacoesDoServicoRepository.getById(id);
            if (informacoesDoServicoOptional.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Informações do serviço não encontradas").build();
            }
            var informacoesDoServico = informacoesDoServicoOptional.get();
            return Response.status(Response.Status.OK).entity(informacoesDoServico).build();
        } catch (Exception e) {
            logger.error("Erro ao obter informações do serviço por ID: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response adicionarInformacoesDoServico(InformacoesDoServico informacoesDoServico) {
        try {
            if (informacoesDoServico.getId() == 0 || informacoesDoServico.getNome() == null || informacoesDoServico.getDescricao() == null
                    || informacoesDoServico.getCategoria() == null || informacoesDoServico.getPreco() <= 0) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Os campos das informações do serviço não estão especificados corretamente.").build();
            }

            informacoesDoServicoRepository.create(informacoesDoServico);

            return Response.status(Response.Status.CREATED).entity(informacoesDoServico).build();
        } catch (Exception e) {
            logger.error("Erro ao adicionar informações do serviço: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarInformacoesDoServico(@PathParam("id") int id, InformacoesDoServico informacoesDoServicoAtualizadas) {
        try {
            if (informacoesDoServicoRepository.getById(id).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Informações do serviço não encontradas").build();
            }
            informacoesDoServicoAtualizadas.setId(id);
            informacoesDoServicoRepository.update(informacoesDoServicoAtualizadas);
            return Response.status(Response.Status.OK).entity(informacoesDoServicoAtualizadas).build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar informações do serviço: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarInformacoesDoServico(@PathParam("id") int id) {
        try {
            if (informacoesDoServicoRepository.getById(id).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Informações do serviço não encontradas").build();
            }
            informacoesDoServicoRepository.delete(id);
            return Response.status(Response.Status.OK).entity("Informações do serviço removidas").build();
        } catch (Exception e) {
            logger.error("Erro ao deletar informações do serviço: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
