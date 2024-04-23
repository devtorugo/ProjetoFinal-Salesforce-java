package fiap.ddd.sprint4.services;

import fiap.ddd.sprint4.entities.DadosDaCompra;
import fiap.ddd.sprint4.repositories.DadosDaCompraRepository;

public class DadosDaCompraService {

    private final DadosDaCompraRepository dadosDaCompraRepository;

    public DadosDaCompraService(DadosDaCompraRepository dadosDaCompraRepository) {
        this.dadosDaCompraRepository = dadosDaCompraRepository;
    }

    public void adicionarDadosDaCompra(DadosDaCompra dadosDaCompra) {
        if (dadosDaCompra.getId() == 0 || dadosDaCompra.getData() == null || dadosDaCompra.getHora() == null
                || dadosDaCompra.getTotal() <= 0 || dadosDaCompra.getLocal() == null || dadosDaCompra.getImagens() == null
                || dadosDaCompra.getFeedback() == null || dadosDaCompra.getLogin() == null) {
            throw new IllegalArgumentException("Os campos dos dados da compra não estão especificados corretamente.");
        }
        dadosDaCompraRepository.create(dadosDaCompra);
    }

    public void atualizarDadosDaCompra(DadosDaCompra dadosDaCompra) {
        if (dadosDaCompra.getId() == 0 || dadosDaCompra.getData() == null || dadosDaCompra.getHora() == null
                || dadosDaCompra.getTotal() <= 0 || dadosDaCompra.getLocal() == null || dadosDaCompra.getImagens() == null
                || dadosDaCompra.getFeedback() == null || dadosDaCompra.getLogin() == null) {
            throw new IllegalArgumentException("Os campos dos dados da compra não estão especificados corretamente.");
        }
        dadosDaCompraRepository.update(dadosDaCompra);
    }

    public void deletarDadosDaCompra(int id) {
        if (dadosDaCompraRepository.getById(id).isEmpty()) {
            throw new IllegalArgumentException("Dados da compra não encontrados");
        }
        dadosDaCompraRepository.delete(id);
    }
}
