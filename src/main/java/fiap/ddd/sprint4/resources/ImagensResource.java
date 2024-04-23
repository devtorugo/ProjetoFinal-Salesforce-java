package fiap.ddd.sprint4.resources;

import fiap.ddd.sprint4.entities.Imagens;
import fiap.ddd.sprint4.repositories.ImagensRepository;
import fiap.ddd.sprint4.utils.Log4Logger;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("imagens")
public class ImagensResource {

    private final ImagensRepository imagensRepository = new ImagensRepository();
    private static final Log4Logger logger = new Log4Logger(ImagensResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Imagens> getImagens() {
        try {
            return imagensRepository.getAll();
        } catch (Exception e) {
            logger.error("Erro ao obter imagens: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImagensById(@PathParam("id") int id) {
        try {
            var imagemOptional = imagensRepository.getById(id);
            if (imagemOptional.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Imagem não encontrada").build();
            }
            var imagem = imagemOptional.get();
            return Response.status(Response.Status.OK).entity(imagem).build();
        } catch (Exception e) {
            logger.error("Erro ao obter imagem por ID: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response adicionarImagem(Imagens imagens) {
        try {
            if (imagens.getId() == 0 || imagens.getDescricao() == null || imagens.getDescricao().isEmpty()
                    || imagens.getNomeArquivo() == null || imagens.getNomeArquivo().isEmpty()
                    || imagens.getTamanhoArquivo() <= 0) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Os campos da imagem não estão especificados corretamente.").build();
            }

            imagensRepository.create(imagens);

            return Response.status(Response.Status.CREATED).entity(imagens).build();
        } catch (Exception e) {
            logger.error("Erro ao adicionar imagem: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarImagem(@PathParam("id") int id, Imagens imagemAtualizada) {
        try {
            if (imagensRepository.getById(id).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Imagem não encontrada").build();
            }
            imagemAtualizada.setId(id);
            imagensRepository.update(imagemAtualizada);
            return Response.status(Response.Status.OK).entity(imagemAtualizada).build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar imagem: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarImagem(@PathParam("id") int id) {
        try {
            if (imagensRepository.getById(id).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Imagem não encontrada").build();
            }
            imagensRepository.delete(id);
            return Response.status(Response.Status.OK).entity("Imagem removida").build();
        } catch (Exception e) {
            logger.error("Erro ao deletar imagem: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
