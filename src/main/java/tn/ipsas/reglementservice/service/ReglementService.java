package tn.ipsas.reglementservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.ipsas.coremodels.exceptions.EntityNotFoundException;
import tn.ipsas.coremodels.models.reglement.Reglement;
import tn.ipsas.reglementservice.data.ReglementRepository;

@Service
public class ReglementService {
    private final ReglementRepository repository;

    public ReglementService(ReglementRepository repository) {
        this.repository = repository;
    }
    public Page<Reglement> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    public Reglement getById(String id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }
    public Reglement save(Reglement reglement) {
        Reglement reglementSaved = repository.save(reglement);
        return reglementSaved;
    }
    public void delete(String id) {
        Reglement reglement = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        repository.deleteById(id);
    }
    public boolean exists(String id) {
        return repository.existsById(id);
    }

}
