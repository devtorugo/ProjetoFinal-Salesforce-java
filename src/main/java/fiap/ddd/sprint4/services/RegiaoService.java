package fiap.ddd.sprint4.services;

import fiap.ddd.sprint4.entities.Regiao;
import fiap.ddd.sprint4.repositories.RegiaoRepository;

public class RegiaoService {

    private final RegiaoRepository regiaoRepository;

    public RegiaoService(RegiaoRepository regiaoRepository) {
        this.regiaoRepository = regiaoRepository;
    }

    public void adicionarRegiao(Regiao regiao) {
        if (regiao.getId() == 0 || regiao.getPaisNome() == null || regiao.getPaisNome().isEmpty()) {
            throw new IllegalArgumentException("Os campos da região não estão especificados corretamente.");
        }
        regiaoRepository.create(regiao);
    }

    public void atualizarRegiao(Regiao regiao) {
        if (regiao.getId() == 0 || regiao.getPaisNome() == null || regiao.getPaisNome().isEmpty()) {
            throw new IllegalArgumentException("Os campos da região não estão especificados corretamente.");
        }
        regiaoRepository.update(regiao);
    }

    public void deletarRegiao(int id) {
        if (regiaoRepository.getByID(id).isEmpty()) {
            throw new IllegalArgumentException("Região não encontrada");
        }
        regiaoRepository.delete(id);
    }
}
