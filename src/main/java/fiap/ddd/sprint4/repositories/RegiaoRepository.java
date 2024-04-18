package fiap.ddd.sprint4.repositories;

import fiap.ddd.sprint4.entities.Regiao;
import fiap.ddd.sprint4.infrastructure.OracleDbConfiguration;
import fiap.ddd.sprint4.utils.Log4Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegiaoRepository {

    private static final String TB_NAME = "REGIAOPAIS";
    private static final Log4Logger logger = new Log4Logger(RegiaoRepository.class);

    public static Optional<Regiao> getByID(int id){
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID_PAIS = ?")) {
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

    public void create(Regiao regiao) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (ID_PAIS, PAIS_NOME) VALUES (?, ?)")) {
            stmt.setInt(1, regiao.getId());
            stmt.setString(2, regiao.getPaisNome());
            stmt.executeUpdate();

            logger.info("Região adicionada ao banco de dados: " + regiao.toString());
        } catch (SQLException e) {
            logger.error("Erro ao criar a região no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao criar a região no banco de dados", e);
        }
    }

    public void update(Regiao regiao) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE " + TB_NAME + " SET PAIS_NOME = ? WHERE ID_PAIS = ?")) {
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
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE ID_PAIS = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Região removida do banco de dados. ID: " + id);
        } catch (SQLException e) {
            logger.error("Erro ao excluir a região do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir a região do banco de dados", e);
        }
    }

    private static Regiao mapResultSetToRegiao(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID_PAIS");
        String paisNome = rs.getString("PAIS_NOME");
        return new Regiao(id, paisNome);
    }
}
