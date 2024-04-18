package fiap.ddd.sprint4.repositories;

import fiap.ddd.sprint4.entities.Termo;
import fiap.ddd.sprint4.infrastructure.OracleDbConfiguration;
import fiap.ddd.sprint4.utils.Log4Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TermoRepository {

    private static final String TB_NAME = "TERMO";
    private static final Log4Logger logger = new Log4Logger(TermoRepository.class);

    public List<Termo> getAll() {
        List<Termo> termos = new ArrayList<>();
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                termos.add(mapResultSetToTermo(rs));
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter todos os termos do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter todos os termos do banco de dados", e);
        }
        return termos;
    }

    public static Optional<Termo> getById(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID_TERMO = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToTermo(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter termo por ID do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter termo por ID do banco de dados", e);
        }
        return Optional.empty();
    }

    public void create(Termo termo) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (ID_TERMO, ACEITAR_TERMO) VALUES (?, ?)")) {
            stmt.setInt(1, termo.getId());
            stmt.setBoolean(2, termo.isAceitarTermo());
            stmt.executeUpdate();

            logger.info("Termo adicionado ao banco de dados: " + termo.toString());
        } catch (SQLException e) {
            logger.error("Erro ao criar o termo no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao criar o termo no banco de dados", e);
        }
    }

    public void update(Termo termo) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE " + TB_NAME + " SET ACEITAR_TERMO = ? WHERE ID_TERMO = ?")) {
            stmt.setBoolean(1, termo.isAceitarTermo());
            stmt.setInt(2, termo.getId());
            stmt.executeUpdate();
            logger.info("Termo atualizado no banco de dados: " + termo.toString());
        } catch (SQLException e) {
            logger.error("Erro ao atualizar o termo no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar o termo no banco de dados", e);
        }
    }

    public void delete(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE ID_TERMO = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Termo removido do banco de dados. ID: " + id);
        } catch (SQLException e) {
            logger.error("Erro ao excluir o termo do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir o termo do banco de dados", e);
        }
    }

    private static Termo mapResultSetToTermo(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID_TERMO");
        boolean aceitarTermo = rs.getBoolean("ACEITAR_TERMO");
        return new Termo(id, aceitarTermo);
    }
}
