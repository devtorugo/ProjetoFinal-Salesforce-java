package fiap.ddd.sprint4.repositories;

import fiap.ddd.sprint4.entities.InformacoesDoServico;
import fiap.ddd.sprint4.entities.LicencaSoftware;
import fiap.ddd.sprint4.infrastructure.OracleDbConfiguration;
import fiap.ddd.sprint4.utils.Log4Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LicencaSoftwareRepository {

    private static final String TB_NAME = "LICENCA_SOFTWARE";
    private static final Log4Logger logger = new Log4Logger(LicencaSoftwareRepository.class);

    public List<LicencaSoftware> getAll() {
        List<LicencaSoftware> licencaSoftwareList = new ArrayList<>();
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                licencaSoftwareList.add(mapResultSetToLicencaSoftware(rs));
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter todas as licenças de software do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter todas as licenças de software do banco de dados", e);
        }
        return licencaSoftwareList;
    }

    public Optional<LicencaSoftware> getById(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID_LICENCA = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToLicencaSoftware(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter licença de software por ID do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter licença de software por ID do banco de dados", e);
        }
        return Optional.empty();
    }

    public void create(LicencaSoftware licencaSoftware) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (ID_LICENCA, CHAVE_ATIVACAO, DATA_ATIVACAO, LOCAL_ATIVACAO, ID_SERVICO) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setInt(1, licencaSoftware.getId());
            stmt.setString(2, licencaSoftware.getChaveAtivacao());
            stmt.setObject(3, licencaSoftware.getDataAtivacao());
            stmt.setString(4, licencaSoftware.getLocalAtivacao());
            stmt.setInt(5, licencaSoftware.getInformacoesDoServico().getId());
            stmt.executeUpdate();

            logger.info("Licença de software adicionada ao banco de dados: " + licencaSoftware.toString());
        } catch (SQLException e) {
            logger.error("Erro ao criar a licença de software no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao criar a licença de software no banco de dados", e);
        }
    }

    public void update(LicencaSoftware licencaSoftware) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE " + TB_NAME + " SET CHAVE_ATIVACAO = ?, DATA_ATIVACAO = ?, LOCAL_ATIVACAO = ? WHERE ID_LICENCA = ?")) {
            stmt.setString(1, licencaSoftware.getChaveAtivacao());
            stmt.setObject(2, licencaSoftware.getDataAtivacao());
            stmt.setString(3, licencaSoftware.getLocalAtivacao());
            stmt.setInt(4, licencaSoftware.getId());
            stmt.executeUpdate();

            logger.info("Licença de software atualizada no banco de dados: " + licencaSoftware.toString());
        } catch (SQLException e) {
            logger.error("Erro ao atualizar a licença de software no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar a licença de software no banco de dados", e);
        }
    }

    public void delete(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE ID_LICENCA = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Licença de software removida do banco de dados. ID: " + id);
        } catch (SQLException e) {
            logger.error("Erro ao excluir a licença de software do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir a licença de software do banco de dados", e);
        }
    }


    private LicencaSoftware mapResultSetToLicencaSoftware(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID_LICENCA");
        String chaveAtivacao = rs.getString("CHAVE_ATIVACAO");
        LocalDateTime dataAtivacao = rs.getObject("DATA_ATIVACAO", LocalDateTime.class);
        String localAtivacao = rs.getString("LOCAL_ATIVACAO");
        int idServico = rs.getInt("ID_SERVICO");

        InformacoesDoServicoRepository informacoesDoServicoRepository = new InformacoesDoServicoRepository();

        InformacoesDoServico informacoesDoServico = informacoesDoServicoRepository.getById(idServico).orElse(null);

        return new LicencaSoftware(id, chaveAtivacao, dataAtivacao, localAtivacao, informacoesDoServico);
    }
}
