package fiap.ddd.sprint4.entities;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DadosDaCompra extends _BaseEntity{

    private LocalDateTime data;
    private LocalTime hora;
    private int total;
    private String local;
    private Imagens imagens;
    private Feedback feedback;
    private Login login;

    public DadosDaCompra(){

    }

    public DadosDaCompra(int id, LocalDateTime data, LocalTime hora, int total, String local, Imagens imagens, Feedback feedback, Login login) {
        super(id);
        this.data = data;
        this.hora = hora;
        this.total = total;
        this.local = local;
        this.imagens = imagens;
        this.feedback = feedback;
        this.login = login;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Imagens getImagens() {
        return imagens;
    }

    public void setImagens(Imagens imagens) {
        this.imagens = imagens;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "DadosDaCompra{" +
                "data=" + data +
                ", hora=" + hora +
                ", total=" + total +
                ", local='" + local + '\'' +
                ", imagens=" + imagens +
                ", feedback=" + feedback +
                ", login=" + login +
                "} " + super.toString();
    }
}
