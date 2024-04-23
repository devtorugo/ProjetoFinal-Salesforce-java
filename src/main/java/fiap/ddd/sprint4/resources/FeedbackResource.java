package fiap.ddd.sprint4.resources;

import fiap.ddd.sprint4.entities.Feedback;
import fiap.ddd.sprint4.repositories.FeedbackRepository;
import fiap.ddd.sprint4.utils.Log4Logger;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("feedbacks")
public class FeedbackResource {

    private final FeedbackRepository feedbackRepository = new FeedbackRepository();
    private static final Log4Logger logger = new Log4Logger(FeedbackResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Feedback> getFeedbacks() {
        try {
            return feedbackRepository.getAll();
        } catch (Exception e) {
            logger.error("Erro ao obter feedbacks: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFeedbackById(@PathParam("id") int id) {
        try {
            Optional<Feedback> feedback = feedbackRepository.getById(id);
            if (feedback.isPresent()) {
                return Response.status(Response.Status.OK).entity(feedback.get()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Feedback não encontrado").build();
            }
        } catch (Exception e) {
            logger.error("Erro ao obter feedback por ID: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response adicionarFeedback(Feedback feedback) {
        try {
            feedbackRepository.create(feedback);
            return Response.status(Response.Status.CREATED).entity(feedback).build();
        } catch (Exception e) {
            logger.error("Erro ao adicionar feedback: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarFeedback(@PathParam("id") int id, Feedback feedbackAtualizado) {
        try {
            feedbackAtualizado.setId(id);
            feedbackRepository.update(feedbackAtualizado);
            return Response.status(Response.Status.OK).entity(feedbackAtualizado).build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar feedback: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarFeedback(@PathParam("id") int id) {
        try {
            feedbackRepository.delete(id);
            return Response.status(Response.Status.OK).entity("Feedback removido").build();
        } catch (Exception e) {
            logger.error("Erro ao deletar feedback: " + e.getMessage());
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
