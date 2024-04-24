package fiap.ddd.sprint4.services;

import fiap.ddd.sprint4.entities.InformacoesDoServico;
import fiap.ddd.sprint4.repositories.InformacoesDoServicoRepository;

public class InformacoesDoServicoService {

    private final InformacoesDoServicoRepository informacoesDoServicoRepository;

    public InformacoesDoServicoService(InformacoesDoServicoRepository informacoesDoServicoRepository) {
        this.informacoesDoServicoRepository = informacoesDoServicoRepository;
    }

    public void adicionarInformacoesDoServico(InformacoesDoServico informacoesDoServico) {
        if (informacoesDoServico.getId() == 0 || informacoesDoServico.getNome() == null || informacoesDoServico.getDescricao() == null
                || informacoesDoServico.getCategoria() == null || informacoesDoServico.getPreco() <= 0) {
            throw new IllegalArgumentException("Os campos das informações do serviço não estão especificados corretamente.");
        }
        informacoesDoServicoRepository.create(informacoesDoServico);
    }

    public void atualizarInformacoesDoServico(InformacoesDoServico informacoesDoServico) {
        if (informacoesDoServico.getId() == 0 || informacoesDoServico.getNome() == null || informacoesDoServico.getDescricao() == null
                || informacoesDoServico.getCategoria() == null || informacoesDoServico.getPreco() <= 0) {
            throw new IllegalArgumentException("Os campos das informações do serviço não estão especificados corretamente.");
        }
        informacoesDoServicoRepository.update(informacoesDoServico);
    }

    public void deletarInformacoesDoServico(int id) {
        if (informacoesDoServicoRepository.getById(id).isEmpty()) {
            throw new IllegalArgumentException("Informações do serviço não encontradas");
        }
        informacoesDoServicoRepository.delete(id);
    }
}
