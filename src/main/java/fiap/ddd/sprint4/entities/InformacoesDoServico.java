package fiap.ddd.sprint4.entities;

public class InformacoesDoServico extends _BaseEntity{
    private String nome;
    private String descricao;
    private String categoria;
    private double preco;
    private DadosDaCompra dadosDaCompra;

    public InformacoesDoServico(){}

    public InformacoesDoServico(int id, String nome, String descricao, String categoria, double preco, DadosDaCompra dadosDaCompra) {
        super(id);
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.dadosDaCompra = dadosDaCompra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public DadosDaCompra getDadosDaCompra() {
        return dadosDaCompra;
    }

    public void setDadosDaCompra(DadosDaCompra dadosDaCompra) {
        this.dadosDaCompra = dadosDaCompra;
    }

    @Override
    public String toString() {
        return "InformacoesDoServico{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", categoria='" + categoria + '\'' +
                ", preco=" + preco +
                ", dadosDaCompra=" + dadosDaCompra +
                "} " + super.toString();
    }
}
