package fiap.ddd.sprint4.entities;

import java.time.LocalDateTime;

public class Feedback extends _BaseEntity{

    private String comentarios;
    private LocalDateTime dataAvaliacao;
    private String autorAvaliacao;
    private int classificacaoServico;

    public Feedback(){

    }

    public Feedback(int id, String comentarios, LocalDateTime dataAvaliacao, String autorAvaliacao, int classificacaoServico) {
        super(id);
        this.comentarios = comentarios;
        this.dataAvaliacao = dataAvaliacao;
        this.autorAvaliacao = autorAvaliacao;
        this.classificacaoServico = classificacaoServico;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public String getAutorAvaliacao() {
        return autorAvaliacao;
    }

    public void setAutorAvaliacao(String autorAvaliacao) {
        this.autorAvaliacao = autorAvaliacao;
    }

    public int getClassificacaoServico() {
        return classificacaoServico;
    }

    public void setClassificacaoServico(int classificacaoServico) {
        this.classificacaoServico = classificacaoServico;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "comentarios='" + comentarios + '\'' +
                ", dataAvaliacao=" + dataAvaliacao +
                ", autorAvaliacao='" + autorAvaliacao + '\'' +
                ", classificacaoServico=" + classificacaoServico +
                "} " + super.toString();
    }
}
