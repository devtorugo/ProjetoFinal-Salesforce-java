package fiap.ddd.sprint4.services;

import fiap.ddd.sprint4.entities.Feedback;
import fiap.ddd.sprint4.repositories.FeedbackRepository;

public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public void adicionarFeedback(Feedback feedback) {
        if (feedback.getId() == 0 || feedback.getComentarios() == null || feedback.getComentarios().isEmpty()
                || feedback.getDataAvaliacao() == null || feedback.getAutorAvaliacao() == null
                || feedback.getAutorAvaliacao().isEmpty() || feedback.getClassificacaoServico() <= 0) {
            throw new IllegalArgumentException("Os campos do feedback não estão especificados corretamente.");
        }
        feedbackRepository.create(feedback);
    }

    public void atualizarFeedback(Feedback feedback) {
        if (feedback.getId() == 0 || feedback.getComentarios() == null || feedback.getComentarios().isEmpty()
                || feedback.getDataAvaliacao() == null || feedback.getAutorAvaliacao() == null
                || feedback.getAutorAvaliacao().isEmpty() || feedback.getClassificacaoServico() <= 0) {
            throw new IllegalArgumentException("Os campos do feedback não estão especificados corretamente.");
        }
        feedbackRepository.update(feedback);
    }

    public void deletarFeedback(int id) {
        if (feedbackRepository.getById(id).isEmpty()) {
            throw new IllegalArgumentException("Feedback não encontrado");
        }
        feedbackRepository.delete(id);
    }
}
