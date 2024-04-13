package fiap.ddd.sprint4.repositories;

import fiap.ddd.sprint4.entities.Regiao;
import fiap.ddd.sprint4.infrastructure.OracleDbConfiguration;
import fiap.ddd.sprint4.utils.Log4Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegiaoRepository {

    private static final String TB_NAME = "REGIAO";
    private static final Log4Logger logger = new Log4Logger(RegiaoRepository.class);

    public List<Regiao> getAll() {
        List<Regiao> regioes = new ArrayList<>();
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                regioes.add(mapResultSetToRegiao(rs));
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter todas as regiões do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter todas as regiões do banco de dados", e);
        }
        return regioes;
    }

    public Optional<Regiao> getById(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToRegiao(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter região por ID do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter região por ID do banco de dados", e);
        }
        return Optional.empty();
    }

    public void create(Regiao regiao) {
        if (regiao == null) {
            logger.error("Erro ao criar a região no banco de dados: a região está nula.");
            return;
        }

        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (PAIS_NOME) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, regiao.getPaisNome());
            stmt.executeUpdate();

            logger.info("Região adicionada ao banco de dados: " + regiao.toString());
        } catch (SQLException e) {
            logger.error("Erro ao criar a região no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao criar a região no banco de dados", e);
        }
    }

    public void update(Regiao regiao) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE " + TB_NAME + " SET PAIS_NOME = ? WHERE ID = ?")) {
            stmt.setString(1, regiao.getPaisNome());
            stmt.setInt(2, regiao.getId());
            stmt.executeUpdate();
            logger.info("Região atualizada no banco de dados: " + regiao.toString());
        } catch (SQLException e) {
            logger.error("Erro ao atualizar a região no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar a região no banco de dados", e);
        }
    }

    public void delete(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE ID = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Região removida do banco de dados. ID: " + id);
        } catch (SQLException e) {
            logger.error("Erro ao excluir a região do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir a região do banco de dados", e);
        }
    }

    private Regiao mapResultSetToRegiao(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID");
        String paisNome = rs.getString("PAIS_NOME");
        return new Regiao(id, paisNome);
    }
}
