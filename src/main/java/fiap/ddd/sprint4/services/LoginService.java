package fiap.ddd.sprint4.services;

import fiap.ddd.sprint4.entities.Login;
import fiap.ddd.sprint4.repositories.LoginRepository;

import java.util.List;

public class LoginService {

    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public List<Login> getLogins() {
        try {
            return loginRepository.getAll();
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao obter logins: " + e.getMessage());
        }
    }

    public void adicionarLogin(Login login) {
        if (login.getId() == 0) {
            throw new IllegalArgumentException("ID do login não especificado.");
        }
        loginRepository.create(login);
    }

    public void atualizarLogin(Login login) {
        if (loginRepository.getById(login.getId()).isEmpty()) {
            throw new IllegalArgumentException("Login não encontrado");
        }
        loginRepository.update(login);
    }

    public void deletarLogin(int id) {
        if (loginRepository.getById(id).isEmpty()) {
            throw new IllegalArgumentException("Login não encontrado");
        }
        loginRepository.delete(id);
    }
}
