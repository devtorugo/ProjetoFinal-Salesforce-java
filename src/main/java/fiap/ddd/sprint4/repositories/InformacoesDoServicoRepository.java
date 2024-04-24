package fiap.ddd.sprint4.repositories;

import fiap.ddd.sprint4.entities.DadosDaCompra;
import fiap.ddd.sprint4.entities.InformacoesDoServico;
import fiap.ddd.sprint4.infrastructure.OracleDbConfiguration;
import fiap.ddd.sprint4.utils.Log4Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InformacoesDoServicoRepository {

    private static final String TB_NAME = "INFORMACOES_DO_SERVICO";
    private static final Log4Logger logger = new Log4Logger(InformacoesDoServicoRepository.class);

    public List<InformacoesDoServico> getAll() {
        List<InformacoesDoServico> informacoesDoServicoList = new ArrayList<>();
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                informacoesDoServicoList.add(mapResultSetToInformacoesDoServico(rs));
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter todas as informações do serviço do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter todas as informações do serviço do banco de dados", e);
        }
        return informacoesDoServicoList;
    }

    public Optional<InformacoesDoServico> getById(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID_SERVICO = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToInformacoesDoServico(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao obter informações do serviço por ID do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao obter informações do serviço por ID do banco de dados", e);
        }
        return Optional.empty();
    }

    public void create(InformacoesDoServico informacoesDoServico) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + TB_NAME + " (ID_SERVICO, NOME, DESCRICAO, CATEGORIA, PRECO) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setInt(1, informacoesDoServico.getId());
            stmt.setString(2, informacoesDoServico.getNome());
            stmt.setString(3, informacoesDoServico.getDescricao());
            stmt.setString(4, informacoesDoServico.getCategoria());
            stmt.setDouble(5, informacoesDoServico.getPreco());
            stmt.executeUpdate();

            logger.info("Informações do serviço adicionadas ao banco de dados: " + informacoesDoServico.toString());
        } catch (SQLException e) {
            logger.error("Erro ao criar as informações do serviço no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao criar as informações do serviço no banco de dados", e);
        }
    }

    public void update(InformacoesDoServico informacoesDoServico) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE " + TB_NAME + " SET NOME = ?, DESCRICAO = ?, CATEGORIA = ?, PRECO = ? WHERE ID_SERVICO = ?")) {
            stmt.setString(1, informacoesDoServico.getNome());
            stmt.setString(2, informacoesDoServico.getDescricao());
            stmt.setString(3, informacoesDoServico.getCategoria());
            stmt.setDouble(4, informacoesDoServico.getPreco());
            stmt.setInt(5, informacoesDoServico.getId());
            stmt.executeUpdate();

            logger.info("Informações do serviço atualizadas no banco de dados: " + informacoesDoServico.toString());
        } catch (SQLException e) {
            logger.error("Erro ao atualizar as informações do serviço no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar as informações do serviço no banco de dados", e);
        }
    }

    public void delete(int id) {
        try (Connection conn = OracleDbConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE ID_SERVICO = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Informações do serviço removidas do banco de dados. ID: " + id);
        } catch (SQLException e) {
            logger.error("Erro ao excluir as informações do serviço do banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir as informações do serviço do banco de dados", e);
        }
    }


    private InformacoesDoServico mapResultSetToInformacoesDoServico(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID_SERVICO");
        String nome = rs.getString("NOME");
        String descricao = rs.getString("DESCRICAO");
        String categoria = rs.getString("CATEGORIA");
        double preco = rs.getDouble("PRECO");
        int idDadosDaCompra = rs.getInt("ID_COMPRA");

        DadosDaCompraRepository dadosDaCompraRepository = new DadosDaCompraRepository();

        DadosDaCompra dadosDaCompra = dadosDaCompraRepository.getById(idDadosDaCompra).orElse(null);



        return new InformacoesDoServico(id, nome, descricao, categoria, preco, dadosDaCompra);

    }
}
