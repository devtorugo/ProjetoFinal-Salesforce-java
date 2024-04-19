package fiap.ddd.sprint4.services;

import fiap.ddd.sprint4.entities.TesteGratis;
import fiap.ddd.sprint4.repositories.TesteGratisRepository;

import java.util.List;

public class TesteGratisService {

    private final TesteGratisRepository testeGratisRepository;

    public TesteGratisService(TesteGratisRepository testeGratisRepository) {
        this.testeGratisRepository = testeGratisRepository;
    }

    public List<TesteGratis> getTestesGratis() {
        try {
            return testeGratisRepository.getAll();
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao obter testes grátis: " + e.getMessage());
        }
    }

    public void adicionarTesteGratis(TesteGratis testeGratis) {
        if (testeGratis.getId() == 0) {
            throw new IllegalArgumentException("ID do teste grátis não especificado.");
        }
        testeGratisRepository.create(testeGratis);
    }

    public void atualizarTesteGratis(TesteGratis testeGratis) {
        if (testeGratisRepository.getById(testeGratis.getId()).isEmpty()) {
            throw new IllegalArgumentException("Teste grátis não encontrado");
        }
        testeGratisRepository.update(testeGratis);
    }

    public void deletarTesteGratis(int id) {
        if (testeGratisRepository.getById(id).isEmpty()) {
            throw new IllegalArgumentException("Teste grátis não encontrado");
        }
        testeGratisRepository.delete(id);
    }
}
