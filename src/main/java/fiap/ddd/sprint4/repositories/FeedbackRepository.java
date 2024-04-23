package fiap.ddd.sprint4.repositories;

import fiap.ddd.sprint4.entities.Feedback;
import fiap.ddd.sprint4.infrastructure.OracleDbConfiguration;
import fiap.ddd.sprint4.utils.Log4Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FeedbackRepository {

    private static final String TB_NAME = "FEEDBACK";
    private static final Log4Logger logger = new Log4Logger(FeedbackRepository.class);

    public List<Feedback> getAll() {
        List<Feedback> feedbacks = new ArrayList<>();
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                feedbacks.add(mapResultSetToFeedback(rs));
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter todos os feedbacks do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter todos os feedbacks do banco de dados", e);
        }
        return feedbacks;
    }

    public Optional<Feedback> getById(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID_FEEDBACK = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToFeedback(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter feedback por ID do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter feedback por ID do banco de dados", e);
        }
        return Optional.empty();
    }

    public void create(Feedback feedback) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (ID_FEEDBACK, COMENTARIOS, DATA_AVALIACAO, AUTOR_AVALIACAO, CLASSIFICACAO_SERVICO) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setInt(1, feedback.getId());
            stmt.setString(2, feedback.getComentarios());
            stmt.setObject(3, feedback.getDataAvaliacao());
            stmt.setString(4, feedback.getAutorAvaliacao());
            stmt.setInt(5, feedback.getClassificacaoServico());
            stmt.executeUpdate();

            logger.info("Feedback adicionado ao banco de dados: " + feedback.toString());
        } catch (SQLException e) {
            logger.error("Erro ao criar o feedback no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao criar o feedback no banco de dados", e);
        }
    }

    public void update(Feedback feedback) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE " + TB_NAME + " SET COMENTARIOS = ?, DATA_AVALIACAO = ?, AUTOR_AVALIACAO = ?, CLASSIFICACAO_SERVICO = ? WHERE ID_FEEDBACK = ?")) {
            stmt.setString(1, feedback.getComentarios());
            stmt.setObject(2, feedback.getDataAvaliacao());
            stmt.setString(3, feedback.getAutorAvaliacao());
            stmt.setInt(4, feedback.getClassificacaoServico());
            stmt.setInt(5, feedback.getId());
            stmt.executeUpdate();
            logger.info("Feedback atualizado no banco de dados: " + feedback.toString());
        } catch (SQLException e) {
            logger.error("Erro ao atualizar o feedback no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar o feedback no banco de dados", e);
        }
    }

    public void delete(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE ID_FEEDBACK = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Feedback removido do banco de dados. ID: " + id);
        } catch (SQLException e) {
            logger.error("Erro ao excluir o feedback do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir o feedback do banco de dados", e);
        }
    }

    private static Feedback mapResultSetToFeedback(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID_FEEDBACK");
        String comentarios = rs.getString("COMENTARIOS");
        LocalDateTime dataAvaliacao = rs.getObject("DATA_AVALIACAO", LocalDateTime.class);
        String autorAvaliacao = rs.getString("AUTOR_AVALIACAO");
        int classificacaoServico = rs.getInt("CLASSIFICACAO_SERVICO");
        return new Feedback(id, comentarios, dataAvaliacao, autorAvaliacao, classificacaoServico);
    }
}
