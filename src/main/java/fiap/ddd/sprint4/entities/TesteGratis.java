package fiap.ddd.sprint4.entities;

public class TesteGratis extends _BaseEntity{

    private String nome;
    private String telefone;
    private String email;
    private String senha;
    private String empresa;
    private String idioma;
    private Regiao regiao;
    private Termo termo;


    public TesteGratis(){

    }

    public TesteGratis(int id, String nome, String telefone, String email, String senha, String empresa, String idioma, Regiao regiao, Termo termo) {
        super(id);
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.empresa = empresa;
        this.idioma = idioma;
        this.regiao = regiao;
        this.termo = termo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Regiao getRegiao() {
        return regiao;
    }

    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
    }

    public Termo getTermo() {
        return termo;
    }

    public void setTermo(Termo termo) {
        this.termo = termo;
    }

    @Override
    public String toString() {
        return "TesteGratis{" +
                "nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", empresa='" + empresa + '\'' +
                ", idioma='" + idioma + '\'' +
                ", regiao=" + regiao +
                ", termo=" + termo +
                "} " + super.toString();
    }
}
