package fiap.ddd.sprint4.repositories;

import fiap.ddd.sprint4.entities.Regiao;
import fiap.ddd.sprint4.entities.Termo;
import fiap.ddd.sprint4.entities.TesteGratis;
import fiap.ddd.sprint4.infrastructure.OracleDbConfiguration;
import fiap.ddd.sprint4.utils.Log4Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TesteGratisRepository {

    private static final String TB_NAME = "TESTE_GRATIS";
    private static final Log4Logger logger = new Log4Logger(TesteGratisRepository.class);

    public List<TesteGratis> getAll() {
        List<TesteGratis> testesGratis = new ArrayList<>();
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                testesGratis.add(mapResultSetToTesteGratis(rs));
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter todos os testes grátis do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter todos os testes grátis do banco de dados", e);
        }
        return testesGratis;
    }

    public Optional<TesteGratis> getById(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToTesteGratis(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter teste grátis por ID do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter teste grátis por ID do banco de dados", e);
        }
        return Optional.empty();
    }

    public void create(TesteGratis testeGratis) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (ID, NOME, TELEFONE, EMAIL, SENHA, EMPRESA, IDIOMA, ID_PAIS, ID_TERMO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setInt(1,testeGratis.getId());
            stmt.setString(2, testeGratis.getNome());
            stmt.setString(3, testeGratis.getTelefone());
            stmt.setString(4, testeGratis.getEmail());
            stmt.setString(5, testeGratis.getSenha());
            stmt.setString(6, testeGratis.getEmpresa());
            stmt.setString(7, testeGratis.getIdioma());
            stmt.setInt(8, testeGratis.getRegiao().getId());
            stmt.setInt(9, testeGratis.getTermo().getId());
            stmt.executeUpdate();

            logger.info("Teste grátis adicionado ao banco de dados: " + testeGratis.toString());
        } catch (SQLException e) {
            logger.error("Erro ao criar o teste grátis no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao criar o teste grátis no banco de dados", e);
        }
    }

    public void update(TesteGratis testeGratis) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE " + TB_NAME + " SET NOME = ?, TELEFONE = ?, EMAIL = ?, SENHA = ?, EMPRESA = ?, IDIOMA = ?, ID_PAIS = ?, ID_TERMO = ? WHERE ID = ?")) {
            stmt.setString(1, testeGratis.getNome());
            stmt.setString(2, testeGratis.getTelefone());
            stmt.setString(3, testeGratis.getEmail());
            stmt.setString(4, testeGratis.getSenha());
            stmt.setString(5, testeGratis.getEmpresa());
            stmt.setString(6, testeGratis.getIdioma());
            stmt.setInt(7, testeGratis.getRegiao().getId());
            stmt.setInt(8, testeGratis.getTermo().getId());
            stmt.setInt(9, testeGratis.getId());
            stmt.executeUpdate();

            logger.info("Teste grátis atualizado no banco de dados: " + testeGratis.toString());
        } catch (SQLException e) {
            logger.error("Erro ao atualizar o teste grátis no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar o teste grátis no banco de dados", e);
        }
    }

    public void delete(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE ID = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Teste grátis removido do banco de dados. ID: " + id);
        } catch (SQLException e) {
            logger.error("Erro ao excluir o teste grátis do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir o teste grátis do banco de dados", e);
        }
    }

    private TesteGratis mapResultSetToTesteGratis(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID");
        String nome = rs.getString("NOME");
        String telefone = rs.getString("TELEFONE");
        String email = rs.getString("EMAIL");
        String senha = rs.getString("SENHA");
        String empresa = rs.getString("EMPRESA");
        String idioma = rs.getString("IDIOMA");
        int idRegiao = rs.getInt("ID_PAIS");
        int idTermo = rs.getInt("ID_TERMO");

        Regiao regiao = RegiaoRepository.getById(idRegiao).orElse(null);
        Termo termo = TermoRepository.getById(idTermo).orElse(null);
        return new TesteGratis(id, nome, telefone, email, senha, empresa, idioma, regiao, termo);
    }
}
