package fiap.ddd.sprint4.services;

import fiap.ddd.sprint4.entities.Termo;
import fiap.ddd.sprint4.repositories.TermoRepository;

public class TermoService {

    private final TermoRepository termoRepository;

    public TermoService(TermoRepository termoRepository) {
        this.termoRepository = termoRepository;
    }

    public void adicionarTermo(Termo termo) {
        if (termo.getId() == 0 || !termo.isAceitarTermo()) {
            throw new IllegalArgumentException("Os campos do termo não estão especificados corretamente.");
        }
        termoRepository.create(termo);
    }

    public void atualizarTermo(Termo termo) {
        if (termo.getId() == 0 || !termo.isAceitarTermo()) {
            throw new IllegalArgumentException("Os campos do termo não estão especificados corretamente.");
        }
        termoRepository.update(termo);
    }

    public void deletarTermo(int id) {
        if (termoRepository.getById(id).isEmpty()) {
            throw new IllegalArgumentException("Termo não encontrado");
        }
        termoRepository.delete(id);
    }
}
