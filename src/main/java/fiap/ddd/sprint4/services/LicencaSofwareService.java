package fiap.ddd.sprint4.services;

import fiap.ddd.sprint4.entities.LicencaSoftware;
import fiap.ddd.sprint4.repositories.LicencaSoftwareRepository;

public class LicencaSofwareService {

    private final LicencaSoftwareRepository licencaSoftwareRepository;

    public LicencaSofwareService(LicencaSoftwareRepository licencaSoftwareRepository) {
        this.licencaSoftwareRepository = licencaSoftwareRepository;
    }

    public void adicionarLicencaSoftware(LicencaSoftware licencaSoftware) {
        if (licencaSoftware.getId() == 0 || licencaSoftware.getChaveAtivacao() == null || licencaSoftware.getDataAtivacao() == null
                || licencaSoftware.getLocalAtivacao() == null) {
            throw new IllegalArgumentException("Os campos da licença de software não estão especificados corretamente.");
        }
        licencaSoftwareRepository.create(licencaSoftware);
    }

    public void atualizarLicencaSoftware(LicencaSoftware licencaSoftware) {
        if (licencaSoftware.getId() == 0 || licencaSoftware.getChaveAtivacao() == null || licencaSoftware.getDataAtivacao() == null
                || licencaSoftware.getLocalAtivacao() == null) {
            throw new IllegalArgumentException("Os campos da licença de software não estão especificados corretamente.");
        }
        licencaSoftwareRepository.update(licencaSoftware);
    }

    public void deletarLicencaSoftware(int id) {
        if (licencaSoftwareRepository.getById(id).isEmpty()) {
            throw new IllegalArgumentException("Licença de software não encontrada");
        }
        licencaSoftwareRepository.delete(id);

    }
}
