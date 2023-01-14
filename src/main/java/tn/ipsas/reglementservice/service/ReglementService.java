package tn.ipsas.reglementservice.service;

import com.google.common.util.concurrent.AtomicDouble;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import tn.ipsas.coremodels.exceptions.EntityNotFoundException;
import tn.ipsas.coremodels.models.facture.Facture;
import tn.ipsas.coremodels.models.reglement.Reglement;
import tn.ipsas.coremodels.models.reglement.ReglementItem;
import tn.ipsas.reglementservice.data.ReglementRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public List<ReglementItem> byFacture(String factureId) {
        return repository.findAllByItemsFactureId(factureId)
                .stream()
                .flatMap(reglement -> reglement.getItems().stream())
                .filter(reglementItem -> Objects.equals(reglementItem.getFacture().getId(), factureId))
                .collect(Collectors.toList());
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

    public double solde(String clientId) {
        return repository.findAllByClientId(clientId).stream().mapToDouble(reglement -> {
            double used = reglement.getItems().stream().mapToDouble(ReglementItem::getAmount).sum();
            return reglement.getAmount() - used;
        }).sum();
    }

    public void addFActure(Facture source) {
        AtomicDouble amount = new AtomicDouble(source.getTotal());
        repository.findAllByClientId(source.getClient().getId())
                .stream()
                .filter(reglement -> {
                    double used = reglement.getItems().stream().mapToDouble(ReglementItem::getAmount).sum();
                    return used < reglement.getAmount();
                }).forEach(reglement -> {
                    if (amount.get() > 0) {
                        double used = reglement.getItems().stream().mapToDouble(ReglementItem::getAmount).sum();
                        double notUsed = reglement.getAmount() - used;

                        ReglementItem item = new ReglementItem();
                        item.setFacture(source);
                        if (amount.get() > notUsed) {
                            item.setAmount(notUsed);
                            amount.set(amount.get() - notUsed);
                        } else {
                            item.setAmount(amount.get());
                            amount.set(0);
                        }
                        reglement.getItems().add(item);
                        repository.save(reglement);
                    }
                });
    }
}
