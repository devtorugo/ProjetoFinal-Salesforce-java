package fiap.ddd.sprint4.resources;

import fiap.ddd.sprint4.entities.DadosDaCompra;
import fiap.ddd.sprint4.repositories.DadosDaCompraRepository;
import fiap.ddd.sprint4.utils.Log4Logger;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("dados-da-compra")
public class DadosDaCompraResource {

    private final DadosDaCompraRepository dadosDaCompraRepository = new DadosDaCompraRepository();
    private static final Log4Logger logger = new Log4Logger(DadosDaCompraResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DadosDaCompra> getDadosDaCompra() {
        try {
            return dadosDaCompraRepository.getAll();
        } catch (Exception e) {
            logger.error("Erro ao obter dados da compra: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDadosDaCompraById(@PathParam("id") int id) {
        try {
            var dadosDaCompraOptional = dadosDaCompraRepository.getById(id);
            if (dadosDaCompraOptional.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Dados da compra não encontrados").build();
            }
            var dadosDaCompra = dadosDaCompraOptional.get();
            return Response.status(Response.Status.OK).entity(dadosDaCompra).build();
        } catch (Exception e) {
            logger.error("Erro ao obter dados da compra por ID: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response adicionarDadosDaCompra(DadosDaCompra dadosDaCompra) {
        try {
            if (dadosDaCompra.getId() == 0 || dadosDaCompra.getData() == null || dadosDaCompra.getHora() == null
                    || dadosDaCompra.getTotal() <= 0 || dadosDaCompra.getLocal() == null || dadosDaCompra.getImagens() == null
                    || dadosDaCompra.getFeedback() == null || dadosDaCompra.getLogin() == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Os campos dos dados da compra não estão especificados corretamente.").build();
            }

            dadosDaCompraRepository.create(dadosDaCompra);

            return Response.status(Response.Status.CREATED).entity(dadosDaCompra).build();
        } catch (Exception e) {
            logger.error("Erro ao adicionar dados da compra: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarDadosDaCompra(@PathParam("id") int id, DadosDaCompra dadosDaCompraAtualizados) {
        try {
            if (dadosDaCompraRepository.getById(id).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Dados da compra não encontrados").build();
            }
            dadosDaCompraAtualizados.setId(id);
            dadosDaCompraRepository.update(dadosDaCompraAtualizados);
            return Response.status(Response.Status.OK).entity(dadosDaCompraAtualizados).build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar dados da compra: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarDadosDaCompra(@PathParam("id") int id) {
        try {
            if (dadosDaCompraRepository.getById(id).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Dados da compra não encontrados").build();
            }
            dadosDaCompraRepository.delete(id);
            return Response.status(Response.Status.OK).entity("Dados da compra removidos").build();
        } catch (Exception e) {
            logger.error("Erro ao deletar dados da compra: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
