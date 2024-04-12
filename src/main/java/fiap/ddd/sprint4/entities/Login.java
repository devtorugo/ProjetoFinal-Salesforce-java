package fiap.ddd.sprint4.entities;

public class Login extends _BaseEntity{

    private String email;
    private String senha;
    private TesteGratis testeGratis;

    public Login(){

    }

    public Login(int id, String email, String senha, TesteGratis testeGratis) {
        super(id);
        this.email = email;
        this.senha = senha;
        this.testeGratis = testeGratis;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TesteGratis getTesteGratis() {
        return testeGratis;
    }

    public void setTesteGratis(TesteGratis testeGratis) {
        this.testeGratis = testeGratis;
    }

    @Override
    public String toString() {
        return "Login{" +
                "email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", testeGratis=" + testeGratis +
                "} " + super.toString();
    }
}
