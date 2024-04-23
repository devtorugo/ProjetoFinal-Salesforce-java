package fiap.ddd.sprint4.services;

import fiap.ddd.sprint4.entities.Imagens;
import fiap.ddd.sprint4.repositories.ImagensRepository;

public class ImagensService {

    private final ImagensRepository imagensRepository;

    public ImagensService(ImagensRepository imagensRepository) {
        this.imagensRepository = imagensRepository;
    }

    public void adicionarImagem(Imagens imagem) {
        if (imagem.getId() == 0 || imagem.getDescricao() == null || imagem.getDescricao().isEmpty()
                || imagem.getNomeArquivo() == null || imagem.getNomeArquivo().isEmpty()
                || imagem.getTamanhoArquivo() <= 0) {
            throw new IllegalArgumentException("Os campos da imagem não estão especificados corretamente.");
        }
        imagensRepository.create(imagem);
    }

    public void atualizarImagem(Imagens imagem) {
        if (imagem.getId() == 0 || imagem.getDescricao() == null || imagem.getDescricao().isEmpty()
                || imagem.getNomeArquivo() == null || imagem.getNomeArquivo().isEmpty()
                || imagem.getTamanhoArquivo() <= 0) {
            throw new IllegalArgumentException("Os campos da imagem não estão especificados corretamente.");
        }
        imagensRepository.update(imagem);
    }

    public void deletarImagem(int id) {
        if (imagensRepository.getById(id).isEmpty()) {
            throw new IllegalArgumentException("Imagem não encontrada");
        }
        imagensRepository.delete(id);
    }
}
