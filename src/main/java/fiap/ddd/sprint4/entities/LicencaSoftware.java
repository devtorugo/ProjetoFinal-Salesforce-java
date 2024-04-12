package fiap.ddd.sprint4.entities;

import java.time.LocalDateTime;

public class LicencaSoftware extends _BaseEntity{

    private String chaveAtivacao;
    private LocalDateTime dataAtivacao;
    private String localAtivacao;
    private InformacoesDoServico informacoesDoServico;

    public LicencaSoftware(){

    }

    public LicencaSoftware(int id, String chaveAtivacao, LocalDateTime dataAtivacao, String localAtivacao, InformacoesDoServico informacoesDoServico) {
        super(id);
        this.chaveAtivacao = chaveAtivacao;
        this.dataAtivacao = dataAtivacao;
        this.localAtivacao = localAtivacao;
        this.informacoesDoServico = informacoesDoServico;
    }

    public String getChaveAtivacao() {
        return chaveAtivacao;
    }

    public void setChaveAtivacao(String chaveAtivacao) {
        this.chaveAtivacao = chaveAtivacao;
    }

    public LocalDateTime getDataAtivacao() {
        return dataAtivacao;
    }

    public void setDataAtivacao(LocalDateTime dataAtivacao) {
        this.dataAtivacao = dataAtivacao;
    }

    public String getLocalAtivacao() {
        return localAtivacao;
    }

    public void setLocalAtivacao(String localAtivacao) {
        this.localAtivacao = localAtivacao;
    }

    public InformacoesDoServico getInformacoesDoServico() {
        return informacoesDoServico;
    }

    public void setInformacoesDoServico(InformacoesDoServico informacoesDoServico) {
        this.informacoesDoServico = informacoesDoServico;
    }

    @Override
    public String toString() {
        return "LicencaSoftware{" +
                "chaveAtivacao='" + chaveAtivacao + '\'' +
                ", dataAtivacao=" + dataAtivacao +
                ", localAtivacao='" + localAtivacao + '\'' +
                ", informacoesDoServico=" + informacoesDoServico +
                "} " + super.toString();
    }
}
