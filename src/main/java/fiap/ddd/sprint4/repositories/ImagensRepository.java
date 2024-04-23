package fiap.ddd.sprint4.repositories;

import fiap.ddd.sprint4.entities.Imagens;
import fiap.ddd.sprint4.infrastructure.OracleDbConfiguration;
import fiap.ddd.sprint4.utils.Log4Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImagensRepository {

    private static final String TB_NAME = "IMAGENS";
    private static final Log4Logger logger = new Log4Logger(ImagensRepository.class);

    public List<Imagens> getAll() {
        List<Imagens> imagensList = new ArrayList<>();
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                imagensList.add(mapResultSetToImagens(rs));
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter todas as imagens do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter todas as imagens do banco de dados", e);
        }
        return imagensList;
    }

    public Optional<Imagens> getById(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID_IMAGEM = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToImagens(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter imagem por ID do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter imagem por ID do banco de dados", e);
        }
        return Optional.empty();
    }

    public void create(Imagens imagens) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (ID_IMAGEM, DESCRICAO, NOME_ARQUIVO, TAMANHO_ARQUIVO) VALUES (?, ?, ?, ?)")) {
            stmt.setInt(1, imagens.getId());
            stmt.setString(2, imagens.getDescricao());
            stmt.setString(3, imagens.getNomeArquivo());
            stmt.setInt(4, imagens.getTamanhoArquivo());
            stmt.executeUpdate();

            logger.info("Imagem adicionada ao banco de dados: " + imagens.toString());
        } catch (SQLException e) {
            logger.error("Erro ao criar a imagem no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao criar a imagem no banco de dados", e);
        }
    }

    public void update(Imagens imagens) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE " + TB_NAME + " SET DESCRICAO = ?, NOME_ARQUIVO = ?, TAMANHO_ARQUIVO = ? WHERE ID_IMAGEM = ?")) {
            stmt.setString(1, imagens.getDescricao());
            stmt.setString(2, imagens.getNomeArquivo());
            stmt.setInt(3, imagens.getTamanhoArquivo());
            stmt.setInt(4, imagens.getId());
            stmt.executeUpdate();
            logger.info("Imagem atualizada no banco de dados: " + imagens.toString());
        } catch (SQLException e) {
            logger.error("Erro ao atualizar a imagem no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar a imagem no banco de dados", e);
        }
    }

    public void delete(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE ID_IMAGEM = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Imagem removida do banco de dados. ID: " + id);
        } catch (SQLException e) {
            logger.error("Erro ao excluir a imagem do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir a imagem do banco de dados", e);
        }
    }

    private static Imagens mapResultSetToImagens(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID_IMAGEM");
        String descricao = rs.getString("DESCRICAO");
        String nomeArquivo = rs.getString("NOME_ARQUIVO");
        int tamanhoArquivo = rs.getInt("TAMANHO_ARQUIVO");
        return new Imagens(id, descricao, nomeArquivo, tamanhoArquivo);
    }
}
