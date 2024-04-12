package fiap.ddd.sprint4.entities;

public class Imagens extends _BaseEntity{

    private String descricao;
    private String nomeArquivo;
    private int tamanhoArquivo;

    public  Imagens (){}

    public Imagens(int id, String descricao, String nomeArquivo, int tamanhoArquivo) {
        super(id);
        this.descricao = descricao;
        this.nomeArquivo = nomeArquivo;
        this.tamanhoArquivo = tamanhoArquivo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public int getTamanhoArquivo() {
        return tamanhoArquivo;
    }

    public void setTamanhoArquivo(int tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }

    @Override
    public String toString() {
        return "Imagens{" +
                "descricao='" + descricao + '\'' +
                ", nomeArquivo='" + nomeArquivo + '\'' +
                ", tamanhoArquivo=" + tamanhoArquivo +
                "} " + super.toString();
    }
}
