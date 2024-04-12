package fiap.ddd.sprint4.entities;

public class Regiao extends _BaseEntity{

    private String paisNome;

    public  Regiao(){}

    public Regiao(int id, String paisNome) {
        super(id);
        this.paisNome = paisNome;
    }

    public String getPaisNome() {
        return paisNome;
    }

    public void setPaisNome(String paisNome) {
        this.paisNome = paisNome;
    }

    @Override
    public String toString() {
        return "Regiao{" +
                "paisNome='" + paisNome + '\'' +
                "} " + super.toString();
    }
}
