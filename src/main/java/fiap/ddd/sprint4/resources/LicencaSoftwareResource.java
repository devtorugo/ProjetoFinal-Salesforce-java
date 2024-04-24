package fiap.ddd.sprint4.resources;

import fiap.ddd.sprint4.entities.LicencaSoftware;
import fiap.ddd.sprint4.repositories.LicencaSoftwareRepository;
import fiap.ddd.sprint4.utils.Log4Logger;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("licenca-software")
public class LicencaSoftwareResource {

    private final LicencaSoftwareRepository licencaSoftwareRepository = new LicencaSoftwareRepository();
    private static final Log4Logger logger = new Log4Logger(LicencaSoftwareResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<LicencaSoftware> getLicencaSoftware() {
        try {
            return licencaSoftwareRepository.getAll();
        } catch (Exception e) {
            logger.error("Erro ao obter licenças de software: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLicencaSoftwareById(@PathParam("id") int id) {
        try {
            var licencaSoftwareOptional = licencaSoftwareRepository.getById(id);
            if (licencaSoftwareOptional.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Licença de software não encontrada").build();
            }
            var licencaSoftware = licencaSoftwareOptional.get();
            return Response.status(Response.Status.OK).entity(licencaSoftware).build();
        } catch (Exception e) {
            logger.error("Erro ao obter licença de software por ID: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response adicionarLicencaSoftware(LicencaSoftware licencaSoftware) {
        try {
            if (licencaSoftware.getId() == 0 || licencaSoftware.getChaveAtivacao() == null || licencaSoftware.getDataAtivacao() == null
                    || licencaSoftware.getLocalAtivacao() == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Os campos da licença de software não estão especificados corretamente.").build();
            }

            licencaSoftwareRepository.create(licencaSoftware);

            return Response.status(Response.Status.CREATED).entity(licencaSoftware).build();
        } catch (Exception e) {
            logger.error("Erro ao adicionar licença de software: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarLicencaSoftware(@PathParam("id") int id, LicencaSoftware licencaSoftwareAtualizada) {
        try {
            if (licencaSoftwareRepository.getById(id).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Licença de software não encontrada").build();
            }
            licencaSoftwareAtualizada.setId(id);
            licencaSoftwareRepository.update(licencaSoftwareAtualizada);
            return Response.status(Response.Status.OK).entity(licencaSoftwareAtualizada).build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar licença de software: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarLicencaSoftware(@PathParam("id") int id) {
        try {
            if (licencaSoftwareRepository.getById(id).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Licença de software não encontrada").build();
            }
            licencaSoftwareRepository.delete(id);
            return Response.status(Response.Status.OK).entity("Licença de software removida").build();
        } catch (Exception e) {
            logger.error("Erro ao deletar licença de software: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
