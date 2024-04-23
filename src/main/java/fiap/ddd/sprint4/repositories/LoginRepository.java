package fiap.ddd.sprint4.repositories;

import fiap.ddd.sprint4.entities.Login;
import fiap.ddd.sprint4.entities.TesteGratis;
import fiap.ddd.sprint4.infrastructure.OracleDbConfiguration;
import fiap.ddd.sprint4.utils.Log4Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoginRepository {

    private static final String TB_NAME = "LOGIN";
    private static final Log4Logger logger = new Log4Logger(LoginRepository.class);

    public List<Login> getAll() {
        List<Login> logins = new ArrayList<>();
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                logins.add(mapResultSetToLogin(rs));
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter todos os logins do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter todos os logins do banco de dados", e);
        }
        return logins;
    }

    public Optional<Login> getById(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID_LOGIN = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToLogin(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter login por ID do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter login por ID do banco de dados", e);
        }
        return Optional.empty();
    }

    public void create(Login login) {

        TesteGratisRepository testeGratisRepository = new TesteGratisRepository();
        Optional<TesteGratis> testeGratis = testeGratisRepository.getByEmailAndPassword(login.getEmail(), login.getSenha());
        if (testeGratis.isPresent()) {
            try (Connection conn = OracleDbConfiguration.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (ID_LOGIN, EMAIL, SENHA, ID_TESTE_GRATIS) VALUES (?, ?, ?, ?)")) {
                stmt.setInt(1, login.getId());
                stmt.setString(2, login.getEmail());
                stmt.setString(3, login.getSenha());
                stmt.setInt(4, testeGratis.get().getId());
                stmt.executeUpdate();

                logger.info("Login adicionado ao banco de dados: " + login.toString());
            } catch (SQLException e) {
                logger.error("Erro ao criar o login no banco de dados: " + e.getMessage());
                throw new RuntimeException("Erro ao criar o login no banco de dados", e);
            }
        } else {
            throw new IllegalArgumentException("O e-mail ou a senha do login não correspondem a nenhum registro de teste grátis.");
        }
    }


    public void update(Login login) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE " + TB_NAME + " SET EMAIL = ?, SENHA = ? WHERE ID_LOGIN = ?")) {
            stmt.setString(1, login.getEmail());
            stmt.setString(2, login.getSenha());
            stmt.setInt(3, login.getId());
            stmt.executeUpdate();

            logger.info("Login atualizado no banco de dados: " + login.toString());
        } catch (SQLException e) {
            logger.error("Erro ao atualizar o login no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar o login no banco de dados", e);
        }
    }

    public void delete(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE ID_LOGIN = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Login removido do banco de dados. ID: " + id);
        } catch (SQLException e) {
            logger.error("Erro ao excluir o login do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir o login do banco de dados", e);
        }
    }

    private Login mapResultSetToLogin(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID_LOGIN");
        String email = rs.getString("EMAIL");
        String senha = rs.getString("SENHA");
        int idTesteGratis = rs.getInt("ID_TESTE_GRATIS");

        TesteGratisRepository testeGratisRepository = new TesteGratisRepository();
        TesteGratis testeGratis = testeGratisRepository.getById(idTesteGratis).orElse(null);

        return new Login(id, email, senha, testeGratis);
    }
}
