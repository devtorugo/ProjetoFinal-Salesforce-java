package fiap.ddd.sprint4.repositories;

import fiap.ddd.sprint4.entities.DadosDaCompra;
import fiap.ddd.sprint4.entities.Feedback;
import fiap.ddd.sprint4.entities.Imagens;
import fiap.ddd.sprint4.entities.Login;
import fiap.ddd.sprint4.infrastructure.OracleDbConfiguration;
import fiap.ddd.sprint4.utils.Log4Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DadosDaCompraRepository {

    private static final String TB_NAME = "DADOS_DA_COMPRA";
    private static final Log4Logger logger = new Log4Logger(DadosDaCompraRepository.class);

    public List<DadosDaCompra> getAll() {
        List<DadosDaCompra> dadosDaCompraList = new ArrayList<>();
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dadosDaCompraList.add(mapResultSetToDadosDaCompra(rs));
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter todos os dados da compra do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter todos os dados da compra do banco de dados", e);
        }
        return dadosDaCompraList;
    }

    public Optional<DadosDaCompra> getById(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID_COMPRA = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToDadosDaCompra(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter dados da compra por ID do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter dados da compra por ID do banco de dados", e);
        }
        return Optional.empty();
    }

    public void create(DadosDaCompra dadosDaCompra) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (ID_COMPRA, DATA, HORA, TOTAL, LOCAL, ID_IMAGENS, ID_FEEDBACK, ID_LOGIN) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setInt(1, dadosDaCompra.getId());
            stmt.setObject(2, dadosDaCompra.getData());
            stmt.setObject(3, dadosDaCompra.getHora());
            stmt.setInt(4, dadosDaCompra.getTotal());
            stmt.setString(5, dadosDaCompra.getLocal());
            stmt.setInt(6, dadosDaCompra.getImagens().getId());
            stmt.setInt(7, dadosDaCompra.getFeedback().getId());
            stmt.setInt(8, dadosDaCompra.getLogin().getId());
            stmt.executeUpdate();

            logger.info("Dados da compra adicionados ao banco de dados: " + dadosDaCompra.toString());
        } catch (SQLException e) {
            logger.error("Erro ao criar os dados da compra no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao criar os dados da compra no banco de dados", e);
        }
    }

    public void update(DadosDaCompra dadosDaCompra) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE " + TB_NAME + " SET DATA = ?, HORA = ?, TOTAL = ?, LOCAL = ?, ID_IMAGENS = ?, ID_FEEDBACK = ?, ID_LOGIN = ? WHERE ID_COMPRA = ?")) {
            stmt.setObject(1, dadosDaCompra.getData());
            stmt.setObject(2, dadosDaCompra.getHora());
            stmt.setInt(3, dadosDaCompra.getTotal());
            stmt.setString(4, dadosDaCompra.getLocal());
            stmt.setInt(5, dadosDaCompra.getImagens().getId());
            stmt.setInt(6, dadosDaCompra.getFeedback().getId());
            stmt.setInt(7, dadosDaCompra.getLogin().getId());
            stmt.setInt(8, dadosDaCompra.getId());
            stmt.executeUpdate();

            logger.info("Dados da compra atualizados no banco de dados: " + dadosDaCompra.toString());
        } catch (SQLException e) {
            logger.error("Erro ao atualizar os dados da compra no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar os dados da compra no banco de dados", e);
        }
    }

    public void delete(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE ID_COMPRA = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Dados da compra removidos do banco de dados. ID: " + id);
        } catch (SQLException e) {
            logger.error("Erro ao excluir os dados da compra do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir os dados da compra do banco de dados", e);
        }
    }

    private DadosDaCompra mapResultSetToDadosDaCompra(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID");
        Timestamp timestampData = rs.getTimestamp("DATA");
        LocalDateTime data = timestampData != null ? timestampData.toLocalDateTime() : null;

        Time timeHora = rs.getTime("HORA");
        LocalTime hora = timeHora != null ? timeHora.toLocalTime() : null;

        int total = rs.getInt("TOTAL");
        String local = rs.getString("LOCAL");
        int idImagens = rs.getInt("ID_IMAGENS");
        int idFeedback = rs.getInt("ID_FEEDBACK");
        int idLogin = rs.getInt("ID_LOGIN");

        ImagensRepository imagensRepository = new ImagensRepository();
        FeedbackRepository feedbackRepository = new FeedbackRepository();
        LoginRepository loginRepository = new LoginRepository();

        Imagens imagens = imagensRepository.getById(idImagens).orElse(null);
        Feedback feedback = feedbackRepository.getById(idFeedback).orElse(null);
        Login login = loginRepository.getById(idLogin).orElse(null);

        return new DadosDaCompra(id, data, hora, total, local, imagens, feedback, login); //
    }

}
